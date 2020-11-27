package in.specialsoft.dhulemall.Catgories;

import android.icu.util.ULocale;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Locale;

public class CategoriesOutput {
    @SerializedName("categorys")
    @Expose
    private List<Category1> categorys = null;

    public List<Category1> getCategorys() {
        return categorys;
    }

    public void setCategorys(List<Category1> categorys) {
        this.categorys = categorys;
    }

}
