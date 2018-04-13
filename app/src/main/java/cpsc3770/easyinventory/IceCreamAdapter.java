package cpsc3770.easyinventory;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

class IceCreamAdapter extends ArrayAdapter<IceCream>{
    private Context mContext;
    private List<IceCream> moviesList = new ArrayList<>();

    public IceCreamAdapter(@NonNull Context context, ArrayList<IceCream> list) {
        super(context, 0 , list);
        mContext = context;
        moviesList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_item,parent,false);

        IceCream currentIceCream = moviesList.get(position);

        ImageView image = (ImageView)listItem.findViewById(R.id.imageView_poster);
        image.setImageResource(R.drawable.common_full_open_on_phone);

        TextView name = (TextView) listItem.findViewById(R.id.textView_name);
        name.setText(currentIceCream.getProductName());

        TextView release = (TextView) listItem.findViewById(R.id.textView_release);
        release.setText(currentIceCream.getDescription());

        return listItem;
    }
}
