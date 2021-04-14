from bs4 import BeautifulSoup
import requests
import os
import re
import urllib.request
import shutil # for deleting dirs

# Bacau, incasari persoane juridice, Luni, Marti, Miercuri, Joi, 8.30-14-30

divId = "anf_read_speaker01"
director = "./resources"
url = "https://www.anaf.ro/anaf/internet/Iasi/contact_iasi/program_iasi/!ut/p/a1/pZI9C8IwEIZ_TdbeVTEVtxa1HygiKNYsEiXWSE0ljfbvmxYHB00Hb0qO57kjLwEGOTDFn7LgRlaKl-2d0UPiJzQZjAfZKp4hrkfzKN4sfYyn1AJ7C-CPCtHpR8Hb_wUsgr98nPpO36c9-6PgP98CPfntgLmQ7gUd4IrYuaTN0AlsewEKGbCirI7dj9iH6jgcF8C0OAsttPfQtn0x5l5PCBJsmsbjip89XRFsDwSlMkIrYQimvJa2e-I1J_ht2KWqDeQfM-B-29rKUabXUflchC8cOFyI/dl5/d5/L2dBISEvZ0FBIS9nQSEh/"


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


def getTitles(soup):
    firstTitle  = soup.select("td[bgcolor] > div > strong")
    otherTitles = soup.select("td[bgcolor] > strong")
    lastTitle   = soup.select("td > div > strong")
    return firstTitle + otherTitles + lastTitle

def isTitle(titles, string):
    for title in titles:
        if str(title) in string:
            return True
    return False

def isTable(string):
    tagList = ["tr", "td", "sup" ]
    notInTag = ["colspan", "table"]
    for tag in tagList:
        condition = tag in string
        if not condition:
            return False

    for tag in notInTag:
        if tag in string:
            return False
    return True



def main():
    if os.path.isdir(director):
        deletingFiles()
    makeDirectors()


    source = requests.get(url).text
    soup = BeautifulSoup(source, 'lxml')
    section = soup.select("div#anf_read_speaker01 tr")

    titles = getTitles(soup)

    path = director + "/data.txt"
    f = open(path, "w", encoding='utf-8')
    

    for line in section:
        txt = str(line)
        
        if isTitle(titles, txt):
            f.write('\n')
            txt = re.sub('\<(.|\n)*?\>','', txt)
            txt = re.sub('^\s*','', txt)
            f.write(txt)
            f.write('\n')
        elif "•" in txt:
            # txt = re.sub('^\u00A0*','', txt)
            # txt = re.sub('^\s*','', txt)
            txt = re.sub('•','\n•',txt)
            txt = re.sub('\<(.|\n)*?\>','', txt)
            txt = re.sub(r"^\s+|\s+$",'', txt)
            
            f.write(txt)
            f.write('\n')
        elif isTable(txt):
            # txt = re.sub('^\s*','', txt)
            txt = re.sub('\<(.|\n)*?\>','', txt)
            f.write(txt)
            f.write('\n')


    f.close()


if __name__=="__main__":
    main()

