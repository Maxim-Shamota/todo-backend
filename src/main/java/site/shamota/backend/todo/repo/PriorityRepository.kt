package site.shamota.backend.todo.repo

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import site.shamota.backend.todo.entity.Priority

@Repository
interface PriorityRepository : JpaRepository<Priority, Long> {
    // поиск всех значений данного пользователя
    fun findByUserEmailOrderByIdAsc(email: String): List<Priority>

    // поиск значений по названию для конкретного пользователя
    @Query(
        "SELECT p FROM Priority p where " +
                "(:title is null or :title='' " +  // если передадим параметр title пустым, то выберутся все записи (сработает именно это условие)
                " or lower(p.title) like lower(concat('%', :title,'%'))) " +  // если параметр title не пустой, то выполнится уже это условие
                " and p.user.email=:email  " +  // фильтрация для конкретного пользователя
                " order by p.title asc"
    )
    // сортировка по названию
    fun findByTitle(@Param("title") title: String?, @Param("email") email: String): List<Priority>
}