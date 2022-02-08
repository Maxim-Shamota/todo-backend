package site.shamota.backend.todo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.shamota.backend.todo.entity.Stat;
import site.shamota.backend.todo.repo.StatRepository;

@Service
@Transactional
public class StatService {

    private final StatRepository repository;

    public StatService(StatRepository repository) {
        this.repository = repository;
    }

    public Stat findStat(String email) {
        return repository.findByUserEmail(email);
    }
}
