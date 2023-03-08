# Equipo-1---Hotel-California 
Descripción General Proyecto Desarrollo de tres microservicios los cuales son enfocados a crear reservaciones y realizar la factura de dicha reservación
 
 Habitaciones: Encargado de proveer información relacionada a las habitaciones como su costo, el número de ocupantes máximos o el estado de la habitación

Reservaciones: Enfocado naturalmente en generar reservaciones controlando el estado de disponibilidad de las habitaciones.
 
 Facturas: El cual recibe reservas calculando los costos totales dependiendo de la cantidad de días a ocupar una habitación 

Comandos para configuracion del proyecto Abir una terminal de Git Bash en el directorio donde vaya a guardar el proyecto Mediante la terminal Git Bash ejecutar el comando git clone https://github.com/ASOMinsait/Equipo-1-Hotel-California.git Seguidamente en la terminal ingresar el siguiente comando  cd Equipo-1-Hotel-

California Finalmente ingresamos el comando  para iniciar los servicios docker-compose up --build -d
y para detener el proyecto se utilza docker-compose down Los microservicios se ejecutaran y podran ser accesibles mediante las siguientes URLs para verficar su funcionamiento http://localhost:8001/swagger-ui/index.html  ->Facturashttp://localhost:8002/swagger-ui/index.html  ->habitacioneshttp://localhost:8003/swagger-ui/index.html  ->reservas 
