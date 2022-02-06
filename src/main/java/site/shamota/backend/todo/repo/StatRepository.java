package site.shamota.backend.todo.repo;

import org.springframework.data.repository.CrudRepository;
import site.shamota.backend.todo.entity.Stat;

public interface StatRepository extends CrudRepository<Stat, Long> {

    Stat findByUserEmail(String email); // всегда получаем только 1 объект, т.к. 1 пользователь содержит только 1 строку статистики(связь "один к одному")
}
