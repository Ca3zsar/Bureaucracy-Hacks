#################
# Crawler for dlep iasi 
# save the content in which we find the key words "acte necesare" 
# and also save the html for further research 
#################
import re
import os
import shutil
import requests 
import sys
from bs4 import BeautifulSoup

URL='https://www.dlep-iasi.ro' 
#prepare a list in order to store the links 
URLS = [] 
director="./resources"
url = []
page=requests.get(URL) 
soup= BeautifulSoup(page.content,'html.parser') 


def deleteDirector(document_director):
  shutil.rmtree(document_director)

def createDirector(document_director):
   os.makedirs(document_director)

   
def createDirectorNested(document_director):
    os.mkdir(document_director)


# first, grab the links that we are interested with 

for links in soup.find_all('div',class_='evidenta') : 
    for link in links.find_all('a') :
       lin= link.get('href') 
       url = URL + '/?page=' + lin[1:-5] + '&n='
       URLS.append(url)
     
  
for links in soup.find_all('div',class_='starea') : 
    for link in links.find_all('a') :
       lin= link.get('href') 
       url = URL + '/?page=' + lin[1:-5] + '&n='
       URLS.append(url)

# check if the links are good ! 
#for links in URLS :
 #    print(links)

# function for scrapping an interesting link 

def go_spider_scrapping(url):
  fisier= url.split("/")[-1]
  fileToWrite= fisier[0:-5]
  print(fileToWrite)
  url=URL+url
  page=requests.get(url) 
  soup= BeautifulSoup(page.content,'html.parser') 
  soup.prettify(formatter=lambda s: s.replace(u'\xa0', ' '))
  for e in soup.findAll('br'):
    e.extract()
  information = soup.find('div', class_='camere_text') 
  text= information.text.strip()
  text = text.replace(u'\xa0', u' ')
  # in case we descover that it helps to remove the space from text
  # text = re.sub("(\s)+", " ",text)
  # text = re.sub(r'[\ \n]{2,}', '',text)
  # text = text.replace('(\n)+', ' ')
  # text = re.sub('\n', " ", text)
  text= text.strip()
  if(text.find('ACTE NECESARE') != -1 or text.find('Acte necesare') != -1) : 
      print(url)
      print(fileToWrite)
      document_director = director + "/" + fileToWrite
      createDirectorNested(document_director)
      path = document_director + "/data.txt" 
      f = open(path, "w+", encoding='utf-8')
      f.write(text)
      f.close()
      path = document_director + "/data.html" 
      f = open(path, "w+", encoding='utf-8')
      f.write(str(information))
      f.close()

    
def spider():
     conditieOprire='Nu s-a gasit nici o inregistrare.'
     searchFor="acte-necesare"
     toContinue=True
     for page in URLS : 
         pageNumber=1
         while toContinue: 
            newUrl = page+str(pageNumber) 
            pageNumber+=1
            pageSearch=requests.get(newUrl) 
            soup = BeautifulSoup(pageSearch.content,'html.parser') 
            stop= soup.find('div',class_='products')
            if(stop.text == conditieOprire):
                break
            else: 
                for link in soup.find_all('div', class_='products'):
                   for link2 in link.find_all('div',class_='camere_thumb'):
                     for actual_link in link2.find_all('a') : 
                       lin= actual_link.get('href') 
                       title = actual_link['title']
                       go_spider_scrapping(lin) 


def main():
    if os.path.isdir(director):
        deleteDirector(director)
    createDirector(director)
    spider()

main()












