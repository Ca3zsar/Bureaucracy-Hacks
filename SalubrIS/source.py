import requests
from bs4 import BeautifulSoup
import re
import codecs
import urllib.request
from urllib.parse import urlparse, unquote
import os
import shutil

baseURL = "https://salubris.ro/"
follows = ["servicii/"]
sufixes = ["colectarea-transportul-si-depozitarea-deseurilor-rezultate-din-constructii-si-demolari",
           "eliberare-avize-de-principiu",
           "acte-necesare-intocmirii-contractului-de-servicii-salubritate"]

paperLinks = ["https://salubris.ro/wp-content/uploads/2020/05/aviz-principiu.pdf",
              "https://salubris.ro/wp-content/uploads/2020/02/CONTRACT-persoane-fizice.pdf",
              "https://salubris.ro/wp-content/uploads/2020/02/CONTRACT-asociatii.pdf",
              "https://salubris.ro/wp-content/uploads/2020/02/CONTRACT-agenti-economici.institutii.pdf",
              "https://salubris.ro/wp-content/uploads/2021/04/Cerere-Colectare-Transport-%C8%99i-Depozitare-Deseuri-inerte.pdf",
              "https://salubris.ro/wp-content/uploads/2021/04/Cerere-Depozitare-de%C8%99euri-inerte.pdf",
              "https://salubris.ro/wp-content/uploads/2021/04/Formular-de-%C3%AEnc%C4%83rcare-%E2%80%93-desc%C4%83rcare-de%C5%9Feuri-nepericuloase.pdf"
              ]

filesName = ["Aviz-principiu",
             "CONTRACT-persoane-fizice",
             "CONTRACT-asociatii",
             "CONTRACT-agenti-economici-institutii",
             "Cerere-Colectare-Transport-si-Depozitare-Deseuri-inerte",
             "Cerere-Depozitare-deseuri-inerte",
             "Formular-de-incarcare-descarcare-deseuri-nepericuloase"
             ]

# os.path.dirname(__file__)
director = f"Content"
HTMLFiles = f"HTMLFiles"
acte = f"Acte"


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


def getContent():
    for i in follows:
        for j in sufixes:
            page = requests.get(baseURL + i + j)
            content = BeautifulSoup(page.text, "html.parser")
            [x.extract() for x in content.findAll('script')]

            htmlFile = re.sub("(\n\n)*", "", str(content))
            with open(f"{HTMLFiles}/{j}.html", "w", encoding="utf-8") as file:
                file.write(str(htmlFile))

            textContent = content.find_all("div", {"class": "col-md-12"})
            textContent = re.sub("<.*?>", "", str(textContent))
            textContent = re.sub("(\n\n)*", "", str(textContent))
            textContent = textContent.replace('[', '')
            textContent = textContent.replace(']', '')
            with open(f"{director}/{j}.txt", "w", encoding="utf-8") as file:
                file.write(textContent)


def downloadForms():
    index = 0
    for i in paperLinks:
        response = urllib.request.urlopen(i)
        fileName = f"{filesName[index]}.pdf"
        index += 1

        file = open(f"{fileName}", "wb")
        file.write(response.read())
        file.close()

        os.rename(f"{fileName}", f"{acte}\\{fileName}")


def main():
    deletingFiles()
    makeDirectors()
    getContent()
    downloadForms()


main()
