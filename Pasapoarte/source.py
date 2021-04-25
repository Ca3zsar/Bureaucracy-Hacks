from bs4 import BeautifulSoup
import requests
import os
import re
import urllib.request
import shutil # for deleting dirs

import selenium
from selenium import webdriver
from selenium.webdriver.firefox.options import Options as FirefoxOptions 
from selenium.webdriver.firefox.firefox_binary import FirefoxBinary

import time

director = f"{os.path.dirname(__file__)}/Content"
HTMLFiles = f"{os.path.dirname(__file__)}/HTMLFiles"
acte = f"{os.path.dirname(__file__)}/Acte"

url = "https://epasapoarte.ro"

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

def fillContent(section, fileName):
    f = open(fileName, "a+", encoding='utf-8')
    f.write(str(section))
    f.close()


def getTextFromTag(tag):
    txt = re.sub('<.*?>', '', str(tag))
    # txt = re.sub(r"^\s+|\s+$",'', txt)
    return txt

# headless

def generateHTMLS(driver):
    driver.get(url)
    source = driver.page_source
    soup = BeautifulSoup(source, "lxml")


    i = 0
    for line in soup.select("#acte > div[align] > a"):
        
        subUrl = line['href']
        
        if subUrl[0] == '/':
            # print(subUrl)
            subUrl = url + subUrl
        
        res = requests.get(subUrl)
        if res == None:
            continue
        
        subDriver = getDriver()
        subDriver.get(subUrl)
        
        source  = subDriver.page_source
        subSoup = BeautifulSoup(source, "lxml")

        title = getTextFromTag(line)

        section1 = subSoup.select("article button")
        section2 = subSoup.select("content > div > div > div > section > div > div > div > div > div > div")


        for index in range(0,len(section1)):
            i = i + 1
            fileName = f"file{i}"
            pathHTML = f"{HTMLFiles}\\{fileName}.html"
            fillContent(title + "\n", pathHTML)
            fillContent(section1[index], pathHTML)
            fillContent(section2[index], pathHTML)

            pathTXT = f"{director}\\{fileName}.txt"
            fillContent(title + "\n", pathTXT)
            fillContent(getTextFromTag(section1[index]), pathTXT)
            fillContent(getTextFromTag(section2[index]), pathTXT)

        subDriver.close()

    driver.quit()

def generateSchedule(driver):
    URL = "https://pasapoarte.mai.gov.ro/serviciul-public-comunitar-de-pasapoarte-iasi/"

    source = requests.get(URL).text
    soup = BeautifulSoup(source, 'lxml')
    
    section = soup.select("table")
    
    with open(f"{HTMLFiles}/orar.html","w",encoding="utf-8") as file:
        file.write(str(section))

    path = director + "/orar.txt"
    f = open(path, "w", encoding='utf-8')

    for line in section:
        txt = str(line)
        
        txt = re.sub('\<(.|\n)*?\>','', txt)
        txt = re.sub('^\s*','', txt)
        f.write(txt)
        f.write('\n') 

    f.close()


def getDriver():
    firefoxOptions = FirefoxOptions()
    firefoxOptions.add_argument("--headless")

    firefoxPref = webdriver.FirefoxProfile()
    firefoxPref.set_preference("browser.download.manager.showWhenStarting", False)
    firefoxPref.set_preference("browser.download.dir",acte)
    firefoxPref.set_preference("browser.helperApps.neverAsk.saveToDisk", "attachment/pdf")
    firefoxDriver = webdriver.Firefox(options=firefoxOptions,firefox_profile=firefoxPref,firefox_binary=os.getenv('FIREFOX_BIN'))
    
    return firefoxDriver


def main():
    deletingFiles()
    makeDirectors()

    driver = getDriver()

    generateSchedule(driver)
    generateHTMLS(driver)
    
    
if __name__=="__main__":
    main()

