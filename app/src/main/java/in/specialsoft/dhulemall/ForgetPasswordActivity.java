package in.specialsoft.dhulemall;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import in.specialsoft.dhulemall.Api.ApiCLient;
import in.specialsoft.dhulemall.UserDetails.Users;

import static in.specialsoft.dhulemall.AdminUpdateProductDetails.DecodeString;
import static in.specialsoft.dhulemall.CreateAccountActivity.isValidMob;

public class ForgetPasswordActivity extends AppCompatActivity {

    TextView textView7;
    EditText et_mob,et_otp,et_sec,et_new_pass;
    Button btn_otp,btn_submit,btn_reset,button12,btn_reset_pass;
    static String security="";
    static String  mob="";
    static String code="";

    String phone,otp,verificationId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        et_mob = findViewById(R.id.et_mob);
        button12 = findViewById(R.id.button12);

        et_sec = findViewById(R.id.et_sec);
        btn_reset = findViewById(R.id.btn_reset);
        btn_otp = findViewById(R.id.btn_otp);
        et_otp = findViewById(R.id.et_otp);
        btn_submit = findViewById(R.id.btn_submit);
        textView7 = findViewById(R.id.textView7);

        et_new_pass = findViewById(R.id.et_new_pass);
        btn_reset_pass = findViewById(R.id.btn_reset_pass);

        //INVISIBLE OPTIONS
        et_sec.setVisibility(View.GONE);
        btn_reset.setVisibility(View.GONE);
        btn_otp.setVisibility(View.GONE);
        et_otp.setVisibility(View.GONE);
        btn_submit.setVisibility(View.GONE);

        et_new_pass.setVisibility(View.GONE);
        btn_reset_pass.setVisibility(View.GONE);
    }

    public void validateUser(View view) {
        mob = et_mob.getText().toString().trim();
        if (! mob.equals(""))
        {
            if (isValidMob(mob)){
                checkUserfromDB(mob);
            }
            else {
                Toast.makeText(this, "Please check your mobileNumber !", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(this, "Enter Mobile number !", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkUserfromDB(String mob) {
        final Gson gson = new Gson();
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                String[] field = new String[1];
                field[0] = "phone_number";

                String[] data = new String[1];
                data[0] = mob;
                try {
                    PutData putData = new PutData(ApiCLient.BASE_URL+"API/UserDetails/checkUserMobile.php", "POST", field, data);
                    if (putData.startPut()) {
                        if (putData.onComplete()) {
                            String result = putData.getResult();
                            if (!result.equals("Error: Database connection")){
                                //User Exist
                                Users uData1 = gson.fromJson(DecodeString(result),Users.class);

                                if (uData1 == null){
                                    Toast.makeText(ForgetPasswordActivity.this, "User Not Exist !!", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    security=uData1.getSecurity();
                                    Toast.makeText(ForgetPasswordActivity.this, "User Exist !", Toast.LENGTH_SHORT).show();
                                    makeFieldsVisible();
                                }
                            }
                            else {
                                // Error
                            }
                        }
                    }
                }catch (Exception e){
                }
            }
        });
    }

    private void makeFieldsVisible() {

        et_sec.setVisibility(View.VISIBLE);
        btn_reset.setVisibility(View.VISIBLE);
        //btn_otp.setVisibility(View.VISIBLE);
        //et_otp.setVisibility(View.VISIBLE);
        //btn_submit.setVisibility(View.VISIBLE);

        et_mob.setVisibility(View.GONE);
        button12.setVisibility(View.GONE);
    }

    public void toresetBySecurity(View view) {
        String securityAns = et_sec.getText().toString();
        if (! securityAns.equals("")){
            if (securityAns.equals(security)){
                Toast.makeText(this, "Security answer Matched", Toast.LENGTH_SHORT).show();
                toResetingPassword();
            }
            else
            {
                Toast.makeText(this, "Security answer Don't Matched", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void toResetingPassword() {
        et_sec.setVisibility(View.GONE);
        btn_reset.setVisibility(View.GONE);
        btn_otp.setVisibility(View.GONE);
        et_otp.setVisibility(View.GONE);
        btn_submit.setVisibility(View.GONE);
        textView7.setText("New Password");

        et_new_pass.setVisibility(View.VISIBLE);
        btn_reset_pass.setVisibility(View.VISIBLE);
    }

    public void toUpdatePassword(View view) {
        String pass = et_new_pass.getText().toString().trim();
        if (! pass.equals("")){
            Handler handler = new Handler();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    String[] field = new String[2];
                    field[0] = "phone_number";
                    field[1] = "password";

                    String[] data = new String[2];
                    data[0] = mob;
                    data[1] = pass;
                    PutData putData = new PutData(ApiCLient.BASE_URL+"API/UserDetails/updateUserPassword.php","POST",field,data);
                    if (putData.startPut()){
                        if (putData.onComplete()){
                            String result = putData.getResult();
                            if (result.equals("Success")){
                                Toast.makeText(ForgetPasswordActivity.this, "Password Changed Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ForgetPasswordActivity.this,SplashScreen.class);
                                startActivity(intent);
                                finish();
                            }
                            else {
                                Toast.makeText(ForgetPasswordActivity.this, "Error : "+result, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            });
        }
        else {
            Toast.makeText(this, "Enter new password", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    public void toBack(View view) {
        onBackPressed();
    }
}