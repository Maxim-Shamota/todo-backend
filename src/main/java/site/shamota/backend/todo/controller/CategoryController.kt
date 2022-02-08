package site.shamota.backend.todo.controller

import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import site.shamota.backend.todo.entity.Category
import site.shamota.backend.todo.search.CategorySearchValues
import site.shamota.backend.todo.service.CategoryService

/*

Используем @RestController вместо обычного @Controller, чтобы все ответы сразу оборачивались в JSON,
иначе пришлось бы добавлять лишние объекты в код, использовать @ResponseBody для ответа, указывать тип отправки JSON

Названия методов могут быть любыми, главное не дублировать их имена внутри класса и URL mapping

*/
@RestController
@RequestMapping("/category") // базовый URI
class CategoryController
    (
    private val categoryService: CategoryService // доступ к данным из БД
) {

    @PostMapping("/all")
    fun findAll(@RequestBody email: String): List<Category> {
        return categoryService.findAll(email)
    }

    @PostMapping("/add")
    fun add(@RequestBody category: Category): ResponseEntity<Any> {

        // проверка на обязательные параметры
        if (category.id != null && category.id != 0L) { // это означает, что id заполнено
            // id создается автоматически в БД (autoincrement), поэтому его передавать не нужно, иначе может быть конфликт уникальности значения
            return ResponseEntity<Any>("redundant param: id MUST be null", HttpStatus.NOT_ACCEPTABLE)
        }

        if (category.title == null || category.title.trim().isEmpty()) {
            return ResponseEntity<Any>("missed param: title MUST be not null", HttpStatus.NOT_ACCEPTABLE)
        }

        // если передали пустое значение title
        return ResponseEntity.ok(
            categoryService.add(category) // возвращаем добавленный объект с заполненным ID
        )
    }

    @PutMapping("/update")
    fun update(@RequestBody category: Category): ResponseEntity<Any> {

        // проверка на обязательные параметры
        if (category.id == null || category.id == 0L) {
            return ResponseEntity<Any>("missed param: id", HttpStatus.NOT_ACCEPTABLE)
        }

        // если передали пустое значение title
        if (category.title == null || category.title.trim().isEmpty()) {
            return ResponseEntity<Any>("missed param: title", HttpStatus.NOT_ACCEPTABLE)
        }

        categoryService.update(category)
        return ResponseEntity<Any>(HttpStatus.OK) // просто отправляем статус 200 (операция прошла успешно)
    }

    @DeleteMapping("/delete/{id}")
    fun delete(@PathVariable("id") id: Long): ResponseEntity<Any> {

        // можно обойтись и без try-catch, тогда будет возвращаться полная ошибка(stacktrace)
        // здесь показан пример, как можно обрабатывать исключения и отправлять свой текст/статус
        try {
            categoryService.deleteById(id)
        } catch (e: EmptyResultDataAccessException) {
            e.printStackTrace()
            return ResponseEntity<Any>("id=$id not found", HttpStatus.NOT_ACCEPTABLE)
        }
        return ResponseEntity<Any>(HttpStatus.OK) // просто отправляем статус 200 без объектов
    }

    @PostMapping("/search")
    fun search(@RequestBody categorySearchValues: CategorySearchValues): ResponseEntity<Any> {

        // проверка на обязательные параметры
        if (categorySearchValues.email == null || categorySearchValues.email.trim().isEmpty()) {
            return ResponseEntity<Any>("missed param: email", HttpStatus.NOT_ACCEPTABLE)
        }

        // поиск категорий пользователя по названию
        val list = categoryService.findByTitle(categorySearchValues.title, categorySearchValues.email)
        return ResponseEntity.ok(list)
    }

    @PostMapping("/id")
    fun findById(@RequestBody id: Long): ResponseEntity<Any> {
        var category: Category = try {
            categoryService.findById(id)
        } catch (e: NoSuchElementException) { // если объект не будет найден
            e.printStackTrace()
            return ResponseEntity<Any>("id=$id not found", HttpStatus.NOT_ACCEPTABLE)
        }
        return ResponseEntity.ok(category)
    }
}