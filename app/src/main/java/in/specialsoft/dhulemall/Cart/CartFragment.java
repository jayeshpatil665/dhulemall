package in.specialsoft.dhulemall.Cart;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import in.specialsoft.dhulemall.Api.ApiCLient;
import in.specialsoft.dhulemall.Api.AuthenticationApi;
import in.specialsoft.dhulemall.PlaceOrder.PlaceOderActivity;
import in.specialsoft.dhulemall.R;
import in.specialsoft.dhulemall.UserDetails.UserDetails;
import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartFragment extends Fragment {
    List<Cart> cartList;
    RecyclerView cartRecycler;
    View root;
    TextView textView,totalvalue;
    Button shop;
    ProgressBar progressBar;
    int overallTotalPrice = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root=inflater.inflate(R.layout.fragment_cart, container, false);
        cartRecycler=root.findViewById(R.id.cartrecycler);
        textView=root.findViewById(R.id.txt);
        progressBar=root.findViewById(R.id.pg);
        totalvalue=root.findViewById(R.id.totalvalue);
        shop=root.findViewById(R.id.shop);

        getCartitem();
        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), PlaceOderActivity.class);
                intent.putExtra("value",overallTotalPrice);
                startActivity(intent);
            }
        });
        return root;
    }

    private void getTotalcartValue() {
        totalvalue.setVisibility(View.VISIBLE);
        shop.setVisibility(View.VISIBLE);
        int sum=0;
        for(int i=0;i<cartList.size();i++){
        overallTotalPrice=overallTotalPrice+Integer.parseInt(cartList.get(i).getPPrice())*Integer.parseInt(cartList.get(i).getPQuantity());

        }
        totalvalue.setText("Total Cart Price : \u20B9 "+overallTotalPrice);
    }

    private void getCartitem() {
        progressBar.setVisibility(View.VISIBLE);
        AuthenticationApi api= ApiCLient.getClient().create(AuthenticationApi.class);
        int id = Paper.book().read(UserDetails.UserIDKey);
        CartInput input=new CartInput();
        input.setId(id);
        Call<CartOutput> call=api.retriveCart(input);
        call.enqueue(new Callback<CartOutput>() {
            @Override
            public void onResponse(Call<CartOutput> call, Response<CartOutput> response) {
                if (response.body()!=null){
                    progressBar.setVisibility(View.GONE);
                    cartList=response.body().getCart();
                    if (cartList == null){
                        textView.setVisibility(View.VISIBLE);
                    }
                    else {
                        progressBar.setVisibility(View.GONE);
                        getTotalcartValue();
                        CartAdaptor adaptor=new CartAdaptor(getContext(),cartList,1);
                        cartRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
                        cartRecycler.setAdapter(adaptor);
                    }
                }else {
                    Toast.makeText(getContext(),"Please Try Again after some time .......",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<CartOutput> call, Throwable t) {

            }
        });
    }
}