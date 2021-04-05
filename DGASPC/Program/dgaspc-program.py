import re
import requests 
from bs4 import BeautifulSoup

URL='https://www.dasiasi.ro/' 
page=requests.get(URL) 
soup= BeautifulSoup(page.content,'html.parser') 

orar = soup.select("div[class=orar]  p") 
txt= str(orar)
txt = re.sub("<p[^>]*>", "", txt)
txt = re.sub("</?p[^>]*>", "", txt)
filetoWrite = open("orarDGASPC.txt","w+") 
filetoWrite.write(txt) 
