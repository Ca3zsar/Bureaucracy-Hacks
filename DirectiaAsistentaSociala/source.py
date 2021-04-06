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

    # container = re.sub('<.*?>','', str(section))
    # container = re.sub('\s\s\s\s\s\s','\n',container)
    # f.write(container)

    matches = ["h1", "h3", "p"]

    for line in section:
        txt = str(line)
        if any(x in txt for x in matches):
            txt = re.sub('\<img.*?/>','\n',txt)
            txt = re.sub('\<img.*?/>','\n',txt)
            txt = re.sub('\<a.*?\>(.|\n)*?\<\/a\>','',txt)
            txt = re.sub('\<script.*?\>(.|\n)*?\<\/script\>','',txt)
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



def getResources():
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



def getSchedule():
    URL='https://www.dac-iasi.ro/contact' 
    page=requests.get(URL) 
    soup= BeautifulSoup(page.content,'html.parser') 
    searchFor=["Luni","Marti", "Miercuri", "Joi", "Vineri", "Adresa :" ] 
    orare = soup.select("div[class=art-article] > p")
    title= soup.select("div[class=art-article] > h4") 

    Institutie=[]
    for titlu in title: 
        txtTitle = str(titlu)
        txtTitle = re.sub("<h4[^>]>", "", txtTitle)
        txtTitle = re.sub("</?h4[^>]>", "", txtTitle)
        txtTitle = re.sub("<strong[^>]>", "", txtTitle)
        txtTitle = re.sub("</?strong[^>]>", "", txtTitle)
        txtTitle = re.sub("<.*?>", "", txtTitle).strip().splitlines()
        Institutie+=txtTitle
        #print(txtTitle)
    
    program=[]
    for orar in orare:
        txtProgram = str(orar)
        if any(x in txtProgram for x in searchFor):
            txtProgram = re.sub("<p[^>]>", "", txtProgram)
            txtProgram = re.sub("</?p[^>]>", "", txtProgram)
            txtProgram = re.sub("<strong[^>]>", "", txtProgram)
            txtProgram = re.sub("</?strong[^>]>", "", txtProgram)
            txtProgram = re.sub("<.*?>", "", txtProgram).strip().splitlines()
            program+=txtProgram
            #print(txtProgram)

    f = open(director + "/" + "program.txt", "w+", encoding='utf-8')
    f.write(program[0] + "\n\n")
    for i in range(0,len(Institutie)):
        #print(Institutie[i] +"\n"+ program[i] + "\n\n")
        if i == len(Institutie)-1:
            f.write(Institutie[i] +"\n"+ program[i+1])
        else:
            f.write(Institutie[i] +"\n"+ program[i+1] + "\n\n")
    f.close()


def main():
    getResources()
    getSchedule()


main()


# https://www.dac-iasi.ro/file/Cerere_indemnizatie_model_2020_DAS_Iasi.pdf  