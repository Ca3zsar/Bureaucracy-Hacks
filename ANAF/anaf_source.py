from bs4 import BeautifulSoup
import requests
import os
import re
import urllib.request
from urllib.request import urlopen, urlretrieve, quote
from urllib.parse import urljoin
import shutil # for deleting dirs

URL_1="http://static.anaf.ro/static/10/Galati/gl1_61.htm"
URL_2="http://static.anaf.ro/static/10/Galati/GL1_39.htm"

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

def getDataUrl1(var):
    htmlToText=str(var)
    #print(htmlToText)
    occurrence=3
    ol_3rd_pos = [m.start() for m in re.finditer(r"<ol", htmlToText)]
    last_2_tags = '</body>' + '</html>'
    textToHtml =''
    if len(ol_3rd_pos)>= 3:
        for i in range(ol_3rd_pos[occurrence-1],len(htmlToText)-(len(last_2_tags)+2)):
            textToHtml+=htmlToText[i]
    else:
        print ("No {} occurrence of substring lies in given string".format(occurrence))

    new_html=BeautifulSoup(textToHtml, 'html.parser')

    

    for a in new_html.findAll('a', href=True):
        a.extract()

    anaf_text=str(new_html.text)
    anaf_text = re.sub("\s(\s)+", "\n",anaf_text)
 
    with open(f"{director}\\ANAF_Persoane_Juridice_data.txt","w",encoding="utf-8") as file:
        file.write(anaf_text)

def getDataUrl2(var):
    htmlToText=str(var)
    #print(htmlToText)
    occurrence=10
    p_10th_pos = [m.start() for m in re.finditer(r"<p", htmlToText)]
    last_2_tags = '</body>' + '</html>'
    textToHtml =''
    if len(p_10th_pos)>= 10:
        for i in range(p_10th_pos[occurrence-1],len(htmlToText)-(len(last_2_tags)+5)):
            textToHtml+=htmlToText[i]
    else:
        print ("No {} occurrence of substring lies in given string".format(occurrence))

    new_html=BeautifulSoup(textToHtml, 'html.parser')

    for a in new_html.findAll('a', href=True):
        a.extract()

    anaf_text=str(new_html.text)
    anaf_text = re.sub("\s(\s)+", "\n",anaf_text)

    with open(f"{director}\\ANAF_Certificat_Atestare_Fiscala_data.txt","w",encoding="utf-8") as file:
        file.write(anaf_text)

def downloadDocuments(var, html_filename):
    a_tags=var.findAll("a")
    for link in a_tags:
        href=link.get('href')
        print(href)
        if href is None:
            continue
        if href.startswith('#'):
            continue
        if href.startswith('d') or href.startswith('A') or href.endswith("42.pdf"):
            continue
        filename =os.path.join(acte, href.rsplit('/', 1)[-1])
        if href.endswith('.pdf'):
            try:
                urlretrieve(href, filename)
            except:
                print('failed to download')
    
    #get HTML
    with open(f"{HTMLFiles}\\" + html_filename,"w",encoding="utf-8") as file:
        file.write(var.prettify())

def scrape_data():
    page=requests.get(URL_1)
    page1=requests.get(URL_2)
    soup=BeautifulSoup(page.content, 'html.parser')
    soup1=BeautifulSoup(page1.content, 'html.parser')

    getDataUrl1(soup)
    getDataUrl2(soup1)

    downloadDocuments(soup, "ANAF_Persoane_Juridice.html")
    downloadDocuments(soup1, "ANAF_Certificat_Atestare_Fiscala.html")

def main():
    deletingFiles()
    makeDirectors()

    scrape_data()

main()