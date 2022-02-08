package site.shamota.backend.todo.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import site.shamota.backend.todo.entity.Priority
import site.shamota.backend.todo.repo.PriorityRepository

@Service
@Transactional
// работает встроенный механизм DI из Spring, который при старте приложения подставит в эту переменную нужные класс-реализацию
class PriorityService(
    private val repository // сервис имеет право обращаться к репозиторию (БД)
    : PriorityRepository
) {
    fun findAll(email: String): List<Priority> {
        return repository.findByUserEmailOrderByIdAsc(email)
    }

    fun add(priority: Priority): Priority {
        return repository.save(priority) // метод save обновляет или создает новый объект, если его не было
    }

    fun update(priority: Priority): Priority {
        return repository.save(priority) // метод save обновляет или создает новый объект, если его не было
    }

    fun deleteById(id: Long) {
        repository.deleteById(id)
    }

    fun findById(id: Long): Priority {
        return repository.findById(id).get() // так как возвращается Optional - можно получить объект методом get()
    }

    fun find(title: String?, email: String): List<Priority> {
        return repository.findByTitle(title, email)
    }
}