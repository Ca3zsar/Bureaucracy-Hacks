import importlib
import check_diff
import os

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

def refresh_info():
    pass

def get_differences():
    pass

def get_sites():
    pass

def get_specific_diff(name):
    pass

def _url(path):
    return 'https://check-diff.herokuapp.com' + path

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
