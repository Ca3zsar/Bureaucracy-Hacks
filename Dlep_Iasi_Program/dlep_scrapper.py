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
results=soup.find(id='focus')

tables=results.find_all('div',class_='divTable') 
headings=results.find_all('div', class_='divTableHeading')

for table in tables: 
    print("this is another table of interest--------------------------------")
    for row in table.find_all('div',class_='divTableRow'):
       # print(row.text)
        column = row.find_all('div',class_='divTableCell')
        if column:
          print("first column")
          print(column[0].text)
          print("second column")
          print(column[1].text)
          print("third column")
          print(column[2].text)


#for heading in headings: 
    #print("this is a heading---------------------") 
    #header.append(heading.text)


#for head in header: 
    #print(head)




print("all good!") 