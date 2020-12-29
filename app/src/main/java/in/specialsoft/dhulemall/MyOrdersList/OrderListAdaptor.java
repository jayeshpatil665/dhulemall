package in.specialsoft.dhulemall.MyOrdersList;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import in.specialsoft.dhulemall.Catgories.CategorydAaptor;
import in.specialsoft.dhulemall.R;

public class OrderListAdaptor extends RecyclerView.Adapter<OrderListAdaptor.ViewHolder> {
    List<Order> orderList;
    Context context;

    public OrderListAdaptor(List<Order> orderList, Context context) {
        this.orderList = orderList;
        this.context = context;
    }

    @NonNull
    @Override
    public OrderListAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View product=layoutInflater.inflate(R.layout.orderlistlayout,parent,false);
        OrderListAdaptor.ViewHolder viewHolder=new OrderListAdaptor.ViewHolder(product);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OrderListAdaptor.ViewHolder holder, int position) {
        holder.txt_name.setText("Billing Name :"+orderList.get(position).getName().toString());
        holder.txt_phone.setText("Mobile No :"+orderList.get(position).getPhoneNumber().toString());
        holder.txt_address.setText("Billing Address:"+orderList.get(position).getAddress()+","+orderList.get(position).getCity().toString());
        holder.txt_price.setText("Total Amount:"+orderList.get(position).getTotalPrice().toString());
        holder.txt_status.setText("Shipping Status: "+orderList.get(position).getOrderState().toString());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String orderid=orderList.get(position).getOrderId().toString();
                Intent intent=new Intent(context,ActivityOrderDetails.class);
                intent.putExtra("orderid",orderid);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_name,txt_price,txt_address,txt_phone,txt_status;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_name=itemView.findViewById(R.id.txt_name);
            txt_price=itemView.findViewById(R.id.txt_price);
            txt_address=itemView.findViewById(R.id.txt_address);
            txt_phone=itemView.findViewById(R.id.txt_phone);
            txt_status=itemView.findViewById(R.id.txt_status);
            cardView=itemView.findViewById(R.id.crd);
        }
    }
}
