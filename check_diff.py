import os
import filecmp
import boto3

VERSION = 0

def checkIfContentDifferent(oldPath, newPath):
    status = filecmp.dircmp(oldPath, newPath)
    differences = status.diff_files
    
    
    
    answer = {}
    if len(differences) == 0:
        answer["differences"] = []
    else:
        diff = []
        for name in differences:
            diff.append(name)
        answer["differences"] = diff
    
    return answer


def differentFiles(oldFiles, newFiles, oldPath,newPath):

    answer = {}    
    files = []
    deletedFiles = set(oldFiles) - set(newFiles)
    
    for file in deletedFiles:
        files.append(files)
    answer["deleted"] = files
    
    addedFiles = set(newFiles) - set(oldFiles)
    
    files = []
    for file in addedFiles:
        files.append(file)
    answer["added"] = files

    answer.update(checkIfContentDifferent(oldPath,newPath))


def downloadFiles(path,s3_folder):
    S3_BUCKET = os.getenv('S3_BUCKET_NAME')
    s3 = boto3.client('s3')
    os.mkdir(path)
    
    for obj in s3.objects.filter(Prefix=s3_folder):
        if obj.key[-1] == '/':
            continue
        s3.download_file(obj.key, path)


def compareFiles(path):
    #Get the current version
    global VERSION
    
    S3_BUCKET = os.getenv('S3_BUCKET_NAME')
    s3 = boto3.client('s3')

    s3.download_file(S3_BUCKET, 'version.log','version.log')
    with open('version.log','r') as f:
        VERSION = int(f.read())
    
    
    if VERSION <= 1:
        return {"differences:[]"}
    
    newFilesPath = f"{path}/{VERSION}/"
    oldFilesPath = f"{path}/{VERSION-1}/"
    
    downloadFiles("Old",f"{VERSION-1}/HTMLFiles")
    downloadFiles("New",f"{VERSION}/HTMLFiles")
    
    oldFiles = os.listdir("Old")
    newFiles = os.listdir("New")
    
    if set(oldFiles).symmetric_difference(set(newFiles)):
        return differentFiles(oldFiles, newFiles,"Old","New")
    else:
        return checkIfContentDifferent("Old","New")
    