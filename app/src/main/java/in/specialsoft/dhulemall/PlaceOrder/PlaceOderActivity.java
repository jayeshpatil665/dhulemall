package in.specialsoft.dhulemall.PlaceOrder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import in.specialsoft.dhulemall.Api.ApiCLient;
import in.specialsoft.dhulemall.Api.AuthenticationApi;
import in.specialsoft.dhulemall.MainActivity;
import in.specialsoft.dhulemall.R;
import in.specialsoft.dhulemall.UserDetails.UserDetails;
import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static in.specialsoft.dhulemall.CreateAccountActivity.isValidMob;

public class PlaceOderActivity extends AppCompatActivity {
    private EditText confirm_name,confirm_number,confirm_address,city1;
    private Button confirm_order_btn;
    private String orderID;
    private TextView confirm_price,confirm_ID;
    private ProgressBar progressBar;
    int totalAmount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_oder);
        init();
        totalAmount = getIntent().getIntExtra("value",0);
        confirm_price.setText("Amount to Pay : "+totalAmount);
        genrateorderid();
        confirm_order_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name,phone,city,address;
                name=confirm_name.getText().toString();
                phone=confirm_number.getText().toString();
                city=city1.getText().toString().trim();
                address=confirm_address.getText().toString();
                if(name.equals("") || phone.equals("") || city.equals("") || address.equals("")){
                    Toast.makeText(PlaceOderActivity.this,"Please Enter all Required fields",Toast.LENGTH_LONG).show();
                }else
                {
                    if (isValidMob(phone)){
                        placeOrder(name,phone,city,address);
                    }
                    else {
                        Toast.makeText(PlaceOderActivity.this, "Please check your Mobile Number", Toast.LENGTH_SHORT).show();
                    }
                }
            };
        });
    }

    private void placeOrder(String name, String phone, String city, String address) {
        progressBar.setVisibility(View.VISIBLE);
        int id = Paper.book().read(UserDetails.UserIDKey);
        String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());

        AuthenticationApi api= ApiCLient.getClient().create(AuthenticationApi.class);
        PlaceOrderInput i=new PlaceOrderInput();
        i.setAddress(address);
        i.setCity(city);
        i.setName(name);
        i.setPhoneNumber(phone);
        i.setOrderState("Not-shiped");
        i.setId(id);
        i.setOrderId(orderID);
        i.setTotalPrice(String.valueOf(totalAmount));
        i.setDateTime(currentDateTimeString);
        Call<PlaceOrderOutput> call=api.placeOrder(i);
        call.enqueue(new Callback<PlaceOrderOutput>() {
            @Override
            public void onResponse(Call<PlaceOrderOutput> call, Response<PlaceOrderOutput> response) {
                if(response.body()!=null){
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(PlaceOderActivity.this,"Order Placed Succesfully !",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(PlaceOderActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(PlaceOderActivity.this,"Server Down, Please Try again after some time",Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<PlaceOrderOutput> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void genrateorderid() {
        String saveCurrentTime,saveCurrentDate;
        Random random = new Random();
        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());
        SimpleDateFormat currentTime = new SimpleDateFormat("HHmmss");
        saveCurrentTime = currentTime.format(calForDate.getTime());
        orderID =random.nextInt(1000)+saveCurrentTime.toString().toLowerCase()+random.nextInt(100);
        confirm_ID.setText("Order ID : "+orderID);
    }

    private void init() {
        progressBar=findViewById(R.id.pg);
        confirm_name =findViewById(R.id.confirm_name);
        confirm_number=findViewById(R.id.confirm_number);
        confirm_address=findViewById(R.id.confirm_address);
        confirm_order_btn=findViewById(R.id.confirm_order_btn);
        city1=findViewById(R.id.confirm_city);
        confirm_price = findViewById(R.id.confirm_price);
        confirm_ID = findViewById(R.id.confirm_ID);
    }

}