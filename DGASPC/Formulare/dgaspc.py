###################
#
# Script to scrape PDF documents from https://www.dasiasi.ro/
#
#
###################
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
director = "./resources"

def deleteDirector(document_director):
  shutil.rmtree(document_director)


def createDirector(document_director):
   os.makedirs(document_director)


def createDirectorNested(document_director):
    os.mkdir(document_director)

# scrape the data 
 
def go_spider_scrapping(url, fileName): 
    page = requests.get(url)
    soup = BeautifulSoup(page.content,'html.parser') 
    txt = soup.find_all('div',class_='camere_text')
    links = txt[0].find_all('a')
    if links : 
        for link in links : 
            link_for_download = link.get('href') 
            link_for_download = DOMAIN + link_for_download
            path = fileName.rstrip()
            print(path)
            title = link_for_download.split('/')[-1]
            title = title.replace('%20',' ')
            path = path + '/' + title
            print(path)
            with open(path, 'wb') as file: 
                        response= requests.get(link_for_download)
                        file.write(response.content)
                        file.close()
    if txt : 
        path = fileName.rstrip() + "/data.txt"
        print(path)
        f = open(path, "w+", encoding='utf-8')
        f.write(txt[0].text)
        f.close()
    pdfs= soup.select('div[class=galleriesDocsPad] a')
    if pdfs : 
        for pdf in pdfs : 
            link_for_download = DOMAIN + pdf.get('href')
            link_for_title= pdf.get('title')
            print(link_for_download)
            print (link_for_title)
            path = fileName.rstrip()
            print(path)
            with open(link_for_title, 'wb') as file: 
                response= requests.get(link_for_download)
                file.write(response.content)
                file.close()
                type_of_file =  magic.from_file(link_for_title)
                if 'PDF' in type_of_file : 
                    os.rename(link_for_title, link_for_title + ".pdf")
                    link_for_title= link_for_title+".pdf"
                    shutil.move(link_for_title, path+ '/' + link_for_title)
                    print("is a pdF!!")
                else : 
                    os.rename(link_for_title, link_for_title + ".doc")
                    link_for_title= link_for_title+".doc"
                    shutil.move(link_for_title, path+ '/' + link_for_title)
                    print("is a DOC")
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
                print(DOMAIN+further_research)
                document_director= link_documents[0].text
                document_director = document_director.replace('/','.')
                print(document_director)
                document_director = director + "/" + document_director
                createDirectorNested(document_director)
                go_spider_scrapping(DOMAIN+further_research,document_director)


def main():
    if os.path.isdir(director):
        deleteDirector(director)
    createDirector(director)
    go_spider_crawler(URL)


main()









