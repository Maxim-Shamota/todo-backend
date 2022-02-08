package site.shamota.backend.todo.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import site.shamota.backend.todo.repo.StatRepository
import site.shamota.backend.todo.entity.Stat

@Service
@Transactional
class StatService(private val repository: StatRepository) {
    fun findStat(email: String): Stat {
        return repository.findByUserEmail(email)
    }
}