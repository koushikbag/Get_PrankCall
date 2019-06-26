package com.kbinfo.prankcall;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.kbinfo.prankcall.util.SharedPref;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static android.content.Context.ALARM_SERVICE;

public class HomeFragment extends Fragment {

    private Context mContext;
    private static HomeFragment mHomeFragmentInstance;
    private String mTimeSelected;
    private InterstitialAd mInterstitialAd;

    private void HomeFragment() {
    }

    public static HomeFragment getInstance() {
        if (mHomeFragmentInstance == null) {
            mHomeFragmentInstance = new HomeFragment();
        }
        return mHomeFragmentInstance;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        final EditText etName = view.findViewById(R.id.et_name);
        final EditText etMobileNumber = view.findViewById(R.id.et_mobile_number);
        final CheckBox cbName = view.findViewById(R.id.cb_name);
        final CheckBox cbNumber = view.findViewById(R.id.cb_number);
        Button btnStartCall = view.findViewById(R.id.btn_call_me);
        final EditText etTimer = view.findViewById(R.id.et_reminder);
        Spinner spTime = view.findViewById(R.id.spinner_time);

        mInterstitialAd = new InterstitialAd(mContext);

        // set the ad unit ID
        mInterstitialAd.setAdUnitId(getString(R.string.call_end_fullscreen));

        // Load ads into Interstitial Ads
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        List<String> categories = new ArrayList<String>();
        categories.add("Seconds");
        categories.add("Minutes");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spTime.setAdapter(dataAdapter);

        spTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                mTimeSelected = parent.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        etTimer.setText("5");

        btnStartCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int seconds = 5;
                if (cbName.isChecked()) {
                    if (etName.getText().toString().isEmpty()) {
                        SharedPref.setName(mContext, "NA");
                    } else
                        SharedPref.setName(mContext, etName.getText().toString());
                } else {
                    SharedPref.setName(mContext, "NA");
                }
                if (cbNumber.isChecked()) {
                    if (etMobileNumber.getText().toString().isEmpty())
                        SharedPref.setNumber(mContext, "NA");
                    else
                        SharedPref.setNumber(mContext, etMobileNumber.getText().toString());
                } else {
                    SharedPref.setNumber(mContext, "NA");
                }

                if (mTimeSelected.equalsIgnoreCase("Seconds")) {
                    if (!etTimer.getText().toString().isEmpty())
                        seconds = Integer.parseInt(etTimer.getText().toString());
                    else
                        seconds = 1;
                } else {
                    if (!etTimer.getText().toString().isEmpty())
                        seconds = Integer.parseInt(etTimer.getText().toString()) * 60;
                    else
                        seconds = 1;
                }

                Intent intent = new Intent(mContext, CallScreenActivity.class);
                intent.putExtra("name", etName.getText().toString());
                intent.putExtra("mobile_number", etMobileNumber.getText().toString());
                PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent, PendingIntent.FLAG_ONE_SHOT);
                ((AlarmManager) mContext.getSystemService(ALARM_SERVICE)).set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + seconds * 1000, pendingIntent);

                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                    Log.d("PrankMainActivity: ", "Loaded Succesfully");
                } else {
                    Log.d("PrankMainActivity: ", "The interstitial wasn't loaded yet.");
                }

                if (getActivity() != null){
                    getActivity().finish();
                }
            }
        });

        if (SharedPref.getName(mContext).equalsIgnoreCase("NA")) {
            cbName.setChecked(false);
            etName.setText("");
        } else {
            cbName.setChecked(true);
            etName.setText(SharedPref.getName(mContext));
        }

        if (SharedPref.getNumber(mContext).equalsIgnoreCase("NA")) {
            cbNumber.setChecked(false);
            etMobileNumber.setText("");
        } else {
            cbNumber.setChecked(true);
            etMobileNumber.setText(SharedPref.getNumber(mContext));
        }
        return view;
    }

    @Override
    public void onDestroy() {
        mContext = null;
        super.onDestroy();
    }
}
