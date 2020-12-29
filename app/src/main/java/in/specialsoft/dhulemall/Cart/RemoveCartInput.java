package in.specialsoft.dhulemall.Cart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RemoveCartInput {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("p_id")
    @Expose
    private String pId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPId() {
        return pId;
    }

    public void setPId(String pId) {
        this.pId = pId;
    }
}
