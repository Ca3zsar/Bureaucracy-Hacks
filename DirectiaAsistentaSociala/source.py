from bs4 import BeautifulSoup
import requests
import os
import re
import urllib.request

root = "https://www.dac-iasi.ro"
sufixes = ["alocatii", "autoritate", "serviciul-social", "alte-servicii"]

director = "./resources"



os.makedirs(director)
for sufix in sufixes:
    os.makedirs(director + "/" + sufix)


for sufix in sufixes:
    url = root + "/" + sufix
    source = requests.get(url).text
    soup = BeautifulSoup(source, 'lxml')
    section = soup.find(class_="art-article")


    #saving into a file
    fileName = sufix + ".html"
    path = director + "/" + fileName
    f = open(path, "w", encoding='utf-8')

    matches = ["h1", "h3", "p"]

    for line in section:
        txt = str(line)
        if any(x in txt for x in matches):
            f.write(line)

    f.close()
    
    for a in soup.select('.art-article p a'):

        toDownload = a['href']
        if toDownload.startswith("/"):
            toDownload = root + toDownload
        print(toDownload)

        baseName = os.path.basename(toDownload)

        urllib.request.urlretrieve(toDownload, f"{director}/{sufix}/{baseName}")

    print("\n###############\n")




# https://www.dac-iasi.ro/file/Cerere_indemnizatie_model_2020_DAS_Iasi.pdf  