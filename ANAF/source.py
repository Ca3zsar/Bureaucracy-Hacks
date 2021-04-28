from bs4 import BeautifulSoup
import requests
import os
import re
import urllib.request
from urllib.request import urlopen, urlretrieve, quote
from urllib.parse import urljoin
import shutil  # for deleting dirs

# Bacau, incasari persoane juridice, Luni, Marti, Miercuri, Joi, 8.30-14-30

divId = "anf_read_speaker01"
director = "./program"
url = "https://www.anaf.ro/anaf/internet/Iasi/contact_iasi/program_iasi/!ut/p/a1/pZI9C8IwEIZ_TdbeVTEVtxa1HygiKNYsEiXWSE0ljfbvmxYHB00Hb0qO57kjLwEGOTDFn7LgRlaKl-2d0UPiJzQZjAfZKp4hrkfzKN4sfYyn1AJ7C-CPCtHpR8Hb_wUsgr98nPpO36c9-6PgP98CPfntgLmQ7gUd4IrYuaTN0AlsewEKGbCirI7dj9iH6jgcF8C0OAsttPfQtn0x5l5PCBJsmsbjip89XRFsDwSlMkIrYQimvJa2e-I1J_ht2KWqDeQfM-B-29rKUabXUflchC8cOFyI/dl5/d5/L2dBISEvZ0FBIS9nQSEh/"

URL_1 = "http://static.anaf.ro/static/10/Galati/gl1_61.htm"
URL_2 = "http://static.anaf.ro/static/10/Galati/GL1_39.htm"

director = f"{os.path.dirname(__file__)}/Content"
HTMLFiles = f"{os.path.dirname(__file__)}/HTMLFiles"
acte = f"{os.path.dirname(__file__)}/Acte"


def deletingFilesForProgram():
    shutil.rmtree(director)
    print("Files deleted")


def makeDirectorsForProgram():
    os.makedirs(director)
    print("Files created..")


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


def getDataUrl1(var):
    htmlToText = str(var)
    # print(htmlToText)
    occurrence = 3
    ol_3rd_pos = [m.start() for m in re.finditer(r"<ol", htmlToText)]
    last_2_tags = '</body>' + '</html>'
    textToHtml = ''
    if len(ol_3rd_pos) >= 3:
        for i in range(ol_3rd_pos[occurrence-1], len(htmlToText)-(len(last_2_tags)+2)):
            textToHtml += htmlToText[i]
    else:
        print("No {} occurrence of substring lies in given string".format(occurrence))

    new_html = BeautifulSoup(textToHtml, 'html.parser')

    for a in new_html.findAll('a', href=True):
        a.extract()

    anaf_text = str(new_html.text)
    anaf_text = re.sub("\s(\s)+", "\n", anaf_text)

    with open(f"{director}\\ANAF_Persoane_Juridice_data.txt", "w", encoding="utf-8") as file:
        file.write(anaf_text)


def getDataUrl2(var):
    htmlToText = str(var)
    # print(htmlToText)
    occurrence = 10
    p_10th_pos = [m.start() for m in re.finditer(r"<p", htmlToText)]
    last_2_tags = '</body>' + '</html>'
    textToHtml = ''
    if len(p_10th_pos) >= 10:
        for i in range(p_10th_pos[occurrence-1], len(htmlToText)-(len(last_2_tags)+5)):
            textToHtml += htmlToText[i]
    else:
        print("No {} occurrence of substring lies in given string".format(occurrence))

    new_html = BeautifulSoup(textToHtml, 'html.parser')

    for a in new_html.findAll('a', href=True):
        a.extract()

    anaf_text = str(new_html.text)
    anaf_text = re.sub("\s(\s)+", "\n", anaf_text)

    with open(f"{director}\\ANAF_Certificat_Atestare_Fiscala_data.txt", "w", encoding="utf-8") as file:
        file.write(anaf_text)


def downloadDocuments(var, html_filename):
    a_tags = var.findAll("a")
    for link in a_tags:
        href = link.get('href')
        if href is None:
            continue
        if href.startswith('#'):
            continue
        if href.startswith('d') or href.startswith('A') or href.endswith("42.pdf"):
            continue
        filename = os.path.join(acte, href.rsplit('/', 1)[-1])
        if href.endswith('.pdf'):
            try:
                urlretrieve(href, filename)
            except:
                print('failed to download')

    # get HTML
    with open(f"{HTMLFiles}\\{html_filename}", "w", encoding="utf-8") as file:
        file.write(var.prettify())


def scrape_data():
    page = requests.get(URL_1)
    page1 = requests.get(URL_2)
    soup = BeautifulSoup(page.content, 'html.parser')
    soup1 = BeautifulSoup(page1.content, 'html.parser')

    getDataUrl1(soup)
    getDataUrl2(soup1)

    downloadDocuments(soup, "ANAF_Persoane_Juridice.html")
    downloadDocuments(soup1, "ANAF_Certificat_Atestare_Fiscala.html")

#####

# def fillContent(section):
#     path = director + "/file.html"
#     f = open(path, "w", encoding='utf-8')
#     f.write(str(section))
#     f.close()


def getTitles(soup):
    firstTitle = soup.select("td[bgcolor] > div > strong")
    otherTitles = soup.select("td[bgcolor] > strong")
    lastTitle = soup.select("td > div > strong")
    return firstTitle + otherTitles + lastTitle


def isTitle(titles, string):
    for title in titles:
        if str(title) in string:
            return True
    return False


def isTable(string):
    tagList = ["tr", "td", "sup"]
    notInTag = ["colspan", "table"]
    for tag in tagList:
        condition = tag in string
        if not condition:
            return False

    for tag in notInTag:
        if tag in string:
            return False
    return True


def getSchedule():
    source = requests.get(url).text
    soup = BeautifulSoup(source, 'lxml')
    section = soup.select("div#anf_read_speaker01 tr")

    titles = getTitles(soup)

    path = director + "/program.txt"
    f = open(path, "w", encoding='utf-8')

    for line in section:
        txt = str(line)

        if isTitle(titles, txt):
            f.write('\n')
            txt = re.sub('\<(.|\n)*?\>', '', txt)
            txt = re.sub('^\s*', '', txt)
            f.write(txt)
            f.write('\n')
        elif "•" in txt:
            # txt = re.sub('^\u00A0*','', txt)
            # txt = re.sub('^\s*','', txt)
            txt = re.sub('•', '\n•', txt)
            txt = re.sub('\<(.|\n)*?\>', '', txt)
            txt = re.sub(r"^\s+|\s+$", '', txt)

            f.write(txt)
            f.write('\n')
        elif isTable(txt):
            # txt = re.sub('^\s*','', txt)
            txt = re.sub('\<(.|\n)*?\>', '', txt)
            f.write(txt)
            f.write('\n')

    f.close()


def main():
    deletingFiles()
    makeDirectors()
    
    getSchedule()
    scrape_data()


if __name__ == "__main__":
    main()
