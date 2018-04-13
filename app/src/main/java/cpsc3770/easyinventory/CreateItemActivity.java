package cpsc3770.easyinventory;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.common.api.CommonStatusCodes;

public class CreateItemActivity extends AppCompatActivity implements BackgroundWorker.AsyncResponse {

    private EditText editTextName;
    private EditText editTextPrice;
    private EditText editTextSize;
    private EditText editTextDescription;
    private String productID;
    private Spinner spinnerUnits;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        // Set layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_item);

        // Create toolbar
        Toolbar toolbar = (Toolbar)findViewById(R.id.actionbar);
        setSupportActionBar(toolbar);

        // Get layout elements
        editTextName = findViewById(R.id.editTextName);
        editTextPrice = findViewById(R.id.editTextPrice);
        editTextSize = findViewById(R.id.editTextSize);
        editTextDescription = findViewById(R.id.editTextDescription);

        // Set spinner data
        spinnerUnits = (Spinner) findViewById(R.id.spinnerUnits);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.units_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerUnits.setAdapter(adapter);

        // Get data from intent
        Intent intent = getIntent();
        productID = intent.getStringExtra("newProductID");

    }

    // Submit changes of product to database
    public void createProduct(View v) {
        String type = "createProduct";
        int errorCount = 0;

        if(TextUtils.isEmpty(editTextName.getText().toString().trim())){
            editTextName.setError("Ice Cream Name cannot be empty.");
            errorCount++;
        }
        if(TextUtils.isEmpty(editTextPrice.getText().toString().trim())){
            editTextPrice.setError("Ice Cream Price cannot be empty.");
            errorCount++;
        }
        if(TextUtils.isEmpty(editTextSize.getText().toString().trim())){
            editTextSize.setError("Ice Cream Size cannot be empty.");
            errorCount++;
        }
        if(TextUtils.isEmpty(editTextDescription.getText().toString().trim())){
            editTextDescription.setError("Ice Cream Description cannot be empty.");
            errorCount++;
        }

        if(errorCount > 0)
            return;

        // Create the MySQL accessor
        new BackgroundWorker(this).execute(type, productID,
                editTextName.getText().toString(),
                editTextPrice.getText().toString(),
                editTextSize.getText().toString(),
                spinnerUnits.getSelectedItem().toString(),
                editTextDescription.getText().toString());
    }

    // End this activity and return to the calling activity with new field values
    @Override
    public void processFinish(String output) {
        Intent intent = new Intent();
        intent.putExtra("productName", editTextName.getText().toString()); // Get the name of the product
        intent.putExtra("price", editTextPrice.getText().toString()); // Get the price of the product
        intent.putExtra("size", editTextSize.getText().toString()); // Get the volume of the product
        intent.putExtra("unit", spinnerUnits.getSelectedItem().toString());
        intent.putExtra("description", editTextDescription.getText().toString()); // Get the description of the product
        setResult(CommonStatusCodes.SUCCESS, intent);
        finish();
    }
}
