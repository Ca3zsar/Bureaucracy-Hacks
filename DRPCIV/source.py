from bs4 import BeautifulSoup
import requests
import os
import re
import urllib.request
import shutil # for deleting dirs

import selenium
from selenium import webdriver
from selenium.webdriver.chrome.options import Options  

from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.common.by import By
from selenium.webdriver.support.wait import WebDriverWait

import time
import copy
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


def downloadResources(url_page):
        download_index = 1
        keepRunning = True
        while keepRunning:
            chrome_options = Options() 
            # chrome_options.add_argument("--headless") 
            chrome_options.add_argument("download.default_directory=C:\\Users\\rafae\\Documents\\GitHub\\Bureaucracy-Hacks\\DRPCIV")
            driver = webdriver.Chrome(driverPath,options=chrome_options)
            driver.get(url_page)

            time.sleep(7)

            download_xpath = f'//*[@id="main"]/section/div/div/div/div/div[3]/div[2]/div[{download_index}]/a'

            try:
                driver.find_element_by_xpath(download_xpath).click()
                print('Am apasat pe buton')
                time.sleep(6)
            except Exception as exception:
                print('idk')
                return

            download_index = download_index + 1

            


def clickAndTakeSource(url_page):
    index = 1
    keepRunning = True
    while keepRunning:
        chrome_options = Options()  
        # chrome_options.add_argument("--headless") 
        driver = webdriver.Chrome(driverPath,options=chrome_options)
        driver.get(url_page)
        time.sleep(7)

        xPath = f'//*[@id="documentsContainer"]/div[{index}]/div/div[2]/h4/a'

        try:
            driver.find_element_by_xpath(xPath).click()
            
            time.sleep(6)
        except Exception as exception:
            return

        htmlContent = driver.execute_script("return document.body.innerHTML")

        soup = BeautifulSoup(htmlContent, "lxml")
        container = soup.select('.single-content')
        # downloadResources(driver.current_url)

        index = index + 1


def main():
    if os.path.isdir(director):
        deletingFiles()
    makeDirectors()


    clickAndTakeSource('https://www.drpciv.ro/documents-and-forms/inmatriculari')


#     chrome_options = Options()  
# #     # chrome_options.add_argument("--headless") 

#     driver = webdriver.Chrome(driverPath,options=chrome_options)
#     driver.get(url)
    
# # //*[@id="documentsContainer"]/div[1]/div/div[2]/h4/a
# # //*[@id="documentsContainer"]/div[2]/div/div[2]/h4/a
# # //*[@id="documentsContainer"]/div[3]/div/div[2]/h4/a
#     source = driver.page_source
#     soup = BeautifulSoup(source, "lxml")
    # time.sleep(7)

    print("hello")
    # driver.find_element_by_xpath('//*[@id="main"]/section/div/div/div/div[2]/div/div[2]/ul/li[1]/a').click()
#     # driver.find_element_by_css_selector('#documentsContainer > div:nth-child(1) > div > div.documents-content > h4 > a').click()

    # time.sleep(3)

    # driver2 = copy.copy(driver)

    # driver2.switch_to.window(driver2.current_window_handle)

    # print(driver2.current_url)

#     time.sleep(7)
#     htmlContent = driver.execute_script("return document.body.innerHTML")

#     newSoup = BeautifulSoup(htmlContent, "lxml")

#     # elements = newSoup.select('.documents-content')
#     elements = newSoup.find_all('h4', class_='documents-title')

#     for element in elements:
#         print(element.text)

#     print(elements)

    # driver.close()

main()
