package site.shamota.backend.todo.search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class PrioritySearchValues {

    private String title; // такое же значение должно быть у объекта на frontend
    private String email; // для фильтрации значений конкретного пользователя
}
