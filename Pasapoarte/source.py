from bs4 import BeautifulSoup
import requests
import os
import re
import urllib.request
import shutil # for deleting dirs

import selenium
from selenium import webdriver
from selenium.webdriver.Edge

import time


driverPath = ".\\chromedriver.exe"
driverPath2 = ".\\msedgedriver.exe"
# Bacau, incasari persoane juridice, Luni, Marti, Miercuri, Joi, 8.30-14-30

divId = "acte"
director = "./resources"
url = "https://epasapoarte.ro"

def deletingFiles():
    shutil.rmtree(director)
    print("Files deleted")

def makeDirectors():
    os.makedirs(director)
    print("Files created..")

def fillContent(section, fileName):
    f = open(fileName, "w", encoding='utf-8')
    f.write(str(section))
    f.close()


def getTextFromTag(tag):
    txt = re.sub('<.*?>', '', str(tag))
    txt = re.sub(r"^\s+|\s+$",'', txt)
    return txt

# headless

def main():
    if os.path.isdir(director):
        deletingFiles()
    makeDirectors()


    driver = webdriver.Chrome(driverPath)
    driver
    driver.get(url)
    source = driver.page_source
    soup = BeautifulSoup(source, "lxml")


    i = 0
    for line in soup.select("#acte > div[align] > a"):
        i = i + 1

        fileName = f"file{i}"

        title = getTextFromTag(line)

        subUrl = line['href']

        subDriver = webdriver.Chrome(driverPath)
        subDriver.get(subUrl)
        source  = subDriver.page_source
        subSoup = BeautifulSoup(source, "lxml")

        section1 = subSoup.select("content > div > div > div > section > div > div > div > div > div > div")
        
        section2 = subSoup.select("article button")
        
        fillContent(section2, f"{director}\\{fileName}.html")
        subDriver.close()

    driver.quit()

#post_1525 > content > div > div > div > section > div > div > div > div > div > div:nth-child(6)
#post_1267 > content > div > div > div > section > div > div > div > div > div > div:nth-child(6)


    # source = requests.get(url).text
    # soup = BeautifulSoup(source, 'lxml')
    # section = soup.find("div", {"id": "acte"})
    # section = soup.find(id="acte")
    # section = soup.select("div#acte[_ngcontent-c7]")
    # section = soup.select("body")
    # print(section)


    # path = director + "/data.txt"
    # f = open(path, "w", encoding='utf-8')
    


    # f.close()


main()


# tr td table tbody tr td

# PROGRAM LUCRU ...

# INCASARI PERSAONE JURIDICE
# ZILE, ORE
# LUNI, 10-12
# MARTI , ..
# ..

# INCASARI PERSAONE FIZICE


# [
#     "SECTIUNE": "PROGRAM LUCRU"
#     "SUBSECTIUNE" : ""



# ]
