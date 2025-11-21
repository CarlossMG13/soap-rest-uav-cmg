from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker
from sqlalchemy.ext.declarative import declarative_base

#---------------------------------------------------------
# CREDENCIALES DE RAILWAY
# ---------------------------------------------------------

RAILWAY_URL = "mysql://root:LFGNynBdZestEZKaKzdJkVxdbmNvrqnN@turntable.proxy.rlwy.net:14879/railway"

# Reemplazo automático para usar el driver correcto de Python
if RAILWAY_URL.startswith("mysql://"):
    CONNECTION_URL = RAILWAY_URL.replace("mysql://", "mysql+mysqlconnector://", 1)
else: 
    CONNECTION_URL = RAILWAY_URL
    
# Motor de la conexion

# pool_recycle=3600 Evita que Railway cierre la conexión por inactividad
engine = create_engine(CONNECTION_URL, pool_recycle=3600)
Base = declarative_base()
SessionLocal = sessionmaker(autocommit=False, autoflush=False, bind=engine)

# Obtener sesion de BD
def get_bd():
    db = SessionLocal()
    try: 
        return db
    finally:
        db.close()