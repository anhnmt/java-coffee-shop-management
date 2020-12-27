package coffeeshop.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillDetail {

    private int bill_id;
    private int product_id;
    private int amount;
    // Lấy thông tin từ id
    private String product_name;
    private float product_price;

    public BillDetail(int bill_id, int product_id, int amount) {
        this.bill_id = bill_id;
        this.product_id = product_id;
        this.amount = amount;
    }

}
