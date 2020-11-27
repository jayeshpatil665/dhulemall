package in.specialsoft.dhulemall.ProductList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductListInput {
    @SerializedName("category_name")
    @Expose
    private String categoryName;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }



}
