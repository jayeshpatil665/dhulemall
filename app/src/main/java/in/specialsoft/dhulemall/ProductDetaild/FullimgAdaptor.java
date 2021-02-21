package in.specialsoft.dhulemall.ProductDetaild;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import in.specialsoft.dhulemall.R;

public class FullimgAdaptor extends SwiperecyclerAdaptor{
    public FullimgAdaptor(String[] products) {
        super(products);
    }

    @NonNull
    @Override
    public Viewhold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View product=layoutInflater.inflate(R.layout.proimg,parent,false);
        SwiperecyclerAdaptor.Viewhold viewHolder=new SwiperecyclerAdaptor.Viewhold(product);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewhold holder, int position) {
        super.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }
}
