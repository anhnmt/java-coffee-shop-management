package coffeeshop.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Table {

    private Integer id;
    private Integer area_id;
    private String name;
    private String note;
    private Boolean status;
}
