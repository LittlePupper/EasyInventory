package cpsc3770.easyinventory;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements BackgroundWorker.AsyncResponse{

    TextView barcodeResult;
    ListView myListView;
    IceCreamAdapter iceCreamAdapter;
    ArrayList<IceCream> iceCreamList;
    Context context;

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
        setSupportActionBar(toolbar);

        // Set barcode results
        barcodeResult = (TextView)findViewById(R.id.barcode_result);
    }

    @Override
    public void onResume()
    {  // After a pause OR at startup
        super.onResume();
        getAll();
    }

    /* Click event for barcode scan button */

    public void scanBarcode(View v) {
        Intent intent = new Intent(this, ScanBarcodeActivity.class);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==0) {
            if(resultCode == CommonStatusCodes.SUCCESS) {
                if(data!=null) {
                    Barcode barcode = data.getParcelableExtra("barcode");
                    barcodeResult.setText("Barcode value: " + barcode.displayValue);
                    Intent intent = new Intent(this, ConfirmationActivity.class);
                    intent.putExtra("barcode", barcode.displayValue);
                    startActivity(intent);
                } else {
                    barcodeResult.setText("No barcode found");
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
