from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker
from sqlalchemy.ext.declarative import declarative_base

# --- CONFIGURACIÓN LOCAL ---
USUARIO = 'root'
PASSWORD = 'Alien2100' 
HOST = 'localhost'
PUERTO = '3306'
BASE_DATOS = 'db_matriculas_soap'

# Conexion
DATABASE_URL = f"mysql+mysqlconnector://{USUARIO}:{PASSWORD}@{HOST}:{PUERTO}/{BASE_DATOS}"

# Motor
engine = create_engine(DATABASE_URL)
SessionLocal = sessionmaker(autocommit=False, autoflush=False, bind=engine)
Base = declarative_base()

def get_bd():
    db = SessionLocal()
    try:
        return db
    finally:
        db.close()