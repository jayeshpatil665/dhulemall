package in.specialsoft.dhulemall.ProductDetaild;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import in.specialsoft.dhulemall.Api.ApiCLient;
import in.specialsoft.dhulemall.Api.AuthenticationApi;
import in.specialsoft.dhulemall.MainActivity;
import in.specialsoft.dhulemall.R;
import in.specialsoft.dhulemall.UserDetails.UserDetails;
import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetails extends AppCompatActivity {
    int flag=0;
    //ViewPager viewpager ;
    List<Product1> product;
    String[] links;
    RecyclerView recyclerView;
    String pid;
    TextView txt_productnm,txt_productdisc,txt_productPrice,txt_productQuantity;
    ProgressBar progressBar;
    String UseSkipKey;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        init();

        Paper.init(this);
        UseSkipKey = Paper.book().read(UserDetails.UserSkipKey);
        recyclerView=findViewById(R.id.prouct_img);

        pid=getIntent().getStringExtra("pid");
        getdetails();
        txt_productQuantity.setText(""+1);


    }

    private void init() {
        progressBar=findViewById(R.id.pg);
        txt_productnm=findViewById(R.id.txt_product_name);
        txt_productdisc=findViewById(R.id.txt_product_Discription);
        txt_productPrice=findViewById(R.id.txt_product_price);
        txt_productQuantity=findViewById(R.id.integer_number);
    }

    private void getdetails() {
        progressBar.setVisibility(View.VISIBLE);
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
                    txt_productnm.setText(""+response.body().getProduct().get(0).getPName().toString());
                    txt_productdisc.setText("Discription : "+response.body().getProduct().get(0).getPDescription().toString());
                    txt_productPrice.setText("Price : " + response.body().getProduct().get(0).getPPrice()+"/-");
                    //txt_productQuantity.setText(""+response.body().getProduct().get(0).getPQuantity());
                    progressBar.setVisibility(View.GONE);
                }else {
                    Toast.makeText(ProductDetails.this, "Server Error please try after some time...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProductDetailOutput> call, Throwable t) {

            }
        });

    }

    public void onAdd(View view) {
        int quan=Integer.parseInt(txt_productQuantity.getText().toString());

        txt_productQuantity.setText(quan+1 +"");

    }

    public void onRemove(View view) {
        int quan=Integer.parseInt(txt_productQuantity.getText().toString());
        if (quan <2 ) {
            Toast.makeText(ProductDetails.this,"You have to put atleast 1 item",Toast.LENGTH_LONG).show();
        }else {
            txt_productQuantity.setText(quan-1+"");
        }
    }

    public void AddtoCart(View view) {
        if (flag==1){
            Snackbar.make(findViewById(R.id.rel),"Product Already Added To Cart Sucessfully",Snackbar.LENGTH_LONG).show();
        }
        else {
            if ("skiped".equals(UseSkipKey))
            {
                Toast.makeText(ProductDetails.this, "First Login to Add to cart !", Toast.LENGTH_SHORT).show();
            }
            else
            {
                addtocartApi();
            }
        }
    }

    private void addtocartApi() {
        if ("skiped".equals(UseSkipKey))
        {
            Toast.makeText(ProductDetails.this, "First Login to Add to cart !", Toast.LENGTH_SHORT).show();
        }
        else
        {
            progressBar.setVisibility(View.VISIBLE);
            AuthenticationApi api=ApiCLient.getClient().create(AuthenticationApi.class);
            AddToCartInput i=new AddToCartInput();

            int id = Paper.book().read(UserDetails.UserIDKey);
            i.setId(id);
            i.setPId(pid);
            i.setPQuantity(txt_productQuantity.getText().toString());
            Call<AddtoCartOutput> call =api.Addtocart(i);
            call.enqueue(new Callback<AddtoCartOutput>() {
                @Override
                public void onResponse(Call<AddtoCartOutput> call, Response<AddtoCartOutput> response) {
                    if(response.body()!=null){
                        progressBar.setVisibility(View.GONE);
                        //Snackbar.make(findViewById(R.id.rel),response.body().getOutput().toString(),Snackbar.LENGTH_LONG).show();
                        Toast.makeText(ProductDetails.this, "Product Added succesfully !", Toast.LENGTH_SHORT).show();
                        flag=1;
                        Intent intent = new Intent(ProductDetails.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }else {
                        Snackbar.make(findViewById(R.id.rel),"Network Error Check internet connetion and try again..",Snackbar.LENGTH_LONG).show();

                    }
                }

                @Override
                public void onFailure(Call<AddtoCartOutput> call, Throwable t) {

                }
            });
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}