package cpsc3770.easyinventory;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ConfirmationActivity extends Activity implements BackgroundWorker.AsyncResponse {

    protected void onCreate (Bundle savedInstanceState) {

        // Set layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        // Get data from scanner (intent)
        Intent intent = getIntent();
        String productID = intent.getStringExtra("barcode");
//        int productID = Integer.parseInt(intent.getStringExtra("barcode"));
        String type = "findProduct";

        // Create the MySQL accessor
        new BackgroundWorker(this).execute(type, productID);


    }

    public void viewItem(View v) {
        Intent intent = new Intent(this, ViewItemActivity.class);
        startActivity(intent);
    }

    @Override
    public void processFinish(String output) {
        TextView barcodeResult;
        barcodeResult = (TextView)findViewById(R.id.itemName);
        barcodeResult.setText(output);
    }

//    public void scanAgain() {
//        Intent intent = new Intent(this, ScanBarcodeActivity.class);
//        startActivity(intent);
//        startActivityForResult(intent, 0);
//    }

}
