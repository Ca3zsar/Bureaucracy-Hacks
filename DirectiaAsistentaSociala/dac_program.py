import re
import requests 
from bs4 import BeautifulSoup

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

f = open("program.txt", "w+", encoding='utf-8')
f.write(program[0] + "\n\n")
for i in range(0,len(Institutie)):
    #print(Institutie[i] +"\n"+ program[i] + "\n\n")
    if i == len(Institutie)-1:
        f.write(Institutie[i] +"\n"+ program[i+1])
    else:
        f.write(Institutie[i] +"\n"+ program[i+1] + "\n\n")
f.close()