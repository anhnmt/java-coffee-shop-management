package coffeeshop.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillDetail {

    private Integer bill_id;
    private Integer product_id;
    private Integer amount;
    // Lấy thông tin từ id
    private String product_name;
    private Float product_price;

    public BillDetail(int bill_id, int product_id, int amount) {
        this.bill_id = bill_id;
        this.product_id = product_id;
        this.amount = amount;
    }

}
