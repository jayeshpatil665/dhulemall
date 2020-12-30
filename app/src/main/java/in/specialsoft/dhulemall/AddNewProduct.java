package in.specialsoft.dhulemall;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import in.specialsoft.dhulemall.Api.ApiCLient;

public class AddNewProduct extends AppCompatActivity {

    //Image selection variabels start ----------
    ImageView imageView2,imageView3,imageView4;
    private String userChoosenTask;
    private Bitmap bm;
    String img2,img3,img4;
    byte[] bb = null;
    String base64String = "";
    String type ="";
    //Image selection variabels end ----------

    AutoCompleteTextView et_view_Categoryy;
    ArrayAdapter<String> adapter;
    String selectedCat ="0";

    TextView textView6;
    EditText et_Upload_p_name,et_Upload_p_description,et_Upload_p_price,et_Upload_p_quantity;
    CheckBox checkBox2;

    String productID;
    ProgressBar progress4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_product);

        et_view_Categoryy = findViewById(R.id.et_view_Categoryy);
        imageView2 = findViewById(R.id.imageView2);
        imageView3 = findViewById(R.id.imageView3);
        imageView4 = findViewById(R.id.imageView4);
        textView6 = findViewById(R.id.textView6);
        checkBox2 = findViewById(R.id.checkBox2);

        et_Upload_p_name = findViewById(R.id.et_Upload_p_name);
        et_Upload_p_description = findViewById(R.id.et_Upload_p_description);
        et_Upload_p_price = findViewById(R.id.et_Upload_p_price);
        et_Upload_p_quantity = findViewById(R.id.et_Upload_p_quantity);
        progress4 = findViewById(R.id.progress4);

        getAllAvailableCategorys();
        genrateProductID();

        et_view_Categoryy.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(AddNewProduct.this, ""+adapter.getItem(position), Toast.LENGTH_SHORT).show();
                selectedCat = adapter.getItem(position);
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
                                adapter = new ArrayAdapter<String>(AddNewProduct.this,R.layout.cat_list_item,catList);
                                et_view_Categoryy.setThreshold(1);
                                et_view_Categoryy.setAdapter(adapter);
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
        Snackbar.make(findViewById(R.id.rl_upload_product),""+snackDisplay,Snackbar.LENGTH_LONG)
                .setAction("ok", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //
                    }
                }).show();
    }
    //Image selection START .....................................
    public void selectTitleImage(View view) {
        type = "Title2";
        openOptionDialogue();
    }
    public void selectImage3(View view) {
        type = "Title3";
        openOptionDialogue();
    }
    public void selectImage4(View view) {
        type = "Title4";
        openOptionDialogue();
    }

    //Image Selection Methods
    private void openOptionDialogue() {
        final CharSequence[] items = {"Choose from Library","Cancle"};
        AlertDialog.Builder builder = new AlertDialog.Builder(AddNewProduct.this);
        builder.setTitle("Add Photo !");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (items[which].equals("Choose from Library")){
                    userChoosenTask = "Choose from Library";
                    galleryIntent();
                }
                else if (items[which].equals("Cancle")){
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
    private void galleryIntent() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,1);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK){
            if (requestCode == 1){
                onSelectFromGalleryResult(data,1);
            }
        }
    }
    private void onSelectFromGalleryResult(Intent data, int i){
        bm = null;
        if (data != null){
            try {
                Uri imageUri = data.getData();
                InputStream imageStream = getContentResolver().openInputStream(imageUri);
                bm = BitmapFactory.decodeStream(imageStream);
                bm = getResizedBitmap(bm,400);
                //-------------------------------------------------------------------------------
                if (type.equals("Title2")){
                    imageView2.setImageBitmap(bm);
                    img2 = getBase64(bm);
                }
                else if (type.equals("Title3")){
                    imageView3.setImageBitmap(bm);
                    img3 = getBase64(bm);
                }
                else{
                    imageView4.setImageBitmap(bm);
                    img4 = getBase64(bm);
                }

            }catch (FileNotFoundException e){
                e.printStackTrace();
            }
        }
        getBase64(bm);
    }

    private Bitmap getResizedBitmap(Bitmap image,int maxSize){
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 0){
            width = maxSize;
            height = (int) (width/bitmapRatio);
        }else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image,width,height,true);
    }

    private String getBase64(Bitmap bm1) {
        if (bm1 != null){
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bm1.compress(Bitmap.CompressFormat.JPEG,50,bos);
            bb = bos.toByteArray();
            base64String = Base64.encodeToString(bb,Base64.DEFAULT);
        }
        return base64String;
    }
    //Image selection END .....................................
    private void genrateProductID() {
        String saveCurrentTime,saveCurrentDate;

        Random random = new Random();

        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HHmmss");
        saveCurrentTime = currentTime.format(calForDate.getTime());

        productID =random.nextInt(10)+saveCurrentTime.toString().toLowerCase()+random.nextInt(100);
        textView6.setText("Product ID : "+productID);

    }

    public void uploadProduct(View view) {
        if (checkBox2.isChecked()){
            if (! "0".equals(selectedCat)){
                //String p_id = productID;
                String p_name = et_Upload_p_name.getText().toString();
                String p_description = et_Upload_p_description.getText().toString();
                String p_price = et_Upload_p_price.getText().toString().trim();
                String p_quantity = et_Upload_p_quantity.getText().toString().trim();

                if (imageView2.getDrawable() != null && imageView3.getDrawable() != null && imageView4.getDrawable() != null && !p_name.equals("") && !p_description.equals("") && !p_price.equals("") && !p_quantity.equals("")){
                    //showSnackMessage("OK to upload");
                    sendInsertDataTOProductsTable(productID,selectedCat,p_name,p_price,p_quantity,p_description,img2,img3,img4);
                }
                else {
                    showSnackMessage("All fields are required !");
                }
            }
            else {
                showSnackMessage("Please Select category first !");
            }
        }
    }

    private void sendInsertDataTOProductsTable(String productID, String selectedCat, String p_name, String p_price, String p_quantity, String p_description, String img2, String img3, String img4) {
        progress4.setVisibility(View.VISIBLE);
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {

                String[] field = new String[9];
                field[0] = "p_id";
                field[1] = "category_name";
                field[2] = "p_name";
                field[3] = "p_price";
                field[4] = "p_quantity";
                field[5] = "p_description";
                field[6] = "p_image1";
                field[7] = "p_image2";
                field[8] = "p_image3";

                String[] data = new String[9];
                data[0] = productID;
                data[1] = selectedCat;
                data[2] = p_name;
                data[3] = p_price;
                data[4] = p_quantity;
                data[5] = p_description;
                data[6] = img2;
                data[7] = img3;
                data[8] = img4;

                PutData putData = new PutData(ApiCLient.BASE_URL+"API/Product/productCreate.php","POST",field,data);
                if (putData.startPut()){
                    if (putData.onComplete()){
                        String result = putData.getResult();
                        if (result.equals("Success")){
                            progress4.setVisibility(View.GONE);
                            showSnackMessage("Product Uploaded Succesfully ");
                        }
                        else {
                            progress4.setVisibility(View.GONE);
                            Toast.makeText(AddNewProduct.this, "Product Already exist !", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

            }
        });
    }
}