package coffeeshop.DTO;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bill implements Serializable {

    private Integer id;
    private Integer user_id;
    private Integer table_id;
    private Float total_price;
    private Float discount;
    private String note;
    private Boolean status;
    private String created_at;

    // Custom
    private String user_name;
    private String table_name;

    public Bill(Integer table_id, Boolean status) {
        this.table_id = table_id;
        this.status = status;
    }
    
    
}
