import os
import filecmp

HTMLFiles = "/HTMLFiles"
OLD = "/Old"

def differentFiles(oldFiles, newFiles, oldPath,newPath):
    numberOfOldFiles = len(oldFiles)
    numberOfNewFiles = len(newFiles)
    
    if numberOfOldFiles > numberOfNewFiles:
        with open("differences.log","a") as fileLogs:
            print("Some pages were removed!")
            fileLogs.write(f"{oldPath}. Some pages were removed!\n")
            
            differences = set(oldFiles) - set(newFiles)
            for file in differences:
                print(file)
                fileLogs.write(f"{oldPath}/{file}\n")
                
    elif numberOfOldFiles < numberOfNewFiles:
        with open("differences.log","a") as fileLogs:
            print("New pages were added!")
            fileLogs.write(f"{newPath}. New pages were added!\n")
            
            differences = set(newFiles) - set(oldFiles)
            for file in differences:
                print(file)
                fileLogs.write(f"{newPath}/{file}\n")
    else:
        with open("differences.log","a") as fileLogs:
            deletedFiles = set(oldFiles) - set(newFiles)
            
            print("Files that were deleted : ")
            fileLogs.write(f"{oldPath}. File that were deleted : \n")
            for file in deletedFiles:
                fileLogs.write(f"{oldPath}/{file}\n")
                print(file)
                
            addedFiles = set(newFiles) - set(oldFiles)
            
            print("Files that were added : ")
            fileLogs.write(f"{newPath}. Files that were added : \n")
            for file in addedFiles:
                fileLogs.write(f"{newPath}/{file}\n")
                print(file)


def checkIfContentDifferent(oldPath, newPath):
    status = filecmp.dircmp(oldPath, newPath)
    # status.report()
    
    with open("differences.log","a") as fileLogs:
        differences = status.diff_files
        if len(differences) == 0:
            print("No difference!")
            fileLogs.write(f"{newPath}. No difference!\n")
            
        else:
            for name in differences:
                print(f"{name} file is different!")
                fileLogs.write(f"{newPath}/{name} file is different!\n")


def compareFiles(path):
    if not os.path.exists("differences.log"):
        with open("differences.log","w") as file:
            pass
    
    newFilesPath = path + HTMLFiles
    oldFilesPath = path + OLD
    
    oldFiles = os.listdir(oldFilesPath)
    newFiles = os.listdir(newFilesPath)
    
    if set(oldFiles).symmetric_difference(set(newFiles)):
        differentFiles(oldFiles, newFiles,oldFilesPath,newFilesPath)
    else:
        checkIfContentDifferent(oldFilesPath,newFilesPath)
        
    with open("differences.log","a") as fileLogs:
        fileLogs.write("-----------\n")
        