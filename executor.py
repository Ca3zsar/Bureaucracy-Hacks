import importlib
import check_diff
import os
import threading

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
    global FILES
    FILES = []
    modules = import_modules()
    
    updated = []
    index = 0
    executeWithThread(modules) 
    for index in range(len(moduleNames)): 
        rootDir = os.path.join(os.path.dirname(os.path.abspath(__file__)),moduleNames[index],'Acte')
        files = [os.path.join(dp, f) for dp, dn, filenames in os.walk(rootDir) for f in filenames]
        
        updated.append(
            {
                "name":moduleNames[index],
                "files":files
            }
        )
        FILES.extend(files)
        print(FILES)

    return updated


def get_files_list():    
    return FILES

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