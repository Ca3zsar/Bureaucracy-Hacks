import requests
from bs4 import BeautifulSoup
import re
import codecs
import urllib.request
from urllib.parse import urlparse, unquote
import os
import shutil

url = "https://is.politiaromana.ro/ro/utile/program-de-audiente"
baseUrl = "https://is.politiaromana.ro/ro/utile/documente-eliberari-acte/"
sufixes = ["obtinerea-certificatului-de-cazier-judiciar",
           "modele-documente-privind-activitatea-din-domeniul-arme-explozivi-substante-periculoase",
           "detinerea-si-comercializarea-detectoarelor-de-metale",
           "modele-de-cereri-privind-activitatea-politiei-rutiere",
           "modele-documente-privind-activitatea-politiei-de-investigare-a-criminalitatii-economice",
           "modele-documente-privind-activitatea-politiei-de-ordine-publica"]
folders = ["cazier", "arme_explozivi", "detectoare_metal", "politie_rutiera",
           "frauda_economica", "ordine_publica"]


def programAudieriPolitie():
    page = requests.get(url)

    page.encoding = page.apparent_encoding

    content = BeautifulSoup(page.text, "html.parser")
    programHTML = content.find_all("div", {"class": "stireDesc"})
    
    with open("HTMLFiles/program.html","w",encoding="utf-8") as file:
        file.write(str(programHTML))

    program = re.sub('<.*?>', '', str(programHTML))

    fisier = codecs.open("Content\\program_politie.txt", "w", "utf-8")

    fisier.write(program)
    fisier.close()
    print(program)


def downloadPoliceDocuments():
    cont = 0
    for i in sufixes:
        page = requests.get(baseUrl + i)
        page.encoding = page.apparent_encoding
        downloadableDiv = BeautifulSoup(page.text, "html.parser")
        downloadContent = downloadableDiv.findAll('a', {"class": "numeFisier"})
        
        if os.path.exists(f"Acte\\{folders[cont]}"):
            shutil.rmtree(f"Acte\\{folders[cont]}")
        print(folders[cont])
        os.mkdir(f"Acte\\{folders[cont]}")
        for data in downloadContent:
            response = urllib.request.urlopen(data["href"])

            parsed = urlparse(data["href"])
            fileName = os.path.basename(parsed.path)

            file = open(f"{fileName}", "wb")
            file.write(response.read())
            file.close()

            os.rename(f"{fileName}", f"Acte\\{folders[cont]}\\{fileName}")
            print(f"{fileName}", f"Acte\\{folders[cont]}\\{fileName}")
        cont += 1
    page = requests.get("https://www.politiaromana.ro/ro/utile/documente-eliberari-acte/formulare-tipizate-privind-activitatea-directiei-arme-explozivi-si-substante-periculoase/cereri-formulare-privind-activitatea-directiei-arme-explozivi-si-substante-periculoase")
    page.encoding = page.apparent_encoding
    downloadableDiv = BeautifulSoup(page.text, "html.parser")
    downloadContent = downloadableDiv.findAll('a', {"class": "numeFisier"})

    if os.path.exists("Acte\\arme_explozivi"):
        shutil.rmtree("Acte\\arme_explozivi")
    os.mkdir("Acte\\arme_explozivi")

    for data in downloadContent:
        link = data["href"]
        link = re.sub("ă", '%C4%83', link)
        link = re.sub("ț", "%C8%9B", link)
        link = re.sub("â", "%C3%A2", link)
        link = re.sub("ș", "%C8%99", link)
        link = re.sub("î", "%C3%AE", link)

        print(link, "\n---------------")
        response = urllib.request.urlopen(link)
        parsed = urlparse(link)
        fileName = os.path.basename(parsed.path)
        file = open(f"{fileName}", "wb")
        file.write(response.read())
        file.close()
        os.rename(f"{fileName}", f"Acte\\arme_explozivi\\{fileName}")
        print(f"{fileName}", f"Acte\\arme_explozivi\\{fileName}")


def getContentFromPages():
    if os.path.exists("HTMLFiles"):
        shutil.rmtree("HTMLFiles")
    
    os.mkdir("HTMLFiles")
    
    programAudieriPolitie()
    downloadPoliceDocuments()
    
    contor = 0
    for i in sufixes:
        page = requests.get(baseUrl + i)
        page.encoding = page.apparent_encoding
        soup = BeautifulSoup(page.text, "html.parser")
        content = soup.find_all("div", {"class": "stireDesc"})
        
        with open(f"HTMLFiles/{folders[contor]}.html","w",encoding="utf-8") as file:
            file.write(str(content))
        
        content = re.sub("<.*?>", "", str(content))
        fisier = codecs.open(f"content\\{folders[contor]}.txt", "w", "utf-8")
        fisier.write(content)
        fisier.close()
        contor += 1

def main():
    downloadPoliceDocuments()
    getContentFromPages()
    programAudieriPolitie()
