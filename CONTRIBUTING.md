# Руководство по внесению вклада в Pathfinders

Спасибо за ваш интерес к проекту! Мы рады любому вкладу в развитие Pathfinders.

## 📋 Содержание

- [Начало работы](#начало-работы)
- [Процесс разработки](#процесс-разработки)
- [Стиль кода](#стиль-кода)
- [Commit-сообщения](#commit-сообщения)
- [Pull Request процесс](#pull-request-процесс)
- [Тестирование](#тестирование)

## 🚀 Начало работы

### Требования

- Java 17+
- Maven 3.6+
- Docker и Docker Compose
- Git
- IDE (рекомендуется IntelliJ IDEA или Eclipse)

### Настройка окружения

1. Форкните репозиторий
2. Клонируйте свой форк:
```bash
git clone https://github.com/YOUR_USERNAME/Pathfinders.git
cd Pathfinders
```

3. Добавьте upstream remote:
```bash
git remote add upstream https://github.com/kozionov/Pathfinders.git
```

4. Запустите базу данных:
```bash
docker-compose -f docker-compose-psgr.yml up -d
```

5. Соберите проект:
```bash
mvn clean install
```

## 🔧 Процесс разработки

### Создание новой функции

1. Синхронизируйте с upstream:
```bash
git checkout master
git pull upstream master
```

2. Создайте feature-ветку:
```bash
git checkout -b feature/your-feature-name
```

3. Внесите изменения и регулярно коммитьте их

4. Запустите тесты:
```bash
mvn test
```

5. Запустите проверку стиля кода:
```bash
mvn spotless:check
```

6. При необходимости, примените форматирование:
```bash
mvn spotless:apply
```

### Исправление ошибок

1. Создайте bugfix-ветку от master:
```bash
git checkout -b fix/bug-description
```

2. Исправьте ошибку и добавьте тесты, подтверждающие исправление

3. Следуйте остальным шагам как для feature-ветки

## 📝 Стиль кода

### Java код

- Следуйте [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html)
- Используйте 4 пробела для отступов
- Максимальная длина строки: 120 символов
- Всегда используйте фигурные скобки для if/else/for/while
- Документируйте public методы с помощью Javadoc

### Именование

- **Классы**: `PascalCase` (например, `StudentController`)
- **Методы**: `camelCase` (например, `findStudentById`)
- **Константы**: `UPPER_SNAKE_CASE` (например, `MAX_STUDENTS_PER_CLUB`)
- **Переменные**: `camelCase` (например, `studentName`)

### Примеры кода

```java
// ✅ Хорошо
public class StudentService {
    private static final int MAX_RETRY_ATTEMPTS = 3;
    
    /**
     * Находит студента по ID.
     *
     * @param id ID студента
     * @return найденный студент
     * @throws EntityNotFoundException если студент не найден
     */
    public Student findStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student", id));
    }
}

// ❌ Плохо
public class studentservice {
    private static final int max_retry = 3;
    
    public Student find(Long ID) {
        return studentRepository.findById(ID).get();
    }
}
```

## 💬 Commit-сообщения

Используйте [Conventional Commits](https://www.conventionalcommits.org/):

```
<type>(<scope>): <subject>

<body>

<footer>
```

### Типы коммитов

- `feat`: новая функциональность
- `fix`: исправление ошибки
- `docs`: изменения в документации
- `style`: форматирование, отсутствующие точки с запятой и т.д.
- `refactor`: рефакторинг кода
- `test`: добавление тестов
- `chore`: обновление задач сборки, конфигурации и т.д.

### Примеры

```bash
# Хорошие примеры
git commit -m "feat(students): add ability to filter students by age"
git commit -m "fix(auth): resolve login redirect issue"
git commit -m "docs(readme): update installation instructions"

# Плохие примеры
git commit -m "update"
git commit -m "fixed bug"
git commit -m "changes"
```

## 🔀 Pull Request процесс

### Перед созданием PR

1. Убедитесь, что все тесты проходят:
```bash
mvn clean test
```

2. Проверьте покрытие кода тестами:
```bash
mvn test jacoco:report
```

3. Убедитесь, что нет конфликтов с master:
```bash
git fetch upstream
git rebase upstream/master
```

4. Отформатируйте код:
```bash
mvn spotless:apply
```

### Создание PR

1. Отправьте изменения в ваш форк:
```bash
git push origin feature/your-feature-name
```

2. Создайте Pull Request через GitHub

3. Заполните шаблон PR:
   - Описание изменений
   - Связанные Issues
   - Тип изменений (feature/fix/docs)
   - Чеклист проверки

### Пример описания PR

```markdown
## Описание
Добавлена возможность фильтрации студентов по возрасту.

## Связанные Issues
Closes #123

## Тип изменений
- [x] Новая функциональность (feature)
- [ ] Исправление ошибки (fix)
- [ ] Документация (docs)

## Чеклист
- [x] Код следует стилю проекта
- [x] Добавлены/обновлены тесты
- [x] Все тесты проходят
- [x] Обновлена документация
- [x] Нет конфликтов с master
```

## 🧪 Тестирование

### Unit тесты

- Покрывайте тестами всю бизнес-логику
- Используйте `@SpringBootTest` для интеграционных тестов
- Используйте `@WebMvcTest` для тестирования контроллеров
- Используйте Mockito для mock-объектов

### Пример теста

```java
@SpringBootTest
class StudentServiceTest {
    
    @Mock
    private StudentRepository studentRepository;
    
    @InjectMocks
    private StudentService studentService;
    
    @Test
    @DisplayName("Should find student by ID")
    void shouldFindStudentById() {
        // Given
        Long studentId = 1L;
        Student expectedStudent = new Student();
        expectedStudent.setId(studentId);
        expectedStudent.setName("John Doe");
        
        when(studentRepository.findById(studentId))
            .thenReturn(Optional.of(expectedStudent));
        
        // When
        Student actualStudent = studentService.findById(studentId);
        
        // Then
        assertThat(actualStudent).isNotNull();
        assertThat(actualStudent.getId()).isEqualTo(studentId);
        assertThat(actualStudent.getName()).isEqualTo("John Doe");
    }
    
    @Test
    @DisplayName("Should throw exception when student not found")
    void shouldThrowExceptionWhenStudentNotFound() {
        // Given
        Long studentId = 999L;
        when(studentRepository.findById(studentId))
            .thenReturn(Optional.empty());
        
        // When & Then
        assertThatThrownBy(() -> studentService.findById(studentId))
            .isInstanceOf(EntityNotFoundException.class)
            .hasMessageContaining("Student");
    }
}
```

## 📚 Дополнительные ресурсы

- [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [JPA Documentation](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)
- [JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)
- [Mockito Documentation](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html)

## ❓ Вопросы?

Если у вас есть вопросы, создайте Issue или свяжитесь с мейнтейнерами проекта.

---

Спасибо за ваш вклад! 🎉