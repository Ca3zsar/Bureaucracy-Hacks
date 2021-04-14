import re
import os
import shutil
import requests
import sys
from bs4 import BeautifulSoup

URL = 'https://www.dlep-iasi.ro'
URLS = []

director = "./Content"
HTMLFiles = "./HTMLFiles"
acte = "./Acte"

url = []
page = requests.get(URL)
soup = BeautifulSoup(page.content, 'html.parser')

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


def getURLs():
    for links in soup.find_all('div', class_='evidenta'):
        for link in links.find_all('a'):
            lin = link.get('href')
            url = URL + '/?page=' + lin[1:-5] + '&n='
            URLS.append(url)


    for links in soup.find_all('div', class_='starea'):
        for link in links.find_all('a'):
            lin = link.get('href')
            url = URL + '/?page=' + lin[1:-5] + '&n='
            URLS.append(url)


def go_spider_scrapping(url):
    fisier = url.split("/")[-1]
    fileToWrite = fisier[0:-5]
    
    print(fileToWrite)
    url = URL+url
    
    page = requests.get(url)
    soup = BeautifulSoup(page.content, 'html.parser')
    soup.prettify(formatter=lambda s: s.replace(u'\xa0', ' '))
    
    for e in soup.findAll('br'):
        e.extract()
    
    information = soup.find('div', class_='camere_text')
    text = information.text.strip()
    text = text.replace(u'\xa0', u' ')
    
    text = text.strip()
    if(text.find('ACTE NECESARE') != -1 or text.find('Acte necesare') != -1):
        print(url)
        print(fileToWrite)
        path = f"{director}/{fileToWrite}.txt"
        
        f = open(path, "w+", encoding='utf-8')
        f.write(text)
        f.close()
        
        path = f"{HTMLFiles}/{fileToWrite}.html"
        f = open(path, "w+", encoding='utf-8')
        f.write(str(information))
        f.close()


def spider():
    conditieOprire = 'Nu s-a gasit nici o inregistrare.'
    searchFor = "acte-necesare"
    toContinue = True
    for page in URLS:
        pageNumber = 1
        while toContinue:
            newUrl = page+str(pageNumber)
            pageNumber += 1
            pageSearch = requests.get(newUrl)
            soup = BeautifulSoup(pageSearch.content, 'html.parser')
            stop = soup.find('div', class_='products')
            if(stop.text == conditieOprire):
                break
            else:
                for link in soup.find_all('div', class_='products'):
                    for link2 in link.find_all('div', class_='camere_thumb'):
                        for actual_link in link2.find_all('a'):
                            lin = actual_link.get('href')
                            title = actual_link['title']
                            go_spider_scrapping(lin)


# function for program
def go_spider_scrapper():
    URL = 'https://www.dlep-iasi.ro/program-de-lucru.html'
    page = requests.get(URL)
    # make an array of headings
    # each table has a heading
    # that is not in the same class with the class of table..
    header = []
    soup = BeautifulSoup(page.content, 'html.parser')
    soup.prettify(formatter=lambda s: s.replace(u'\xa0', ' '))
    results = soup.find(id='focus')
    tables = results.find_all('div', class_='divTable')
    headings = results.find_all('div', class_='divTableHeading')
    pauze = []
    for heading in headings[:]:
        if heading.text.startswith("Pauz"):
            pauze.append(heading)
            headings.remove(heading)

    headings[-2].string = headings[-2].text + '\n' + headings[-1].text
    headings.pop()

    # first : take the program and write it in a csv file
    content_for_csv = []
    field1 = []
    field2 = []
    field3 = []
    i = 0
    for table in tables:
        content_for_csv.append([headings[tables.index(table)].text])
        for row in table.find_all('div', class_='divTableRow'):
            columns = row.find_all('div', class_='divTableCell')

            if len(columns) == 3:
                field1 = columns[0].text
                field2 = columns[1].text
                field3 = columns[2].text
            elif len(columns) == 2:
                field1 = columns[0].text
                field2 = columns[1].text
                filed3 = " "
            if (columns):
                for_table = [field1, field2, field3]
                content_for_csv.append(for_table)

    import csv

    with open(director + '/program.csv', mode='w', encoding="utf-8") as program_file:
        program_writer = csv.writer(
            program_file, delimiter=',', quotechar='"', quoting=csv.QUOTE_MINIMAL)
        for row in content_for_csv:
            program_writer.writerow(row)
    # second, save the html for further research
    further_research = soup.find('div', class_='container')
    f1 = open(HTMLFiles + "/data.html", "w+", encoding='utf-8')
    f1.write(str(further_research))
    f1.close()


def main():
    deletingFiles()
    makeDirectors()
    
    getURLs()
    
    spider()
    go_spider_scrapper()


if __name__=="__main__":
    main()
