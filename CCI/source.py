from bs4 import BeautifulSoup
import requests
import os
import re
import urllib.request
import shutil

url = "http://www.cciasi.ro/documente_comerciale.htm"
root = "http://www.cciasi.ro/"
download_link="http://www.cciasi.ro/doc/macheta_CO.doc"

director = f"{os.path.dirname(__file__)}/Content"
HTMLFiles = f"{os.path.dirname(__file__)}/HTMLFiles"
acte = f"{os.path.dirname(__file__)}/Acte"

def deletingFiles():
    if os.path.exists(director):
        shutil.rmtree(director)
        
    if os.path.exists(HTMLFiles):
        shutil.rmtree(HTMLFiles)

    if os.path.exists(acte):
        shutil.rmtree(acte)

def makeDirectors():
    os.mkdir(director)
    os.mkdir(HTMLFiles)
    os.mkdir(acte)
    
def getData(var):
    htmlToText = str(var)
    occurrence=9
    table_9th_pos=[m.start() for m in re.finditer(r"<table", htmlToText)]
    end_table=[m.start() for m in re.finditer(r"</table>", htmlToText)]

    end_text = ''
    if len(end_table) >= 3:
        for i in range(end_table[2], len(htmlToText)):
            end_text += htmlToText[i]
    else:
        print("No {} occurrence of substring lies in given string".format(3))
    
    htmlToText = str(var)
    textToHtml = ''
    if len(table_9th_pos) >= 9:
        for i in range(table_9th_pos[occurrence-1], len(htmlToText)-len(end_text)):
            textToHtml += htmlToText[i]
    else:
        print("No {} occurrence of substring lies in given string".format(occurrence))

    new_html = BeautifulSoup(textToHtml, 'html.parser')

    #writing content to .txt file in Content
    sep='-------------------------------'
    cci_text=''
    tr_tags=new_html.findAll("tr")
    row=''
    for tr_tag in tr_tags:
        td_tags=tr_tag.findAll("td")
        for td_tag in td_tags:
            row=td_tag.text
            row=re.sub("\s(\s)+", " ", row)
            cci_text+=row+" | "
        cci_text+="\n" + sep + "\n"

    with open(f"{director}\\Camera de Comerț și Industrie Iași.txt", "w", encoding="utf-8") as file:
        file.write(cci_text)

    #writing content to .html file in HTMLFiles
    with open(f"{HTMLFiles}\\Camera de Comerț și Industrie Iași.txt.html", "w", encoding="utf-8") as file:
        file.write(str(new_html))

    #download file
    source=requests.get(download_link)
    baseName = os.path.basename(download_link)
    f = open (acte + '/' + baseName, 'wb')
    try:
        f.write(source.content)
    except:
        f.close(source.content)
    f.close()

def main():
    deletingFiles()
    makeDirectors()

    source = requests.get(url)
    soup = BeautifulSoup(source.content, 'html.parser')

    getData(soup)

if __name__=="__main__":
    main()
