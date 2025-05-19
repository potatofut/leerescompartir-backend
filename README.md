# Leer es compartir - Backend

![Slogan](https://img.shields.io/badge/Slogan-Comparte_tus_libros_favoritos-blue)

Backend de la aplicación web "Leer es compartir", una plataforma para compartir libros entre lectores. Este proyecto proporciona los servicios API necesarios para el funcionamiento del frontend, que se encuentra en el [repositorio frontend](https://github.com/potatofut/biblioteca-prestamos).

## 📖 Descripción

"Leer es compartir" es una comunidad donde los usuarios pueden:
- Compartir los libros que ya han leído
- Descubrir nuevas historias
- Prestar y reservar libros de otros usuarios
- Explorar libros por temáticas y ubicaciones

**Slogan:** *Comparte tus libros favoritos. Únete a nuestra comunidad de lectores y comparte los libros que ya has leído. Descubre nuevas historias y permite que otros disfruten de tus lecturas favoritas.*

## 🛠️ Tecnologías

- **Java 17**
- **Spring Boot 3.x**
- **MongoDB** (Base de datos)
- **Spring Security** (Autenticación Basic Auth)
- **Maven** (Gestión de dependencias)
- **JUnit 5** + **Mockito** (Pruebas unitarias y de integración)

## 📂 Estructura del proyecto

```
.
├── src/
│   ├── main/
│   │   ├── java/com/compartir/libros/
│   │   │   ├── controller/       # Controladores REST
│   │   │   ├── dto/              # Objetos de transferencia de datos
│   │   │   ├── model/            # Entidades de MongoDB
│   │   │   ├── repository/       # Repositorios de MongoDB
│   │   │   ├── security/         # Configuración de seguridad
│   │   │   └── service/          # Lógica de negocio
│   │   └── resources/
│   │       └── application.properties
│   └── test/                     # Pruebas unitarias y de integración
├── target/                       # Artefactos generados
├── docker-compose.yml            # Configuración de Docker
├── pom.xml                       # Configuración de Maven
└── mvnw                          # Wrapper de Maven
```

## 🔌 API Endpoints

### 📚 Libros
- `GET /api/libros` - Obtener libros del usuario
- `POST /api/libros` - Agregar un nuevo libro
- `PUT /api/libros/{indice}` - Actualizar un libro
- `DELETE /api/libros/{indice}` - Eliminar un libro
- `PUT /api/libros/{indice}/estado` - Cambiar estado de un libro
- `POST /api/libros/reservar` - Reservar un libro
- `POST /api/libros/devolver` - Devolver un libro
- `GET /api/libros/prestados` - Obtener libros prestados
- `GET /api/libros/filtrar` - Filtrar libros por criterios
- `GET /api/libros/buscar` - Buscar libros por título

### 👤 Usuarios
- `POST /api/usuarios/registro` - Registrar nuevo usuario
- `POST /api/usuarios/login` - Iniciar sesión
- `PUT /api/usuarios/actualizar` - Actualizar perfil
- `POST /api/usuarios/cambiar-password` - Cambiar contraseña
- `GET /api/usuarios/perfil` - Obtener perfil del usuario

### 🏷️ Temáticas
- `GET /api/tematicas` - Obtener todas las temáticas
- `GET /api/tematicas/{id}` - Obtener temática por ID

### 🌍 Regiones
- `GET /api/regiones/continentes` - Obtener todos los continentes
- `GET /api/regiones/continentes/{nombreContinente}/paises` - Obtener países por continente
- `GET /api/regiones/continentes/{nombreContinente}/paises/{nombrePais}/provincias` - Obtener provincias por país
- `GET /api/regiones/continentes/{nombreContinente}/paises/{nombrePais}/provincias/{nombreProvincia}/ciudades` - Obtener ciudades por provincia

## 🚀 Instalación y ejecución

### Requisitos previos
- Java 17
- Maven
- MongoDB

### 1. Configuración local
1. Clonar el repositorio:
   ```bash
   git clone https://github.com/potatofut/leer-es-compartir.git
   cd leer-es-compartir
   ```

2. Configurar MongoDB en `application.properties`:
   ```properties
   spring.data.mongodb.uri=mongodb://localhost:27017/libros
   ```

3. Ejecutar la aplicación:
   ```bash
   ./mvnw spring-boot:run
   ```

La aplicación estará disponible en `http://localhost:8080`.

## 🧪 Pruebas
Ejecutar las pruebas con:
```bash
./mvnw test
```

## 🤝 Contribución
Las contribuciones son bienvenidas. Por favor, abre un issue o envía un pull request.

## 📄 Licencia
Este proyecto está bajo la licencia MIT. Ver el archivo `LICENSE` para más detalles.

---

**"Comparte tus libros favoritos"** - Conectando lectores a través de las páginas.
