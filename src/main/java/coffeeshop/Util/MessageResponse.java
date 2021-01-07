/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coffeeshop.Util;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @author TUANANH-LAPTOP
 * @param <T>
 */
//sử dụng generic để nhận các kiểu dữ liệu tùy ý
@EqualsAndHashCode(callSuper = true)
@Data
public class MessageResponse<T> extends BaseMessage {

    private T result;

    public MessageResponse(Boolean status, String response) {
        super(status, response);
    }

    public MessageResponse(Boolean status, String response, T result) {
        super(status, response);
        this.result = result;
    }
}
