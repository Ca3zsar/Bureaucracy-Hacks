import flask

app = flask.Flask(__name__)
app.config["DEBUG"] = True

@app.route('/', methods=['GET'])
def home():
    return "Distant Reading Archive: This site is a prototype API for distant reading of science fiction novels."

app.run()