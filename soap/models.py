import enum
from sqlalchemy import Column, Integer, String, DateTime, Enum
from database import Base

class EstatusAlumno(enum.Enum):
    ACTIVO = "ACTIVO"
    BAJA_TEMPORAL = "BAJA_TEMPORAL"
    EGRESADO = "EGRESADO"
    SUSPENDIDO = "SUSPENDIDO"

class Alumno(Base):
    __tablename__ = "alumnos"
    
    id = Column(Integer, primary_key=True, index=True)
    matricula = Column(String(20), unique=True, nullable=False)
    curp = Column(String(18), unique=True)
    nombre = Column(String(100), nullable=False)
    apellido = Column(String(100), nullable=False)
    email = Column(String(150), nullable=False)
    estatus = Column(Enum(EstatusAlumno), default=EstatusAlumno.ACTIVO, nullable=False)
    fecha_registro = Column(DateTime(timezone=True))    