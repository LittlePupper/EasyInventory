package cpsc3770.easyinventory;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class EditItemDialog extends AppCompatDialogFragment {

    private EditText editTextName;

    private EditItemDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_edit_item, null);

        builder.setView(view)
                .setTitle("Edit item")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = editTextName.getText().toString();
                        listener.applyTexts(name);
                    }
                });

        editTextName = view.findViewById(R.id.edit_name);

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

    public interface EditItemDialogListener {
        void applyTexts(String name);
    }


}
