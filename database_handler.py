import psycopg2

def get_connection():
    conn = psycopg2.connect(
        host="ec2-54-74-14-109.eu-west-1.compute.amazonaws.com",
        database="da385ubja6sllm",
        user="bvvqslqwgcnbiy",
        password="d39f9c8696f33a870b7ef5cff9399172613ce7a0649bbac57be63f713ca78c9b")
    
    return conn

def add_to_database(params):
    connection = database_handler.get_connection()
    
    statement = "INSERT INTO files VALUES(%s, %s)"
    cursor = connection.cursor()
    
    cursor.executemany(statement,(params.keys(),params.valies())
    connection.commit()
    
    cursor.close()
    connection.close()