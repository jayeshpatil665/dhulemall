package in.specialsoft.dhulemall;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AdminPannel extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_pannel);
    }

    @Override
    public void onBackPressed() {
        //
    }

    public void logOutButton(View view) {
        Intent intent = new Intent(AdminPannel.this,SplashScreen.class);
        startActivity(intent);
        finish();
    }

    public void addRemoveCategory(View view) {
        Intent intent = new Intent(AdminPannel.this,AdminAddRemoveCategory.class);
        startActivity(intent);
    }
}