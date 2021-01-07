/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coffeeshop.Util;

import lombok.Data;

/**
 *
 * @author TUANANH-LAPTOP
 */
@Data
public class BaseMessage {

    private Boolean status; // 0 - thanh cong, 1 - that bai
    private String message; // mo ta loi
    private long timestamp;

    public BaseMessage(Boolean status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = Common.getTimeStamp();
    }
}
