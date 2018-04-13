package cpsc3770.easyinventory;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements BackgroundWorker.AsyncResponse{

    ListView myListView;
    IceCreamAdapter iceCreamAdapter;
    ArrayList<IceCream> iceCreamList;
    Context context;
    String toast;

    protected void onCreate (Bundle savedInstanceState) {

        // Set layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        getAll();
        //Get listview setup
        myListView = (ListView) findViewById(R.id.listView);

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, ViewItemActivity.class);
                intent.putExtra("productInfo", iceCreamList.get(position).getMyValues());
                startActivity(intent);
//                finish();
            }
        });

        // Create toolbar
        Toolbar toolbar = (Toolbar)findViewById(R.id.actionbar);
        TextView toolbarTitle = toolbar.findViewById(R.id.toolbar_title);
        toolbarTitle.setText("Easy Inventory");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.ic_photo_camera_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            // Starts main activity and finishes this one
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ScanBarcodeActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        if(getIntent().getExtras()!=null){
            toast = getIntent().getStringExtra("deletedProduct") + " successfully deleted";
            Toast.makeText(getApplicationContext(), toast, Toast.LENGTH_LONG).show();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_edit_item, menu);
//        return true;
//    }

    @Override
    public void onResume()
    {  // After a pause OR at startup
        super.onResume();
        getAll();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==0) {
            if(resultCode == CommonStatusCodes.SUCCESS) {
                if(data!=null) {
                    Barcode barcode = data.getParcelableExtra("barcode");
                    Intent intent = new Intent(this, ConfirmationActivity.class);
                    intent.putExtra("barcode", barcode.displayValue);
                    startActivity(intent);
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void getAll() {
        String type = "getAll";

        // Create the MySQL accessor
        new BackgroundWorker(this).execute(type);
    }

    @Override
    public void processFinish(String output) {
        iceCreamList = new ArrayList<>();

        String temp[] = output.split("@");

        for(String myIceCream:temp){
            String item[] = myIceCream.split("~");
            iceCreamList.add(new IceCream(Integer.parseInt(item[0]), item[1], Float.parseFloat(item[2]), Integer.parseInt(item[3]), item[4], item[5], Integer.parseInt(item[6]), Integer.parseInt(item[0])));
        }

        iceCreamAdapter = new IceCreamAdapter(this, iceCreamList);
        myListView.setAdapter(iceCreamAdapter);

    }
}
