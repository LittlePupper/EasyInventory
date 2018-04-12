package cpsc3770.easyinventory;

import android.app.Activity;
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

    protected void onCreate (Bundle savedInstanceState) {

        // Set layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item);

        // Create toolbar
        Toolbar toolbar = (Toolbar)findViewById(R.id.actionbar);
        setSupportActionBar(toolbar);

        // Get layout elements
        textViewName = findViewById(R.id.name);
        textViewSize = findViewById(R.id.size);
        textViewDescription = findViewById(R.id.description);
        textViewPrice = findViewById(R.id.price);
        textViewStock = findViewById(R.id.stock);
        buttonModifyStock = findViewById(R.id.modifyStockButton);

        // Get data from intent
        Intent intent = getIntent();
        String[] productInfo = intent.getStringArrayExtra("productInfo");
        textViewName.setText(productInfo[1]);
        textViewSize.setText(productInfo[3] + " " + productInfo[4]);
        textViewDescription.setText(productInfo[5]);
        textViewPrice.setText("$" + productInfo[2]);
        textViewStock.setText(productInfo[6]);
    }

//    private class EditItemDialog extends DialogFragment

    public void openDialog(View v) {
        EditItemDialog editItemDialog = new EditItemDialog();
        editItemDialog.show(getSupportFragmentManager(), "Edit item dialog");
    }

    @Override
    public void applyTexts(String name) {
        textViewName.setText(name);
    }
}
