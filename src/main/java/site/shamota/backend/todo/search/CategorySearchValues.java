package site.shamota.backend.todo.search;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class CategorySearchValues {

    private String title; // такое же значение должно быть у объекта на frontend
    private String email; // для фильтрации значений конкретного пользователя
}
