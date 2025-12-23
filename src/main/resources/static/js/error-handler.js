/**
 * Глобальный обработчик ошибок для клиентской части приложения.
 * Перехватывает ошибки от сервера и отображает их пользователю.
 */

/**
 * Отображает уведомление об ошибке.
 * @param {string} message - Текст сообщения об ошибке
 * @param {string} type - Тип уведомления ('error', 'warning', 'success', 'info')
 */
function showNotification(message, type = 'error') {
    // Удаляем предыдущие уведомления
    const existingNotifications = document.querySelectorAll('.notification');
    existingNotifications.forEach(n => n.remove());

    // Создаем элемент уведомления
    const notification = document.createElement('div');
    notification.className = `notification notification-${type}`;
    notification.innerHTML = `
        <div class="notification-content">
            <span class="notification-icon">${getIcon(type)}</span>
            <span class="notification-message">${message}</span>
            <button class="notification-close" onclick="this.parentElement.parentElement.remove()">&times;</button>
        </div>
    `;

    // Добавляем стили, если они еще не добавлены
    if (!document.getElementById('notification-styles')) {
        const style = document.createElement('style');
        style.id = 'notification-styles';
        style.textContent = `
            .notification {
                position: fixed;
                top: 20px;
                right: 20px;
                max-width: 400px;
                padding: 16px;
                border-radius: 8px;
                box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
                z-index: 9999;
                animation: slideIn 0.3s ease-out;
            }

            @keyframes slideIn {
                from {
                    transform: translateX(400px);
                    opacity: 0;
                }
                to {
                    transform: translateX(0);
                    opacity: 1;
                }
            }

            .notification-error {
                background-color: #fee;
                border-left: 4px solid #e74c3c;
                color: #c33;
            }

            .notification-warning {
                background-color: #ffeaa7;
                border-left: 4px solid #fdcb6e;
                color: #d63031;
            }

            .notification-success {
                background-color: #d5f4e6;
                border-left: 4px solid #00b894;
                color: #00643c;
            }

            .notification-info {
                background-color: #dfe6e9;
                border-left: 4px solid #0984e3;
                color: #2d3436;
            }

            .notification-content {
                display: flex;
                align-items: center;
                gap: 12px;
            }

            .notification-icon {
                font-size: 24px;
                flex-shrink: 0;
            }

            .notification-message {
                flex: 1;
                font-size: 14px;
                line-height: 1.5;
            }

            .notification-close {
                background: none;
                border: none;
                font-size: 24px;
                cursor: pointer;
                opacity: 0.6;
                transition: opacity 0.2s;
                padding: 0;
                width: 24px;
                height: 24px;
                flex-shrink: 0;
            }

            .notification-close:hover {
                opacity: 1;
            }

            @media (max-width: 480px) {
                .notification {
                    right: 10px;
                    left: 10px;
                    max-width: none;
                }
            }
        `;
        document.head.appendChild(style);
    }

    // Добавляем уведомление в документ
    document.body.appendChild(notification);

    // Автоматически удаляем через 5 секунд
    setTimeout(() => {
        notification.style.animation = 'slideOut 0.3s ease-out';
        setTimeout(() => notification.remove(), 300);
    }, 5000);
}

/**
 * Возвращает иконку для типа уведомления.
 */
function getIcon(type) {
    const icons = {
        error: '❌',
        warning: '⚠️',
        success: '✅',
        info: 'ℹ️'
    };
    return icons[type] || icons.info;
}

/**
 * Обрабатывает ошибку от fetch запроса.
 * @param {Response} response - Ответ от сервера
 */
async function handleFetchError(response) {
    if (!response.ok) {
        try {
            const errorData = await response.json();
            
            // Если есть ошибки валидации, отображаем их
            if (errorData.validationErrors) {
                const errorMessages = Object.entries(errorData.validationErrors)
                    .map(([field, message]) => `${field}: ${message}`)
                    .join('<br>');
                showNotification(`Ошибка валидации:<br>${errorMessages}`, 'error');
            } else {
                // Отображаем общее сообщение об ошибке
                showNotification(errorData.message || 'Произошла ошибка на сервере', 'error');
            }
        } catch (e) {
            // Если не удалось распарсить JSON, показываем общую ошибку
            showNotification('Произошла непредвиденная ошибка', 'error');
        }
    }
}

/**
 * Оборачивает fetch запрос с обработкой ошибок.
 * @param {string} url - URL запроса
 * @param {Object} options - Опции для fetch
 * @returns {Promise<Response>}
 */
async function fetchWithErrorHandling(url, options = {}) {
    try {
        const response = await fetch(url, options);
        
        if (!response.ok) {
            await handleFetchError(response);
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        
        return response;
    } catch (error) {
        console.error('Fetch error:', error);
        showNotification('Не удалось выполнить запрос. Проверьте подключение к интернету.', 'error');
        throw error;
    }
}

/**
 * Добавляет валидацию форм на стороне клиента.
 */
function setupFormValidation() {
    const forms = document.querySelectorAll('form[data-validate]');
    
    forms.forEach(form => {
        form.addEventListener('submit', async function(e) {
            e.preventDefault();
            
            // Очищаем предыдущие ошибки
            const errorElements = form.querySelectorAll('.error-message');
            errorElements.forEach(el => el.textContent = '');
            
            // Получаем данные формы
            const formData = new FormData(form);
            const data = Object.fromEntries(formData);
            
            try {
                const response = await fetchWithErrorHandling(form.action, {
                    method: form.method,
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(data)
                });
                
                if (response.ok) {
                    showNotification('Операция выполнена успешно', 'success');
                    // Опционально: перенаправление или обновление страницы
                    const redirectUrl = form.dataset.redirectUrl;
                    if (redirectUrl) {
                        setTimeout(() => window.location.href = redirectUrl, 1000);
                    }
                }
            } catch (error) {
                // Ошибка уже обработана в fetchWithErrorHandling
            }
        });
    });
}

// Инициализация при загрузке страницы
if (document.readyState === 'loading') {
    document.addEventListener('DOMContentLoaded', setupFormValidation);
} else {
    setupFormValidation();
}

// Экспортируем функции для использования в других скриптах
window.showNotification = showNotification;
window.fetchWithErrorHandling = fetchWithErrorHandling;
