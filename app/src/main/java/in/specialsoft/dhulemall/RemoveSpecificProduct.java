package in.specialsoft.dhulemall;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import in.specialsoft.dhulemall.Api.ApiCLient;

public class RemoveSpecificProduct extends AppCompatActivity {

    AutoCompleteTextView et_view_Categoryyy,et_view_pro_list;

    ArrayAdapter<String> adapter;
    String selectedDelCat ="0",selectedPreDelCat="0";
    ArrayAdapter<String> adapter1;
    String selectedDelPro ="0";

    ImageButton imageButton5;
    Button button9;
    TextInputLayout floating_hintt;
    CheckBox checkBox3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_specific_product);

        et_view_Categoryyy = findViewById(R.id.et_view_Categoryyy);
        imageButton5 = findViewById(R.id.imageButton5);
        button9 = findViewById(R.id.button9);
        floating_hintt = findViewById(R.id.floating_hintt);
        et_view_pro_list = findViewById(R.id.et_view_pro_list);
        checkBox3 = findViewById(R.id.checkBox3);

        getAllAvailableCategorys();
        et_view_Categoryyy.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(RemoveSpecificProduct.this, ""+adapter.getItem(position), Toast.LENGTH_SHORT).show();
                selectedDelCat = adapter.getItem(position);

                floating_hintt.setVisibility(View.VISIBLE);
                et_view_pro_list.setText("");
                imageButton5.setVisibility(View.GONE);
                button9.setVisibility(View.GONE);
                checkBox3.setVisibility(View.GONE);
                checkBox3.setChecked(false);

                getAllAvailableProductNames(selectedDelCat);
            }
        });

        et_view_pro_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(RemoveSpecificProduct.this, ""+adapter1.getItem(position), Toast.LENGTH_SHORT).show();
                selectedDelPro = adapter1.getItem(position);
                imageButton5.setVisibility(View.VISIBLE);
                button9.setVisibility(View.VISIBLE);
                checkBox3.setVisibility(View.VISIBLE);
            }
        });
    }

    private void getAllAvailableProductNames(String selectedDelCat) {
        final Gson gson= new Gson();
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                String[] field = new String[1];
                field[0] = "category_name";

                String[] data = new String[1];
                data[0] = selectedDelCat;

                PutData putData = new PutData(ApiCLient.BASE_URL+"API/Product/getProductNameList.php","POST",field,data);
                if (putData.startPut()){
                    if (putData.onComplete()){
                        String result = putData.getResult();
                        String[] catList =  gson.fromJson(result, String[].class);
                        if (!result.equals("null")){
                            try {
                                adapter1 = new ArrayAdapter<String>(RemoveSpecificProduct.this,R.layout.cat_list_item,catList);
                                et_view_pro_list.setThreshold(1);
                                et_view_pro_list.setAdapter(adapter1);

                            }catch (Exception e){}
                        }
                        else {
                            et_view_pro_list.setText("");
                            String[] empty = {};
                            adapter1 = new ArrayAdapter<String>(RemoveSpecificProduct.this,R.layout.cat_list_item,empty);
                            et_view_pro_list.setThreshold(1);
                            et_view_pro_list.setAdapter(adapter1);
                            showSnackMessage("Error : No product exist of category or error in Product list retrival");
                        }
                    }
                }
            }
        });
    }

    private void getAllAvailableCategorys() {
        final Gson gson = new Gson();
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                String[] field = new String[1];
                field[0] = "table";

                String[] data = new String[1];
                data[0] = "categorys";

                PutData putData = new PutData(ApiCLient.BASE_URL+"API/Category/getCategoryNameList.php","POST",field,data);
                if (putData.startPut()){
                    if (putData.onComplete()){
                        String result = putData.getResult();
                        String[] catList =  gson.fromJson(result, String[].class);
                        if (!result.equals("")){
                            try{
                                adapter = new ArrayAdapter<String>(RemoveSpecificProduct.this,R.layout.cat_list_item,catList);
                                et_view_Categoryyy.setThreshold(1);
                                et_view_Categoryyy.setAdapter(adapter);
                            }catch (Exception e){}

                        }
                        else {
                            showSnackMessage("Error : in category retrival");
                        }
                    }
                }
            }
        });
    }
    public static String DecodeString(String string) {
        String tempStr = string;
        tempStr = tempStr.replace("[", "");
        return tempStr.replace("]", "");
    }
    //SnackBar
    private void showSnackMessage(String snackDisplay) {
        Snackbar.make(findViewById(R.id.rl_delete_product),""+snackDisplay,Snackbar.LENGTH_LONG)
                .setAction("ok", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //
                    }
                }).show();
    }

    public void deleteSpecificProduct(View view) {
        if (checkBox3.isChecked()){
            deleteProduct(selectedDelPro,selectedDelCat);
            selectedDelPro ="0";
        }
    }

    private void deleteProduct(String selectedDelPro,String selectedDelCat) {
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {

                String[] field = new String[2];
                field[0] = "p_name";
                field[1] = "category_name";

                String[] data = new String[2];
                data[0] = selectedDelPro;
                data[1] = selectedDelCat;

                PutData putData = new PutData(ApiCLient.BASE_URL+"API/Product/productDelete.php","POST",field,data);
                if (putData.startPut()){
                    if (putData.onComplete()){
                        String result = putData.getResult();
                        if (result.equals("Success")){
                            Toast.makeText(RemoveSpecificProduct.this, "Product deleted succesfuly", Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(getIntent());
                        }
                        else {
                            Toast.makeText(RemoveSpecificProduct.this, "Already deleted product ! "+result, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }

    public void toUpdateProductInfo(View view) {
        Intent intent = new Intent(RemoveSpecificProduct.this,AdminUpdateProductDetails.class);
        intent.putExtra("p_name",selectedDelPro);
        intent.putExtra("category_name",selectedDelCat);
        startActivity(intent);
    }
}