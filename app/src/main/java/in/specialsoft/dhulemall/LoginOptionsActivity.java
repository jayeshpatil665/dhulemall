package in.specialsoft.dhulemall;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.util.regex.Pattern;

import in.specialsoft.dhulemall.UserDetails.UserDetails;
import in.specialsoft.dhulemall.UserDetails.Users;
import io.paperdb.Paper;

import static in.specialsoft.dhulemall.SplashScreen.DecodeString;

public class LoginOptionsActivity extends AppCompatActivity {

    LinearLayout ll_login;
    TextInputEditText ed_id,ed_pass;
    String toastValidation="";
    ProgressBar progress2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_options);

        Paper.init(this);
        ll_login = findViewById(R.id.ll_login);
        ll_login.getBackground().setAlpha(10);

        ed_id = findViewById(R.id.ed_id);
        ed_pass = findViewById(R.id.ed_pass);

        progress2 = findViewById(R.id.progress2);
    }

    public void loginTheUser(View view) {
        if(checkIfEmpty()){
            progress2.setVisibility(View.GONE);
            showSnackMessage(toastValidation);
        }
        else {
            showSnackMessage(toastValidation);
        }
    }

    private boolean checkIfEmpty() {
        String email,pass;
        email = ed_id.getText().toString().trim();
        pass = ed_pass.getText().toString().trim();
        if (!email.equals("") && !pass.equals(""))
        {
            if (isEmailValid(email)){
                    checkUserfromDB(email,pass);
                    return true;
                }
                else {
                    toastValidation ="Please check your email !";
                    return false;
                }
        }
        toastValidation ="All fields are required !";
        return false;
    }

    private void checkUserfromDB(String email, String pass) {
        progress2.setVisibility(View.VISIBLE);
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                String[] field = new String[2];
                field[0] = "email";
                field[1] = "password";

                String[] data = new String[2];
                data[0] = email;
                data[1] = pass;
                PutData putData = new PutData("https://dhulemall.ml/API/UserDetails/passwordAuthinticate.php","POST",field,data);
                if (putData.startPut()){
                    if (putData.onComplete()){
                        String result = putData.getResult();
                        if (result.equals("Authorised")){
                            Toast.makeText(LoginOptionsActivity.this, "Authorised", Toast.LENGTH_SHORT).show();
                            getUserData(email);
                            progress2.setVisibility(View.GONE);
                            Intent intent = new Intent(LoginOptionsActivity.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else {
                            toastValidation ="Error : "+result;
                            Log.i("ERRRRRRRRRRor : ",""+result);
                        }
                    }
                }
            }
        });
    }

    private void getUserData(String email) {
        final Gson gson = new Gson();
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                 String[] field = new String[1];
                 field[0] = "email";

                 String[] data = new String[1];
                 data[0] = email;
                 PutData putData = new PutData("https://dhulemall.ml/API/UserDetails/privateLoadDataJSON.php","POST",field,data);
                 if (putData.startPut()){
                     if (putData.onComplete()){
                         String result = putData.getResult();
                         if (!result.equals("Error: Database connection")){
                             Users uData1 = gson.fromJson(DecodeString(result),Users.class);
                             saveUserDataLocaly(uData1);
                         }
                         else {
                                toastValidation ="Error : in data retrival";
                         }
                     }
                 }
            }
        });
    }

    private void saveUserDataLocaly(Users uData1) {
        Paper.book().write(UserDetails.UserSecurityKey,"NotSkiped");

        Paper.book().write(UserDetails.UserExistKey,"Exist");
        Paper.book().write(UserDetails.UserSkipKey,"NotSkiped");

        Paper.book().write(UserDetails.UserPhoneKey,uData1.getPhone_number());
        Paper.book().write(UserDetails.UserEmailKey,uData1.getEmail());
        Paper.book().write(UserDetails.UserPasswordKey,uData1.getPassword());
        Paper.book().write(UserDetails.UserNameKey,uData1.getName());
        Paper.book().write(UserDetails.UserSecurityKey,uData1.getSecurity());

        Paper.book().write(UserDetails.UserIDKey,uData1.getId());
    }

    //Validate Email ID
    public static boolean isEmailValid(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }
    //SnackBar
    private void showSnackMessage(String snackDisplay) {
        Snackbar.make(findViewById(R.id.ll_login),""+snackDisplay,Snackbar.LENGTH_LONG)
                .setAction("ok", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //
                    }
                }).show();
    }
    //Admin Login
    public void toAdminPannel(View view) {
        if(checkIfEmpty()){
            if ("admin@dhule.mall".equals(ed_id.getText().toString().trim()) && "admin".equals(ed_pass.getText().toString().trim())) {
                progress2.setVisibility(View.GONE);
                showSnackMessage("Login to Admin Pannel");
                Intent intent = new Intent(LoginOptionsActivity.this,AdminPannel.class);
                startActivity(intent);
                finish();
            }
            else{
                progress2.setVisibility(View.GONE);
                showSnackMessage("Check Admin Credentials !");
            }
        }
        else {
            showSnackMessage(toastValidation);
        }
    }
}