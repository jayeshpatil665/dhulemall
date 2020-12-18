package in.specialsoft.dhulemall.Cart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CartOutput {
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
