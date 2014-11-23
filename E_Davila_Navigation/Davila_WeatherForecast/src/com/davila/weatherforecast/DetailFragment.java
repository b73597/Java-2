package com.davila.weatherforecast;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class DetailFragment extends Fragment {
    TextView detailDesc;
    
    // This simply assigned the previously bundled text string to the Detail Fragment.
    // Invoked by the Master ItemClicked method.
    
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_fragment, container, false);
        detailDesc= (TextView) view.findViewById(R.id.name_text_view);
        Bundle bundle = getArguments();
        String name = bundle.getString("INGREDIENTS");
        detailDesc.setText(name);
               
        return view;
    }
}