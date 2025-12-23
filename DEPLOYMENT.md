# Руководство по развертыванию

## 📦 Содержание

- [Development](#development)
- [Production](#production-deployment)
- [Docker](#docker-deployment)
- [Kubernetes](#kubernetes-deployment)
- [CI/CD](#cicd)

## Development

### Требования

- Java 17+
- Maven 3.8+
- PostgreSQL 14+
- Docker (опционально)

### Запуск БД через Docker

```bash
docker run -d \
  --name pathfinders-postgres \
  -e POSTGRES_DB=pathfinders \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=postgres \
  -p 5432:5432 \
  postgres:14
```

### Запуск приложения

```bash
# Сборка
mvn clean install

# Запуск
mvn spring-boot:run

# Или через JAR
java -jar target/pathfinders-0.1.jar
```

Приложение будет доступно по адресу: `http://localhost:8080`

## Production Deployment

### Переменные окружения

Создайте файл `.env`:

```env
# База данных
SPRING_DATASOURCE_URL=jdbc:postgresql://db-host:5432/pathfinders
SPRING_DATASOURCE_USERNAME=prod_user
SPRING_DATASOURCE_PASSWORD=secure_password

# Сервер
SERVER_PORT=8080

# Логирование
LOG_LEVEL=INFO

# Security
SECURITY_USER_NAME=admin
SECURITY_USER_PASSWORD=change_me_in_production
```

### Сборка для Production

```bash
mvn clean package -DskipTests -Pprod
```

### Запуск

```bash
java -jar \
  -Dspring.profiles.active=prod \
  -Xms512m -Xmx1024m \
  target/pathfinders-0.1.jar
```

## Docker Deployment

### Сборка образа

```bash
docker build -f Dockerfile.optimized -t pathfinders:latest .
```

### Запуск через Docker Compose

Создайте `docker-compose.yml`:

```yaml
version: '3.8'

services:
  postgres:
    image: postgres:14
    environment:
      POSTGRES_DB: pathfinders
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: postgres
    volumes:
      - postgres_data:/var/lib/postgresql/data
    healthcheck:
      test: ["CMD-SHELL", "pg_isready -U postgres"]
      interval: 10s
      timeout: 5s
      retries: 5

  app:
    image: pathfinders:latest
    ports:
      - "8080:8080"
    environment:
      SPRING_DATASOURCE_URL: jdbc:postgresql://postgres:5432/pathfinders
      SPRING_DATASOURCE_USERNAME: postgres
      SPRING_DATASOURCE_PASSWORD: postgres
    depends_on:
      postgres:
        condition: service_healthy
    healthcheck:
      test: ["CMD", "curl", "-f", "http://localhost:8080/actuator/health"]
      interval: 30s
      timeout: 10s
      retries: 3

volumes:
  postgres_data:
```

Запуск:

```bash
docker-compose up -d
```

## Kubernetes Deployment

### ConfigMap

```yaml
apiVersion: v1
kind: ConfigMap
metadata:
  name: pathfinders-config
data:
  SPRING_DATASOURCE_URL: "jdbc:postgresql://postgres:5432/pathfinders"
  SERVER_PORT: "8080"
  LOG_LEVEL: "INFO"
```

### Secret

```yaml
apiVersion: v1
kind: Secret
metadata:
  name: pathfinders-secret
type: Opaque
stringData:
  SPRING_DATASOURCE_USERNAME: "postgres"
  SPRING_DATASOURCE_PASSWORD: "secure_password"
```

### Deployment

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: pathfinders
spec:
  replicas: 3
  selector:
    matchLabels:
      app: pathfinders
  template:
    metadata:
      labels:
        app: pathfinders
    spec:
      containers:
      - name: pathfinders
        image: pathfinders:latest
        ports:
        - containerPort: 8080
        envFrom:
        - configMapRef:
            name: pathfinders-config
        - secretRef:
            name: pathfinders-secret
        livenessProbe:
          httpGet:
            path: /actuator/health
            port: 8080
          initialDelaySeconds: 60
          periodSeconds: 10
        readinessProbe:
          httpGet:
            path: /actuator/health
            port: 8080
          initialDelaySeconds: 30
          periodSeconds: 5
        resources:
          requests:
            memory: "512Mi"
            cpu: "500m"
          limits:
            memory: "1Gi"
            cpu: "1000m"
```

### Service

```yaml
apiVersion: v1
kind: Service
metadata:
  name: pathfinders
spec:
  selector:
    app: pathfinders
  ports:
  - port: 80
    targetPort: 8080
  type: LoadBalancer
```

Применение:

```bash
kubectl apply -f k8s/
```

## CI/CD

### GitHub Actions

CI/CD пайплайн настроен в `.github/workflows/ci.yml`

**Автоматически выполняются:**
- Сборка проекта
- Запуск тестов
- Checkstyle проверка
- JaCoCo отчет о покрытии
- Docker сборка

### Релизы

Для создания релиза:

```bash
git tag -a v1.0.0 -m "Release version 1.0.0"
git push origin v1.0.0
```

Это запустит workflow `.github/workflows/release.yml`

## Мониторинг

### Prometheus

Добавьте в `prometheus.yml`:

```yaml
scrape_configs:
  - job_name: 'pathfinders'
    metrics_path: '/actuator/prometheus'
    static_configs:
      - targets: ['localhost:8080']
```

### Grafana

Используйте готовые dashboard для Spring Boot:
- Spring Boot 2.1 Statistics (ID: 11378)
- JVM Dashboard (ID: 4701)

## Резервное копирование

### Бэкап БД

```bash
pg_dump -h localhost -U postgres pathfinders > backup.sql
```

### Восстановление

```bash
psql -h localhost -U postgres pathfinders < backup.sql
```

## Устранение неполадок

### Проверка логов

```bash
# Docker
docker logs pathfinders

# Kubernetes
kubectl logs -f deployment/pathfinders
```

### Проверка здоровья

```bash
curl http://localhost:8080/actuator/health
```

### Общие проблемы

1. **Не могу подключиться к БД**
   - Проверьте переменные окружения
   - Убедитесь, что PostgreSQL запущен

2. **Liquibase ошибки**
   - Проверьте схему БД
   - Очистите таблицу `databasechangelog`

3. **Out of Memory**
   - Увеличьте heap: `-Xmx2048m`
   - Проверьте утечки памяти
