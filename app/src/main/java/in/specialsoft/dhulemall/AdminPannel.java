package in.specialsoft.dhulemall;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import in.specialsoft.dhulemall.MyOrdersList.MyOrderList;

public class AdminPannel extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_pannel);
    }

    @Override
    public void onBackPressed() {
        //
    }

    public void logOutButton(View view) {
        Intent intent = new Intent(AdminPannel.this,SplashScreen.class);
        startActivity(intent);
        finish();
    }

    public void addRemoveCategory(View view) {
        Intent intent = new Intent(AdminPannel.this,AdminAddRemoveCategory.class);
        startActivity(intent);
    }

    public void toAddNewProduct(View view) {
        Intent intent = new Intent(AdminPannel.this,AddNewProduct.class);
        startActivity(intent);
    }

    public void toRemoveProduct(View view) {
        Intent intent = new Intent(AdminPannel.this,RemoveSpecificProduct.class);
        startActivity(intent);
    }

    public void getNewOrderList(View view) {
        FeatureContraoller.getInstance().setFlag(2);
        FeatureContraoller.getInstance().setStatus("Not-shiped");
        Intent intent=new Intent(AdminPannel.this, MyOrderList.class);
        startActivity(intent);


    }

    public void getShipedOrder(View view) {
        FeatureContraoller.getInstance().setFlag(2);
        FeatureContraoller.getInstance().setStatus("Shipped");
        Intent intent=new Intent(AdminPannel.this, MyOrderList.class);
        startActivity(intent);

    }

    public void getDeliveredOrders(View view) {
        FeatureContraoller.getInstance().setFlag(2);
        FeatureContraoller.getInstance().setStatus("Delivered");

        Intent intent=new Intent(AdminPannel.this, MyOrderList.class);
        //intent.putExtra("flag",2);
        //intent.putExtra("state","Delivered");
        startActivity(intent);
    }
}