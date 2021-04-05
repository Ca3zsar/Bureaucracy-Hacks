###########
#  scrapper for dlep iasi-program 
#  write the program in a csv file 
#  then save the html for further research 
###########

import re
import os
import shutil
import requests 
import sys
from bs4 import BeautifulSoup

director = "./resources"

def deleteDirector(document_director):
  shutil.rmtree(document_director)


def createDirector(document_director):
   os.makedirs(document_director)

def go_spider_scrapper(): 
    URL='https://www.dlep-iasi.ro/program-de-lucru.html'
    page=requests.get(URL) 
    #make an array of headings 
    #each table has a heading 
    #that is not in the same class with the class of table..
    header = []  
    soup= BeautifulSoup(page.content,'html.parser') 
    soup.prettify(formatter=lambda s: s.replace(u'\xa0', ' '))
    results=soup.find(id='focus')
    tables=results.find_all('div',class_='divTable') 
    headings=results.find_all('div', class_='divTableHeading')
    pauze=[] 
    for heading in headings[:]: 
        if heading.text.startswith("Pauz"): 
            pauze.append(heading)
            headings.remove(heading)
            
    headings[-2].string = headings[-2].text + '\n' + headings[-1].text 
    headings.pop()  

    # first : take the program and write it in a csv file 
    content_for_csv = [ ] 
    field1 =[]
    field2 =[]
    field3 =[] 
    i=0
    for table in tables: 
        content_for_csv.append([  headings[tables.index(table)].text ] )
        for row in table.find_all('div',class_='divTableRow'):
            columns = row.find_all('div',class_='divTableCell')
            
            if len(columns) == 3 :
                field1 = columns[0].text
                field2 = columns[1].text 
                field3 = columns[2].text
            elif len(columns) == 2: 
                field1 = columns[0].text 
                field2 = columns[1].text
                filed3 = " "
            if (columns) :
                for_table=[field1,field2,field3] 
                content_for_csv.append(for_table) 
        

    import csv

    with open(director + '/program.csv', mode='w',encoding="utf-8") as program_file:
        program_writer = csv.writer(program_file, delimiter=',', quotechar='"', quoting=csv.QUOTE_MINIMAL)
        for row in content_for_csv: 
            program_writer.writerow(row)    
#second, save the html for further research  
    further_research = soup.find('div', class_='container')
    f = open(director+ "/data.html", "w+", encoding='utf-8')
    f.write(str(further_research))
    f.close()


def main():
    if os.path.isdir(director):
        deleteDirector(director)
    createDirector(director)
    go_spider_scrapper()


main()














