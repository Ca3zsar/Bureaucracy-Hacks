from bs4 import BeautifulSoup
import requests
import os
import re
import urllib.request
import shutil


links = [ 
 'http://www.cnas.ro/page/program-audiente.html',
 'http://www.cnas.ro/hih/page/dobandirea-i-certificarea-calitatii-de-asigurat.html',
 'http://www.cnas.ro/hih/page/card-european.html',
 'http://www.cjasis.ro/W_CAS_IS/cas/fsm/dm/formulare_utile',
 'http://www.cnas.ro/hih/page/formulare-necesare.html',
 'http://www.cnas.ro/hih/page/procedura-privind-eliberarea-listei-cu-serviciile-medicale.html'
 ]

download_links = [
'http://www.cjasis.ro/W_CAS_IS/cas/fsm/dm/formulare_utile/Cerere%20dispozitive%20medicale.doc',
'http://www.cjasis.ro/W_CAS_IS/cas/fsm/dm/formulare_utile/Declara%C8%9Bie%20de%20%C3%AEmputernicire.doc',
'http://www.cjasis.ro/W_CAS_IS/cas/fsm/dm/formulare_utile/Model%20prescrip%C8%9Bie%20medical%C4%83%20dispozitive%20Anexa%2039D.doc',
'http://www.cjasis.ro/W_CAS_IS/cas/fsm/dm/formulare_utile/O%201081.2018%20-%20Preturi%20Dispozitive%20Medicale.pdf',
'http://www.cjasis.ro/W_CAS_IS/cas/fsm/dm/formulare_utile/furnizori%20%20DM%20%20-%2001-08-2019%20.xls',
'http://www.hih.ro/W_CAS_IS/cas/programare_spitale/Informatii%20privind%20programarea%20la%20spitalele%20publice%20si%20private%20din%20jude%C8%9Bul%20Ia%C8%99i.xls',
'http://www.cnas.ro/hih//theme/cnas/js/ckeditor/filemanager/userfiles/Anunturi/2018/cerere_card_european.doc',
'http://www.cnas.ro/hih//theme/cnas/js/ckeditor/filemanager/userfiles/Anunturi/2018/adeverinta_salariat_CEASS.doc',
'http://www.casan.ro/hih//theme/cnas/js/ckeditor/filemanager/userfiles/CEAS/model_certificat_provizori_inlocuire_CEAS.PDF',
'http://www.cnas.ro/hih//theme/cnas/js/ckeditor/filemanager/userfiles/Anunturi/2020/cerere_card_european_duplicat.pdf',
'http://www.cnas.ro/hih//theme/cnas/js/ckeditor/filemanager/userfiles/2020/MODEL_CERERE_LISTA_SERVICII.doc',
'http://www.casan.ro/hih//theme/cnas/js/ckeditor/filemanager/userfiles/Formulare/CERERE_ELIBERARE_ADEVERINTA_REFUZ_CARD.PDF',
]

director = f"{os.path.dirname(__file__)}/Content"
HTMLFiles = f"{os.path.dirname(__file__)}/HTMLFiles"
acte = f"{os.path.dirname(__file__)}/Acte"

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



def getSources(links):
    for link in links:
        source = requests.get(link).text
        soup = BeautifulSoup(source, 'lxml')
        section = soup.select('.content.pageLi')

        baseName = os.path.basename(link)
        f = open (HTMLFiles + '/' + baseName + '.html','w',encoding='utf-8')
        try:
            f.write(str(section[0]))
        except:
            f.close()
            continue
        f.close()


        f = open (director + '/' + baseName + '.txt','w',encoding='utf-8')
        try:
            untagged= re.sub('<.*?>', '', str(section[0]))
            f.write(untagged)
        except:
            f.close()
            continue
        f.close()


def downloadResources(download_links):
    for link in download_links:
        source = requests.get(link)

        baseName = os.path.basename(link)
        f = open (acte + '/' + baseName, 'wb')
        try:
            f.write(source.content)
        except:
            f.close(source.content)
            continue
        f.close()

def main():
    deletingFiles()
    makeDirectors()


    getSources(links)
    downloadResources(download_links)

if __name__ == "__main__":
    main()
