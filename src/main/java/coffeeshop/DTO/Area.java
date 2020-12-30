package coffeeshop.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Area {

    private Integer id;
    private String name;
    private Boolean status;

    @Override
    public String toString() {
        return this.name;
    }
}
