package coffeeshop.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Area {

    private int id;
    private String name;
    private boolean status;

    @Override
    public String toString() {
        return this.name;
    }
}
