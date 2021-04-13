import re
import os
import shutil
import requests 
import sys
import mimetypes
import magic
from bs4 import BeautifulSoup

DOMAIN = 'https://www.dasiasi.ro/'
URL = 'https://www.dasiasi.ro/?page=lista&Nume=formulare&tp=lst&id=6&n='
FileType = 'pdf' 

director = "./Content"
HTMLFiles = "./HTMLFiles"
acte = "./Acte"

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

# scrape the data 
 
def go_spider_scrapping(url,document_director, fileName): 
    page = requests.get(url)
    soup = BeautifulSoup(page.content,'html.parser') 
    further_research = soup.find_all('div',class_='content-text')
    path = f"{HTMLFiles}/{fileName}.html"
    
    f= open(path,'w+', encoding='utf-8') 
    f.write(str(further_research[0])) 
    f.close()
    
    txt = soup.find_all('div',class_='camere_text')
    links = txt[0].find_all('a')
    if links : 
        for link in links : 
            link_for_download = link.get('href') 
            link_for_download = DOMAIN + link_for_download
            
            path = document_director.rstrip()
            title = link_for_download.split('/')[-1]
            title = title.replace('%20',' ')
            
            path = path + '/' + title
            with open(path, 'wb') as file: 
                        response= requests.get(link_for_download)
                        file.write(response.content)
                        file.close()
    if txt : 
        path = f"{director}/{fileName}.txt"
        f = open(path, "w+", encoding='utf-8')
        f.write(txt[0].text)
        f.close()
    
    pdfs= soup.select('div[class=galleriesDocsPad] a')
    
    if pdfs : 
        for pdf in pdfs : 
            link_for_download = DOMAIN + pdf.get('href')
            link_for_title= pdf.get('title')
            
            path = document_director.rstrip()
            
            with open(link_for_title, 'wb') as file: 
                response= requests.get(link_for_download)
                file.write(response.content)
                file.close()
                type_of_file =  magic.from_file(link_for_title)
                
                if 'PDF' in type_of_file : 
                    os.rename(link_for_title, link_for_title + ".pdf")
                    link_for_title= link_for_title+".pdf"
                    shutil.move(link_for_title, path+ '/' + link_for_title)
                else : 
                    os.rename(link_for_title, link_for_title + ".doc")
                    link_for_title= link_for_title+".doc"
                    shutil.move(link_for_title, path+ '/' + link_for_title)
    else : 
        print("no file to download here")
        
# search for data (crawl)

def go_spider_crawler(URL) : 
    keepRunning = True 
    pageNumber = 1
    while keepRunning:
        newUrl = URL + str(pageNumber) 
        pageNumber +=1 
        page = requests.get(newUrl)
        soup = BeautifulSoup(page.content,'html.parser') 
        files = soup.find_all('div', class_='docbox')
        if not files: 
            keepRunning = False 
        else:
            for document in files: 
                link_documents = document.select('div[class=doc-title] a')
                further_research = link_documents[0].get('href')
                
                document_director= link_documents[0].text
                document_director = document_director.replace('/','.')
                
                document_director = acte + "/" + document_director
                os.mkdir(document_director)
                
                go_spider_scrapping(DOMAIN+further_research,document_director,link_documents[0].text.replace('/','.'))

def scrape_schedule(DOMAIN):
    page=requests.get(DOMAIN) 
    soup= BeautifulSoup(page.content,'html.parser') 

    html_div = soup.find("div", {"class":"orar"})
    html_div_content = str(html_div)
    
    path = HTMLFiles + "/" + "orar.html"
    f = open(path, "w+", encoding='utf-8')
    f.write(html_div_content)
    f.close()

    orar = soup.select("div[class=orar]  p") 
    txt= str(orar)
    txt = re.sub("<p[^>]*>", "", txt)
    txt = re.sub("</?p[^>]*>", "", txt)

    path = director + "/" + "orar.txt"
    filetoWrite = open(path,"w+", encoding='utf-8') 
    filetoWrite.write(txt)
    filetoWrite.close()


def main():
    deletingFiles()
    makeDirectors()
    
    go_spider_crawler(URL)
    scrape_schedule(DOMAIN)


main()









