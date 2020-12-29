package in.specialsoft.dhulemall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import in.specialsoft.dhulemall.Cart.CartFragment;
import in.specialsoft.dhulemall.Catgories.CategoryFragment;
import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {
    private Fragment fragment;
    public static int selectedTab=0;

    BottomNavigationView bottom_navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottom_navigation = findViewById(R.id.bottom_navigation);


        Paper.init(this);
        if (selectedTab==0){
            bottom_navigation.setSelectedItemId(R.id.navigation_home);
            fragment=new HomeFragment();
        }else if (selectedTab==1){
            bottom_navigation.setSelectedItemId(R.id.navigation_home);
            fragment=new CategoryFragment();
        }else if(selectedTab==2){
            bottom_navigation.setSelectedItemId(R.id.navigation_home);
            fragment=new CartFragment();
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,fragment).commit();



//        bottom_navigation.setSelectedItemId(R.id.navigation_home);

        bottom_navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                String men = item.getTitle().toString();
                Fragment frag=null;

                switch (men){
                    case "Home" :

                        selectedTab=0;
                        frag=new HomeFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,frag).commit();


                        Toast.makeText(MainActivity.this, "Home Menu ", Toast.LENGTH_SHORT).show();
                        break;
                    case "categorys" :
                        Toast.makeText(MainActivity.this, "Categorys ", Toast.LENGTH_SHORT).show();
                        selectedTab=1;
                        frag=new CategoryFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,frag).commit();

                        break;
                    case "Cart" :
                        selectedTab=2;
                        frag=new CartFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,frag).commit();

                        Toast.makeText(MainActivity.this, "Cart", Toast.LENGTH_SHORT).show();
                        break;
                    case "Profile" :
                        Toast.makeText(MainActivity.this, "Profile ", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this,Profile.class);
                        startActivity(intent);
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
}