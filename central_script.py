import importlib

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

def main():
    modules = import_modules()
    for module in modules:
        try:
            print(f"Executing module : {module.__name__}")
            module.main()
        except:
            print(f"Can't execute module : {module.__name__}")

if __name__ == "__main__":
    main()
