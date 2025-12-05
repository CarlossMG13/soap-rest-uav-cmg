from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker
from sqlalchemy.ext.declarative import declarative_base

# --- CONFIGURACIÓN LOCAL (LOCALHOST) ---
# Reemplaza 'TU_CONTRASENA' por la contraseña real de tu MySQL local
USUARIO = 'root'
PASSWORD = 'Alien2100' 
HOST = 'localhost'
PUERTO = '3306'
BASE_DATOS = 'db_matriculas_soap'

# Construimos la URL de conexión local
DATABASE_URL = f"mysql+mysqlconnector://{USUARIO}:{PASSWORD}@{HOST}:{PUERTO}/{BASE_DATOS}"

# Creamos el motor
engine = create_engine(DATABASE_URL)
SessionLocal = sessionmaker(autocommit=False, autoflush=False, bind=engine)
Base = declarative_base()

def get_bd():
    db = SessionLocal()
    try:
        return db
    finally:
        db.close()