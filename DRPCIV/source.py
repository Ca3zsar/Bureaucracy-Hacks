from bs4 import BeautifulSoup
import requests
import os
import re
import urllib.request
import shutil # for deleting dirs

import selenium
from selenium import webdriver
from selenium.webdriver.chrome.options import Options  

import time
driverPath = ".\\chromedriver.exe"

divId = "acte"
director = "./HTMLFiles"
resources = "./resources"
url = "https://www.drpciv.ro/documents-and-forms/inmatriculari"


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


def main():
    if os.path.isdir(director):
        deletingFiles()
    makeDirectors()

    chrome_options = Options()  
    # chrome_options.add_argument("--headless") 

    driver = webdriver.Chrome(driverPath,options=chrome_options)
    driver.get(url)
    time.sleep(10)
    driver.find_element_by_xpath('//*[@id="main"]/section/div/div/div/div[2]/div/div[2]/ul/li[1]/a').click()
    source = driver.page_source
    soup = BeautifulSoup(source, "lxml")
    time.sleep(3)
    # elements = soup.find( class_ = "documents-category-name" )
    elements = soup.select('.documents-head')
    print(elements)

    driver.close()

main()
