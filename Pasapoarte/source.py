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

def fillContent(section):
    path = director + "/file.html"
    f = open(path, "w", encoding='utf-8')
    f.write(str(section))
    f.close()


# headless

def main():
    if os.path.isdir(director):
        deletingFiles()
    makeDirectors()


    driver = webdriver.Chrome(driverPath)
    driver.get(url)
    source = driver.page_source
    soup = BeautifulSoup(source, "lxml")


    i = 0
    for line in soup.select("#acte > div[align] > a"):
        i = i + 1

        fileName = f"file {i}"

        href = line['href']

        print(str(i) + " " + href)



    print(i)

    # print(section)




    time.sleep(10)

    driver.quit()


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
