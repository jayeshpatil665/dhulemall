package in.specialsoft.dhulemall.ProductDetaild;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

import in.specialsoft.dhulemall.Api.ApiCLient;
import in.specialsoft.dhulemall.Api.AuthenticationApi;
import in.specialsoft.dhulemall.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetails extends AppCompatActivity {
    ViewPager viewpager ;
    List<Product1> product;
    String[] links;
    RecyclerView recyclerView;
    String pid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        recyclerView=findViewById(R.id.prouct_img);
        pid=getIntent().getStringExtra("pid");
        getdetails();


    }

    private void getdetails() {
        AuthenticationApi api= ApiCLient.getClient().create(AuthenticationApi.class);
        ProductDetailInput i=new ProductDetailInput();
        i.setPId(pid);
        Call<ProductDetailOutput> call=api.getProductDetail(i);
        call.enqueue(new Callback<ProductDetailOutput>() {
            @Override
            public void onResponse(Call<ProductDetailOutput> call, Response<ProductDetailOutput> response) {
                if (response.body()!=null){
                   product=response.body().getProduct();


                   links=new String[3];
                   links[0]=product.get(0).getPImage1().toString();
                    links[2]=product.get(0).getPImage3().toString();
                    links[1]=product.get(0).getPImage2().toString();
                    recyclerView.setLayoutManager(new LinearLayoutManager(ProductDetails.this, LinearLayoutManager.HORIZONTAL, false));
                    recyclerView.setAdapter(new SwiperecyclerAdaptor(links));
                }else {
                    Toast.makeText(ProductDetails.this, "Server Error please try after some time...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProductDetailOutput> call, Throwable t) {

            }
        });

    }
}