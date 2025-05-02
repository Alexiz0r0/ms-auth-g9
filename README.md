# Microservice 1: ms-auth

Este microservicio gestiona el registro y autenticación de usuarios con roles: SUPERADMIN, ADMIN, USUARIO.

## Tecnologías Utilizadas:
- Java 17 
- Spring Boot 3.3.5 
- Maven 
- Spring Security
- JWT (JSON Web Tokens)
- JUnit y Mockito (para unit test) 
- Jacoco (para cobertura de código) 
- SonarCloud (para análisis de calidad) 
- Eureka Server (para registro de servicios) 
- Spring Cloud Config Server (para configuración externa) 
- HashiCorp Vault (para gestión de secretos) 

## Endpoints:

#### Registrar usuarios

```http
POST /auth/register
```

| Parameter | Type     | Description                         |
|:----------|:---------|:------------------------------------|
| `nombre`  | `String` |                                     |
| `email`  | `String` |                                     |
| `password`  | `String` |                                     |
| `rol`  | `String` | `SUPERADMIN`,`ADMIN`,`USUARIO`            |

#### Autentica al usuario

```http
POST /auth/login
```

| Parameter | Type     | Description                         |
|:----------|:---------|:------------------------------------|
| `email`  | `String` |                                     |
| `password`  | `String` |                                     |

#### Validar el token

```http
GET /auth/validate
Authorization: Bearer eyJ0eXBlIjoiSldUIiwiYWxnIjoiSFM1MTIifQ.eyJyb2wiOiJTVVBFUkFETUlOIiwiaWF0IjoxNzQ2MjA3ODU2LCJleHAiOjE3NDYyMDgwOTYsInN1YiI6InN1cGVyQHN1cGVyLmNvbSJ9.K70iPItBEHeXNNfM7v6wurYYlzpSIgRTLCFeZYeZ6wZsdZ0-mYg1wr-rIgVMjwJXdwq1Cq1mjyuS7yqn1OiuMw
```

#### Listar todos los usuarios filtrados por el ROL `SUPERADMIN`

```http
GET /test/superadmin
Authorization: Bearer eyJ0eXBlIjoiSldUIiwiYWxnIjoiSFM1MTIifQ.eyJyb2wiOiJTVVBFUkFETUlOIiwiaWF0IjoxNzQ2MjA3Njk0LCJleHAiOjE3NDYyMDc5MzQsInN1YiI6InN1cGVyQHN1cGVyLmNvbSJ9.KPzQVzA-hHkSZt4p9AvqQW_AMqwgyxrBqMQb1Njo0T9vehHNFf9izeVBQdbeM-xOHcJElSvnrnJ1SowM002UFg
```

#### Listar todos los usuarios filtrados por el ROL `ADMIN`

```http
GET /test/admin
Authorization: Bearer eyJ0eXBlIjoiSldUIiwiYWxnIjoiSFM1MTIifQ.eyJyb2wiOiJBRE1JTiIsImlhdCI6MTc0NjIwNzczNCwiZXhwIjoxNzQ2MjA3OTc0LCJzdWIiOiJhZG1pbkBhZG1pbi5jb20ifQ.QOC1UboDMk3-aIjnyotwz3K5DiaYjiFSLNGyumMZYa0cQDn7jnSawaXv6n0PcGQN8CGNt5X9iiZTFcUnbiFjXw
```

#### Listar todos los usuarios filtrados por el ROL `USUARIO`

```http
GET /test/user
Authorization: Bearer eyJ0eXBlIjoiSldUIiwiYWxnIjoiSFM1MTIifQ.eyJyb2wiOiJVU1VBUklPIiwiaWF0IjoxNzQ2MjA3NzY1LCJleHAiOjE3NDYyMDgwMDUsInN1YiI6InVzZXJAdXNlci5jb20ifQ.32X2JVS75Bv9vGkayfTtzXhHlnyXwf93DOeMwO-lVMNho4IRgcoUgQP5eslxAf_YHkSjnoDmQEYZV9Q68XkFNA
```

## Links - Proyecto Completo:

[<a href="https://github.com/Alexiz0r0/ms-auth-g9"><img src="https://img.shields.io/badge/ms%20auth-1b1f23?style=for-the-badge&logo=springboot&logoColor=%23ffffff&labelColor=%236db33f"></a>](#) [<a href="https://github.com/Alexiz0r0/ms-productos-g9"><img src="https://img.shields.io/badge/ms%20productos-1b1f23?style=for-the-badge&logo=springboot&logoColor=%23ffffff&labelColor=%236db33f"></a>](#) [<a href="https://github.com/Alexiz0r0/ms-ordenes-g9"><img src="https://img.shields.io/badge/ms%20ordenes-1b1f23?style=for-the-badge&logo=springboot&logoColor=%23ffffff&labelColor=%236db33f"></a>](#) [<a href="https://github.com/Alexiz0r0/ms-eureka-server-g9"><img src="https://img.shields.io/badge/ms%20eureka%20server-1b1f23?style=for-the-badge&logo=spring&logoColor=%23ffffff&labelColor=%236db33f"></a>](#) [<a href="https://github.com/Alexiz0r0/ms-config-server-g9"><img src="https://img.shields.io/badge/ms%20config%20server-1b1f23?style=for-the-badge&logo=spring&logoColor=%23ffffff&labelColor=%236db33f"></a>](#) [<a href="https://github.com/Alexiz0r0/ms-config-files-g9"><img src="https://img.shields.io/badge/ms%20config%20files-fe603b?style=for-the-badge&logo=files&logoColor=%23ffffff&labelColor=%23181717" ></a>](#)
