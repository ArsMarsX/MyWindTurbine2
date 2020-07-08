package com.revolve44.mywindturbine.ui.setturbine;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarFinalValueListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.revolve44.mywindturbine.R;
//import com.revolve44.windturbine1.R;

import static android.content.ContentValues.TAG;

public class SettingNominalPowerFragment extends Fragment {
    // Store instance variables
    int a = 0;
    private String title;
    private int page;
    CrystalRangeSeekbar rangeSeekbar;

    // newInstance constructor for creating fragment with arguments
    public static SettingNominalPowerFragment newInstance(int page, String title) {
        SettingNominalPowerFragment fragmentFirst = new SettingNominalPowerFragment();
        Bundle args = new Bundle();
        args.putInt("someInt2", page);
        args.putString("someTitle2", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("someInt2", 0);
        title = getArguments().getString("someTitle2");
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmentlocation_nominalpower, container, false);

        // get min and max text view
        final TextView tvMin = (TextView) view.findViewById(R.id.textMin1);
        final TextView tvMax = (TextView) view.findViewById(R.id.textMax1);
        // get seekbar from view
        rangeSeekbar = (CrystalRangeSeekbar) view.findViewById(R.id.rangeSeekbar1);

        rangeSeekbar.setMinValue(1)
                    .setMaxValue(24)
                    .setMinStartValue(2)
                    .setMaxStartValue(20)
                    .apply();

        rangeSeekbar.setMinStartValue(2)
                    .setMaxStartValue(15)
                    .apply();

        if(a<=1){

            Log.d(TAG, "onCreateView:  set max init "+ a);
            a++;
        }
        // set listener
        rangeSeekbar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                tvMin.setText("Cut-in wind speed \n"+minValue + " m/s");
                tvMax.setText("Rated power at \n"+maxValue + " m/s");
            }
        });
         // set final value listener
        rangeSeekbar.setOnRangeSeekbarFinalValueListener(new OnRangeSeekbarFinalValueListener() {
            @Override
            public void finalValue(Number minValue, Number maxValue) {

               Log.d("CRS=>", String.valueOf(minValue) + " : " + String.valueOf(maxValue));
            }
        });
        return view;
    }
}
