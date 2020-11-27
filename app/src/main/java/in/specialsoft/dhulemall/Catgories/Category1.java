package in.specialsoft.dhulemall.Catgories;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Category1 {
    @SerializedName("category_name")
    @Expose
    private String categoryName;
    @SerializedName("cat_img")
    @Expose
    private String catImg;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCatImg() {
        return catImg;
    }

    public void setCatImg(String catImg) {
        this.catImg = catImg;
    }

}
