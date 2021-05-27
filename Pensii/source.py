from bs4 import BeautifulSoup
import requests
import os
import re
import urllib.request
import shutil

url = "https://www.cjpiasi.ro/informatiin/"
root = "https://www.cjpiasi.ro/"

director = f"{os.path.dirname(__file__)}/Content"
HTMLFiles = f"{os.path.dirname(__file__)}/HTMLFiles"
acte = f"{os.path.dirname(__file__)}/Acte"

def deletingFiles():
    if os.path.exists(director):
        shutil.rmtree(director)
        
    if os.path.exists(HTMLFiles):
        shutil.rmtree(HTMLFiles)

    if os.path.exists(acte):
        shutil.rmtree(acte)

def makeDirectors():
    os.mkdir(director)
    os.mkdir(HTMLFiles)
    os.mkdir(acte)
    
def fillContent(section):
    with open(f"{HTMLFiles}/pensii.html","w",encoding="utf-8") as file:
        file.write(str(section))
    

def downloadHrefs(soup):
    for a in soup.select('.listaform a'):
    
        toDownload = a.get('href')
        if not toDownload:
            continue
        
        toDownload = toDownload[2:]
        if toDownload.startswith("/"):
            toDownload = root + toDownload[1:]

        baseName = os.path.basename(toDownload)

        req = requests.get(toDownload, allow_redirects=True)

        open(f"{acte}/Casa Județeană de Pensii Iași_{baseName}", 'wb').write(req.content)


def main():
    deletingFiles()
    makeDirectors()

    source = requests.get(url).text
    soup = BeautifulSoup(source, 'lxml')
    section = soup.select('.listaform')

    fillContent(section)
    downloadHrefs(soup)

if __name__=="__main__":
    main()
