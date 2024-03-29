package in.specialsoft.dhulemall;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import in.specialsoft.dhulemall.MyOrdersList.MyOrderList;
import in.specialsoft.dhulemall.UserDetails.UserDetails;
import io.paperdb.Paper;

public class Profile extends AppCompatActivity {

    Button button2,acc_signin,acc_create_acc;
    LinearLayout ll_profile2,ll_profile3,ll_profile4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Paper.init(this);
        button2 = findViewById(R.id.button2);
        acc_signin = findViewById(R.id.acc_signin);
        acc_create_acc = findViewById(R.id.acc_create_acc);

        ll_profile2 = findViewById(R.id.ll_profile2);
        ll_profile3 = findViewById(R.id.ll_profile3);
        ll_profile4 = findViewById(R.id.ll_profile4);

        String UseSkipKey = Paper.book().read(UserDetails.UserSkipKey);
        if ("skiped".equals(UseSkipKey))
        {
            button2.setVisibility(View.GONE);
            ll_profile2.setVisibility(View.GONE);
            ll_profile3.setVisibility(View.GONE);
        }
        else {
            acc_signin.setVisibility(View.GONE);
            acc_create_acc.setVisibility(View.GONE);
        }

        ll_profile4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this,AboutActivity.class);
                startActivity(intent);
            }
        });
    }

    public void tologInPage(View view) {
        Intent logInIntent = new Intent(Profile.this,LoginOptionsActivity.class);
        startActivity(logInIntent);
    }


    public void toCreateAcount(View view) {
        Intent createAccountIntent = new Intent(Profile.this,CreateAccountActivity.class);
        startActivity(createAccountIntent);
    }

    public void logOutUser(View view) {
        Paper.book().destroy();
        finish();
        moveTaskToBack(true);
        Intent intent = new Intent(Profile.this,HomeActivity.class);
        startActivity(intent);
        System.exit(0);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Profile.this, MainActivity.class));
        finish();
    }

    public void getOrderList(View view) {
        FeatureContraoller.getInstance().setFlag(1);
        Intent intent=new Intent(Profile.this,MyOrderList.class);
        startActivity(intent);
    }

    public void getwhatsapp(View view) {
        String mobilenumber="+919130919088";
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone="+mobilenumber+"Hii")));
    }
}