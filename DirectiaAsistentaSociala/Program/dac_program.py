###################
#
# Script to scrape time schedule from https://www.dac-iasi.ro/contact
#
#
###################
import shutil
import os
import re
import requests 
from bs4 import BeautifulSoup

URL='https://www.dac-iasi.ro/contact' 
director = "./resources"

def deleteDirector(document_director):
  shutil.rmtree(document_director)

def createDirector(document_director):
   os.makedirs(document_director)

def scrape_schedule(URL):

    page=requests.get(URL) 
    soup= BeautifulSoup(page.content,'html.parser') 

    html_div = soup.find("div", {"class":"art-article"})
    html_div_content = str(html_div)
    path = director + "/" + "div_orarDAC.html"
    f = open(path, "w+", encoding='utf-8')
    f.write(html_div_content)
    f.close()

    searchFor=["Luni","Marti", "Miercuri", "Joi", "Vineri"] 
    orare = soup.select("div[class=art-article] > p")
    title= soup.select("div[class=art-article] > h4") 

    institution=[]
    for titlu in title: 
        txtTitle = str(titlu)
        txtTitle = re.sub("<h4[^>]>", "", txtTitle)
        txtTitle = re.sub("</?h4[^>]>", "", txtTitle)
        txtTitle = re.sub("<strong[^>]>", "", txtTitle)
        txtTitle = re.sub("</?strong[^>]>", "", txtTitle)
        txtTitle = re.sub("<.*?>", "", txtTitle).strip().splitlines()
        institution+=txtTitle
 
    schedule=[]
    for orar in orare:
      txtProgram = str(orar)
      if any(x in txtProgram for x in searchFor):
        txtProgram = re.sub("<p[^>]>", "", txtProgram)
        txtProgram = re.sub("</?p[^>]>", "", txtProgram)
        txtProgram = re.sub("<strong[^>]>", "", txtProgram)
        txtProgram = re.sub("</?strong[^>]>", "", txtProgram)
        txtProgram = re.sub("<.*?>", "", txtProgram).strip().splitlines()
        schedule+=txtProgram
    
    path = director + "/" + "orarDAC.txt"
    f = open(path, "w+", encoding='utf-8')
    for i in range(0,len(institution)):
        if i == len(institution)-1:
            f.write(institution[i] +"\n"+ schedule[i])
        else:
            f.write(institution[i] +"\n"+ schedule[i] + "\n\n")
    f.close()

def main():
    if os.path.isdir(director):
        deleteDirector(director)
    createDirector(director)
    scrape_schedule(URL)

main()
