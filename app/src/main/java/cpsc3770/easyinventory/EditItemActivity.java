package cpsc3770.easyinventory;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.common.api.CommonStatusCodes;

public class EditItemActivity extends AppCompatActivity implements BackgroundWorker.AsyncResponse {

    private EditText editTextName;
    private EditText editTextPrice;
    private EditText editTextSize;
    private EditText editTextDescription;
    private String[] productInfo;
    private String productID;
    private Spinner spinnerUnits;
    private Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        context = this;

        // Set layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);


        // Create toolbar
        Toolbar toolbar = (Toolbar)findViewById(R.id.actionbar);
        TextView toolbarTitle = toolbar.findViewById(R.id.toolbar_title);
        toolbarTitle.setText("Edit product");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.ic_clear_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(CommonStatusCodes.INTERRUPTED, intent);
                finish();
            }
        });

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
        productInfo = intent.getStringArrayExtra("productInfo");
        productID = productInfo[0];
        editTextName.setText(productInfo[1]);
        editTextName.getBackground().mutate().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
        editTextPrice.setText(productInfo[2]);
        editTextPrice.getBackground().mutate().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
        editTextSize.setText(productInfo[3]);
        editTextSize.getBackground().mutate().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
        editTextDescription.setText(productInfo[5]);
        editTextDescription.getBackground().mutate().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.Submit) {
            updateProduct();
        } else if (id == R.id.Delete) {
            AlertDialog.Builder confirmation = new AlertDialog.Builder(EditItemActivity.this);
            confirmation.setMessage("Are you sure you want to delete " + productInfo[1] + "?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            deleteProduct();
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = confirmation.create();
            alert.show();
        }

        return super.onOptionsItemSelected(item);
    }

    public void updateProduct() {
        String type = "updateProduct";

        // Create the MySQL accessor
        new BackgroundWorker(this).execute(type, productID,
                editTextName.getText().toString(),
                editTextPrice.getText().toString(),
                editTextSize.getText().toString(),
                spinnerUnits.getSelectedItem().toString(),
                editTextDescription.getText().toString());
    }

    // Delete product in database
    public void deleteProduct() {

        String type = "deleteProduct";

        //Create the MySQL accessor
        new BackgroundWorker(this).execute(type, productID);

    }

    // End this activity and return to the calling activity with new field values
    @Override
    public void processFinish(String output) {
        if (output.equals("Deleted")) {
            Intent intent = new Intent();
            intent.putExtra("deletedProduct", productInfo[1]);
            setResult(CommonStatusCodes.CANCELED, intent);
        } else if (output.equals("Updated")) {
            Intent intent = new Intent();
            intent.putExtra("productName", editTextName.getText().toString()); // Get the name of the product
            intent.putExtra("price", editTextPrice.getText().toString()); // Get the price of the product
            intent.putExtra("size", editTextSize.getText().toString()); // Get the volume of the product
            intent.putExtra("unit", spinnerUnits.getSelectedItem().toString());
            intent.putExtra("description", editTextDescription.getText().toString()); // Get the description of the product
            setResult(CommonStatusCodes.SUCCESS, intent);
        }
        finish();
    }
}
