package cpsc3770.easyinventory;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

import java.text.ParseException;

public class ConfirmationActivity extends Activity implements BackgroundWorker.AsyncResponse {

    private TextView barcodeResult;
    private String[] productInfo;

    protected void onCreate (Bundle savedInstanceState) {

        // Set layout
        super.onCreate(savedInstanceState);

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

        try{
            Integer.parseInt(productInfo[0]);

            if(productInfo.length > 1) {
                setContentView(R.layout.activity_confirmation);
                barcodeResult = (TextView)findViewById(R.id.itemName);
                barcodeResult.setText(productInfo[1]);

            }
            else{
                finish();
                Intent intent = new Intent(this, CreateItemActivity.class);
                intent.putExtra("newProductID", productInfo[0]);
                startActivity(intent);
            }

        }catch(Exception e) {
            Log.e("Scan", "This QR code is not compatible with this system.");
        }
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
