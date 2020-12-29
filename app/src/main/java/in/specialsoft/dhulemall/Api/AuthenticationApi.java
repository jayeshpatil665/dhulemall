package in.specialsoft.dhulemall.Api;

import in.specialsoft.dhulemall.Cart.CartInput;
import in.specialsoft.dhulemall.Cart.CartOutput;
import in.specialsoft.dhulemall.Cart.RemoveCartInput;
import in.specialsoft.dhulemall.Cart.RemoveCartOutput;
import in.specialsoft.dhulemall.Catgories.CategoriesOutput;
import in.specialsoft.dhulemall.MyOrdersList.OrderDetailInput;
import in.specialsoft.dhulemall.MyOrdersList.OrderDetailOutput;
import in.specialsoft.dhulemall.MyOrdersList.OrderListInput;
import in.specialsoft.dhulemall.MyOrdersList.OrderListOutput;
import in.specialsoft.dhulemall.PlaceOrder.PlaceOrderInput;
import in.specialsoft.dhulemall.PlaceOrder.PlaceOrderOutput;
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
    @POST("API/Cart/retriveCart.php")
    Call<CartOutput> retriveCart(@Body CartInput i);

    @POST("API/Product/UserSide/getProductByID.php")
    Call<ProductDetailOutput> getProductDetail(@Body ProductDetailInput i);

    @GET("API/Category/returnData.php")
    Call<CategoriesOutput> getList();
    @POST("API/Cart/removeFromCart.php")
    Call<RemoveCartOutput> deleteItem(@Body RemoveCartInput i);
    @POST("API/OrderOverview/placeOrder.php")
    Call<PlaceOrderOutput> placeOrder(@Body PlaceOrderInput i);
      @POST("API/OrderOverview/myOrders.php")
      Call<OrderListOutput> getAllOrder(@Body OrderListInput i);
      @POST("API/OrderOverview/myOrderDetails.php")
      Call<OrderDetailOutput> getOrderDetails(@Body OrderDetailInput i);

}
