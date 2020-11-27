package in.specialsoft.dhulemall.Catgories;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import in.specialsoft.dhulemall.ProductList.ProductListAdaptor;
import in.specialsoft.dhulemall.R;

public class CategorydAaptor extends RecyclerView.Adapter<CategorydAaptor.ViewHolder> {
    Context context;
    List<Category1> category;

    public CategorydAaptor(Context context,List<Category1> category) {
        this.context=context;
        this.category=category;

    }

    @NonNull
    @Override
    public CategorydAaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View product=layoutInflater.inflate(R.layout.categorylayout,parent,false);
        CategorydAaptor.ViewHolder viewHolder=new CategorydAaptor.ViewHolder(product);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategorydAaptor.ViewHolder holder, int position) {

        Glide.with(context)
                .load(category.get(position).getCatImg().toString())
                .circleCrop()
                .placeholder(R.drawable.ic_user)
                .into(holder.category_img);

        holder.category_name.setText(""+category.get(position).getCategoryName().toString());
    }

    @Override
    public int getItemCount() {
        return category.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView category_img;
        TextView category_name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            category_img=itemView.findViewById(R.id.category_image);
            category_name=itemView.findViewById(R.id.category_name);

        }
    }
}
