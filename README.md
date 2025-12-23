# 🎯 Pathfinders - Электронный журнал клуба "Следопыт"

Современное web-приложение для управления клубами "Следопыт", ведения журнала занятий и оценки учеников.

[![CI/CD Pipeline](https://github.com/kozionov/Pathfinders/actions/workflows/ci.yml/badge.svg)](https://github.com/kozionov/Pathfinders/actions/workflows/ci.yml)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Java](https://img.shields.io/badge/Java-17-blue)](https://openjdk.org/projects/jdk/17/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.1.2-green)](https://spring.io/projects/spring-boot)

## ✨ Особенности

- ✅ **Spring Security** - полноценная аутентификация и авторизация
- ✅ **Swagger/OpenAPI** - интерактивная API документация
- ✅ **Liquibase** - управление миграциями БД
- ✅ **Bean Validation** - валидация данных
- ✅ **Global Error Handling** - централизованная обработка ошибок
- ✅ **Client-side Error Handler** - JavaScript модуль для обработки ошибок
- ✅ **Docker** - оптимизированный Dockerfile
- ✅ **CI/CD** - GitHub Actions пайплайн
- ✅ **Tests** - Unit и Integration тесты
- ✅ **JaCoCo** - покрытие кода тестами

## 🛠️ Технологии

- **Backend**: Spring Boot 3.1.2, Spring Security, Spring Data JPA
- **Database**: PostgreSQL 14+
- **Migrations**: Liquibase
- **API Docs**: SpringDoc OpenAPI 3 (Swagger)
- **Frontend**: Thymeleaf, HTML5, CSS3, JavaScript
- **Build**: Maven
- **Tests**: JUnit 5, Mockito, Spring Test
- **Code Quality**: Checkstyle, JaCoCo
- **CI/CD**: GitHub Actions
- **Containerization**: Docker, Docker Compose

## 🚀 Быстрый старт

### Требования

- Java 17+
- Maven 3.8+
- PostgreSQL 14+ или Docker

### Запуск

```bash
# 1. Клонирование репозитория
git clone https://github.com/kozionov/Pathfinders.git
cd Pathfinders

# 2. Запуск PostgreSQL через Docker
docker run -d \
  --name pathfinders-postgres \
  -e POSTGRES_DB=pathfinders \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=postgres \
  -p 5432:5432 \
  postgres:14

# 3. Сборка и запуск
mvn spring-boot:run
```

Приложение будет доступно по адресу: **http://localhost:8080**

### Доступ

- **Страница входа**: http://localhost:8080/auth/login
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **API Docs**: http://localhost:8080/v3/api-docs
- **Health Check**: http://localhost:8080/actuator/health

**Дефолтные учетные данные:**
- Логин: `dir`
- Пароль: `dir`

## 📚 Документация

- [API Documentation](API_DOCUMENTATION.md) - полное описание API
- [Deployment Guide](DEPLOYMENT.md) - руководство по развертыванию
- [Contributing](CONTRIBUTING.md) - руководство для разработчиков

## 📦 Docker

### Запуск через Docker Compose

```bash
# Сборка образа
docker build -f Dockerfile.optimized -t pathfinders:latest .

# Запуск
docker-compose up -d
```

## 🧠 Тестирование

```bash
# Запуск всех тестов
mvn test

# С покрытием JaCoCo
mvn clean test jacoco:report

# Открыть отчет
open target/site/jacoco/index.html
```

## 📝 Лицензия

MIT License - см. [LICENSE](LICENSE)

## 👥 Автор

Sergey Kozionov - [GitHub](https://github.com/kozionov)

## 🚀 CI/CD

Проект использует GitHub Actions для автоматической сборки, тестирования и деплоя.

Каждый push в `master` или `dev` запускает:
- ✅ Сборку Maven
- ✅ Unit и Integration тесты
- ✅ Checkstyle
- ✅ JaCoCo coverage
- ✅ Docker build

## ⭐ Star History

Если проект оказался полезным, поставьте ⭐ на GitHub!
