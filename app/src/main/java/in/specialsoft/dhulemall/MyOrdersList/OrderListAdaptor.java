package in.specialsoft.dhulemall.MyOrdersList;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import in.specialsoft.dhulemall.AdminOrderStates.ChangeOrderStateInput;
import in.specialsoft.dhulemall.AdminOrderStates.OutPut;
import in.specialsoft.dhulemall.AdminPannel;
import in.specialsoft.dhulemall.Api.ApiCLient;
import in.specialsoft.dhulemall.Api.AuthenticationApi;
import in.specialsoft.dhulemall.Catgories.CategorydAaptor;
import in.specialsoft.dhulemall.FeatureContraoller;
import in.specialsoft.dhulemall.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderListAdaptor extends RecyclerView.Adapter<OrderListAdaptor.ViewHolder> {
    List<Order> orderList;
    Context context;
    int flag;

    public OrderListAdaptor(List<Order> orderList, Context context,int flag) {
        this.orderList = orderList;
        this.context = context;
        this.flag=flag;
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
        if (flag==1) {
            holder.dttime.setText("Date/Time : "+orderList.get(position).getDateTime().toString());

            holder.txt_name.setText("Billing Name : " + orderList.get(position).getName().toString());
            holder.txt_phone.setText("Mobile No : " + orderList.get(position).getPhoneNumber().toString());
            holder.txt_address.setText("Billing Address : " + orderList.get(position).getAddress() + "," + orderList.get(position).getCity().toString());
            holder.txt_price.setText("Total Amount : " + orderList.get(position).getTotalPrice().toString());
            holder.txt_status.setText("Shipping Status : " + orderList.get(position).getOrderState().toString());
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String orderid = orderList.get(position).getOrderId().toString();
                    Intent intent = new Intent(context, ActivityOrderDetails.class);
                    FeatureContraoller.getInstance().setOrderid(orderid);

                    context.startActivity(intent);
                }
            });
        }else if (flag==2){
            holder.txt_name.setText("Billing Name : " + orderList.get(position).getName().toString());
            holder.txt_phone.setText("Mobile No : " + orderList.get(position).getPhoneNumber().toString());
            holder.txt_address.setText("Billing Address : " + orderList.get(position).getAddress() + "," + orderList.get(position).getCity().toString());
            holder.txt_price.setText("Total Amount : " + orderList.get(position).getTotalPrice().toString());
            holder.txt_status.setText("Shipping Status : " + orderList.get(position).getOrderState().toString());
            holder.dttime.setText("Date/Time : "+orderList.get(position).getDateTime().toString());
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String orderid = orderList.get(position).getOrderId().toString();
                    FeatureContraoller.getInstance().setOrderid(orderid);
                    FeatureContraoller.getInstance().setId(orderList.get(position).getId());
                    Intent intent = new Intent(context, ActivityOrderDetails.class);
                    //intent.putExtra("orderid", orderid);
                    context.startActivity(intent);
                }
            });
            holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    CharSequence options[] = new CharSequence[]
                            {
                                    "state : shipped",
                                    "state : Delivered",
                                    "Remove from List"
                            };
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Select Action");
                    builder.setItems(options, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {
                            if (which == 0)
                            {
                                if (orderList.get(position).getOrderState().equals("Not-shiped"))
                                {
                                    String OrderID = orderList.get(position).getOrderId().toString();
                                    AuthenticationApi api= ApiCLient.getClient().create(AuthenticationApi.class);
                                    ChangeOrderStateInput i=new ChangeOrderStateInput();
                                    i.setOrderId(OrderID);
                                    i.setOrderState("Shipped");
                                    Call<OutPut> call=api.changeOrderState(i);
                                    call.enqueue(new Callback<OutPut>() {
                                        @Override
                                        public void onResponse(Call<OutPut> call, Response<OutPut> response) {
                                            if (response.body() != null) {
                                                Intent intent=new Intent(context, AdminPannel.class);
                                                //intent.putExtra("flag",2);
                                                context.startActivity(intent);

                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<OutPut> call, Throwable t) {

                                        }
                                    });
                                }
                                else if (orderList.get(position).getOrderState().equals("Shipped"))
                                {
                                    Toast.makeText(context, "Product shipped already", Toast.LENGTH_SHORT).show();
                                }
                                else if (orderList.get(position).getOrderState().equals("Delivered"))
                                {
                                    String OrderID = orderList.get(position).getOrderId().toString();
                                    AuthenticationApi api= ApiCLient.getClient().create(AuthenticationApi.class);
                                    ChangeOrderStateInput i=new ChangeOrderStateInput();
                                    i.setOrderId(OrderID);
                                    i.setOrderState("Shipped");
                                    Call<OutPut> call=api.changeOrderState(i);
                                    call.enqueue(new Callback<OutPut>() {
                                        @Override
                                        public void onResponse(Call<OutPut> call, Response<OutPut> response) {
                                            if (response.body() != null) {
                                                Intent intent=new Intent(context,AdminPannel.class);
                                                //intent.putExtra("flag",2);//
                                                context.startActivity(intent);
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<OutPut> call, Throwable t) {

                                        }
                                    });
                                }
                            }
                            else if(which == 1)
                            {
                                if (orderList.get(position).getOrderState().equals("Shipped")) {
                                    String OrderID = orderList.get(position).getOrderId().toString();
                                    AuthenticationApi api= ApiCLient.getClient().create(AuthenticationApi.class);
                                    ChangeOrderStateInput i=new ChangeOrderStateInput();
                                    i.setOrderId(OrderID);
                                    i.setOrderState("Delivered");
                                    Call<OutPut> call=api.changeOrderState(i);
                                    call.enqueue(new Callback<OutPut>() {
                                        @Override
                                        public void onResponse(Call<OutPut> call, Response<OutPut> response) {
                                            if (response.body() != null) {
                                                Intent intent=new Intent(context,AdminPannel.class);
                                                //intent.putExtra("flag",2);
                                                context.startActivity(intent);
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<OutPut> call, Throwable t) {

                                        }
                                    });
                                }
                                else if (orderList.get(position).getOrderState().equals("Not-shiped"))
                                {
                                    Toast.makeText(context, "First ship the product !", Toast.LENGTH_SHORT).show();
                                }
                                else if (orderList.get(position).getOrderState().equals("Delivered"))
                                {
                                    Toast.makeText(context, "Already delivered", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else if(which == 2)
                            {
                                if(orderList.get(position).getOrderState().equals("Delivered"))
                                {
                                    String OrderID = orderList.get(position).getOrderId().toString();
                                    AuthenticationApi api= ApiCLient.getClient().create(AuthenticationApi.class);
                                    ChangeOrderStateInput i=new ChangeOrderStateInput();
                                    i.setOrderId(OrderID);
                                    i.setOrderState("Remove");
                                    Call<OutPut> call=api.changeOrderState(i);
                                    call.enqueue(new Callback<OutPut>() {
                                        @Override
                                        public void onResponse(Call<OutPut> call, Response<OutPut> response) {
                                            if (response.body() != null) {
                                                Toast.makeText(context, "Removed ...", Toast.LENGTH_SHORT).show();
                                                Intent intent=new Intent(context,AdminPannel.class);
                                                //intent.putExtra("flag",2);
                                                context.startActivity(intent);
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<OutPut> call, Throwable t) {

                                        }
                                    });

                                }
                                else if (orderList.get(position).getOrderState().equals("Shipped"))
                                {
                                    Toast.makeText(context, "Product not Delivered yet !", Toast.LENGTH_SHORT).show();
                                }
                                else if (orderList.get(position).getOrderState().equals("Not-shiped"))
                                {
                                    String OrderID = orderList.get(position).getOrderId().toString();
                                    AuthenticationApi api= ApiCLient.getClient().create(AuthenticationApi.class);
                                    ChangeOrderStateInput i=new ChangeOrderStateInput();
                                    i.setOrderId(OrderID);
                                    i.setOrderState("Remove");
                                    Call<OutPut> call=api.changeOrderState(i);
                                    call.enqueue(new Callback<OutPut>() {
                                        @Override
                                        public void onResponse(Call<OutPut> call, Response<OutPut> response) {
                                            if (response.body() != null) {
                                                Toast.makeText(context, "Removed ...", Toast.LENGTH_SHORT).show();
                                                Intent intent=new Intent(context,AdminPannel.class);

                                                context.startActivity(intent);
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<OutPut> call, Throwable t) {

                                        }
                                    });

                                }
                            }

                        }

                    });

                    builder.show();



                    return false;
                }
            });
        }

    }


    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_name,txt_price,txt_address,txt_phone,txt_status,dttime;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_name=itemView.findViewById(R.id.txt_name);
            txt_price=itemView.findViewById(R.id.txt_price);
            txt_address=itemView.findViewById(R.id.txt_address);
            txt_phone=itemView.findViewById(R.id.txt_phone);
            txt_status=itemView.findViewById(R.id.txt_status);
            cardView=itemView.findViewById(R.id.crd);
            dttime=itemView.findViewById(R.id.txt_dttime);
        }
    }
}
