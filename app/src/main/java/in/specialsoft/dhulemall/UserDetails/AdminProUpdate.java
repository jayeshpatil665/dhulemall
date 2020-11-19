package in.specialsoft.dhulemall.UserDetails;

public class AdminProUpdate {
    private String p_id,category_name, p_name, p_price,p_quantity,p_description,p_image1,p_image2,p_image3;

    public AdminProUpdate() {
    }

    public AdminProUpdate(String p_id, String category_name, String p_name, String p_price, String p_quantity, String p_description, String p_image1, String p_image2, String p_image3) {
        this.p_id = p_id;
        this.category_name = category_name;
        this.p_name = p_name;
        this.p_price = p_price;
        this.p_quantity = p_quantity;
        this.p_description = p_description;
        this.p_image1 = p_image1;
        this.p_image2 = p_image2;
        this.p_image3 = p_image3;
    }

    public String getP_id() {
        return p_id;
    }

    public void setP_id(String p_id) {
        this.p_id = p_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getP_name() {
        return p_name;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }

    public String getP_price() {
        return p_price;
    }

    public void setP_price(String p_price) {
        this.p_price = p_price;
    }

    public String getP_quantity() {
        return p_quantity;
    }

    public void setP_quantity(String p_quantity) {
        this.p_quantity = p_quantity;
    }

    public String getP_description() {
        return p_description;
    }

    public void setP_description(String p_description) {
        this.p_description = p_description;
    }

    public String getP_image1() {
        return p_image1;
    }

    public void setP_image1(String p_image1) {
        this.p_image1 = p_image1;
    }

    public String getP_image2() {
        return p_image2;
    }

    public void setP_image2(String p_image2) {
        this.p_image2 = p_image2;
    }

    public String getP_image3() {
        return p_image3;
    }

    public void setP_image3(String p_image3) {
        this.p_image3 = p_image3;
    }
}
