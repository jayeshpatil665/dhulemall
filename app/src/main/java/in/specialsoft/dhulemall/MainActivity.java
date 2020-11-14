package in.specialsoft.dhulemall;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import in.specialsoft.dhulemall.UserDetails.UserDetails;
import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    Button button2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Paper.init(this);

        button2 = findViewById(R.id.button2);
        String UseSkipKey = Paper.book().read(UserDetails.UserSkipKey);
        if ("skiped".equals(UseSkipKey))
        {
            button2.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        //
    }

    public void logOutUser(View view) {
        Paper.book().destroy();
        Intent intent = new Intent(MainActivity.this,SplashScreen.class);
        startActivity(intent);
        finish();
    }
}