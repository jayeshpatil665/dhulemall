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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import in.specialsoft.dhulemall.UserDetails.Users;

public class AdminAddRemoveCategory extends AppCompatActivity {

    //Image selection variabels start ----------
    ImageView imageView;
    private String userChoosenTask;
    private Bitmap bm;
    String img;
    byte[] bb = null;
    String base64String = "";
    //Image selection variabels end ----------

    TextInputEditText et_category_name;
    ProgressBar progress3;
    AutoCompleteTextView et_view_Category;

    ArrayAdapter<String> adapter;
    String selectedDelCat ="0";
    CheckBox checkBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_remove_category);

        imageView = findViewById(R.id.imageView);
        et_category_name = findViewById(R.id.et_category_name);
        progress3 = findViewById(R.id.progress3);
        et_view_Category = findViewById(R.id.et_view_Category);
        checkBox = findViewById(R.id.checkBox);

        getAllAvailableCategorys();
        et_view_Category.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(AdminAddRemoveCategory.this, ""+adapter.getItem(position), Toast.LENGTH_SHORT).show();
                selectedDelCat = adapter.getItem(position);
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

                PutData putData = new PutData("https://dhulemall.ml/API/Category/getCategoryNameList.php","POST",field,data);
                if (putData.startPut()){
                    if (putData.onComplete()){
                        String result = putData.getResult();
                        String[] catList =  gson.fromJson(result, String[].class);
                        if (!result.equals("")){
                            Log.i("Out : ",""+catList);
                            try{
                                adapter = new ArrayAdapter<String>(AdminAddRemoveCategory.this,R.layout.cat_list_item,catList);
                                et_view_Category.setThreshold(1);
                                et_view_Category.setAdapter(adapter);
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
    //Image selection start ------------------------------------------------------------------------
    public void selectCategoryImage(View view) {
        openOptionDialogue();
    }
    //Image Selection Methods
    private void openOptionDialogue() {
        final CharSequence[] items = {"Choose from Library","Cancle"};
        AlertDialog.Builder builder = new AlertDialog.Builder(AdminAddRemoveCategory.this);
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
                imageView.setImageBitmap(bm);
                img = getBase64(bm);

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
    //Image selection END --------------------------------------------------------------------------
    //Create Category button
    public void createCategory(View view) {
        if (imageView.getDrawable() != null && !et_category_name.getText().toString().trim().equals("")){
            progress3.setVisibility(View.VISIBLE);
            sendCategoryDataToAPI(et_category_name.getText().toString(),img);
        }
        else{
            showSnackMessage("All fields are require !");
        }
    }
    //SnackBar
    private void showSnackMessage(String snackDisplay) {
        Snackbar.make(findViewById(R.id.ll_category2),""+snackDisplay,Snackbar.LENGTH_LONG)
                .setAction("ok", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //
                    }
                }).show();
    }
    //Send data to PHP API
    private void sendCategoryDataToAPI(String cat_name,String img) {
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                  String[] field = new String[2];
                  field[0] = "category_name";
                  field[1] = "cat_img";

                  String[] data = new String[2];
                  data[0] = cat_name;
                  data[1] = img;
                  PutData putData = new PutData("https://dhulemall.ml/API/Category/catUpload.php","POST",field,data);
                  if (putData.startPut()){
                      if (putData.onComplete()){
                          String result = putData.getResult();
                          if (result.equals("Success")){
                              progress3.setVisibility(View.GONE);
                              showSnackMessage("Category Created Succesfully");
                              getAllAvailableCategorys();
//                              finish();
//                              startActivity(getIntent());
                          }
                          else
                          {
                              progress3.setVisibility(View.GONE);
                              showSnackMessage("Category already exist : failed to create category ");
                          }
                      }
                  }
            }
        });
    }

    public void deleteCategory(View view) {
        if (checkBox.isChecked()){
            if (! "0".equals(selectedDelCat)){
                AlertDialog.Builder builder= new AlertDialog.Builder(this);
                builder.setMessage("Are you Sure to delete "+selectedDelCat+" !")
                        .setNegativeButton("NO",null)
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deleteCategoryConfirm(selectedDelCat);
                            }
                        }).show();
            }
        }
    }

    private void deleteCategoryConfirm(String selectedDelCat) {

        //Toast.makeText(this, ""+selectedDelCat, Toast.LENGTH_SHORT).show();
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                String[] field = new String[1];
                field[0] = "category_name";

                String[] data = new String[1];
                data[0] = selectedDelCat;

                PutData putData = new PutData("https://dhulemall.ml/API/Category/catDelete.php","POST",field,data);
               if (putData.startPut()){
                   if (putData.onComplete()){
                       String result = putData.getResult();
                       if (result.equals("Success")) {
                           progress3.setVisibility(View.GONE);
                           Toast.makeText(AdminAddRemoveCategory.this, "Category deleted succesfully !", Toast.LENGTH_SHORT).show();
                           //showSnackMessage("Category deleted succesfully !");
                           finish();
                           startActivity(getIntent());
                       }
                       else{
                           progress3.setVisibility(View.GONE);
                           //Toast.makeText(AdminAddRemoveCategory.this, "Error : "+result, Toast.LENGTH_SHORT).show();
                           showSnackMessage("Error : "+result);
                       }
                   }
               }

            }
        });
    }
}