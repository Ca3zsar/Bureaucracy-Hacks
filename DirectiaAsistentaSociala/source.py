from bs4 import BeautifulSoup
import requests
import os
import re
import urllib.request
import shutil # for deleting dirs

root = "https://www.dac-iasi.ro"
sufixes = ["alocatii", "autoritate", "serviciul-social", "alte-servicii"]

director = "./resources"

def deletingFiles():
    shutil.rmtree(director)
    print("Files deleted")


def makeDirectors():
    os.makedirs(director)
    for sufix in sufixes:
        os.makedirs(director + "/" + sufix)
    print("Files created..")


def fillContent(sufix, section):
    fileName = sufix + ".html"
    path = director + "/" + fileName
    f = open(path, "w", encoding='utf-8')

    matches = ["h1", "h3", "p"]

    for line in section:
        txt = str(line)
        if any(x in txt for x in matches):
            f.write(txt)

    f.close()

def downloadHrefs(sufix, soup):
    for a in soup.select('.art-article p a'):

        toDownload = a['href']
        if toDownload.startswith("/"):
            toDownload = root + toDownload
        print(toDownload)

        baseName = os.path.basename(toDownload)

        req = requests.get(toDownload, allow_redirects=True)

        open(f"{director}/{sufix}/{baseName}", 'wb').write(req.content)



def main():
    if os.path.isdir(director):
        deletingFiles()
    makeDirectors()

    for sufix in sufixes:
        url = root + "/" + sufix
        source = requests.get(url).text
        soup = BeautifulSoup(source, 'lxml')
        section = soup.find(class_="art-article")

        fillContent(sufix, section)
        downloadHrefs(sufix, soup)



main()


# https://www.dac-iasi.ro/file/Cerere_indemnizatie_model_2020_DAS_Iasi.pdf  