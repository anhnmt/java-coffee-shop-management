package coffeeshop.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    private Integer id;
    private Integer category_id;
    private String name;
    private Float price;
    private Boolean status;
    // Lấy thông tin từ id
    private String category_name;
}
