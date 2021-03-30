from urllib.parse import urlparse
import urllib.request
import requests
import unicodedata
import csv

from bs4 import BeautifulSoup

import selenium 
import os
import re

from selenium import webdriver
from selenium.webdriver.firefox.options import Options as FirefoxOptions

urlPrimarie = "http://www.primaria-iasi.ro/portal-iasi/pmi/primaria-municipiului-iasi/60/acte-necesare"
urlOrar = "http://www.primaria-iasi.ro/portal-iasi/pmi/meniu-pmi/54/program-cu-publicul"

downloadDir = "/Primaria_Iasi_Files"
csvProgram = "/program.csv"

firefoxOptions = FirefoxOptions()
firefoxOptions.add_argument("--headless")

firefoxPref = webdriver.FirefoxProfile()
firefoxPref.set_preference("browser.download.manager.showWhenStarting", False)
firefoxPref.set_preference("browser.download.dir", downloadDir)
firefoxPref.set_preference("browser.helperApps.neverAsk.saveToDisk", "attachment/pdf")


# firefoxDriver = webdriver.Firefox(options=firefoxOptions,firefox_profile=firefoxPref)
# firefoxDriver.get(urlPrimarie)

def changeEntriesNumber():
    choiceButton = firefoxDriver.find_element_by_xpath("/html/body/div[2]/div/div[1]/div/div[3]/div/div[2]/div[1]/div[1]/div/label/select");
    choiceButton.click()
    
    entriesButton = firefoxDriver.find_element_by_xpath("/html/body/div[2]/div/div[1]/div/div[3]/div/div[2]/div[1]/div[1]/div/label/select/option[4]")
    entriesButton.click()
    
    downloadButtons = firefoxDriver.find_elements_by_xpath("/html/body/div[2]/div/div[1]/div/div[3]/div/div[2]/div[2]/div/table/tbody/tr/td[1]/a")
    
    urls = []
                                                            
    for button in downloadButtons:
        urls.append(button.get_attribute("href"))
    
    return urls
    

def get_file_name(url):
    if not url:
        return None
    
    fname = re.findall('filename=(.+)', url)
    if len(fname) == 0:
        return None
    return fname[0]


def downloadFiles(urls):
    for url in urls:
        print(url)
        response =  urllib.request.urlopen(url)
        
        parsed = urlparse(url)
        fileName = os.path.basename(parsed.path)
        
        file = open(f"{fileName}","wb")
        file.write(response.read())
        file.close()
        
        os.rename(f"{fileName}", f"Primaria_Iasi_Files\\{fileName}")
        

def getProgram(url):
    page = requests.get(url)
    soup = BeautifulSoup(page.content,'html.parser', from_encoding="utf-8")
    
    programClass = soup.find("div",class_="col-xs-12 col-sm-12 col-md-12 text-justify")
    toDeleteDiv = programClass.find("div",class_="table-responsive")
    
    tableProgram = programClass.find("table")
    tableRow = tableProgram.find_all("tr")
    
    programBig = []
    
    for row in tableRow:
        tableColumn = row.find_all("td")
        for column in tableColumn:
            info = column.text
            
            info = re.sub("\n",' ',info)
            info = re.sub("(\s)+"," ",info)

            programBig.append(info)
    
    toDeleteDiv.decompose()
    
    paragraphs = programClass.find_all("p")
    
    programs = []
    directors = []
    
    toLookFor = ["program cu publicul","luni","marti","miercuri","joi","vineri","sambata","duminica",
                 "audiente","audienta","audiențe", "marți"]
    
    institutes = ["directia","biroul","sc","compania","politia","serviciul","compartiment","centrul",
                  "casieriile","direcția","compartimentul"]
    
    for paragraph in paragraphs:
        found = 0
        for word in toLookFor:
            if paragraph.text.lower().find(word) != -1:
                found = 1
                break
        if found:
            currentIndex = paragraphs.index(paragraph)
            for i in range(currentIndex-1,-1,-1):
                words = paragraphs[i].text.split()
                if len(words) != 0:
                    if words[0].lower() in institutes:
                        programs.append(paragraph.text)
                        directors.append(paragraphs[i].text)
                        break
    
    for i in range(len(directors)):
        print(directors[i])
        print(programs[i])
        print("------")
    
    # for paragraph in paragraphs:
    #     if ord(paragraph.text[0]) == 160 and len(paragraph.text)==1:
    #         paragraph.decompose()
    #     else:
    #         print(paragraph.text)
    
    # print(programClass)

# downloadFiles(changeEntriesNumber())
getProgram(urlOrar)
# firefoxDriver.quit()
