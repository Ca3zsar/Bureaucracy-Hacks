from bs4 import BeautifulSoup
import requests
import os
import re
import urllib.request
import shutil # for deleting dirs

import selenium
from selenium import webdriver


import time


driverPath = ".\\chromedriver.exe"

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
    f = open(fileName, "a+", encoding='utf-8')
    f.write(str(section))
    f.close()


def getTextFromTag(tag):
    txt = re.sub('<.*?>', '', str(tag))
    # txt = re.sub(r"^\s+|\s+$",'', txt)
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
        
        subUrl = line['href']

        subDriver = webdriver.Chrome(driverPath)
        subDriver.get(subUrl)
        source  = subDriver.page_source
        subSoup = BeautifulSoup(source, "lxml")

 

        title = getTextFromTag(line)

        section1 = subSoup.select("article button")

        section2 = subSoup.select("content > div > div > div > section > div > div > div > div > div > div")


        for index in range(0,len(section1)):
            i = i + 1
            fileName = f"file{i}"
            path = f"{director}\\{fileName}.html"
            fillContent(title + "\n", path)
            # fillContent(section1[index], path)
            # fillContent(section2[index], path)
            fillContent(getTextFromTag(section1[index]), path)
            fillContent(getTextFromTag(section2[index]), path)

        subDriver.close()

    driver.quit()

#post_1525 > content > div > div > div > section > div > div > div > div > div > div:nth-child(6)
#post_1267 > content > div > div > div > section > div > div > div > div > div > div:nth-child(6)


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
