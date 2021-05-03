import importlib
import check_diff
import os
import threading
import magic
import boto3
from botocore.config import Config
import botocore
import database_handler


import multiprocessing
from queue import Queue

VERSION = 0

moduleNames = ["ANAF","CNAS","DGASPC","DAC","DLEP","IPJ","Pasapoarte","Pensii",
               "Primarie"]
FILES = []

def import_modules():
    modules = []
    for name in moduleNames:
        try:
            modules.append(importlib.import_module(f"{name}.source"))
        except:
            print(f"Can't import module {name}!")
    
    return modules


def add_to_database(params):
    connection = database_handler.get_connection()
    
    statement = "INSERT INTO files VALUES(%s, %s)"
    cursor = connection.cursor()
    
    cursor.executemany(statement,(params.keys(),params.valies())
    connection.commit()
    
    cursor.close()
    connection.close()

def executeWithThread(modules):

   q = multiprocessing.Manager().JoinableQueue()
   pool = multiprocessing.Pool(len(moduleNames)) 


   for module in modules:
      print(f"Executing module : {module.__name__}")
      pool.apply_async( module.main ) 
     
   pool.close()
   pool.join()
    
    
def refresh_info():
    toReturn = []
    modules = import_modules()
    
    updated = []
    index = 0
    executeWithThread(modules) 
    
    for index in range(len(moduleNames)): 
        rootDir = os.path.join(os.path.dirname(os.path.abspath(__file__)),moduleNames[index],'HTMLFiles')
        files = [os.path.join(dp, f) for dp, dn, filenames in os.walk(rootDir) for f in filenames]
        
        links = add_to_S3(files,"HTMLFiles")
        for i in range(len(links)):
            updated.append({"name":moduleNames[index],os.path.basename(files[i]):links[i]})
        
        secondRootDir = os.path.join(os.path.dirname(os.path.abspath(__file__)),moduleNames[index],'Acte')
        secondFiles = [os.path.join(dp, f) for dp, dn, filenames in os.walk(secondRootDir) for f in filenames]
        
        secondLinks = add_to_S3(secondFiles,"Acte")
        for i in range(len(secondLinks)):
            toReturn.append({os.path.basename(secondFiles[i]):secondLinks[i]})

        add_to_database(toReturn)

    with open("version.log",'w') as file:
        file.write(str(VERSION+1))
    
    S3_BUCKET = os.getenv('S3_BUCKET_NAME')
    s3 = boto3.client('s3')
    s3.upload_file("version.log",S3_BUCKET,'version.log')
    
    return updated, toReturn


def add_to_S3(files,type):
    global VERSION
    
    S3_BUCKET = os.getenv('S3_BUCKET_NAME')
    config = Config(signature_version=botocore.UNSIGNED)
    config.signature_version = botocore.UNSIGNED
    
    s3_link = boto3.client('s3',config=config)
    
    s3 = boto3.client('s3')

    s3.download_file(S3_BUCKET, 'version.log','version.log')
    with open('version.log','r') as f:
        VERSION = int(f.read())
    
    links = []

    for file in files:
        file_name = file
        
        file_path_S3 = f"V{VERSION}/{type}/{os.path.basename(file_name)}"
        s3.upload_file(file_name,S3_BUCKET,file_path_S3)
        os.remove(file)
        links.append(s3_link.generate_presigned_url('get_object', ExpiresIn=0, Params={'Bucket': S3_BUCKET, 'Key': os.path.basename(file_path_S3)}))

    return links


def get_files_list(url):
    global VERSION
    
    S3_BUCKET = os.getenv('S3_BUCKET_NAME')
    config = Config(signature_version=botocore.UNSIGNED)
    config.signature_version = botocore.UNSIGNED
    
    s3 = boto3.client('s3',config=config)

    s3.download_file(S3_BUCKET, 'version.log','version.log')
    with open('version.log','r') as f:
        VERSION = int(f.read())
    
    files_list = dict()
    
    s3_resource = boto3.resource('s3')
    bucket = s3_resource.Bucket(S3_BUCKET)
    s3_folder = f"V{VERSION-1}/Acte"
    
    for obj in bucket.objects.filter(Prefix=s3_folder):
        if obj.key[-1] == '/':
            continue
        files_list[obj.key] = s3.generate_presigned_url('get_object', ExpiresIn=0, Params={'Bucket': S3_BUCKET, 'Key': obj.key})
    
    return files_list

def main():
    modules = import_modules()    
    for module in modules:
        try:
            print(f"Executing module : {module.__name__}")
            module.main()
            
            path = os.path.dirname(module.__file__)
            
            if os.path.exists(f"{path}/Old"):
                check_diff.compareFiles(path)
            else:
                os.rename(f"{path}/HTMLFiles",f"{path}/Old")
            
        except:
            print(f"Can't execute module : {module.__name__}")

if __name__ == "__main__":
    main()