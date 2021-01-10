package in.specialsoft.dhulemall;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    TextView tv_version;
    TextView installed_version;
    String installedVersio="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        tv_version = findViewById(R.id.tv_version);
        try {
            installedVersio = BuildConfig.VERSION_NAME;
            tv_version.setText("Version : "+installedVersio);
        } catch (Exception e) { }
    }

    public void toLinkedIn(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/jayeshpatil665/"));
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    public void toBack(View view) {
        onBackPressed();
    }

    public void toLinkedIn1(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/aniket-varma/"));
        startActivity(intent);
    }
}