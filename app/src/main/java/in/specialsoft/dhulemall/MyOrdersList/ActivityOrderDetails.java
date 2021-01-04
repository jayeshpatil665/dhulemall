package in.specialsoft.dhulemall.MyOrdersList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import in.specialsoft.dhulemall.Api.ApiCLient;
import in.specialsoft.dhulemall.Api.AuthenticationApi;
import in.specialsoft.dhulemall.Cart.Cart;
import in.specialsoft.dhulemall.Cart.CartAdaptor;
import in.specialsoft.dhulemall.Cart.CartInput;
import in.specialsoft.dhulemall.Cart.CartOutput;
import in.specialsoft.dhulemall.FeatureContraoller;
import in.specialsoft.dhulemall.R;
import in.specialsoft.dhulemall.UserDetails.UserDetails;
import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityOrderDetails extends AppCompatActivity {
    List<Cart> cartList;
    RecyclerView cartRecycler;
    ProgressBar progressBar;
    String ordid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        cartRecycler=findViewById(R.id.cartrecycler);
        progressBar=findViewById(R.id.pg);
        ordid= FeatureContraoller.getInstance().getOrderid().toString();
        getCartitem();
    }

    private void getCartitem() {

        progressBar.setVisibility(View.VISIBLE);
        AuthenticationApi api= ApiCLient.getClient().create(AuthenticationApi.class);
//        int id = Paper.book().read(UserDetails.UserIDKey);
        OrderDetailInput i=new OrderDetailInput();
        if (FeatureContraoller.getInstance().getFlag()==2){
            i.setOrderId(ordid);
            i.setId(Integer.parseInt(FeatureContraoller.getInstance().getId()));
        }else if(FeatureContraoller.getInstance().getFlag()==1) {
            int id = Paper.book().read(UserDetails.UserIDKey);

            i.setOrderId(ordid);
            i.setId(id);
        }
        Call<OrderDetailOutput> call=api.getOrderDetails(i);
        call.enqueue(new Callback<OrderDetailOutput>() {
            @Override
            public void onResponse(Call<OrderDetailOutput> call, Response<OrderDetailOutput> response) {
                if (response.body()!=null){
                    progressBar.setVisibility(View.GONE);
                    cartList=response.body().getCart();
                        progressBar.setVisibility(View.GONE);
                        CartAdaptor adaptor=new CartAdaptor(getApplicationContext(),cartList,2);
                        cartRecycler.setLayoutManager(new LinearLayoutManager(ActivityOrderDetails.this));
                        cartRecycler.setAdapter(adaptor);
                    }
                }

            @Override
            public void onFailure(Call<OrderDetailOutput> call, Throwable t) {

            }
        });
    }
}