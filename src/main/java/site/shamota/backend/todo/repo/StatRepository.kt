package site.shamota.backend.todo.repo

import org.springframework.data.repository.CrudRepository
import site.shamota.backend.todo.entity.Stat

interface StatRepository : CrudRepository<Stat, Long> {
    // всегда получаем только 1 объект, т.к. 1 пользователь содержит только 1 строку статистики(связь "один к одному")
    fun findByUserEmail(email: String): Stat
}