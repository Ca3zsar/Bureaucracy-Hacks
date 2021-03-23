from urllib.parse import urlparse
import urllib.request

import selenium
import os
import re

from selenium import webdriver
from selenium.webdriver.firefox.options import Options as FirefoxOptions

urlPrimarie = "http://www.primaria-iasi.ro/portal-iasi/pmi/primaria-municipiului-iasi/60/acte-necesare"
downloadDir = "/Primaria_Iasi_Files"

firefoxOptions = FirefoxOptions()
firefoxOptions.add_argument("--headless")

firefoxPref = webdriver.FirefoxProfile()
firefoxPref.set_preference("browser.download.manager.showWhenStarting", False)
firefoxPref.set_preference("browser.download.dir", downloadDir)
firefoxPref.set_preference("browser.helperApps.neverAsk.saveToDisk", "attachment/pdf")


firefoxDriver = webdriver.Firefox(options=firefoxOptions,firefox_profile=firefoxPref)
firefoxDriver.get(urlPrimarie)

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
        
        
downloadFiles(changeEntriesNumber())
firefoxDriver.quit()
    