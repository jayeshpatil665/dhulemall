package in.specialsoft.dhulemall.MyOrdersList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import in.specialsoft.dhulemall.Cart.Cart;

public class OrderDetailOutput {
    @SerializedName("cart")
    @Expose
    private List<Cart> cart = null;

    public List<Cart> getCart() {
        return cart;
    }

    public void setCart(List<Cart> cart) {
        this.cart = cart;
    }

}
