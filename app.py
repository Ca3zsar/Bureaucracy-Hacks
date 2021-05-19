# app.py
from flask import Flask, request, jsonify, redirect, url_for
import flask
import requests
from executor import refresh_info, get_files_list, add_to_S3
import check_diff
import auto_complete

from rq import Queue
from rq.job import Job
from worker import conn
import os
import json

app = Flask(__name__)
DEBUG = False if 'DYNO' in os.environ else True

LOADED_DATA = 0
LOADING_DATA = 0
DATA_LINK = ''
VALUE_TO_RETURN = ''
FILES_TO_RETURN = ''    

@app.route('/refresh-info/', methods=['GET'])
def refresh():
    global LOADED_DATA, DATA_LINK, LOADING_DATA
    if LOADED_DATA == 0 and LOADING_DATA == 0:
        LOADING_DATA = 1
        q = Queue(connection=conn)
        job = q.enqueue_call(refresh_info, timeout=5000)
        DATA_LINK = job.get_id()
        return redirect(f"https://check-diff.herokuapp.com/refresh-info/{DATA_LINK}", code=202)

    if LOADING_DATA == 1:
        return redirect(f"https://check-diff.herokuapp.com/refresh-info/{DATA_LINK}", code=202)
    
    return jsonify(VALUE_TO_RETURN), 200

@app.route('/refresh-forced/', methods=['GET'])
def refresh_forced():
    global LOADING_DATA, LOADED_DATA,DATA_LINK
    if LOADING_DATA == 0:
      q = Queue(connection=conn)
      job = q.enqueue_call(refresh_info, timeout=5000)
      LOADING_DATA = 1
      DATA_LINK = job.get_id()

    return redirect(f"https://check-diff.herokuapp.com/refresh-info/{DATA_LINK}", code=202)


@app.route("/refresh-info/<job_key>", methods=['GET'])
def get_results(job_key):
    global LOADING_DATA,VALUE_TO_RETURN,LOADED_DATA, FILES_TO_RETURN
    try:
        job = Job.fetch(job_key, connection=conn)
        if job.is_finished:
            LOADING_DATA = 0
            LOADED_DATA = 1
            VALUE_TO_RETURN = job.result[0]
            FILES_TO_RETURN = job.result[1]
            os.environ["FILE_VERSION"] = str(int(os.environ.get("FILE_VERSION"))+1)
            return jsonify(VALUE_TO_RETURN), 200
        else:
            return jsonify({"error":"info not loaded yet"}), 202
    except:
        return jsonify({'error':'there is no longer a job with this id'})


@app.route("/complete-file/",methods=["POST"])
def complete_file():
    information = request.json
    fileURL = information["file_url"]
    
    fileName = fileURL.split('/')[-1]
    fileName = fileName.replace('+',' ')
    
    print(fileName)
    
    found = 0
    with open("annotated.txt","r") as file:
        content = [file.strip() for file in file.readlines()]
        content = [line.lower() for line in content]
        if fileName.lower() in content:
            found = 1
    
    if not found:
        return fileURL
    else:
        information.pop("file_url")
        newFileName = f"Annotated_{information['nume']}_{information['prenume']}_{fileName}"
        
        result = requests.get(f"https://bureaucracy-files.s3.eu-central-1.amazonaws.com/Annotated/{fileName}")
        with open(fileName,"wb") as file:
            file.write(result.content)
        
        auto_complete.update_form_values(fileName, newFileName,information)
        
        links = add_to_S3([newFileName],'Annotated')
        return links[0]

    return 1    

@app.route('/get-differences/', methods=['GET'])
def diff():
    differences = check_diff.compareFiles('https://bureaucracy-files.s3.eu-central-1.amazonaws.com')
    return jsonify(differences)

@app.route('/get-files/', methods=['GET'])
def get_files():
    global LOADED_DATA, FILES_TO_RETURN
    if LOADED_DATA == 1:
        return jsonify(FILES_TO_RETURN), 200
    else:
        if FILES_TO_RETURN == '':
            FILES_TO_RETURN = get_files_list("https://bureaucracy-files.s3.eu-central-1.amazonaws.com") 
        return jsonify(FILES_TO_RETURN),200


@app.route('/')
def home():
    return '<h1>Hello</h1>'


if __name__ == '__main__':
    # Threaded option to enable multiple instances for multiple user access support
    app.run(threaded=True, port=5000, debug=DEBUG)
