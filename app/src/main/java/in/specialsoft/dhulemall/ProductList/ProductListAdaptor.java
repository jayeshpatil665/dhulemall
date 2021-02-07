package in.specialsoft.dhulemall.ProductList;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import in.specialsoft.dhulemall.ProductDetaild.ProductDetails;
import in.specialsoft.dhulemall.R;

public class ProductListAdaptor extends RecyclerView.Adapter<ProductListAdaptor.ViewHolder> {
    List<Product> productList;
    Context context;
    public ProductListAdaptor(List<Product> productList, Context context){
        this.context=context;
        this.productList=productList;
    }
    @NonNull
    @Override
    public ProductListAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View product=layoutInflater.inflate(R.layout.product_layout,parent,false);
        ViewHolder viewHolder=new ViewHolder(product);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductListAdaptor.ViewHolder holder, int position) {
        Glide.with(context)
                .load(productList.get(position).getPImage1())
                .apply(RequestOptions.centerCropTransform())
                .placeholder(R.drawable.ic_loading)
                .into(holder.proctimg);
        holder.name.setText(""+productList.get(position).getPName().toString());
        holder.price.setText("Rs : " +productList.get(position).getPPrice().toString()+"/-");
        holder.cat.setText("Category : "+productList.get(position).getCategoryName().toString());
        holder.disc.setText(""+productList.get(position).getPDescription().toString());

        holder.proctimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, ProductDetails.class);
                intent.putExtra("pid",productList.get(position).getPId().toString());
                context.startActivity(intent);

            }
        });


        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, ProductDetails.class);
                intent.putExtra("pid",productList.get(position).getPId().toString());
                context.startActivity(intent);

            }
        });

        holder.price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, ProductDetails.class);
                intent.putExtra("pid",productList.get(position).getPId().toString());
                context.startActivity(intent);

            }
        });
        holder.c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, ProductDetails.class);
                intent.putExtra("pid",productList.get(position).getPId().toString());
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public void filterData(List<Product> filterlist) {
        productList=filterlist;
        notifyDataSetChanged();


    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView proctimg;
        CardView c;
        TextView name,disc,price,cat;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            proctimg=itemView.findViewById(R.id.product_image);
            name=itemView.findViewById(R.id.txt_product_name);
            disc=itemView.findViewById(R.id.txt_product_Discription);
            price=itemView.findViewById(R.id.txt_product_price);
            cat=itemView.findViewById(R.id.txt_product_Category);
            //cat=itemView.findViewById(R.id.accelerate);
            c=itemView.findViewById(R.id.card);
        }
    }
}