package site.shamota.backend.todo.search

import java.util.*

// для поиска категорий
data class CategorySearchValues(val email: String, val title: String?)

// для поиска приоритетов
data class PrioritySearchValues(val email: String, val title: String?)

// для поиска задач
// возможные значения, по которым можно искать задачи + значения сортировки
data class TaskSearchValues( val email: String,
                             val pageNumber: Int,
                             val pageSize: Int,
                             val sortColumn: String,
                             val sortDirection: String) {
    // поля поиска (все типы - объектные, не примитивные. Чтобы можно было передать null)
    val title: String? = null
    val completed: Int? = null
    val priorityId: Long? = null
    val categoryId: Long? = null
    val dateFrom: Date? = null // для задания периода по датам
    val dateTo: Date? = null
}