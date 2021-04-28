import importlib
import check_diff
import os
import threading

import multiprocessing
from queue import Queue


moduleNames = ["ANAF","CNAS","DGASPC","DAC","DLEP","IPJ","Pasapoarte","Pensii",
               "Primarie"]


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
    modules = import_modules()
    
    updated = []
    index = 0
    executeWithThread(modules) 
    for index in range(len(moduleNames)): 
        rootDir = os.path.join(moduleNames[index],'HTMLFiles')
        files = [os.path.join(dp, f) for dp, dn, filenames in os.walk(rootDir) for f in filenames]
        
        updated.append(
            {
                "name":moduleNames[index],
                "files":files
            }
        )
        

    return updated


def get_files_list():
    updated = []
        
    for index in range(len(moduleNames)): 
        rootDir = os.path.join(moduleNames[index],'Acte')
        files = [os.path.join(dp, f) for dp, dn, filenames in os.walk(rootDir) for f in filenames]
        
        updated.extend(files)
    
    return updated

# def main():
    
#     for module in modules:
#         try:
#             print(f"Executing module : {module.__name__}")
#             module.main()
            
#             path = os.path.dirname(module.__file__)
            
#             if os.path.exists(f"{path}/Old"):
#                 check_diff.compareFiles(path)
#             else:
#                 os.rename(f"{path}/HTMLFiles",f"{path}/Old")
            
#         except:
#             print(f"Can't execute module : {module.__name__}")

