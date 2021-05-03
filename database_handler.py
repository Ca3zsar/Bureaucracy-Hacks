import psycopg2

def get_connection():
    conn = psycopg2.connect(
        host="ec2-54-74-14-109.eu-west-1.compute.amazonaws.com",
        database="da385ubja6sllm",
        user="bvvqslqwgcnbiy",
        password="d39f9c8696f33a870b7ef5cff9399172613ce7a0649bbac57be63f713ca78c9b")
    
    return conn

def add_to_database(params):
    connection = get_connection()
    
    statement = "INSERT INTO files VALUES(%s, %s)"
    delete_statement = "DELETE FROM files"
    cursor = connection.cursor()
    
    cursor.execute(delete_statement)
    for key in list(params.keys()):
        statement = f"INSERT INTO files VALUES('{key}', '{params[key]}')"
        cursor.execute(statement)
        
    connection.commit()
    
    cursor.close()
    connection.close()