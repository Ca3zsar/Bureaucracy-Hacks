import requests 
from bs4 import BeautifulSoup
import pprint


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
#print(headings[-1].text)

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
    

#for row in content_for_csv: 
 #   print(row) 

#content_for_csv.replace((u'\u2013', '-'))

import csv

with open('program.csv', mode='w',encoding="utf-8") as employee_file:
    employee_writer = csv.writer(employee_file, delimiter=',', quotechar='"', quoting=csv.QUOTE_MINIMAL)
    for row in content_for_csv: 
        employee_writer.writerow(row)
    #employee_writer.writerow(['John Smith', 'Accounting', 'November'])
    #employee_writer.writerow(['Erica Meyers', 'IT', 'March'])

print("all good!") 












