package in.specialsoft.dhulemall.ProductByCategory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import in.specialsoft.dhulemall.Api.ApiCLient;
import in.specialsoft.dhulemall.Api.AuthenticationApi;
import in.specialsoft.dhulemall.ProductList.Product;
import in.specialsoft.dhulemall.ProductList.ProductListAdaptor;
import in.specialsoft.dhulemall.ProductList.ProductListInput;
import in.specialsoft.dhulemall.ProductList.ProductListOutput;
import in.specialsoft.dhulemall.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductByCatgory extends AppCompatActivity {
    String categoryname;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    List<Product> productsList;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_by_catgory);
        categoryname=getIntent().getStringExtra("name");
        recyclerView=findViewById(R.id.productbycategory);
        progressBar=findViewById(R.id.pgbar);
        textView=findViewById(R.id.txt);
        getproduct();
    }

    private void getproduct() {
        AuthenticationApi api= ApiCLient.getClient().create(AuthenticationApi.class);
        ProductListInput i=new ProductListInput();
        i.setCategoryName(categoryname);
        Call<ProductListOutput> call=api.getProductlist(i);
        call.enqueue(new Callback<ProductListOutput>() {
            @Override
            public void onResponse(Call<ProductListOutput> call, Response<ProductListOutput> response) {
                if (response.body()!=null){
                    productsList=  response.body().getProducts();
                    if (productsList==null){
                        textView.setVisibility(View.VISIBLE);
                    }else {

                        ProductListAdaptor adaptor=new ProductListAdaptor(productsList,ProductByCatgory.this);
                        GridLayoutManager gridLayoutManger = new GridLayoutManager(ProductByCatgory.this,2);

                        recyclerView.setLayoutManager(gridLayoutManger);
                        recyclerView.setHasFixedSize(false);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAdapter(adaptor);
                    }
                }
            }

            @Override
            public void onFailure(Call<ProductListOutput> call, Throwable t) {
                Log.i("error",t.toString());
                Toast.makeText(ProductByCatgory.this,t.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }
}