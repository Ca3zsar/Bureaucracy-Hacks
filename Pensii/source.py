from bs4 import BeautifulSoup
import requests
import os
import re
import urllib.request
import shutil

url = "https://www.cjpiasi.ro/informatiin/"
root = "https://www.cjpiasi.ro/"

director = "./resources"

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

def downloadHrefs(soup):
    for a in soup.select('.listaform a'):
    
        toDownload = a['href']
        if toDownload.startswith("/"):
            toDownload = root + toDownload
        print(toDownload)

        baseName = os.path.basename(toDownload)

        req = requests.get(toDownload, allow_redirects=True)

        open(f"{director}/{baseName}", 'wb').write(req.content)


def main():
    if os.path.isdir(director):
        deletingFiles()
    makeDirectors()

    source = requests.get(url).text
    soup = BeautifulSoup(source, 'lxml')
    #section = soup.find_all("div", {"class": "listaform"})
    section = soup.select('.listaform')

    print(section)

    fillContent(section)
    # downloadHrefs(soup)

main()
