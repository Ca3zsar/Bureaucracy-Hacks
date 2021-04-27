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

director = f"{os.path.dirname(__file__)}/Content"
HTMLFiles = f"{os.path.dirname(__file__)}/HTMLFiles"
acte = f"{os.path.dirname(__file__)}/Acte"

def deletingFiles():
    if os.path.exists(acte):
        shutil.rmtree(acte)
    if os.path.exists(director):
        shutil.rmtree(director)
    if os.path.exists(HTMLFiles):
        shutil.rmtree(HTMLFiles)

def makeDirectors():
    os.mkdir(HTMLFiles)
    os.mkdir(director)
    os.mkdir(acte)

def programAudieriPolitie():
    page = requests.get(url)

    page.encoding = page.apparent_encoding

    content = BeautifulSoup(page.text, "html.parser")
    programHTML = content.find_all("div", {"class": "stireDesc"})
    
    with open(f"{HTMLFiles}/program.html","w",encoding="utf-8") as file:
        file.write(str(programHTML))

    program = re.sub('<.*?>', '', str(programHTML))

    fisier = codecs.open(f"{director}\\program_politie.txt", "w", "utf-8")

    fisier.write(program)
    fisier.close()


def downloadPoliceDocuments():
    cont = 0
    for i in sufixes:
        page = requests.get(baseUrl + i)
        page.encoding = page.apparent_encoding
        downloadableDiv = BeautifulSoup(page.text, "html.parser")
        downloadContent = downloadableDiv.findAll('a', {"class": "numeFisier"})
            
        index = 1
        for data in downloadContent:
            response = urllib.request.urlopen(data["href"])

            parsed = urlparse(data["href"])
            something, extension = os.path.splitext(parsed.path)
            fileName = f"{folders[cont]}_Anexa{index}{extension}"
            index += 1
            
            file = open(f"{fileName}", "wb")
            file.write(response.read())
            file.close()

            os.rename(f"{fileName}", f"{acte}\\{fileName}")
        cont += 1
        
    page = requests.get("https://www.politiaromana.ro/ro/utile/documente-eliberari-acte/formulare-tipizate-privind-activitatea-directiei-arme-explozivi-si-substante-periculoase/cereri-formulare-privind-activitatea-directiei-arme-explozivi-si-substante-periculoase")
    page.encoding = page.apparent_encoding
    downloadableDiv = BeautifulSoup(page.text, "html.parser")
    downloadContent = downloadableDiv.findAll('a', {"class": "numeFisier"})

    index = 1
    for data in downloadContent:
        link = data["href"]
        link = re.sub("ă", '%C4%83', link)
        link = re.sub("ț", "%C8%9B", link)
        link = re.sub("â", "%C3%A2", link)
        link = re.sub("ș", "%C8%99", link)
        link = re.sub("î", "%C3%AE", link)

        response = urllib.request.urlopen(link)
        parsed = urlparse(link)
        something, extension = os.path.splitext(parsed.path)
        
        fileName = f"arme_explozivi_Anexa{index}{extension}"
        index += 1
            
        file = open(f"{fileName}", "wb")
        file.write(response.read())
        file.close()
        os.rename(f"{fileName}", f"{acte}\\{fileName}")


def getContentFromPages():
    
    programAudieriPolitie()
    downloadPoliceDocuments()
    
    contor = 0
    for i in sufixes:
        page = requests.get(baseUrl + i)
        page.encoding = page.apparent_encoding
        soup = BeautifulSoup(page.text, "html.parser")
        content = soup.find_all("div", {"class": "stireDesc"})
        
        with open(f"{HTMLFiles}/{folders[contor]}.html","w",encoding="utf-8") as file:
            file.write(str(content))
        
        content = re.sub("<.*?>", "", str(content))
        fisier = codecs.open(f"{director}\\{folders[contor]}.txt", "w", "utf-8")
        fisier.write(content)
        fisier.close()
        contor += 1

def main():
    deletingFiles()
    makeDirectors()
    
    downloadPoliceDocuments()
    getContentFromPages()
    programAudieriPolitie()

if __name__=="__main__":
    main()