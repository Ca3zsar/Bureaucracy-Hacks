import requests
from bs4 import BeautifulSoup
import re
import codecs
import urllib.request
from urllib.parse import urlparse, unquote
import os

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

    program = re.sub('<.*?>', '', str(programHTML))

    fisier = codecs.open("program_politie.txt", "w", "utf-8")

    fisier.write(program)
    fisier.close()
    print(program)


def downloadActePolitie():
    cont = 0
    for i in sufixes:
        page = requests.get(baseUrl + i)
        page.encoding = page.apparent_encoding
        downloadableDiv = BeautifulSoup(page.text, "html.parser")
        downloadContent = downloadableDiv.findAll('a', {"class": "numeFisier"})
        for data in downloadContent:
            response = urllib.request.urlopen(data["href"])

            parsed = urlparse(data["href"])
            fileName = os.path.basename(parsed.path)

            file = open(f"{fileName}", "wb")
            file.write(response.read())
            file.close()

            os.rename(f"{fileName}", f"acte\\{folders[cont]}\\{fileName}")
            print(f"{fileName}", f"acte\\{folders[cont]}\\{fileName}")
        cont += 1
    page = requests.get("https://www.politiaromana.ro/ro/utile/documente-eliberari-acte/formulare-tipizate-privind-activitatea-directiei-arme-explozivi-si-substante-periculoase/cereri-formulare-privind-activitatea-directiei-arme-explozivi-si-substante-periculoase")
    page.encoding = page.apparent_encoding
    downloadableDiv = BeautifulSoup(page.text, "html.parser")
    downloadContent = downloadableDiv.findAll('a', {"class": "numeFisier"})

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
        os.rename(f"{fileName}", f"acte\\arme_explozivi\\{fileName}")
        print(f"{fileName}", f"acte\\arme_explozivi\\{fileName}")


def getContentFromPages():
    contor = 0
    for i in sufixes:
        page = requests.get(baseUrl + i)
        page.encoding = page.apparent_encoding
        soup = BeautifulSoup(page.text, "html.parser")
        content = soup.find_all("div", {"class": "stireDesc"})
        content = re.sub("<.*?>", "", str(content))
        fisier = codecs.open(f"content\\{folders[contor]}.txt", "w", "utf-8")
        fisier.write(content)
        fisier.close()
        contor += 1

getContentFromPages()
