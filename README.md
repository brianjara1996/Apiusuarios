# Apiusuarios
Api de consulta a usuarios 

# Instalación
Pasos para instalar el proyecto:

Clona el repositorio, en la siguientes url: https://github.com/brianjara1996/Apiusuarios.git
Abrir el proyecto con Eclipse(o cualquier otro IDE) y ejecutarlo con Java 1.8  


#Uso
Cómo usar el proyecto:

- Ejecutar la clase UsuariosApplication.java para iniciar el proyecto
- el Proyecto se Inicia en el puerto 8080
-El proyecto tiene dos EndPoints Expuestos:

/sign-up: El cual sirve para que un usuario no registrado en el sistema pueda hacerlo, se inserta los datos del usuario en la base de datos usuariosDB a traves de un json con los datos del usuario recibido

/login: El cual sirve para logearse al sistema un usuario ya registrado, se recibo los datos del usuario con un toke antes brindado para poder consultarlo en la base de datos usuariosDB y poder devolver el nuevo token


# Contribución

Brian Nicolas Jara (Developer)

Email: brianjara1996@gmail.com
 





