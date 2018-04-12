package cpsc3770.easyinventory;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

public class ConfirmationActivity extends Activity implements BackgroundWorker.AsyncResponse {

    private TextView barcodeResult;
    private String[] productInfo;

    protected void onCreate (Bundle savedInstanceState) {

        // Set layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);
        barcodeResult = (TextView)findViewById(R.id.itemName);

        // Get data from scanner (intent)
        Intent intent = getIntent();
        if (intent != null) {
            String productID = intent.getStringExtra("barcode");
            findProduct(productID);
        }
    }

    private void findProduct(String productID) {
        String type = "findProduct";

        // Create the MySQL accessor
        new BackgroundWorker(this).execute(type, productID);
    }

    public void viewItem(View v) {
        Intent intent = new Intent(this, ViewItemActivity.class);
        intent.putExtra("productInfo", productInfo);
        startActivity(intent);
    }

    @Override
    public void processFinish(String output) {
        productInfo = output.split("~");
        barcodeResult.setText(productInfo[1]);
    }

    public void scanAgain(View v) {
        Intent intent = new Intent(this, ScanBarcodeActivity.class);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==0) {
            if(resultCode == CommonStatusCodes.SUCCESS) {
                if(data!=null) {
                    Barcode barcode = data.getParcelableExtra("barcode");
                    String productID = barcode.displayValue;
                    findProduct(productID);
                } else {
                    barcodeResult.setText("No barcode found");
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}
