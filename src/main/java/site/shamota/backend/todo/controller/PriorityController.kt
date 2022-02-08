package site.shamota.backend.todo.controller;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.shamota.backend.todo.entity.Category;
import site.shamota.backend.todo.entity.Priority;
import site.shamota.backend.todo.search.CategorySearchValues;
import site.shamota.backend.todo.search.PrioritySearchValues;
import site.shamota.backend.todo.service.CategoryService;
import site.shamota.backend.todo.service.PriorityService;

import java.util.List;
import java.util.NoSuchElementException;


/*

Используем @RestController вместо обычного @Controller, чтобы все ответы сразу оборачивались в JSON,
иначе пришлось бы добавлять лишние объекты в код, использовать @ResponseBody для ответа, указывать тип отправки JSON

*/

@RestController
@RequestMapping("/priority") // базовый URI
public class PriorityController {

    private PriorityService priorityService;

    public PriorityController(PriorityService priorityService) {
        this.priorityService = priorityService;
    }

    @PostMapping("/all")
    public List<Priority> findAll(@RequestBody String email) {
        return priorityService.findAll(email);
    }

    @PostMapping("/add")
    public ResponseEntity<Priority> add(@RequestBody Priority priority) {

        // проверка на обязательные параметры
        if (priority.getId() != null && priority.getId() != 0) { // это означает, что id заполнено
            // id создается автоматически в БД (autoincrement), поэтому его передавать не нужно, иначе может быть конфликт уникальности значения
            return new ResponseEntity("redundant param: id MUST be null", HttpStatus.NOT_ACCEPTABLE);
        }

        // если передали пустое значение title
        if (priority.getTitle() == null || priority.getTitle().trim().length() == 0) {
            return new ResponseEntity("missed param: title MUST be not null", HttpStatus.NOT_ACCEPTABLE);
        }

        return ResponseEntity.ok(priorityService.add(priority)); // возвращаем добавленный объект с заполненным ID
    }

    @PutMapping("/update")
    public ResponseEntity update(@RequestBody Priority priority) {

        // проверка на обязательные параметры
        if (priority.getId() == null && priority.getId() == 0) {
            return new ResponseEntity("missed param: id", HttpStatus.NOT_ACCEPTABLE);
        }

        // если передали пустое значение title
        if (priority.getTitle() == null || priority.getTitle().trim().length() == 0) {
            return new ResponseEntity("missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }

        priorityService.update(priority);

        return new ResponseEntity(HttpStatus.OK); // просто отправляем статус 200 (операция прошла успешно)
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        try {
            priorityService.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return new ResponseEntity("id=" + id + " not found", HttpStatus.NOT_ACCEPTABLE);
        }

        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<List<Priority>> search(@RequestBody PrioritySearchValues prioritySearchValues) {

        // проверка на обязательные параметры
        if (prioritySearchValues.getEmail() == null && prioritySearchValues.getEmail().trim().length()== 0) {
            return new ResponseEntity("missed param: email", HttpStatus.NOT_ACCEPTABLE);
        }

        // поиск категорий пользователя по названию
        List<Priority> list = priorityService.find(prioritySearchValues.getTitle(), prioritySearchValues.getEmail());

        return ResponseEntity.ok(list);
    }

    @PostMapping("/id")
    public ResponseEntity<Priority> findById(@RequestBody Long id) {
        Priority priority = null;

        try {
            priority = priorityService.findById(id);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return new ResponseEntity("id=" + id + " not found", HttpStatus.NOT_ACCEPTABLE);
        }

        return ResponseEntity.ok(priority);
    }
}
