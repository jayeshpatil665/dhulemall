package in.specialsoft.dhulemall.ProductDetaild;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
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
        Glide.with(holder.productimg.getContext())
                .load(products[position])
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .circleCrop()
                .placeholder(R.drawable.ic_user)

                .into(holder.productimg);
        //Picasso.get().load(products[position]).into(holder.productimg);

    }

    @Override
    public int getItemCount() {
        return products.length;
    }

    public class Viewhold extends RecyclerView.ViewHolder {
        ImageView productimg;


        public Viewhold(@NonNull View itemView) {
            super(itemView);
            productimg=itemView.findViewById(R.id.prouct_img1);
        }
    }
}
