package coffeeshop.DTO;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Table implements Serializable {

    private Integer id;
    private Integer area_id;
    private String name;
    private Boolean status;

    public Table(Integer area_id) {
        this.area_id = area_id;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
