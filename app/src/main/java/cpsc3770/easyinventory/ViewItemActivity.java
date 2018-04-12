package cpsc3770.easyinventory;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ViewItemActivity extends AppCompatActivity implements EditItemDialog.EditItemDialogListener {

    private TextView textViewName;
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
        buttonModifyStock = findViewById(R.id.modifyStockButton);
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
