package in.specialsoft.dhulemall.ProductDetaild;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import in.specialsoft.dhulemall.R;

public class FulllImage extends AppCompatActivity {
    RecyclerView recyclerView;
    String[] product;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fulll_image);
        recyclerView=findViewById(R.id.recycler);
        product=getIntent().getStringArrayExtra("img");
        FullimgAdaptor fullimgAdaptor=new FullimgAdaptor(product);
        recyclerView.setLayoutManager(new LinearLayoutManager(FulllImage.this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(fullimgAdaptor);
    }
}