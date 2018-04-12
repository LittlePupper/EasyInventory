package cpsc3770.easyinventory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ConfirmationActivity extends Activity {

    protected void onCreate (Bundle savedInstanceState) {

        // Set layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        // Get data from scanner (intent)
        Intent intent = getIntent();
        String result = intent.getStringExtra("barcode");
        TextView barcodeResult;
        barcodeResult = (TextView)findViewById(R.id.itemName);
        barcodeResult.setText(result);
    }

    public void viewItem(View v) {
        Intent intent = new Intent(this, ViewItemActivity.class);
        startActivity(intent);
    }

//    public void scanAgain() {
//        Intent intent = new Intent(this, ScanBarcodeActivity.class);
//        startActivity(intent);
//        startActivityForResult(intent, 0);
//    }

}
