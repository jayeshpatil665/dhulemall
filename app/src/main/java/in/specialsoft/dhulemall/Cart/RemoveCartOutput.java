package in.specialsoft.dhulemall.Cart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RemoveCartOutput {

    @SerializedName("output")
    @Expose
    private String output;

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }
}
