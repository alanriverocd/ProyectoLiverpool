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
