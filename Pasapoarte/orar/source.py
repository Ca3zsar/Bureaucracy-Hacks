from bs4 import BeautifulSoup
import requests
import os
import re
import urllib.request
import shutil 

director = "./resources"
url = "https://pasapoarte.mai.gov.ro/serviciul-public-comunitar-de-pasapoarte-iasi/"

def deletingFiles():
    shutil.rmtree(director)
    print("Files deleted")

def makeDirectors():
    os.makedirs(director)
    print("Files created..")

def fillContent(section):
    path = director + "/file.html"
    f = open(path, "w", encoding='utf-8')
    f.write(str(section))
    f.close()


def main():
    if os.path.isdir(director):
        deletingFiles()
    makeDirectors()


    source = requests.get(url).text
    soup = BeautifulSoup(source, 'lxml')
    
    section = soup.select("table")
    # print(section)

    path = director + "/data.txt"
    f = open(path, "w", encoding='utf-8')
    

    for line in section:
        txt = str(line)
        
        txt = re.sub('\<(.|\n)*?\>','', txt)
        txt = re.sub('^\s*','', txt)
        f.write(txt)
        f.write('\n')

    f.close()


main()

