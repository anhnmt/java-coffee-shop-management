package coffeeshop.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bill {

    private int id;
    private int user_id;
    private int table_id;
    private float total_price;
    private float discount;
    private String note;
    private boolean status;
    private String created_at;
}
