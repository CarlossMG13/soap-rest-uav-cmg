import logging
from spyne import Application, rpc, ServiceBase, Integer, Unicode, Boolean, ComplexModel
from spyne.protocol.soap import Soap11
from spyne.server.wsgi import WsgiApplication
from wsgiref.simple_server import make_server

# Modulos locales
from database import get_bd, engine, Base
from models import Alumno, EstatusAlumno

# Modelo Complejo para devolver datos del alumno al consultar
class AlumnoModelResponse(ComplexModel):
    matricula = Unicode
    nombre = Unicode
    apellido = Unicode
    email = Unicode
    estatus = Unicode

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
        
    # Consultar Alumno (requisito previo para editar/eliminar)
    @rpc(Unicode, _returns=AlumnoModelResponse)
    def consultar_alumno(ctx, matricula_buscada):
        db = get_bd()
        alumno = db.query(Alumno).filter(Alumno.matricula == matricula_buscada).first()
        
        if alumno:
            # BD -> SOAP
            return AlumnoModelResponse(
                matricula=alumno.matricula,
                curp=alumno.curp,
                nombre=alumno.nombre,
                apellido=alumno.apellido,
                email=alumno.email,
                estatus=alumno.estatus.value
            )
        else:
            return None
        
    # Editar Alumno
    @rpc(Unicode, Unicode, Unicode, Unicode, Unicode, _returns=Unicode)
    def editar_alumno(ctx, matricula, nuevo_nombre, nuevo_apellido, nuevo_email, nuevo_estatus):
        db = get_bd()
        
        # Consultar (Validacion)
        alumno = db.query(Alumno).filter(Alumno.matricula == matricula).first()
        
        if alumno:
            try:
                # Actualizar datos
                alumno.nombre = nuevo_nombre
                alumno.apellido = nuevo_apellido
                alumno.email = nuevo_email
                
                # Actualizar estatus
                if nuevo_estatus:
                    alumno.estatus = EstatusAlumno(nuevo_estatus)
                
                db.commit()
                return f"Éxito: Alumno {matricula} actualizado a estatus {nuevo_estatus}."
            
            except ValueError:
                return "Error: Estatus inválido. Opciones: ACTIVO, BAJA_TEMPORAL, EGRESADO, SUSPENDIDO"
            except Exception as e:
                db.rollback()
                return f"Error al actualizar: {str(e)}"
        else:
            return "Error: No se puede editar. El alumno no existe."
        
    # Eliminar Alumno
    @rpc(Unicode, _returns=Unicode)
    def eliminar_alumno(ctx, matricula):
        db = get_bd()
        # Consultar
        alumno = db.query(Alumno).filter(Alumno.matricula == matricula).first()
        
        if alumno:
            # Eliminar
            db.delete(alumno)
            db.commit()
            return f"Éxito: Alumno {matricula} eliminado correctamente."
        else:
            return "Error: No se puede eliminar. El alumno no existe."
            
        
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
    print("Conectando a la BD...")
    Base.metadata.create_all(bind=engine)
    print("Tablas listas.")
    
    # Iniciar Servidor
    print("Servidor SOAP corriendo en http://127.0.0.1:8000")
    print("WSDL disponible en http://127.0.0.1:8000/?wsdl")
    
    logging.basicConfig(level=logging.WARNING)
    server = make_server('127.0.0.1', 8000, wsgi_app)
    server.serve_forever()

    