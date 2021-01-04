package in.specialsoft.dhulemall.AdminOrderStates;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderOverviewInput {
    @SerializedName("order_state")
    @Expose
    private String orderState;

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }
}
