from bs4 import BeautifulSoup
import requests
import os
import re
import urllib.request
import shutil


links = [ 'http://www.cnas.ro/page/program-audiente.html',
 'http://www.cnas.ro/hih/page/dobandirea-i-certificarea-calitatii-de-asigurat.html',
 'http://www.cnas.ro/hih/page/card-european.html',
 'http://www.cjasis.ro/W_CAS_IS/cas/fsm/dm/formulare_utile',
 'http://www.cnas.ro/hih/page/ingrijiri-la-domiciliu.html',
 'http://www.cnas.ro/hih/page/formulare-necesare.html']

director = './HTMLFiles'

def deletingFiles():
    shutil.rmtree(director)
    print("Files deleted")

def makeDirectors():
    os.makedirs(director)
    print("Files created..")

def getHTMLs(links):
    index = 1
    for link in links:
        baseName = os.path.basename(link)
        # baseName = f'file{index}'
        f = open (director + '/' + baseName + '.html','w',encoding='utf-8')
        content = requests.get(link,)
        f.write(content.text)
        f.close()
        index += 1

def main():
    if os.path.isdir(director):
        deletingFiles()
    makeDirectors()


    getHTMLs(links)

main()
