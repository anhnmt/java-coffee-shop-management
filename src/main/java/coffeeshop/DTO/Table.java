package coffeeshop.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Table {
    private int id;
    private int area_id;
    private String name;
    private String note;
    private boolean status;
}
