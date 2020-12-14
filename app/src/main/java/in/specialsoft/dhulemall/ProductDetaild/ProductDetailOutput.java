package in.specialsoft.dhulemall.ProductDetaild;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductDetailOutput {
    @SerializedName("product")
    @Expose
    private List<Product1> product = null;

    public List<Product1> getProduct() {
        return product;
    }

    public void setProduct(List<Product1> product) {
        this.product = product;
    }

}
