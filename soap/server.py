import logging
from spyne import Application, rpc, ServiceBase, Integer, Unicode, Boolean
from spyne.protocol.soap import Soap11
from spyne.server.wsgi import WsgiApplication
from wsgiref.simple_server import make_server

# Modulos locales
from database import get_bd, engine, Base
from models import Alumno, EstatusAlumno

# -------------- Servicio SOAP --------------
class InscripcionesService(ServiceBase):
    
    # Crear un alumno
    @rpc(Unicode, Unicode, Unicode, Unicode, Unicode, _returns=Unicode)
    def inscribir_alumno(ctx, matricula, curp, nombre, apellido, email):
        db = get_bd()
        try:
            nuevo_alumno = Alumno(
                matricula=matricula, 
                curp=curp,
                nombre=nombre, 
                apellido=apellido, 
                email=email
            )
            db.add(nuevo_alumno)
            db.commit()
            return f"Éxito: Alumno {nombre} {apellido} registrado correctamente."
        except Exception as e:
            db.rollback()
            return f"Error al inscribir: {str(e)}"
        
    # Validar Inscripción
    @rpc(Unicode, _returns=Boolean)
    def validar_inscripcion(ctx, matricula_buscada):
        db = get_bd()
        
        # Buscamos alumno que coincida en matrícula Y que su estatus sea el ENUM 'ACTIVO'
        alumno = db.query(Alumno).filter(
            Alumno.matricula == matricula_buscada,
            Alumno.estatus == EstatusAlumno.ACTIVO
        ).first()
        
        if alumno:
            return True
        else:
            return False
        
# -------------- Configuracion de la APP --------------
application = Application(
    [InscripcionesService],
    tns='uav.soap.inscripciones',
    in_protocol=Soap11(validator='lxml'),
    out_protocol=Soap11()
)

wsgi_app = WsgiApplication(application)

if __name__ == '__main__':
    
    # Sincronizar tablas en Railway
    print("Conectando a Railway y revisando esquema de tablas...")
    Base.metadata.create_all(bind=engine)
    print("Tablas listas.")
    
    # Iniciar Servidor
    print("Servidor SOAP corriendo en http://127.0.0.1:8000")
    print("WSDL disponible en http://127.0.0.1:8000/?wsdl")
    
    logging.basicConfig(level=logging.WARNING)
    server = make_server('127.0.0.1', 8000, wsgi_app)
    server.serve_forever()

    