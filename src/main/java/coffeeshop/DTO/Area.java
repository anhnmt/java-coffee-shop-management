package coffeeshop.DTO;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Area implements Serializable {

    private Integer id;
    private String name;
    private Boolean status;

    @Override
    public String toString() {
        return this.name;
    }
}
