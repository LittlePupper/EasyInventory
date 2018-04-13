package cpsc3770.easyinventory;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.CommonStatusCodes;

public class ViewItemActivity extends AppCompatActivity implements EditItemDialog.EditItemDialogListener {

    private TextView textViewName;
    private TextView textViewSize;
    private TextView textViewDescription;
    private TextView textViewPrice;
    private TextView textViewStock;
    private TextView toolbarTitle;
    private Button buttonModifyStock;
    private String[] productInfo;
    private String productID;
    private Context context;

    protected void onCreate (Bundle savedInstanceState) {

        context = this;

        // Set layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item);

        // Get layout elements
        textViewName = findViewById(R.id.textViewName);
        textViewSize = findViewById(R.id.size);
        textViewDescription = findViewById(R.id.description);
        textViewPrice = findViewById(R.id.price);
        textViewStock = findViewById(R.id.stock);
        buttonModifyStock = findViewById(R.id.modifyStockButton);

        // Get data from intent
        Intent intent = getIntent();
        productInfo = intent.getStringArrayExtra("productInfo");
        productID = productInfo[0];
        textViewName.setText(productInfo[1]);
        textViewPrice.setText("$" + productInfo[2]);
        textViewSize.setText(productInfo[3] + " " + productInfo[4]);
        textViewDescription.setText(productInfo[5]);
        textViewStock.setText(productInfo[6]);

        // Create toolbar
        Toolbar toolbar = (Toolbar)findViewById(R.id.actionbar);
        toolbarTitle = toolbar.findViewById(R.id.toolbar_title);
        toolbarTitle.setText(productInfo[1]);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.ic_clear_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            // Starts main activity and finishes this one
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    // Applies the changed stock to the text view
    // The stock has already been updated in the DB in EditItemDialog.class
    @Override
    public void applyStockChange(int newStockValue) {
        productInfo[6] = Integer.toString(newStockValue);
        textViewStock.setText(Integer.toString(newStockValue));
        Toast.makeText(this, "Stock successfully updated", Toast.LENGTH_LONG).show();
    }

    // On click for the "Modify Stock" button
    public void modifyStock(View v) {
        Bundle args = new Bundle();
        args.putString("productID", productID);
        args.putString("productName", productInfo[1]);
        args.putString("stock", textViewStock.getText().toString());
        EditItemDialog editItemDialog = new EditItemDialog();
        editItemDialog.setArguments(args);
        editItemDialog.show(getSupportFragmentManager(), "Edit item dialog");
    }

    // On click for the "Edit product" button
    public void editItem(View v) {
        Intent intent = new Intent(this, EditItemActivity.class);
        intent.putExtra("productInfo", productInfo);
        startActivityForResult(intent, 0);
    }

    // When EditItemActivity returns results
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==0) {
            if(resultCode == CommonStatusCodes.SUCCESS) {

                // If the product has been updated
                if(data!=null) {
                    toolbarTitle.setText(data.getStringExtra("productName"));
                    textViewName.setText(data.getStringExtra("productName"));
                    textViewPrice.setText("$" + data.getStringExtra("price"));
                    textViewSize.setText(data.getStringExtra("size") + " " + data.getStringExtra("unit"));
                    textViewDescription.setText(data.getStringExtra("description"));
                    productInfo[1] = data.getStringExtra("productName");
                    productInfo[2] = data.getStringExtra("price");
                    productInfo[3] = data.getStringExtra("size");
                    productInfo[4] = data.getStringExtra("unit");
                    productInfo[5] = data.getStringExtra("description");
                    Toast.makeText(this, data.getStringExtra("productName") + " successfully updated", Toast.LENGTH_LONG).show();
                } else {
                    return;
                }

            // If the product has been deleted
            } else if (resultCode == CommonStatusCodes.CANCELED) {
                if(data!=null) {
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.putExtra("deletedProduct", data.getStringExtra("deletedProduct"));
                    startActivity(intent);
                }
            }
        }
    }
}
