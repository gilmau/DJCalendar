package com.gilortal.djcalendar.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.gilortal.djcalendar.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.List;

public class lineupDJAdapters extends ArrayAdapter<String> {

    Context c;
    List<String> DJName;
    List<String> imageUri;
    List<String> checkbox;


    public lineupDJAdapters(Context c, List<String> DJName, List<String> imageUri) {
        super(c, R.layout.lineup_dj_info, R.id.checkedbox_dj_Name, DJName);
        this.c = c;
        this.DJName = DJName;
        this.imageUri = imageUri;

    }

    public View getView(final int position, @NonNull View convertView, @android.support.annotation.NonNull ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater)getContext().
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = layoutInflater.inflate(R.layout.lineup_dj_info, parent, false);
        ImageView djImage = row.findViewById(R.id.image_dj_djlist_lineup);
        TextView nameDj = row.findViewById(R.id.dj_name_djlist_lineup);
        CheckBox checkBoxDj = row.findViewById(R.id.checkedbox_dj_Name);

        nameDj.setText(DJName.get(position));
        Picasso.with(c).load(imageUri.get(position)).into(djImage);
        return row;
    }


}

