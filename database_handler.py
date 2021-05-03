import psycopg2
import os
def get_connection():
    conn = psycopg2.connect(
        host="ec2-176-34-105-15.eu-west-1.compute.amazonaws.com",
        database="d7kes16vrlnf4u",
        user="ogmoaildxvhcpo",
        password="34a32a687974e3b06fcb2dfb47ed23f13106b199d41ad2b54ce4d23984781523")
    
    return conn

def add_to_database(params):
    connection = get_connection()
    
    statement = "INSERT INTO anexe VALUES(%s, %s)"
    delete_statement = "DELETE FROM anexe"
    cursor = connection.cursor()
    
    cursor.execute(delete_statement)
    for key in list(params.keys()):
        statement = f"INSERT INTO anexe (filename, file_link) VALUES('{os.path.splitext(key)[0]}', '{params[key]}')"
        cursor.execute(statement)
        
    connection.commit()
    
    cursor.close()
    connection.close()