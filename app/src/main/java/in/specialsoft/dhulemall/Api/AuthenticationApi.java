package in.specialsoft.dhulemall.Api;

import in.specialsoft.dhulemall.Catgories.CategoriesOutput;
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
    @GET("API/Category/returnData.php")
    Call<CategoriesOutput> getList();
}
