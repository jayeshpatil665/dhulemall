package in.specialsoft.dhulemall;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import in.specialsoft.dhulemall.Api.ApiCLient;
import in.specialsoft.dhulemall.Api.AuthenticationApi;
import in.specialsoft.dhulemall.ProductList.Product;
import in.specialsoft.dhulemall.ProductList.ProductListAdaptor;
import in.specialsoft.dhulemall.ProductList.ProductListInput;
import in.specialsoft.dhulemall.ProductList.ProductListOutput;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    RecyclerView home_recycler;
    ProgressBar progressBar;
    List<Product> productsList;
    View root;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        root=inflater.inflate(R.layout.fragment_home, container, false);
        home_recycler=root.findViewById(R.id.homerecycler);
        swipeRefreshLayout=root.findViewById(R.id.swipRefresh);

       progressBar=root.findViewById(R.id.pgbar);
        progressBar.setVisibility(View.VISIBLE);
            getproducts();

            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    getproducts();
                    swipeRefreshLayout.setRefreshing(false);
                }
            });
        return root;

    }

    private void getproducts() {
        AuthenticationApi api= ApiCLient.getClient().create(AuthenticationApi.class);
        ProductListInput i=new ProductListInput();
        i.setCategoryName("all");
        Call<ProductListOutput> call=api.getProductlist(i);
        call.enqueue(new Callback<ProductListOutput>() {
            @Override
            public void onResponse(Call<ProductListOutput> call, Response<ProductListOutput> response) {
                if (response.body()!=null){
                 progressBar.setVisibility(View.GONE);

                    productsList=  response.body().getProducts();

                    ProductListAdaptor adaptor=new ProductListAdaptor(productsList,getContext());
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

                    home_recycler.setLayoutManager(layoutManager);
                    home_recycler.setHasFixedSize(false);
                    home_recycler.setItemAnimator(new DefaultItemAnimator());
                    home_recycler.setAdapter(adaptor);

                }
            }

            @Override
            public void onFailure(Call<ProductListOutput> call, Throwable t) {
                    Log.i("error",t.toString());
                Toast.makeText(getContext(),t.toString(),Toast.LENGTH_LONG).show();

            }
        });
    }
}
/*
com.google.gson.JsonSyntaxException: java.lang.IllegalStateException: Expected BEGIN_OBJECT but was BEGIN_ARRAY at line 1 column 2 path $
 */