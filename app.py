# app.py
from flask import Flask, request, jsonify
from central_script import refresh_info
from rq import Queue
from rq.job import Job
from worker import conn
import os

app = Flask(__name__)
DEBUG = False if 'DYNO' in os.environ else True

@app.route('/refresh-info/', methods=['GET'])
def refresh():
    q = Queue(connection=conn)
    job = q.enqueue_call(refresh_info,timeout=5000)
    
    return f'<h2>Your request is being processed. Look for results <a href="https://check-diff.herokuapp.com/refresh-info/{job.get_id()}">here </a>'


@app.route("/refresh-info/<job_key>", methods=['GET'])
def get_results(job_key):

    job = Job.fetch(job_key, connection=conn)

    if job.is_finished:
        return jsonify(job.result), 200
    else:
        return "Wait!", 202

@app.route('/get-differences/', methods=['GET'])
def post_something():
    pass


@app.route('/get-sites/',methods=['GET'])
def get_sites():
    pass

@app.route('/get-specific-diff',methods=['GET'])

# A welcome message to test our server
@app.route('/')
def index():
    return "<h1>Welcome to our server !!</h1>"

if __name__ == '__main__':
    # Threaded option to enable multiple instances for multiple user access support
    app.run(threaded=True, port=5000,debug=DEBUG)