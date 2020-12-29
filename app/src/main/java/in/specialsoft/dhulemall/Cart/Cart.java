package in.specialsoft.dhulemall.Cart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Cart {
    @SerializedName("p_quantity")
    @Expose
    private String pQuantity;
    @SerializedName("p_id")
    @Expose
    private String pId;
    @SerializedName("p_name")
    @Expose
    private String pName;
    @SerializedName("p_price")
    @Expose
    private String pPrice;
    @SerializedName("p_image1")
    @Expose
    private String pImage1;
    @SerializedName("p_description")
    @Expose
    private String pDescription;

    public String getPDescription() {
        return pDescription;
    }

    public void setPDescription(String pDescription) {
        this.pDescription = pDescription;
    }

    public String getPQuantity() {
        return pQuantity;
    }

    public void setPQuantity(String pQuantity) {
        this.pQuantity = pQuantity;
    }

    public String getPId() {
        return pId;
    }

    public void setPId(String pId) {
        this.pId = pId;
    }

    public String getPName() {
        return pName;
    }

    public void setPName(String pName) {
        this.pName = pName;
    }

    public String getPPrice() {
        return pPrice;
    }

    public void setPPrice(String pPrice) {
        this.pPrice = pPrice;
    }

    public String getPImage1() {
        return pImage1;
    }

    public void setPImage1(String pImage1) {
        this.pImage1 = pImage1;
    }
}
