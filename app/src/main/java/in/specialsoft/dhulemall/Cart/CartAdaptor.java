package in.specialsoft.dhulemall.Cart;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import in.specialsoft.dhulemall.Api.ApiCLient;
import in.specialsoft.dhulemall.Api.AuthenticationApi;
import in.specialsoft.dhulemall.Catgories.CategorydAaptor;
import in.specialsoft.dhulemall.MainActivity;
import in.specialsoft.dhulemall.ProductDetaild.ProductDetails;
import in.specialsoft.dhulemall.R;
import in.specialsoft.dhulemall.UserDetails.UserDetails;
import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartAdaptor extends RecyclerView.Adapter<CartAdaptor.ViewHolder> {
    Context context;
    int flag;
    List<Cart> cartitem;
    public CartAdaptor(Context context, List<Cart> cartitem,int flag) {
        this.cartitem=cartitem;
        this.context=context;
        this.flag=flag;
    }

    @NonNull
    @Override
    public CartAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View product=layoutInflater.inflate(R.layout.cartproductlayout,parent,false);
        CartAdaptor.ViewHolder viewHolder=new CartAdaptor.ViewHolder(product);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdaptor.ViewHolder holder, int position) {
        if (flag==1) {
            Glide.with(context)
                    .load(cartitem.get(position).getPImage1().toString())
                    .placeholder(R.drawable.ic_user)
                    .into(holder.productImg);

            holder.price.setText("Price : \u20B9 " + cartitem.get(position).getPPrice().toString() + "/-");
            holder.name.setText("" + cartitem.get(position).getPName().toString());
            holder.discription.setText("" + cartitem.get(position).getPDescription().toString());
            holder.quantity.setText("Quantity: " + cartitem.get(position).getPQuantity().toString());
            holder.cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cancelOrder(position);
                }
            });
            holder.edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ProductDetails.class);
                    intent.putExtra("pid", cartitem.get(position).getPId().toString());
                    context.startActivity(intent);
                }
            });
        }else if (flag==2){
            holder.edit.setVisibility(View.GONE);
            holder.cancel.setVisibility(View.GONE);
            Glide.with(context)
                    .load(cartitem.get(position).getPImage1().toString())
                    .placeholder(R.drawable.ic_user)
                    .into(holder.productImg);

            holder.price.setText("Price : \u20B9 " + cartitem.get(position).getPPrice().toString() + "/-");
            holder.name.setText("" + cartitem.get(position).getPName().toString());
            holder.discription.setText("" + cartitem.get(position).getPDescription().toString());
            holder.quantity.setText("Quantity : " + cartitem.get(position).getPQuantity().toString());
        }
    }

    private void cancelOrder(int p) {
        int id = Paper.book().read(UserDetails.UserIDKey);
        AuthenticationApi api= ApiCLient.getClient().create(AuthenticationApi.class);
        RemoveCartInput i=new RemoveCartInput();
        i.setId(id);
        i.setPId(cartitem.get(p).getPId().toString());
        Call<RemoveCartOutput> call=api.deleteItem(i);
        call.enqueue(new Callback<RemoveCartOutput>() {
            @Override
            public void onResponse(Call<RemoveCartOutput> call, Response<RemoveCartOutput> response) {
                if (response.body()!=null){
                    Toast.makeText(context,response.body().getOutput().toString(),Toast.LENGTH_LONG).show();
                    context.startActivity(new Intent(context, MainActivity.class));
                }else {
                    Toast.makeText(context,"Please Try Again After Some Time",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RemoveCartOutput> call, Throwable t) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return cartitem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Button cancel,edit;
        ImageView productImg;
        CardView cardView;
        TextView quantity,name,price,discription;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cancel=itemView.findViewById(R.id.btn_cancel);
            edit=itemView.findViewById(R.id.btn_edit);

            cardView=itemView.findViewById(R.id.res);
            productImg=itemView.findViewById(R.id.pimg);
            quantity=itemView.findViewById(R.id.pquantity);
            name=itemView.findViewById(R.id.txt_pname);
            price=itemView.findViewById(R.id.txt_prdctprice);
            discription=itemView.findViewById(R.id.txt_pdiscription);

        }
    }
}
