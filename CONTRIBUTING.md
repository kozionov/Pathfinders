# Руководство по внесению вклада в Pathfinders

Спасибо за интерес к проекту! Мы рады любому вкладу в развитие системы.

## Содержание

- [Code of Conduct](#code-of-conduct)
- [Как начать](#как-начать)
- [Процесс разработки](#процесс-разработки)
- [Стандарты кода](#стандарты-кода)
- [Commit Guidelines](#commit-guidelines)
- [Pull Request Process](#pull-request-process)
- [Тестирование](#тестирование)

## Code of Conduct

Этот проект придерживается профессиональных стандартов поведения. Мы ожидаем:

- Уважительного отношения ко всем участникам
- Конструктивной критики
- Фокуса на улучшении проекта
- Открытости к обратной связи

## Как начать

### 1. Настройка окружения

```bash
# Fork репозитория на GitHub

# Клонируйте ваш fork
git clone https://github.com/YOUR_USERNAME/Pathfinders.git
cd Pathfinders

# Добавьте upstream remote
git remote add upstream https://github.com/kozionov/Pathfinders.git

# Установите зависимости
mvn clean install
```

### 2. Создание issue

Перед началом работы:

1. Проверьте существующие issues
2. Создайте новый issue с описанием проблемы/улучшения
3. Дождитесь обсуждения и одобрения

### 3. Создание ветки

```bash
# Обновите main
git checkout main
git pull upstream main

# Создайте feature branch
git checkout -b feature/issue-number-short-description

# Примеры:
# feature/123-add-student-validation
# bugfix/456-fix-login-error
# docs/789-update-readme
```

## Процесс разработки

### Типы изменений

- **feature/** - новая функциональность
- **bugfix/** - исправление ошибок
- **hotfix/** - критические исправления для production
- **refactor/** - рефакторинг без изменения функциональности
- **docs/** - изменения в документации
- **test/** - добавление/изменение тестов
- **chore/** - обновление зависимостей, конфигурации

### Workflow

1. **Разработка**
   ```bash
   # Регулярно синхронизируйте с upstream
   git fetch upstream
   git rebase upstream/main
   
   # Коммитьте изменения
   git add .
   git commit -m "feat: add student validation"
   ```

2. **Тестирование**
   ```bash
   # Запустите все тесты
   mvn test
   
   # Проверьте code style
   mvn checkstyle:check
   
   # Проверьте покрытие
   mvn clean test jacoco:report
   ```

3. **Push и PR**
   ```bash
   git push origin feature/your-branch
   # Создайте Pull Request на GitHub
   ```

## Стандарты кода

### Java Code Style

Проект использует Checkstyle конфигурацию OTUS:

```bash
mvn checkstyle:check
```

**Основные правила:**

- Отступы: 4 пробела
- Максимальная длина строки: 120 символов
- Всегда используйте фигурные скобки для if/for/while
- Один класс = один файл
- Имена классов: PascalCase
- Имена методов и переменных: camelCase
- Константы: UPPER_SNAKE_CASE

### Архитектурные принципы

1. **Layered Architecture**
   ```
   Controller → Service → Repository → Entity
   ```

2. **DTO Pattern**
   - Используйте DTO для передачи данных между слоями
   - Не возвращайте Entity из контроллеров

3. **Dependency Injection**
   - Используйте constructor injection
   - Избегайте @Autowired на полях

4. **Exception Handling**
   - Создавайте кастомные исключения
   - Используйте @ControllerAdvice для глобальной обработки

### Примеры кода

**✅ Хороший код:**

```java
@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;
    
    @Override
    @Transactional(readOnly = true)
    public StudentDto getById(Long id) {
        Student student = studentRepository.findById(id)
            .orElseThrow(() -> new StudentNotFoundException(id));
        return modelMapper.map(student, StudentDto.class);
    }
}
```

**❌ Плохой код:**

```java
@Service
public class StudentService {
    @Autowired
    private StudentRepository repo;  // Field injection
    
    public Student get(Long id) {     // Возврат Entity
        return repo.findById(id).get();  // Нет обработки Optional
    }
}
```

## Commit Guidelines

Используем [Conventional Commits](https://www.conventionalcommits.org/):

### Формат

```
<type>(<scope>): <subject>

<body>

<footer>
```

### Types

- **feat**: новая функциональность
- **fix**: исправление бага
- **docs**: изменения в документации
- **style**: форматирование кода (без изменения логики)
- **refactor**: рефакторинг
- **test**: добавление тестов
- **chore**: обновление конфигурации, зависимостей
- **perf**: улучшение производительности

### Примеры

```bash
feat(students): add email validation

fix(auth): resolve login redirect issue

docs(readme): update installation instructions

refactor(services): extract common logic to base service

test(students): add integration tests for CRUD operations
```

## Pull Request Process

### Checklist перед созданием PR

- [ ] Все тесты проходят (`mvn test`)
- [ ] Checkstyle без ошибок (`mvn checkstyle:check`)
- [ ] Покрытие кода не снизилось
- [ ] Добавлены тесты для новой функциональности
- [ ] Документация обновлена
- [ ] Нет конфликтов с main веткой
- [ ] Коммиты следуют Conventional Commits

### Шаблон PR

```markdown
## Описание
Краткое описание изменений

## Тип изменения
- [ ] Bug fix
- [ ] New feature
- [ ] Breaking change
- [ ] Documentation update

## Связанные issues
Closes #123

## Тестирование
Опишите как протестировать изменения

## Скриншоты (если применимо)

## Checklist
- [ ] Код следует style guide проекта
- [ ] Проведен self-review
- [ ] Добавлены комментарии в сложных местах
- [ ] Обновлена документация
- [ ] Изменения не генерируют новых warnings
- [ ] Добавлены тесты
- [ ] Все тесты проходят
```

### Review Process

1. Автоматические проверки (CI/CD)
2. Code review от maintainers
3. Обсуждение и внесение правок
4. Одобрение и merge

## Тестирование

### Unit Tests

```java
@SpringBootTest
class StudentServiceTest {
    @MockBean
    private StudentRepository studentRepository;
    
    @Autowired
    private StudentService studentService;
    
    @Test
    @DisplayName("Should return student when ID exists")
    void shouldReturnStudentWhenIdExists() {
        // given
        Long studentId = 1L;
        Student student = new Student();
        student.setId(studentId);
        
        when(studentRepository.findById(studentId))
            .thenReturn(Optional.of(student));
        
        // when
        StudentDto result = studentService.getById(studentId);
        
        // then
        assertNotNull(result);
        assertEquals(studentId, result.getId());
    }
}
```

### Integration Tests

```java
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
class StudentControllerIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;
    
    @Test
    void shouldCreateStudent() {
        StudentDto dto = new StudentDto("John", "Doe");
        
        ResponseEntity<StudentDto> response = restTemplate
            .postForEntity("/api/students", dto, StudentDto.class);
        
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }
}
```

### Test Coverage

Минимальные требования:
- Unit tests: 70%
- Integration tests: основные сценарии
- Service layer: 80%
- Controller layer: 70%

## Полезные команды

```bash
# Сборка проекта
mvn clean install

# Запуск приложения
mvn spring-boot:run

# Тесты
mvn test
mvn verify  # с интеграционными тестами

# Code quality
mvn checkstyle:check
mvn jacoco:report

# База данных
docker-compose -f docker-compose-psgr.yml up -d

# Логи
mvn spring-boot:run | grep ERROR
docker-compose logs -f
```

## Получение помощи

- Создайте issue с вопросом
- Проверьте существующую документацию
- Спросите в discussions на GitHub

## Лицензия

Внося вклад в проект, вы соглашаетесь с тем, что ваш код будет распространяться под той же лицензией, что и проект.

---

Спасибо за участие в развитии Pathfinders! 🚀