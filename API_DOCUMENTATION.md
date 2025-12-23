# Pathfinders API Documentation

## 📚 Оглавление

- [API Документация](#pathfinders-api-documentation)
- [Swagger UI](#swagger-ui)
- [Аутентификация](#аутентификация)
- [API Endpoints](#api-endpoints)
  - [Clubs](#clubs-api)
  - [Users](#users-api)
  - [Logs](#logs-api)
  - [Scores](#scores-api)
- [Примеры запросов](#примеры-запросов)

## Swagger UI

После запуска приложения, интерактивная API документация доступна по адресу:

```
http://localhost:8080/swagger-ui.html
```

OpenAPI JSON спецификация:
```
http://localhost:8080/v3/api-docs
```

## Аутентификация

### Дефолтные учетные данные

**Директор:**
- Login: `dir`
- Password: `dir`
- Role: `DIRECTOR`

### Аутентификация через Web

```
GET /auth/login - страница входа
POST /auth/login - аутентификация
GET /auth/logout - выход
```

### Аутентификация через curl

```bash
# Получение cookie с сессией
curl -c cookies.txt -X POST http://localhost:8080/auth/login \
  -d "username=dir&password=dir"

# Использование cookie для запросов
curl -b cookies.txt http://localhost:8080/api/clubs
```

## API Endpoints

### Clubs API

#### GET /api/clubs
Получение списка всех клубов.

**Response:**
```json
[
  {
    "id": 1,
    "name": "Клуб Следопыт",
    "city": "Москва",
    "directorId": 1,
    "students": []
  }
]
```

#### GET /api/clubs/{id}
Получение информации о клубе по ID.

**Response:**
```json
{
  "id": 1,
  "name": "Клуб Следопыт",
  "city": "Москва",
  "directorId": 1,
  "students": [
    {
      "id": 2,
      "name": "Иван",
      "surname": "Иванов"
    }
  ]
}
```

#### POST /api/clubs
Создание нового клуба.

**Request:**
```json
{
  "name": "Новый клуб",
  "city": "Санкт-Петербург",
  "directorId": 1
}
```

**Validation:**
- `name`: 3-100 символов, обязательно
- `city`: 2-50 символов, обязательно
- `directorId`: обязательно

#### PUT /api/clubs/{id}
Обновление информации о клубе.

**Request:**
```json
{
  "id": 1,
  "name": "Обновленное название",
  "city": "Москва"
}
```

#### DELETE /api/clubs/{id}
Удаление клуба.

### Users API

#### GET /api/users
Получение списка всех пользователей.

#### GET /api/users/{id}
Получение информации о пользователе.

**Response:**
```json
{
  "id": 1,
  "name": "Директор",
  "surname": "Умолчания",
  "mobileNumber": "+79991234567",
  "email": "director@example.com",
  "login": "dir",
  "roleId": 1
}
```

#### POST /api/users
Создание нового пользователя.

**Request:**
```json
{
  "name": "Иван",
  "surname": "Петров",
  "mobileNumber": "+79997654321",
  "email": "ivan@example.com",
  "login": "ivan",
  "password": "password123",
  "roleId": 2
}
```

**Validation:**
- `name`: 2-50 символов
- `surname`: 2-50 символов
- `mobileNumber`: 10-15 цифр, может начинаться с +
- `email`: валидный email
- `login`: обязательно, уникально
- `password`: обязательно

### Logs API

#### GET /api/logs
Получение списка занятий.

#### POST /api/logs
Создание нового занятия.

**Request:**
```json
{
  "date": "2025-12-23",
  "topic": "Основы программирования",
  "clubId": 1
}
```

### Scores API

#### POST /api/scores
Выставление оценки ученику за занятие.

**Request:**
```json
{
  "studentId": 2,
  "logId": 1,
  "score": 5,
  "comment": "Отличная работа!"
}
```

**Validation:**
- `score`: 1-5
- `studentId`, `logId`: обязательны

## Примеры запросов

### Получение списка клубов

```bash
curl -b cookies.txt http://localhost:8080/api/clubs
```

### Создание нового клуба

```bash
curl -b cookies.txt -X POST http://localhost:8080/api/clubs \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Новый клуб",
    "city": "Казань",
    "directorId": 1
  }'
```

### Получение статистики клуба

```bash
curl -b cookies.txt http://localhost:8080/api/statistic/club/1
```

## Коды ответов

- `200 OK` - Успешный запрос
- `201 Created` - Ресурс создан
- `400 Bad Request` - Ошибка валидации
- `401 Unauthorized` - Требуется аутентификация
- `403 Forbidden` - Недостаточно прав
- `404 Not Found` - Ресурс не найден
- `500 Internal Server Error` - Внутренняя ошибка сервера

## Мониторинг

### Health Check

```bash
curl http://localhost:8080/actuator/health
```

**Response:**
```json
{
  "status": "UP",
  "components": {
    "db": {
      "status": "UP",
      "details": {
        "database": "PostgreSQL",
        "validationQuery": "isValid()"
      }
    }
  }
}
```

### Metrics

```bash
curl http://localhost:8080/actuator/metrics
```

### Prometheus

```bash
curl http://localhost:8080/actuator/prometheus
```
