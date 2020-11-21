package in.specialsoft.dhulemall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import in.specialsoft.dhulemall.UserDetails.UserDetails;
import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottom_navigation;

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


        bottom_navigation = findViewById(R.id.bottom_navigation);
        bottom_navigation.setSelectedItemId(R.id.navigation_home);

        bottom_navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                String men = item.getTitle().toString();
                switch (men){
                    case "Home" :
                        Toast.makeText(MainActivity.this, "Home Menu ", Toast.LENGTH_SHORT).show();
                        break;
                    case "categorys" :
                        Toast.makeText(MainActivity.this, "Categorys ", Toast.LENGTH_SHORT).show();
                        break;
                    case "Cart" :
                        Toast.makeText(MainActivity.this, "Cart", Toast.LENGTH_SHORT).show();
                        break;
                    case "Profile" :
                        Toast.makeText(MainActivity.this, "Profile ", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });
    }

    //Search Bar

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.custom_toolbar2,menu);
        MenuItem item = menu.findItem(R.id.navigation_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
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