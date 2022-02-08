package site.shamota.backend.todo.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import site.shamota.backend.todo.entity.Category
import site.shamota.backend.todo.repo.CategoryRepository

@Service
@Transactional
// работает встроенный механизм DI из Spring, который при старте приложения подставит в эту переменную нужные класс-реализацию
class CategoryService(
    private val repository // сервис имеет право обращаться к репозиторию (БД)
    : CategoryRepository
) {
    fun findAll(email: String): List<Category> {
        return repository.findByUserEmailOrderByTitleAsc(email)
    }

    fun add(category: Category): Category {
        return repository.save(category) // метод save обновляет или создает новый объект, если его не было
    }

    fun update(category: Category): Category {
        return repository.save(category) // метод save обновляет или создает новый объект, если его не было
    }

    fun deleteById(id: Long) {
        repository.deleteById(id)
    }

    fun findByTitle(text: String?, email: String): List<Category> {
        return repository.findByTitle(text, email)
    }

    fun findById(id: Long): Category {
        return repository.findById(id).get() // так как возвращается Optional - можно получить объект методом get()
    }
}