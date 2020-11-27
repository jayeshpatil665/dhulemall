package in.specialsoft.dhulemall.Catgories;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.List;

import in.specialsoft.dhulemall.Api.ApiCLient;
import in.specialsoft.dhulemall.Api.AuthenticationApi;
import in.specialsoft.dhulemall.ProductList.ProductListAdaptor;
import in.specialsoft.dhulemall.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryFragment extends Fragment {
RecyclerView recyclerView;
List<Category1>category;
View root;
ProgressBar progressBar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         root=inflater.inflate(R.layout.fragment_category, container, false);
        progressBar=root.findViewById(R.id.pg);
        progressBar.setVisibility(View.VISIBLE);
        // Inflate the layout for this fragment
        getCategories();
            return root;
    }

    private void getCategories() {

        AuthenticationApi api= ApiCLient.getClient().create(AuthenticationApi.class);
        Call<CategoriesOutput> call=api.getList();
        call.enqueue(new Callback<CategoriesOutput>() {
            @Override
            public void onResponse(Call<CategoriesOutput> call, Response<CategoriesOutput> response) {
                if (response.body()!=null){
                    category=response.body().getCategorys();
                    progressBar.setVisibility(View.GONE);
                    recyclerView=root.findViewById(R.id.categories_recycle);

                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
                    recyclerView.setLayoutManager(gridLayoutManager);
                    CategorydAaptor aaptor=new CategorydAaptor(getContext(),category);
                    recyclerView.setAdapter(aaptor);
                }
            }

            @Override
            public void onFailure(Call<CategoriesOutput> call, Throwable t) {

            }
        });
    }
}