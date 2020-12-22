package coffeeshop.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private int id;
    private int category_id;
    private String name;
    private float price;
    private boolean status;
    private String category_name;
}
