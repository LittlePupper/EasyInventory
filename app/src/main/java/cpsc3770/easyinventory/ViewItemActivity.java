package cpsc3770.easyinventory;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ViewItemActivity extends AppCompatActivity implements EditItemDialog.EditItemDialogListener {

    private TextView textViewName;
    private TextView textViewSize;
    private TextView textViewDescription;
    private TextView textViewPrice;
    private TextView textViewStock;
    private Button buttonModifyStock;

    String[] productInfo;

    private String productID;

    protected void onCreate (Bundle savedInstanceState) {

        // Set layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item);

        // Create toolbar
        Toolbar toolbar = (Toolbar)findViewById(R.id.actionbar);
        setSupportActionBar(toolbar);

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
    }

    public void openDialog(View v) {
        Bundle args = new Bundle();
        args.putString("productID", productID);
        args.putString("productName", productInfo[1]);
        args.putString("stock", productInfo[6]);
        EditItemDialog editItemDialog = new EditItemDialog();
        editItemDialog.setArguments(args);
        editItemDialog.show(getSupportFragmentManager(), "Edit item dialog");
    }

    @Override
    public void applyStockChange(String stock) {
        textViewStock.setText(stock);
//        finish();
//        startActivity(getIntent());
    }
}
