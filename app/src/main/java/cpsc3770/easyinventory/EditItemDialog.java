package cpsc3770.easyinventory;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

public class EditItemDialog extends AppCompatDialogFragment implements BackgroundWorker.AsyncResponse {

    private TextView textViewName;
    private TextView textViewOriginalStock;
    private TextView textViewNewStockValue;
    private EditText editTextStock;
    private EditItemDialogListener listener;
    private String productID;
    private String productName;
    private String originalStockString;
    private int originalStockInt;
    private int newStockValue;
    private Switch addTakeSwitch;
    private boolean takeFromStock = true;
    private String databaseResult = "";

    @Override
    public Dialog onCreateDialog (Bundle savedInstanceState) {

        //Initialize
        productID = getArguments().getString("productID", "0");
        productName = getArguments().getString("productName", "Product name");
        originalStockString = getArguments().getString("stock", "0");
        originalStockInt = Integer.parseInt(originalStockString);

        // Build the dialog box
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_edit_item, null);
        textViewName = view.findViewById(R.id.textViewName);
        textViewName.setText(productName);
        textViewOriginalStock = view.findViewById(R.id.textViewOriginalStock);
        textViewOriginalStock.setText(originalStockString);

        // Change listener on the edit text stock field
        editTextStock = view.findViewById(R.id.editTextStock);
        editTextStock.getBackground().mutate().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
        editTextStock.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {   }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {   }

            @Override
            public void afterTextChanged(Editable s) {
                updateStockTextView();
            }
        });

        textViewNewStockValue = view.findViewById(R.id.textViewNewStockValue);

        // Initialize switch
        addTakeSwitch = view.findViewById(R.id.addTakeSwitch);

        // Change listener on the switch
        addTakeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                takeFromStock = isChecked;
                updateStockTextView();
            }
        });

        builder.setView(view)
//                .setTitle("Edit item")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        updateDatabase();
                        listener.applyStockChange(newStockValue);
                    }
                });

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (EditItemDialogListener)context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement EditItemDialogListener");
        }
    }

    @Override
    public void processFinish(String output) {
        databaseResult = output;
    }

    // Does a thing in the calling activity (ViewItemActivity)
    public interface EditItemDialogListener {
        void applyStockChange(int newStockValue);
    }

    // Updates the new stock value text view
    private void updateStockTextView () {
        if (!editTextStock.getText().toString().matches("")) {
            if (takeFromStock) {
                // Taking from stock
                if ((originalStockInt -Integer.parseInt(editTextStock.getText().toString())<0)) {
                    AlertDialog d = (AlertDialog) getDialog();
                    if (d != null) {
                        Button positiveButton = d.getButton(Dialog.BUTTON_POSITIVE);
                        positiveButton.setEnabled(false);
                        textViewNewStockValue.setTextColor(getResources().getColor(R.color.red));
                    }
                } else {
                    AlertDialog d = (AlertDialog) getDialog();
                    if (d != null) {
                        Button positiveButton = d.getButton(Dialog.BUTTON_POSITIVE);
                        positiveButton.setEnabled(true);
                        textViewNewStockValue.setTextColor(getResources().getColor(R.color.textgrey));
                    }
                }
                newStockValue = originalStockInt - Integer.parseInt(editTextStock.getText().toString());
            } else {
                // Adding to stock
                newStockValue = originalStockInt + Integer.parseInt(editTextStock.getText().toString());
                AlertDialog d = (AlertDialog) getDialog();
                if (d != null) {
                    Button positiveButton = d.getButton(Dialog.BUTTON_POSITIVE);
                    positiveButton.setEnabled(true);
                    textViewNewStockValue.setTextColor(getResources().getColor(R.color.textgrey));
                }
            }
            textViewNewStockValue.setText(Html.fromHtml("New stock is <b>" + newStockValue+"</b>"));
        } else {
            textViewNewStockValue.setText("No change to stock");
        }
    }

    private void updateDatabase() {
        String type = "updateStock";

        // Create the MySQL accessor
        new BackgroundWorker(this).execute(type, productID, Integer.toString(newStockValue));
    }
}
