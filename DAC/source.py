from bs4 import BeautifulSoup
import requests
import os
import re
import urllib.request
import shutil # for deleting dirs

root = "https://www.dac-iasi.ro"
sufixes = ["alocatii", "autoritate", "serviciul-social", "alte-servicii"]

director = "./Content"
acte = "./Acte"
HTMLFiles = "./HTMLFiles"

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
    

# Meant for extracting content, modified into downloading HTMLs
def fillContent(sufix, section):
    fileName = sufix + ".txt"
    htmlFile = sufix + ".html"
    htmlPath = HTMLFiles + "/" + htmlFile
    filePath = director + "/" + fileName
    
    f1 = open(filePath, "w", encoding='utf-8')
    f2 = open(htmlPath, "w", encoding='utf-8')

    # container = re.sub('<.*?>','', str(section))
    # container = re.sub('\s\s\s\s\s\s','\n',container)
    # f.write(container)

    matches = ["h1", "h3", "p"]

    for line in section:
        if any(x in line.text for x in matches):
            try:
                line.find("img").extract()
                line.find("a").extract()
                line.find("script").extract()
            except:
                pass
            txt = line.text
            
            f1.write(txt)
            
            txt = str(line)
            txt = re.sub('\<img.*?/>','\n',txt)
            txt = re.sub('\<a.*?\>(.|\n)*?\<\/a\>','',txt)
            txt = re.sub('\<script.*?\>(.|\n)*?\<\/script\>','',txt)
            
            f2.write(txt)

    f1.close()
    f2.close()

def downloadHrefs(sufix, soup):
    for a in soup.select('.art-article p a'):

        toDownload = a['href']
        
        if toDownload.find("pdf") == -1:
            continue;
        
        if toDownload.startswith("/"):
            toDownload = root + toDownload
        
        print(toDownload)

        baseName = os.path.basename(toDownload)

        req = requests.get(toDownload, allow_redirects=True)

        open(f"{acte}/{sufix}/{baseName}", 'wb').write(req.content)



def getResources():
    for sufix in sufixes:
        url = root + "/" + sufix
        source = requests.get(url).text
        soup = BeautifulSoup(source, 'lxml')
        section = soup.find_all(class_="art-article")

        fillContent(sufix, section)
        downloadHrefs(sufix, soup)


def getSchedule():
    URL='https://www.dac-iasi.ro/contact' 
    page=requests.get(URL) 
    soup= BeautifulSoup(page.content,'html.parser') 
    
    with open(f"{HTMLFiles}\\orar.html","w",encoding="utf-8") as file:
        file.write(soup.prettify())
    
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
    deletingFiles()
    makeDirectors()
    getResources()
    getSchedule()


if __name__=="__main__":
    main()
