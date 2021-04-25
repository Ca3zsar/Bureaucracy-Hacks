from flask import Flask, jsonify
from selenium import webdriver
from selenium.webdriver.firefox.firefox_binary import FirefoxBinary #We import this so we can specify the Firefox browser binary location
import os

app = Flask(__name__)
PORT = os.environ.get('PORT') or 3092
DEBUG = False if 'DYNO' in os.environ else True

FF_options = webdriver.FirefoxOptions()
FF_profile = webdriver.FirefoxProfile()
FF_options.add_argument("-headless")
FF_profile.update_preferences()


@app.route("/get")
def getcurrIp():
    try:
        #Notice in the driver, we specify the executable path to the geckodriver
        driver = webdriver.Firefox(options=FF_options, firefox_profile=FF_profile, executable_path=os.environ.get("GECKODRIVER_PATH"), firefox_binary=FirefoxBinary(os.environ.get("FIREFOX_BIN")))

        driver.get("https://icanhazip.com")

        ipaddr = driver.page_source#2

        driver.close()
        
        return jsonify({'status': 1, 'ip': ipaddr})
    except Exception as e:
        return jsonify({'status': 0, 'error': str(e)}), 500

@app.route('/')
def hello_world():
    return 'Hello, World!'

if __name__ == "__main__":
    app.run(port=PORT, debug=DEBUG)