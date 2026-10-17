# CarritoGT

Migración del carrito de compras básico hecho en PHP 7 orientado a objetos (Twig, sin framework) a una **API REST con Spring Boot** organizada por capas, con un frontend en **HTML + JavaScript (fetch)** que la consume.

Proyecto de Programación II, Ingeniería en Sistemas (UMG).

## 1. Tecnologías

| Parte | Tecnología |
|---|---|
| Sistema operativo | Lubuntu 22.04 en VirtualBox |
| Backend | Java 17 (OpenJDK 17.0.20), Spring Boot 4.1.1, Maven (wrapper `mvnw`) |
| Dependencias | Spring Web, Spring Data JPA, MySQL Driver |
| Base de datos | MySQL 8.0.46 |
| Frontend | HTML, CSS (reutilizado de la versión PHP) y JavaScript con `fetch` |
| IDE | IntelliJ IDEA Community |

## 2. Requisitos

- Java 17 o superior (`java -version`)
- MySQL 8 en `localhost:3306`
- Python 3 (solo para servir el frontend en desarrollo)
- Git

## 3. Estructura del repositorio

- `backend/`: API REST en Spring Boot (`src/main/java/com/carritogt/`)
  - `entity/`: Producto, ShoppingCart (tablas)
  - `repository/`: ProductoRepository, ShoppingCartRepository (acceso a datos)
  - `service/`: ProductoService, ShoppingCartService (lógica y errores)
  - `controller/`: ProductoController, ShoppingCartController (endpoints HTTP)
  - `config/`: CorsConfig (CORS)
- `frontend/`: index.html (catálogo), carrito.html, js/, css/, images/
- `database/`: cc.sql (esquema de 2 tablas y 4 productos de prueba)
- `docs/`: diagramas y evidencias

## 4. Instalación

1. Clonar el repositorio:

        git clone https://github.com/jherandeza-code/proyecto-carritogt.git
        cd proyecto-carritogt

2. Crear la base de datos con el script (si no existe):

        sudo mysql < database/cc.sql

3. Crear el usuario de la aplicación, con solo los permisos que necesita:

        CREATE USER 'carrito'@'localhost' IDENTIFIED BY 'Carrito_2026';
        GRANT SELECT, INSERT, UPDATE, DELETE ON cc.* TO 'carrito'@'localhost';
        FLUSH PRIVILEGES;

## 5. Cómo iniciar los servicios

Se usan dos terminales:

    # Terminal 1: API (http://localhost:8080)
    cd backend
    ./mvnw spring-boot:run

    # Terminal 2: frontend (http://localhost:5500)
    cd frontend
    python3 -m http.server 5500

Luego abrir `http://localhost:5500` en el navegador.

## 6. Archivos de configuración

Archivo: `backend/src/main/resources/application.properties`

| Propiedad | Valor | Para qué |
|---|---|---|
| `server.port` | `8080` | Puerto de la API |
| `spring.datasource.url` | `jdbc:mysql://localhost:3306/cc` | Servidor, puerto y base de datos |
| `spring.datasource.username` | `${DB_USER:carrito}` | Usuario de MySQL |
| `spring.datasource.password` | `${DB_PASSWORD:Carrito_2026}` | Contraseña de MySQL |
| `spring.jpa.hibernate.ddl-auto` | `none` | No modifica el esquema: se usan las 2 tablas del script |
| `spring.jpa.hibernate.naming.physical-strategy` | `PhysicalNamingStrategyStandardImpl` | Usa los nombres de columna tal cual (`idProducto`, `codUsuario`) |
| `server.error.include-message` | `always` | Incluye el mensaje del error en la respuesta JSON |

El usuario y la contraseña pueden cambiarse sin tocar el código, con variables de entorno:

    DB_USER=otro_usuario DB_PASSWORD=otra_clave ./mvnw spring-boot:run

Otras configuraciones que dependen del equipo:

- **CORS:** `backend/src/main/java/com/carritogt/config/CorsConfig.java` solo permite el origen `http://localhost:5500` y los métodos GET, POST y DELETE.
- **Dirección de la API en el frontend:** constante `API` en `frontend/js/app.js` (`http://localhost:8080/api`).

## 7. Endpoints principales

| Método | Ruta | Propósito | Respuesta |
|---|---|---|---|
| GET | `/api/productos` | Listar los productos | 200 + lista JSON |
| GET | `/api/carrito?usuario={codigo}` | Ver el carrito de un visitante | 200 + lista JSON |
| POST | `/api/carrito?usuario={codigo}&idProducto={id}` | Agregar un producto al carrito | 201 + ítem creado; 404 si el producto no existe; 400 si falta un parámetro |
| DELETE | `/api/carrito/{idShoppingCart}` | Eliminar un ítem del carrito | 204; 404 si el ítem no existe |

Ejemplo:

    curl -i http://localhost:8080/api/productos
    curl -i -X POST "http://localhost:8080/api/carrito?usuario=prueba&idProducto=1"

## 8. Limitaciones conocidas

- Cada clic en "Agregar" crea un ítem con cantidad 1; no suma ni modifica cantidades.
- No hay opción para vaciar el carrito completo ni se muestra el total.
- No hay usuarios registrados: el carrito se identifica con un código aleatorio guardado en el navegador (`localStorage`); si se borran los datos del navegador, se pierde el acceso a ese carrito.
- El frontend se sirve con el servidor de desarrollo de Python, no con Apache.
- La contraseña de MySQL por defecto queda en `application.properties` para facilitar la instalación; en producción debe pasarse por variables de entorno.
