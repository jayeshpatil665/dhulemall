package in.specialsoft.dhulemall;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateAccountActivity extends AppCompatActivity {

    LinearLayout ll_signup;
    TextInputEditText ed_up_name,ed_up_mob,ed_up_id,ed_up_pass,ed_up_check_pass,ed_up_sec;
    String toastValidation="";
    ProgressBar progress1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        ll_signup = findViewById(R.id.ll_signup);
        ll_signup.getBackground().setAlpha(10);

        ed_up_name = findViewById(R.id.ed_up_name);
        ed_up_mob = findViewById(R.id.ed_up_mob);
        ed_up_id = findViewById(R.id.ed_up_id);
        ed_up_pass = findViewById(R.id.ed_up_pass);
        ed_up_check_pass = findViewById(R.id.ed_up_check_pass);
        ed_up_sec = findViewById(R.id.ed_up_sec);

        progress1 = findViewById(R.id.progress1);
    }

    public void signupTheUser(View view) {
        if(checkIfEmpty()){
            progress1.setVisibility(View.GONE);
            showSnackMessage(toastValidation);
        }
        else {
            showSnackMessage(toastValidation);
        }
    }

    private boolean checkIfEmpty() {
        String name,mob,email,pass,sec,check_pass;
        name = ed_up_name.getText().toString();
        mob = ed_up_mob.getText().toString().trim();
        email = ed_up_id.getText().toString().trim();
        pass = ed_up_pass.getText().toString().trim();
        sec = ed_up_sec.getText().toString();
        check_pass = ed_up_check_pass.getText().toString().trim();
        if (!name.equals("") && !mob.equals("") && !email.equals("") && !pass.equals("") && !sec.equals("") && !check_pass.equals(""))
        {
            if (isEmailValid(email) && isValidMob(mob)){
                if (pass.equals(check_pass))
                {
                    progress1.setVisibility(View.VISIBLE);
                    insertIntoDB(name,mob,email,pass,sec);
                    return true;
                }
                else {
                    toastValidation ="Please check your passwords !";
                    return false;
                }
            }
            else {
                toastValidation ="Please check your Mobile Number or Email !";
                return false;
            }
        }
        toastValidation ="All fields are required !";
        return false;
    }

    private void insertIntoDB(String name, String mob, String email, String pass, String sec) {
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                String[] field = new String[5];
                field[0] = "name";
                field[1] = "phone_number";
                field[2] = "email";
                field[3] = "password";
                field[4] = "security";

                String[] data = new String[5];
                data[0] = name;
                data[1] = mob;
                data[2] = email;
                data[3] = pass;
                data[4] = sec;

                PutData putData = new PutData("https://dhulemall.ml/API/UserDetails/signup.php","POST",field,data);
                if (putData.startPut()){
                    if (putData.onComplete()){
                        String result = putData.getResult();
                        if (result.equals("Sign Up Success")) {
                            toastValidation ="Account created successfully";
                        }
                        else {
                            toastValidation ="Error in User creation !";
                        }
                    }
                }
            }
        });
    }

    //Validate Mobile Number
    public static boolean isValidMob(String s)
    {
        Pattern p = Pattern.compile("(0/91)?[7-9][0-9]{9}");

        Matcher m = p.matcher(s);
        return (m.find() && m.group().equals(s));
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
        Snackbar.make(findViewById(R.id.ll_signup),""+snackDisplay,Snackbar.LENGTH_LONG)
                .setAction("ok", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //
                    }
                }).show();
    }
}