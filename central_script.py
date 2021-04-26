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

   pool.apply_async(module.main)

   for module in modules:
      pool.apply_async( module.main ) 
     
   pool.close()
   pool.join()
    


def refresh_info():
    modules = import_modules()
    
    updated = []
    index = 0
    executeWithThread(modules) 
    for index in range(1,len(moduleNames)): 
        rootDir = os.path.join(moduleNames[index],'HTMLFiles')
        files = [os.path.join(dp, f) for dp, dn, filenames in os.walk(rootDir) for f in filenames]
        
        updated.append(
            {
                "name":moduleNames[index],
                "files":files
            }
        )
        
        index += 1

    return updated




# def get_differences():
#     pass

# def get_sites():
#     pass

# def get_specific_diff(name):
#     pass

# def _url(path):
#     return 'https://check-diff.herokuapp.com' + path

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

# if __name__ == "__main__":
#     main()
