package in.specialsoft.dhulemall;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.google.gson.Gson;

import in.specialsoft.dhulemall.UserDetails.UserDetails;
import in.specialsoft.dhulemall.UserDetails.Users;
import io.paperdb.Paper;

public class SplashScreen extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Paper.init(this);
        String UsePhoneKey = Paper.book().read(UserDetails.UserPhoneKey);
        if (isNetworkConnected()){

            if(!"".equals(UsePhoneKey)){
                this.allowAccess(UsePhoneKey);
            }
            else{
                newUser();
            }
        }
        else{
            Toast.makeText(this, "Internet Not Available", Toast.LENGTH_SHORT).show();
            Intent networkError = new Intent(SplashScreen.this,NetworkErrorActivity.class);
            startActivity(networkError);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    private void newUser() {
        Intent intent = new Intent(this,HomeActivity.class);
        startActivity(intent);
        finish();
    }

    private void allowAccess(String usePhoneKey) {
        this.checkIfUserExist(usePhoneKey);

        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void checkIfUserExist(final String userPhoneKey) {
        final Gson gson = new Gson();

        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                //Starting Write and Read data with URL
                //Creating array for parameters
                String[] field = new String[1];
                field[0] = "id";
                //Creating array for data
                String[] data = new String[1];
                data[0] = ""+ Paper.book().read(UserDetails.UserIDKey);
                try {
                    PutData putData = new PutData("https://dhulemall.ml/API/UserDetails/checkUser.php", "POST", field, data);
                    if (putData.startPut()) {
                        if (putData.onComplete()) {
                            String result = putData.getResult();
                            if (!result.equals("Error: Database connection")){
                                //User Exist
                                Users uData1 = gson.fromJson(DecodeString(result),Users.class);

                                if (uData1 == null){
                                    Paper.book().write(UserDetails.UserExistKey,"notExist");
                                    newUser();
                                }
                                else{
                                    Paper.book().write(UserDetails.UserExistKey,"Exist");
                                    Paper.book().write(UserDetails.UserSkipKey,"NotSkiped");

                                    Paper.book().write(UserDetails.UserPhoneKey,uData1.getPhone_number());
                                    Paper.book().write(UserDetails.UserEmailKey,uData1.getEmail());
                                    Paper.book().write(UserDetails.UserPasswordKey,uData1.getPassword());
                                    Paper.book().write(UserDetails.UserNameKey,uData1.getName());
                                    Paper.book().write(UserDetails.UserSecurityKey,uData1.getSecurity());

                                    Paper.book().write(UserDetails.UserIDKey,uData1.getId());
                                }
                            }
                            else {
                                // Error
                                Paper.book().write(UserDetails.UserExistKey,"notExist");
                            }
                        }
                    }
                }catch (Exception e){
                }
                //End Write and Read data with URL
            }
        });
    }

    public static String DecodeString(String string) {
        String tempStr = string;
        tempStr = tempStr.replace("[", "");
        return tempStr.replace("]", "");
    }
}