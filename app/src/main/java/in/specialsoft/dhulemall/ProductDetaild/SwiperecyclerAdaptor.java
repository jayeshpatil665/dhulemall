package in.specialsoft.dhulemall.ProductDetaild;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.Picasso;

import java.util.List;

import in.specialsoft.dhulemall.Catgories.CategorydAaptor;
import in.specialsoft.dhulemall.R;

public class SwiperecyclerAdaptor extends RecyclerView.Adapter<SwiperecyclerAdaptor.Viewhold> {
    String[] products;
    public SwiperecyclerAdaptor(String[] products) {
    this.products=products;
    }

    @NonNull
    @Override
    public SwiperecyclerAdaptor.Viewhold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View product=layoutInflater.inflate(R.layout.productdetail,parent,false);
        SwiperecyclerAdaptor.Viewhold viewHolder=new SwiperecyclerAdaptor.Viewhold(product);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SwiperecyclerAdaptor.Viewhold holder, int position) {
        holder.productimg1.setVisibility(View.GONE);
        Glide.with(holder.productimg.getContext())
                .load(products[position])
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .placeholder(R.drawable.ic_loading)
                .into(holder.productimg);
        Glide.with(holder.productimg.getContext())
                .load(products[position])
                .placeholder(R.drawable.ic_loading)
                .into(holder.productimg1);
        holder.productimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             holder.productimg.setVisibility(View.GONE);
                holder.productimg1.setVisibility(View.VISIBLE);
            }
        });
        holder.productimg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.productimg1.setVisibility(View.GONE);
                holder.productimg.setVisibility(View.VISIBLE);
            }
        });

        //Picasso.get().load(products[position]).into(holder.productimg);
    }

    @Override
    public int getItemCount() {
        return products.length;
    }

    public class Viewhold extends RecyclerView.ViewHolder {
        ImageView productimg,productimg1;


        public Viewhold(@NonNull View itemView) {
            super(itemView);
            productimg=itemView.findViewById(R.id.prouct_img1);
            productimg1=itemView.findViewById(R.id.prouct_img2);
        }
    }
}
