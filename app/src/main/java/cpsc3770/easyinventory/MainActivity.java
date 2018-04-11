package cpsc3770.easyinventory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

public class MainActivity extends Activity {

    TextView barcodeResult;

    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        barcodeResult = (TextView)findViewById(R.id.barcode_result);
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
                } else {
                    barcodeResult.setText("No barcode found");
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
