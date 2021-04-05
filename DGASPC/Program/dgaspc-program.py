###################
#
# Script to scrape time schedule from https://www.dasiasi.ro/
#
#
###################
import shutil
import os
import re
import requests 
from bs4 import BeautifulSoup

URL='https://www.dasiasi.ro/'
director="./resources"

def deleteDirector(document_director):
  shutil.rmtree(document_director)

def createDirector(document_director):
   os.makedirs(document_director)

def scrape_schedule(URL):
    page=requests.get(URL) 
    soup= BeautifulSoup(page.content,'html.parser') 

    html_div = soup.find("div", {"class":"orar"})
    html_div_content = str(html_div)
    path = director + "/" + "div_orarDGASPC.html"
    f = open(path, "w+", encoding='utf-8')
    f.write(html_div_content)
    f.close()

    orar = soup.select("div[class=orar]  p") 
    txt= str(orar)
    txt = re.sub("<p[^>]*>", "", txt)
    txt = re.sub("</?p[^>]*>", "", txt)

    path = director + "/" + "orarDGASPC.txt"
    filetoWrite = open(path,"w+", encoding='utf-8') 
    filetoWrite.write(txt)
    filetoWrite.close()

def main():
    if os.path.isdir(director):
        deleteDirector(director)
    createDirector(director)
    scrape_schedule(URL)

main()