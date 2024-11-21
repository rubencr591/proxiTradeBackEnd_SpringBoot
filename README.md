Este repositorio contiene el backend desarrollado en **Spring Boot(Kotlin)** ccomo parte de mi Trabajo de Fin de Grado (TFG). La aplicación es una plataforma de compraventa de artículos entre usuarios, diseñada con una arquitectura basada en microservicios y el modelo vista-controlador (MVC).

## Funcionalidades principales  
- **Gestión de usuarios**: Registro, login y autenticación segura mediante JWT (JSON Web Token).
- **Gestión de artículos**: Permite a los usuarios crear, editar, eliminar y consultar artículos en venta.
- **Ofertas entre usuarios**: Los usuarios pueden hacer y gestionar ofertas por los artículos de otros usuarios.
- **Base de datos**: Interacción con base de datos relacional para el almacenamiento de usuarios, artículos, y ofertas.

## Tecnologías utilizadas  
- **Spring Security**: Para gestionar la autenticación y autorización de usuarios de manera segura.
- **JWT (JSON Web Token)**: Mecanismo de autenticación basado en tokens para garantizar la seguridad de las sesiones de usuario.
- **Spring Data JPA**: Implementación de Java Persistence API (JPA) que facilita la interacción con la base de datos utilizando entidades y repositorios.
- **Firebase Admin SDK**: Usado para integrar servicios de Firebase, como la mensajería en tiempo real.
- **MySQL**: Base de datos relacional utilizada para almacenar la información de usuarios, productos y ofertas.
- **Spring WebSocket**: Para la implementación de comunicación en tiempo real utilizando WebSockets.
- **Spring Messaging**: Facilita la comunicación entre los diferentes componentes de la aplicación.
- **Lombok**: Utilizado para reducir el código repetitivo generando automáticamente los métodos `getters`, `setters`, `toString()`, etc.
- **Jackson**: Biblioteca de Java para el procesamiento de datos JSON, con soporte para Kotlin mediante el `jackson-module-kotlin`.
- **JUnit y Kotlin Test**: Frameworks de prueba para realizar pruebas unitarias y de integración en el proyecto.
- **Spring Boot DevTools**: Herramientas de desarrollo para mejorar la experiencia en la creación, prueba y depuración de la aplicación.
- **Maven**: Herramienta de automatización de construcción y gestión de dependencias.
- **FrontEnd**: Angular+Ionic (TypeScript) https://github.com/rubencr591/proxiTradeBackEnd_Springboot
