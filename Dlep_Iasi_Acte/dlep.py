import requests 
from bs4 import BeautifulSoup

URL='https://www.dlep-iasi.ro' 
#prepare a list in order to store the links 
URLS = [] 
director="./resources"
url = []
page=requests.get(URL) 
soup= BeautifulSoup(page.content,'html.parser') 

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
 fisier= url.split("/")
 fileToWrite= fisier[2][0:-5]
 print(fileToWrite)
 url=URL+url
 page=requests.get(url,allow_redirects=True) 
 page.encoding=page.apparent_encoding 
 soup= BeautifulSoup(page.content,'html.parser') 
 information = soup.find('div', class_='camere_text') 
 open(f"{director}/{fileToWrite}", 'w+',encoding="utf-8").write(information.text)

 
   
def spider():
     conditieOprire='Nu s-a gasit nici o inregistrare.'
     searchFor="acte-necesare"
     toContinue=True
     for page in URLS : 
         pageNumber=1
         while toContinue: 
            newUrl = page+str(pageNumber) 
            pageNumber+=1
           # print(newUrl) 
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
                       if(lin.find('acte-necesare') != -1):
                         go_spider_scrapping(lin) 
                      

              
spider()
















