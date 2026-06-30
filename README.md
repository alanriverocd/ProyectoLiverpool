# Proyecto backend - Liverpool (examen)

Este proyecto es la API del examen: Spring Boot + MongoDB. Aquí dejo pasos sencillos para levantarla y probarla en tu máquina.

Qué hace
- CRUD para Users, Orders y Deliveries.
- Enriquecimiento de pedidos con datos externos (pedidos + items).
- Búsqueda difusa, documentación Swagger y endpoints de ejemplo.

Requisitos
- Java 11
- Maven
- Docker y Docker Compose (opcional: puedes correr con `mvn spring-boot:run` o con el JAR)

Ejecutar local con Maven
1. Construye el JAR (se genera un fat-jar):

```bash
mvn -DskipTests package
```

2. Ejecutar directamente el JAR:

```bash
java -jar target/*.jar
```

Ejecutar con Docker Compose (rápido)
1. Asegúrate de tener el JAR creado (ver paso Maven) porque el `Dockerfile` copia `target/*.jar`.
2. Levanta los contenedores:

```bash
docker-compose up --build
```

Por defecto la app queda expuesta en `http://localhost:8089` (mapeo en `docker-compose.yml`). Mongo queda en el puerto `27017`.

Endpoints útiles
- Swagger UI: http://localhost:8089/swagger-ui/index.html
- API docs (OpenAPI): http://localhost:8089/v3/api-docs
- Health: http://localhost:8089/actuator/health

Ejemplos rápidos
- Crear un usuario:

```bash
curl -X POST http://localhost:8089/api/v1/users \
  -H "Content-Type: application/json" \
  -d '{"userId":"u123","nombre":"Juan","apellidoP":"Perez","apellidoM":"Lopez","email":"j@example.com","deliveryAddress":"Calle 1"}'
```

- Obtener usuario enriquecido (trae orders + items):

```bash
curl http://localhost:8089/api/v1/users/by-userid/u123
```

Tests
Ejecuta los tests con:

```bash
mvn test
```

Notas y consejos
- Si `docker-compose up --build` falla, asegúrate primero de correr `mvn -DskipTests package` para generar el JAR.
- Si el puerto `8089` ya está en uso, cambia el mapeo en `docker-compose.yml`.
- El mock externo de `pedidos` y `items` se usa para enriquecer orders; si necesitas pruebas más robustas puedo añadir WireMock o Testcontainers.

¿Qué sigo haciendo?
- Puedo añadir el `README` en formato más detallado, crear el pipeline CI (GitHub Actions), o preparar capturas/mini-video para la entrega. Dime cuál prefieres.
# Examen Técnico Backend - Liverpool

Proyecto Spring Boot para gestionar clientes y consultar pedidos desde servicios mock.

Repositorio: https://github.com/alanriverocd/ProyectoLiverpool.git

Requisitos locales:

1. Java 11 o superior
2. Maven
3. Docker & Docker Compose (opcional, se incluye `docker-compose.yml`)

Ejecutar localmente con Maven:

```bash
mvn spring-boot:run
```

Ejecutar con Docker Compose:

```bash
docker-compose up --build
```

Conexión a MongoDB:

- Por defecto la aplicación usa la variable de entorno `MONGODB_URI`.
- En `docker-compose.yml` el servicio `app` usa `mongodb://mongo:27017/liverpool_exam`.

Endpoints principales:
- `POST /api/v1/users` - crear cliente
- `GET  /api/v1/users` - listar clientes
- `GET  /api/v1/users/by-userid/{userId}` - obtener cliente y sus pedidos (campo `orders`)
- `PUT  /api/v1/users/{id}` - actualizar cliente
- `DELETE /api/v1/users/{id}` - eliminar cliente
- `GET  /api/v1/search/pedidos?q=texto` - búsqueda flexible contra /pedidos y /items

## Swagger UI

Cuando la aplicación esté corriendo (docker-compose o `java -jar target/*.jar`) abre:

- http://localhost:8089/swagger-ui.html  (redirecciona a la UI empaquetada)
- OpenAPI JSON: http://localhost:8089/v3/api-docs

Si la UI no carga, confirma que la app está accesible en el puerto `8089` y que `springdoc-openapi-ui` está en `pom.xml`.

## Run tests

Ejecuta las pruebas unitarias con Maven:

```bash
mvn test
```

El repositorio incluye pruebas MockMvc para `OrderController` y `DeliveryController`.

CI:

- Se incluye workflow de GitHub Actions en `.github/workflows/maven.yml` que compila y ejecuta pruebas.

Notas:

- He agregado `Dockerfile`, `docker-compose.yml`, `.dockerignore` y el workflow de CI.
- No hice push de estos cambios al remoto; están comprometidos localmente hasta que confirmemos las pruebas y ejecución.

Repositorio remoto: https://github.com/alanriverocd/ProyectoLiverpool.git

Ejecución con Docker (recomendado):

```bash
# Construir y levantar servicios (aplicación + MongoDB)
docker-compose up --build

# La aplicación escuchará en http://localhost:8080
```

Variables de entorno:
- `MONGODB_URI` (opcional) — URI de conexión a MongoDB. Por defecto `mongodb://localhost:27017/liverpool_exam`.

Integración continua:
- Hay un workflow de GitHub Actions en `.github/workflows/maven.yml` que compila el proyecto y ejecuta tests en cada push.
