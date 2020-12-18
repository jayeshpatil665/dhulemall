package in.specialsoft.dhulemall.Api;

import in.specialsoft.dhulemall.Catgories.CategoriesOutput;
import in.specialsoft.dhulemall.ProductDetaild.AddToCartInput;
import in.specialsoft.dhulemall.ProductDetaild.AddtoCartOutput;
import in.specialsoft.dhulemall.ProductDetaild.ProductDetailInput;
import in.specialsoft.dhulemall.ProductDetaild.ProductDetailOutput;
import in.specialsoft.dhulemall.ProductList.ProductListInput;
import in.specialsoft.dhulemall.ProductList.ProductListOutput;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface AuthenticationApi {
    //product list
    @POST("API/Product/UserSide/getCategoryList.php")
    Call<ProductListOutput> getProductlist(@Body ProductListInput i);
    @POST("API/Cart/addToCart.php")
    Call<AddtoCartOutput> Addtocart(@Body AddToCartInput i);

    @POST("API/Product/UserSide/getProductByID.php")
    Call<ProductDetailOutput> getProductDetail(@Body ProductDetailInput i);

    @GET("API/Category/returnData.php")
    Call<CategoriesOutput> getList();
}
