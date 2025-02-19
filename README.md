# SpaceShip API

## Descripción

El proyecto **SpaceShip** es una aplicación basada en Spring Boot que proporciona una API RESTful para gestionar
usuarios y roles en una tienda online de productos eléctricos. Utiliza PostgreSQL como base de datos y Swagger para la
documentación interactiva de la API.

## Requisitos

- Java 17 o superior
- PostgreSQL
- Maven

## Configuración

1. **Base de datos**: Asegúrate de tener PostgreSQL instalado y en ejecución. La base de datos debe llamarse
   `spaceShip`. Usa las siguientes credenciales:

    - **URL**: `jdbc:postgresql://localhost:5432/spaceShip`
    - **Usuario**: `postgres`
    - **Contraseña**: `1234`

2. **Archivo `application.properties`**: Asegúrate de que tu archivo `application.properties` esté configurado
   correctamente. Aquí tienes un ejemplo de configuración:

```properties
spring.application.name=spaceShip
server.port=8080
# Configuración de la base de datos
spring.datasource.url=jdbc:postgresql://localhost:5432/spaceShip
spring.datasource.username=postgres
spring.datasource.password=1234
# Configuración opcional (Hibernate / JPA)
spring.jpa.hibernate.ddl-auto=create
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
# Swagger
springdoc.api-docs.path=/api-docs
```

## Iniciar el Proyecto

1. **Clonar el repositorio**:
    1. `git clone https://github.com/DarioCDDev/Spaceship-Back.git`
    2. `cd Spaceship-Back`
