package in.specialsoft.dhulemall.ProductDetaild;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductDetailInput {
    @SerializedName("p_id")
    @Expose
    private String pId;

    public String getPId() {
        return pId;
    }

    public void setPId(String pId) {
        this.pId = pId;
    }

}
