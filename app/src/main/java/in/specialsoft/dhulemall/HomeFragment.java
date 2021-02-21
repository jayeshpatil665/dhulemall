package in.specialsoft.dhulemall;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
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
    List<Product> productsList,displayedList;
    View root;
    ProductListAdaptor mAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    ProductListAdaptor adaptor;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        root=inflater.inflate(R.layout.fragment_home, container, false);
        home_recycler=root.findViewById(R.id.homerecycler);
        swipeRefreshLayout=root.findViewById(R.id.swipRefresh);
        progressBar=root.findViewById(R.id.pgbar);
        progressBar.setVisibility(View.VISIBLE);
//         adaptor=new ProductListAdaptor(productsList,getContext());


        getproducts();
        adaptor=new ProductListAdaptor(productsList,getContext());

        //      mAdapter=new ProductListAdaptor(productsList,getContext());

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getproducts();
                swipeRefreshLayout.setRefreshing(false);
            }
        });


        return root;

    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.custom_toolbar2,menu);

        MenuItem item = menu.findItem(R.id.navigation_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter(query.toString());
                //        mAdapter.getFilter().filter(query);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//                mAdapter.getFilter().filter(newText);
                filter(newText);
                return true;

            }


        });

        super.onCreateOptionsMenu(menu, inflater);

    }

    private void filter(String text) {
        try {
            List<Product> filterlist = new ArrayList<>();
            for (Product p : productsList) {
                if (p.getPName().toLowerCase().toString().contains(text.toLowerCase().toString()) || p.getPDescription().toLowerCase().toString().contains(text.toLowerCase().toString()) || p.getCategoryName().toLowerCase().toString().contains(text.toLowerCase().toString())) {
                    filterlist.add(p);
                }
                adaptor.filterData(filterlist);
            }
        }catch (Exception e){
            Log.e("tag",e.toString());
        }
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
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);

                    adaptor=new ProductListAdaptor(productsList,getContext());

                    home_recycler.setLayoutManager(gridLayoutManager);
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