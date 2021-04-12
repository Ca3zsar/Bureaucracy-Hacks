from bs4 import BeautifulSoup
import requests
import os
import re
import urllib.request
import shutil

url = "https://www.cjpiasi.ro/informatiin/"
root = "https://www.cjpiasi.ro/"

director = "Acte"
resources = "Content"
htmlFiles = "HTMLFiles"

def deletingFiles():
    if os.path.exists(director):
        shutil.rmtree(director)
        
    if os.path.exists(htmlFiles):
        shutil.rmtree(htmlFiles)

def makeDirectors():
    os.mkdir(director)
    os.mkdir(htmlFiles)
    
def fillContent(section):
    with open(f"{htmlFiles}/pensii.html","w",encoding="utf-8") as file:
        file.write(str(section))
    

def downloadHrefs(soup):
    for a in soup.select('.listaform a'):
    
        toDownload = a.get('href')
        if not toDownload:
            continue
        
        toDownload = toDownload[2:]
        if toDownload.startswith("/"):
            toDownload = root + toDownload[1:]
        print(toDownload)

        baseName = os.path.basename(toDownload)

        req = requests.get(toDownload, allow_redirects=True)

        open(f"{director}/{baseName}", 'wb').write(req.content)


def main():
    deletingFiles()
    makeDirectors()

    source = requests.get(url).text
    soup = BeautifulSoup(source, 'lxml')
    section = soup.select('.listaform')

    fillContent(section)
    downloadHrefs(soup)

main()
