package com.gilortal.djcalendar.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.gilortal.djcalendar.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DJAdapter extends ArrayAdapter<String> {

    Context c;
    List<String> DJName;
    List<String> imageUri;

    public DJAdapter(Context c, List<String> DJName, List<String> imageUri) {
        super(c, R.layout.dj_info, R.id.dj_name_djlist, DJName);
        this.c = c;
        this.DJName = DJName;
        this.imageUri = imageUri;
    }


    @Override
    public View getView(int position, @NonNull View convertView, @android.support.annotation.NonNull ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater)getContext().
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = layoutInflater.inflate(R.layout.dj_info, parent, false);
        ImageView djImage = row.findViewById(R.id.image_dj_djlist);
        TextView djName = row.findViewById(R.id.dj_name_djlist);

        djName.setText(DJName.get(position));
        Picasso.with(c).load(imageUri.get(position)).into(djImage);

        return row;
    }
}
