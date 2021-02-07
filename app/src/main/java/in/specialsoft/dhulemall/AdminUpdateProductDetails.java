package in.specialsoft.dhulemall;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
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

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import in.specialsoft.dhulemall.Api.ApiCLient;
import in.specialsoft.dhulemall.UserDetails.AdminProUpdate;

public class AdminUpdateProductDetails extends AppCompatActivity {

    //Image selection variabels start ----------
    ImageView imageView22,imageView33,imageView44;
    private String userChoosenTask;
    private Bitmap bm;
    String img2,img3,img4;
    byte[] bb = null;
    String base64String = "";
    String type ="";
    //Image selection variabels end ----------

    AutoCompleteTextView et_view_Categoryyyy;
    ArrayAdapter<String> adapter;

    TextView textView66;
    EditText et_Upload_p_namee,et_Upload_p_descriptionn,et_Upload_p_pricee,et_Upload_p_quantityy;
    CheckBox checkBox22;

    String productID;
    ProgressBar progress44;

    String selectedPro,selectedCat ="0";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_update_product_details);

        et_view_Categoryyyy = findViewById(R.id.et_view_Categoryyyy);
        imageView22 = findViewById(R.id.imageView22);
        imageView33 = findViewById(R.id.imageView33);
        imageView44 = findViewById(R.id.imageView44);
        textView66 = findViewById(R.id.textView66);
        checkBox22 = findViewById(R.id.checkBox22);

        et_Upload_p_namee = findViewById(R.id.et_Upload_p_namee);
        et_Upload_p_descriptionn = findViewById(R.id.et_Upload_p_descriptionn);
        et_Upload_p_pricee = findViewById(R.id.et_Upload_p_pricee);
        et_Upload_p_quantityy = findViewById(R.id.et_Upload_p_quantityy);
        progress44 = findViewById(R.id.progress44);

        getAllAvailableCategorys();

        selectedPro =  getIntent().getExtras().getString("p_name");
        selectedCat = getIntent().getExtras().getString("category_name");
        getProductbyNameCat(selectedPro,selectedCat);

        et_view_Categoryyyy.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(AdminUpdateProductDetails.this, ""+adapter.getItem(position), Toast.LENGTH_SHORT).show();
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
                                adapter = new ArrayAdapter<String>(AdminUpdateProductDetails.this,R.layout.cat_list_item,catList);
                                et_view_Categoryyyy.setThreshold(1);
                                et_view_Categoryyyy.setAdapter(adapter);
                            }catch (Exception e){}

                        }
                        else {
                            Toast.makeText(AdminUpdateProductDetails.this, "Error : in category retrival", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }

    //Load product Details
    private void getProductbyNameCat(String selectedPro, String selectedCat) {
        final Gson gson = new Gson();
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

                PutData putData = new PutData(ApiCLient.BASE_URL+"API/Product/getProductDetails.php","POST",field,data);
                if (putData.startPut()){
                    if (putData.onComplete()){
                        String result = putData.getResult();

                        if (!result.equals("Error: Database connection")){
                            //User Exist
                            AdminProUpdate proData = gson.fromJson(DecodeString(result),AdminProUpdate.class);

                            textView66.setText(""+proData.getP_id().toString());
                            et_Upload_p_namee.setText(""+proData.getP_name().toString());
                            et_Upload_p_descriptionn.setText(""+proData.getP_description().toString());
                            et_Upload_p_pricee.setText(""+proData.getP_price().toString());
                            et_Upload_p_quantityy.setText(""+proData.getP_quantity().toString());
                            et_view_Categoryyyy.setText(""+proData.getCategory_name().toString());
                            //set Images
                            try {
                                Glide.with(getApplicationContext()).load(proData.getP_image1()).centerCrop().placeholder(R.drawable.app_logo).into(imageView22);
                                img2 = to64String(imageView22.getDrawable());

                                Glide.with(getApplicationContext()).load(proData.getP_image2()).centerCrop().placeholder(R.drawable.app_logo).into(imageView33);
                                img3 = to64String(imageView33.getDrawable());

                                Glide.with(getApplicationContext()).load(proData.getP_image3()).centerCrop().placeholder(R.drawable.app_logo).into(imageView44);
                                img4 = to64String(imageView44.getDrawable());
                            }catch (Exception e){
                                Log.i("Error in ","Set Image from URL");
                            }

                           /* byte[] decodedString = Base64.decode(proData.getP_image1().toString().trim(), Base64.DEFAULT);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                            imageView22.setImageBitmap(decodedByte);
                            img2 = getBase64(decodedByte);


                            decodedString = Base64.decode(proData.getP_image2().toString().trim(), Base64.DEFAULT);
                            decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                            imageView33.setImageBitmap(decodedByte);
                            img3 = getBase64(decodedByte);

                            decodedString = Base64.decode(proData.getP_image3().toString().trim(), Base64.DEFAULT);
                            decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                            imageView44.setImageBitmap(decodedByte);
                            img4 = getBase64(decodedByte);*/

                            getAllAvailableCategorys();
                        }
                        else {
                            // Error
                        }
                    }
                }
            }
        });
    }
    private String to64String(Drawable drawable) {
        //encode image to base64 string
        Bitmap bitmap = ((android.graphics.drawable.BitmapDrawable) drawable).getBitmap();
        return getBase64(bitmap);
    }
    public static String DecodeString(String string) {
        String tempStr = string;
        tempStr = tempStr.replace("[", "");
        return tempStr.replace("]", "");
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
        AlertDialog.Builder builder = new AlertDialog.Builder(AdminUpdateProductDetails.this);
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
                    imageView22.setImageBitmap(bm);
                    img2 = getBase64(bm);
                }
                else if (type.equals("Title3")){
                    imageView33.setImageBitmap(bm);
                    img3 = getBase64(bm);
                }
                else if (type.equals("Title4")){
                    imageView44.setImageBitmap(bm);
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
            bm1.compress(Bitmap.CompressFormat.JPEG,35,bos);
            bb = bos.toByteArray();
            base64String = Base64.encodeToString(bb,Base64.DEFAULT);
        }
        return base64String;
    }
    //Image selection END .....................................

    public void uploadProduct(View view) {
        if (checkBox22.isChecked()){
            if (! "0".equals(selectedCat)){
                String p_name = et_Upload_p_namee.getText().toString();
                String p_description = et_Upload_p_descriptionn.getText().toString();
                String p_price = et_Upload_p_pricee.getText().toString().trim();
                String p_quantity = et_Upload_p_quantityy.getText().toString().trim();

                if (imageView22.getDrawable() != null && imageView33.getDrawable() != null && imageView44.getDrawable() != null && !p_name.equals("") && !p_description.equals("") && !p_price.equals("") && !p_quantity.equals("")){
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
        progress44.setVisibility(View.VISIBLE);
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
                data[0] = textView66.getText().toString();
                data[1] = selectedCat;
                data[2] = p_name;
                data[3] = p_price;
                data[4] = p_quantity;
                data[5] = p_description;
                data[6] = img2;
                data[7] = img3;
                data[8] = img4;

                PutData putData = new PutData(ApiCLient.BASE_URL+"API/Product/productUpdate.php","POST",field,data);
                if (putData.startPut()){
                    if (putData.onComplete()){
                        String result = putData.getResult();
                        if (result.equals("Success")){
                            progress44.setVisibility(View.GONE);
                            showSnackMessage("Product Uploaded Succesfully ");
                        }
                        else {
                            progress44.setVisibility(View.GONE);
                            Toast.makeText(AdminUpdateProductDetails.this, "Product Already exist !", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

            }
        });
    }

    //SnackBar
    private void showSnackMessage(String snackDisplay) {
        Snackbar.make(findViewById(R.id.rl_update_product),""+snackDisplay,Snackbar.LENGTH_LONG)
                .setAction("ok", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //
                    }
                }).show();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AdminUpdateProductDetails.this,AdminPannel.class);
        startActivity(intent);
        finish();
    }
}