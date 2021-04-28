import importlib
import check_diff
import os
import threading
import magic
import boto3

import multiprocessing
from queue import Queue


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

    os.environ["FILE_VERSION"] = str(int(os.environ.get("FILE_VERSION"))+1)
    return updated, toReturn


def add_to_S3(files,type):
    S3_BUCKET = os.getenv('S3_BUCKET_NAME')
    version = int(os.environ.get("FILE_VERSION"))
    links = []

    for file in files:
        file_name = file
        s3 = boto3.client('s3')

        file_path_S3 = f"V{version}/{type}/{os.path.splitext(os.path.basename(file_name))[0]}"
        s3.upload_file(file_name,S3_BUCKET,file_path_S3)
        links.append(file_path_S3)

    return links

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