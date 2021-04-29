import os
import filecmp
import boto3

VERSION = 0

def checkIfContentDifferent(oldPath, newPath):
    status = filecmp.dircmp(oldPath, newPath)
    differences = status.diff_files
    
    answer = dict()
    if len(differences) == 0:
        answer["differences"] = []
    else:
        diff = []
        for name in differences:
            diff.append(name)
        answer["differences"] = diff[:]
    
    return answer


def differentFiles(oldFiles, newFiles, oldPath,newPath):

    answer = dict()  
    files = []
    deletedFiles = set(oldFiles) - set(newFiles)
    
    for file in deletedFiles:
        files.append(file)
    answer["deleted"] = files[:]
    
    addedFiles = set(newFiles) - set(oldFiles)
    
    files = []
    for file in addedFiles:
        files.append(file)
    answer["added"] = files[:]

    answer.update(checkIfContentDifferent(oldPath,newPath))
    return answer

def downloadFiles(path,s3_folder):
    S3_BUCKET = os.getenv('S3_BUCKET_NAME')
    if not os.path.exists(path):
        os.mkdir(path)
    
    s3_resource = boto3.resource('s3')
    bucket = s3_resource.Bucket(S3_BUCKET)
    for obj in bucket.objects.filter(Prefix=s3_folder):
        if obj.key[-1] == '/':
            continue
        bucket.download_file(obj.key, f"{path}/{os.path.basename(obj.key)}")


def compareFiles(path):
    #Get the current version
    global VERSION
    
    S3_BUCKET = os.getenv('S3_BUCKET_NAME')
    s3 = boto3.client('s3')

    s3.download_file(S3_BUCKET, 'version.log','version.log')
    with open('version.log','r') as f:
        VERSION = int(f.read())
    
    
    if VERSION <= 1:
        return {"differences":[]}
    
    downloadFiles("Old",f"V{VERSION-1}/HTMLFiles")
    downloadFiles("New",f"V{VERSION}/HTMLFiles")
    
    oldFiles = os.listdir("Old")
    newFiles = os.listdir("New")
    
    if set(oldFiles).symmetric_difference(set(newFiles)):
        return differentFiles(oldFiles, newFiles,"Old","New")
    else:
        return checkIfContentDifferent("Old","New")
    