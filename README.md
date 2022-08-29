Nisum
---
Examen Practico


### Pre-requisitos

* Java 11 (JDK)
* Gradle
* Git

## Instalacion

##### Instalar Lombok en el IDE

_Esto depende del IDE(1) que utilices, seguir indicaciones de la web de [Project Lombok](https://projectlombok.org/)


##### Ejecutar tests para ver que esta todo en orden

    gradlew verify

##### Iniciar aplicacion

    gradlew bootRun

## URLs

---

* [Crear un usuario](http://localhost:8080/app/user/create) Realiza el alta del usuario y entrega los datos juntos con el token.
* [Obtener todos los usarios creados](http://localhost:8080/app/users) Utiliza JWT como metodo de autorizacion (Bearer Token).

## Construido con:

---

Herramientas
* [Spring boot](https://spring.io/projects/spring-boot) - Framework de java
* [Spring data rest](https://spring.io/projects/spring-data-rest) - Biblioteca para generar una API REST a partir de una @Entity
* [Json Web Token](https://jwt.io/introduction) Herramienta para segurizar la comunicacion entre cliente/servidor
* [Lombok](https://projectlombok.org/) Libreria para evitar el codigo repetitivo
* [h2database](https://www.h2database.com/html/main.html) Base de datos en memoria


## Notas
(1) _Se recomienda [IntelliJ Community](https://www.jetbrains.com/idea/download/) o [Eclipse IDE for Enterprise Java Developers](https://www.eclipse.org/downloads/packages/)****
