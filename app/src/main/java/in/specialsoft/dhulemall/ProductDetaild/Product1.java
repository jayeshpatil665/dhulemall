package in.specialsoft.dhulemall.ProductDetaild;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product1 {
    @SerializedName("p_id")
    @Expose
    private String pId;
    @SerializedName("category_name")
    @Expose
    private String categoryName;
    @SerializedName("p_name")
    @Expose
    private String pName;
    @SerializedName("p_price")
    @Expose
    private String pPrice;
    @SerializedName("p_quantity")
    @Expose
    private String pQuantity;
    @SerializedName("p_description")
    @Expose
    private String pDescription;
    @SerializedName("p_image1")
    @Expose
    private String pImage1;
    @SerializedName("p_image2")
    @Expose
    private String pImage2;
    @SerializedName("p_image3")
    @Expose
    private String pImage3;

    public String getPId() {
        return pId;
    }

    public void setPId(String pId) {
        this.pId = pId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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

    public String getPQuantity() {
        return pQuantity;
    }

    public void setPQuantity(String pQuantity) {
        this.pQuantity = pQuantity;
    }

    public String getPDescription() {
        return pDescription;
    }

    public void setPDescription(String pDescription) {
        this.pDescription = pDescription;
    }

    public String getPImage1() {
        return pImage1;
    }

    public void setPImage1(String pImage1) {
        this.pImage1 = pImage1;
    }

    public String getPImage2() {
        return pImage2;
    }

    public void setPImage2(String pImage2) {
        this.pImage2 = pImage2;
    }

    public String getPImage3() {
        return pImage3;
    }

    public void setPImage3(String pImage3) {
        this.pImage3 = pImage3;
    }

}
