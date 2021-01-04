package in.specialsoft.dhulemall.AdminOrderStates;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChangeOrderStateInput {
    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("order_state")
    @Expose
    private String orderState;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }
}
