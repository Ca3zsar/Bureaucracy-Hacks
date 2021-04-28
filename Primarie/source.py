from urllib.parse import urlparse
import urllib.request
import requests
import unicodedata
import csv
import shutil

from bs4 import BeautifulSoup

import selenium 
import os
import re

from selenium import webdriver
from selenium.webdriver.chrome.options import Options

urlPrimarie = "http://www.primaria-iasi.ro/portal-iasi/pmi/primaria-municipiului-iasi/60/acte-necesare"
urlOrar = "http://www.primaria-iasi.ro/portal-iasi/pmi/meniu-pmi/54/program-cu-publicul"

director = f"{os.path.dirname(__file__)}/Content"
HTMLFiles = f"{os.path.dirname(__file__)}/HTMLFiles"
acte = f"{os.path.dirname(__file__)}/Acte"
csvProgram = "/program.txt"

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

def changeEntriesNumber(chromeDriver):
    chromeDriver.get("http://www.primaria-iasi.ro/portal-iasi/pmi/primaria-municipiului-iasi/60/acte-necesare")
    
    choiceButton = chromeDriver.find_element_by_xpath("/html/body/div[2]/div/div[1]/div/div[3]/div/div[2]/div[1]/div[1]/div/label/select");
    # choiceButton = chromeDriver.find_element_by_xpath('//*[@id="fisierePMI_length"]/label/select')
    # choiceButton = chromeDriver.find_elements_by_name("fisierePMI_length")
    # choiceButton = chromeDriver.find_element_by_css_selector("#fisierePMI_length > label > select")
    # choiceButton.click()
    chromeDriver.execute_script("arguments[0].click()",choiceButton)
    
    entriesButton = chromeDriver.find_element_by_xpath("/html/body/div[2]/div/div[1]/div/div[3]/div/div[2]/div[1]/div[1]/div/label/select/option[4]")
    # entriesButton = chromeDriver.find_element_by_css_selector("#fisierePMI_length > label > select > option:nth-child(4)")
    # entriesButton.click()
    chromeDriver.execute_script("arguments[0].click()",entriesButton)
    
    page = requests.get(urlPrimarie)
    soup = BeautifulSoup(page.content,'html.parser', from_encoding="utf-8")
        
    programClass = soup.find("div",class_="col-sm-12")
    
    with open(f"{HTMLFiles}/info.html","w",encoding="utf-8") as file:
        file.write(programClass.prettify())
    
    
    downloadButtons = chromeDriver.find_elements_by_xpath("/html/body/div[2]/div/div[1]/div/div[3]/div/div[2]/div[2]/div/table/tbody/tr/td[1]/a")
    
    urls = []
                                                            
    for button in downloadButtons:
        urls.append(button.get_attribute("href"))
    
    driver.quit()
    
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
        response =  urllib.request.urlopen(url)
        
        parsed = urlparse(url)
        fileName = os.path.basename(parsed.path)
        
        file = open(f"{fileName}","wb")
        file.write(response.read())
        file.close()
        
        os.rename(f"{fileName}", f"{acte}\\{fileName}")
    

def getProgram(url):
    page = requests.get(url)
    soup = BeautifulSoup(page.content,'html.parser', from_encoding="utf-8")
        
    programClass = soup.find("div",class_="col-xs-12 col-sm-12 col-md-12 text-justify")
    
    with open(f"{HTMLFiles}/orar.html","w",encoding="utf-8") as file:
        file.write(programClass.prettify())
    
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

    secondaryText = ""
    
    for paragraph in paragraphs[:]:
        text = paragraph.text
        
        text = text.replace("Ţ","Ț")
        text = text.replace("ț","ț")
        
        found = 0
        
        for word in toLookFor:
            if text.lower().find(word) != -1:
                found = 1
                
                if toLookFor.index(word) == 0:
                    secondFound = 0
                    for anotherWord in toLookFor[1:]:
                        if text.lower().find(anotherWord) != -1:
                            secondFound = 1
                            break
                        
                    if secondFound == 0:
                        secondaryText = "Program cu publicul : "
                        found = 0
                
                break
            
        if secondaryText != "" and found == 0:
            continue
        
        if found:
            currentIndex = paragraphs.index(paragraph)
            
            for i in range(currentIndex-1,-1,-1):
                secondText = paragraphs[i].text
        
                secondText = secondText.replace("Ţ","Ț")
                secondText = secondText.replace("ț","ț")
                
                words = secondText.split()
                if len(words) != 0:
                    if words[0].lower() in institutes:
                        programs.append(f"{secondaryText}{text}")
                        directors.append(secondText)
                        
                        secondaryText = ""
                        break
            
            paragraphs = paragraphs[currentIndex+1:]
    
    file = open(f"{director}/orar.txt","w",encoding="utf-8")
    
    for i in range(len(directors)):
        file.write(directors[i])
        file.write(programs[i])
    
    file.close()
    
def getDriver():
    chromeOptions = Options()
    chromeOptions.binary_location = os.environ.get("GOOGLE_CHROME_BIN")
    chromeOptions.add_argument("--headless")   
    chromeOptions.add_argument('--disable-gpu')
    chromeOptions.add_argument('--no-sandbox')
    chromeOptions.add_argument('--disable-dev-shm-usage')
    
    chromeDriver = webdriver.Chrome(executable_path=os.environ.get("CHROMEDRIVER_PATH"),chrome_options=chromeOptions)
    return chromeDriver

def main():
    deletingFiles()
    makeDirectors()
    
    downloadFiles(changeEntriesNumber(getDriver()))
    getProgram(urlOrar)
    
if __name__=="__main__":
    main()
