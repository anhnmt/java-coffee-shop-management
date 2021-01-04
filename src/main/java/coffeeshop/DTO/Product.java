package coffeeshop.DTO;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product implements Serializable {

    private Integer id;
    private Integer category_id;
    private String name;
    private Float price;
    private Boolean status;
    // Custom
    private String category_name;
}
