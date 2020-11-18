package in.specialsoft.dhulemall;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

public class AdminUpdateProductDetails extends AppCompatActivity {

    String selectedPro,selectedCat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_update_product_details);

        selectedPro =  getIntent().getExtras().getString("p_name");
        selectedCat = getIntent().getExtras().getString("category_name");
        getProductbyNameCat(selectedPro,selectedCat);
    }
    //Load product Details
    private void getProductbyNameCat(String selectedPro, String selectedCat) {
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {

                String[] field = new String[2];
                field[0] = "p_name";
                field[1] = "category_name";

                String[] data = new String[2];
                data[0] = selectedPro;
                data[1] = selectedCat;

                PutData putData = new PutData("https://dhulemall.ml/API/Product/getProductDetails.php","POST",field,data);
                if (putData.startPut()){
                    if (putData.onComplete()){
                        String result = putData.getResult();
                        if (!result.equals("errorAPI")){
                            Log.i("OutPut is : ",""+result);
                        }
                        else {
                            Toast.makeText(AdminUpdateProductDetails.this, "Already deleted product ! "+result, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }
}