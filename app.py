# app.py
from flask import Flask, request, jsonify,redirect, url_for
from executor import refresh_info, get_files_list

from rq import Queue
from rq.job import Job
from worker import conn
import os, json

app = Flask(__name__)
DEBUG = False if 'DYNO' in os.environ else True

LOADED_DATA = 0
DATA_LINK = ''

@app.route('/refresh-info/<forced>', methods=['GET'])
def refresh(forced):
    global LOADED_DATA, DATA_LINK
    
    if forced=='forced':
      q = Queue(connection=conn)
      job = q.enqueue_call(refresh_info,timeout=5000)
    else:
      if not LOADED_DATA:
        q = Queue(connection=conn)
        job = q.enqueue_call(refresh_info,timeout=5000)
        
    LOADED_DATA = 1
    DATA_LINK = job.get_id()
    return redirect(f"https://check-diff.herokuapp.com/refresh-info/{DATA_LINK}",code=202)


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


@app.route('/get-files/',methods=['GET'])
def get_files():
  if LOADED_DATA:
    files = get_files_list()
  else:
    refresh()
  
  return jsonify(files), 200


@app.route('/')
def sign_s3():
  # S3_BUCKET = os.getenv('S3_BUCKET_NAME')

  # file_name = 'C:\\Users\\cezar\\Desktop\\dbms_sql.sql'
  # file_type = 'sql'

  # s3 = boto3.client('s3')

  # s3.upload_file(file_name,S3_BUCKET,file_name)
    
  # return json.dumps({
  #   'data': presigned_post,
  #   'url': 'https://%s.s3.amazonaws.com/%s' % (S3_BUCKET, file_name)
  # })
  
  pass

if __name__ == '__main__':
    # Threaded option to enable multiple instances for multiple user access support
    app.run(threaded=True, port=5000,debug=DEBUG)