package in.specialsoft.dhulemall.ProductDetaild;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddToCartInput {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("p_id")
    @Expose
    private String pId;
    @SerializedName("p_quantity")
    @Expose
    private String pQuantity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPId() {
        return pId;
    }

    public void setPId(String pId) {
        this.pId = pId;
    }

    public String getPQuantity() {
        return pQuantity;
    }

    public void setPQuantity(String pQuantity) {
        this.pQuantity = pQuantity;
    }
}
