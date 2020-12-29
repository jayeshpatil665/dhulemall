package in.specialsoft.dhulemall.MyOrdersList;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.UndeclaredThrowableException;
import java.util.List;

import in.specialsoft.dhulemall.Api.ApiCLient;
import in.specialsoft.dhulemall.Api.AuthenticationApi;
import in.specialsoft.dhulemall.R;
import in.specialsoft.dhulemall.UserDetails.UserDetails;
import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyOrderList extends AppCompatActivity {
    RecyclerView orderListRecycler;
    List<Order> orderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order_list);
        orderListRecycler=findViewById(R.id.orderrecycler);
        getOrderList();
    }

    private void getOrderList() {
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
                    OrderListAdaptor adaptor=new OrderListAdaptor(orderList,MyOrderList.this);
                    orderListRecycler.setLayoutManager(new LinearLayoutManager(MyOrderList.this));
                    orderListRecycler.setAdapter(adaptor);
                }else {
                    Toast.makeText(MyOrderList.this,"Server Down plase try again after some time",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<OrderListOutput> call, Throwable t) {

            }
        });
    }
}