package site.shamota.backend.todo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.shamota.backend.todo.entity.Priority;
import site.shamota.backend.todo.repo.PriorityRepository;

import java.util.List;

@Service
@Transactional
public class PriorityService {

    // работает встроенный механизм DI из Spring, который при старте приложения подставит в эту переменную нужные класс-реализацию
    private final PriorityRepository repository; // сервис имеет право обращаться к репозиторию (БД)

    public PriorityService(PriorityRepository repository) {
        this.repository = repository;
    }

    public List<Priority> findAll(String email) {
        return repository.findByUserEmailOrderByIdAsc(email);
    }

    public Priority add(Priority priority) {
        return repository.save(priority); // метод save обновляет или создает новый объект, если его не было
    }

    public Priority update(Priority priority) {
        return repository.save(priority); // метод save обновляет или создает новый объект, если его не было
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public Priority findById(Long id) {
        return repository.findById(id).get();// так как возвращается Optional - можно получить объект методом get()
    }

    public List<Priority> find(String title, String email) {
        return repository.findByTitle(title, email);
    }
}
