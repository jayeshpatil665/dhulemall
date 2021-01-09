package in.specialsoft.dhulemall.MyOrdersList;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.lang.reflect.UndeclaredThrowableException;
import java.util.List;

import in.specialsoft.dhulemall.AdminOrderStates.OrderOverviewInput;
import in.specialsoft.dhulemall.AdminPannel;
import in.specialsoft.dhulemall.Api.ApiCLient;
import in.specialsoft.dhulemall.Api.AuthenticationApi;
import in.specialsoft.dhulemall.FeatureContraoller;
import in.specialsoft.dhulemall.Profile;
import in.specialsoft.dhulemall.R;
import in.specialsoft.dhulemall.UserDetails.UserDetails;
import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyOrderList extends AppCompatActivity {
    RecyclerView orderListRecycler;
    List<Order> orderList;
    ProgressBar progressBar;
    int flag;
    String  orderState;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order_list);
        orderListRecycler=findViewById(R.id.orderrecycler);
        progressBar=findViewById(R.id.pg);
        progressBar.setVisibility(View.GONE);
        flag= FeatureContraoller.getInstance().getFlag();
        orderState=FeatureContraoller.getInstance().getStatus();
        if (flag==1){
            getOrderList();

        }else if (flag==2){
            getAdminOrder();
        }

    }
    @Override
    public void onBackPressed() {
        if (flag==1){
           startActivity(new Intent(MyOrderList.this, Profile.class));
           finish();
        }else if (flag==2){
            startActivity(new Intent(MyOrderList.this, AdminPannel.class));

        }
    }



    private void getAdminOrder() {
        progressBar.setVisibility(View.VISIBLE);
        AuthenticationApi api=ApiCLient.getClient().create(AuthenticationApi.class);
        OrderOverviewInput i=new OrderOverviewInput();
        i.setOrderState(orderState);
        Call<OrderListOutput> call=api.getOrderOverView(i);
        Log.e("TAG", "orderstate input : "+new Gson().toJson(i));
        call.enqueue(new Callback<OrderListOutput>() {
            @Override
            public void onResponse(Call<OrderListOutput> call, Response<OrderListOutput> response) {
                Log.e("TAG", "orderstate input : "+new Gson().toJson(response.body()));

                if (response.body()!=null){
                    orderList=response.body().getOrder();
                    progressBar.setVisibility(View.GONE);
                    OrderListAdaptor adaptor=new OrderListAdaptor(orderList,MyOrderList.this,2);
                    orderListRecycler.setLayoutManager(new LinearLayoutManager(MyOrderList.this));
                    orderListRecycler.setAdapter(adaptor);

                    Log.e("TAG", "orderstate input : "+new Gson().toJson(response.body()));
                }else {
                    Toast.makeText(MyOrderList.this,"Server Down plase try again after some time",Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<OrderListOutput> call, Throwable t) {

            }
        });

    }

    private void getOrderList() {
        progressBar.setVisibility(View.VISIBLE);
        AuthenticationApi api= ApiCLient.getClient().create(AuthenticationApi.class);
        int id = Paper.book().read(UserDetails.UserIDKey);
        OrderListInput i=new OrderListInput();
        i.setId(id);
        Call<OrderListOutput> call=api.getAllOrder(i);
        call.enqueue(new Callback<OrderListOutput>() {
            @Override
            public void onResponse(Call<OrderListOutput> call, Response<OrderListOutput> response) {
                if (response.body()!=null){
                    orderList=response.body().getOrder();
                    progressBar.setVisibility(View.GONE);
                    OrderListAdaptor adaptor=new OrderListAdaptor(orderList,MyOrderList.this,1);
                    orderListRecycler.setLayoutManager(new LinearLayoutManager(MyOrderList.this));
                    orderListRecycler.setAdapter(adaptor);
                }else {
                    progressBar.setVisibility(View.GONE);

                    Toast.makeText(MyOrderList.this,"Server Down plase try again after some time",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<OrderListOutput> call, Throwable t) {

            }
        });
    }
}