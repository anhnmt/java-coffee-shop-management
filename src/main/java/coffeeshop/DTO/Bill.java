package coffeeshop.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bill {

    private Integer id;
    private Integer user_id;
    private Integer table_id;
    private Float total_price;
    private Float discount;
    private String note;
    private Boolean status;
    private String created_at;
}
