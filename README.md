# Examen Técnico Backend - Liverpool

Proyecto Spring Boot mínimo para gestionar usuarios y buscar pedidos contra servicios mock.

Cómo ejecutar (local):

1. Instala Java 11+ y Maven.
2. Levanta MongoDB localmente o usa una URI remota.
3. Exporta la variable de entorno `MONGODB_URI` si usas otra instancia.

Ejecutar:

```bash
mvn spring-boot:run
```

Endpoints principales:
- `POST /api/v1/users` - crear usuario
- `GET  /api/v1/users` - listar usuarios
- `GET  /api/v1/users/by-userid/{userId}` - obtener usuario y sus pedidos (campo `orders`)
- `PUT  /api/v1/users/{id}` - actualizar usuario
- `DELETE /api/v1/users/{id}` - eliminar usuario
- `GET  /api/v1/search/pedidos?q=texto` - búsqueda flexible contra /pedidos y /items

Documentación OpenAPI: `http://localhost:8080/swagger-ui.html` (con springdoc-openapi)
