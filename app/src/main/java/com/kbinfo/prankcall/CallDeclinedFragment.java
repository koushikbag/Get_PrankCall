package com.kbinfo.prankcall;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class CallDeclinedFragment extends Fragment {
    private Context mContext;
    private static CallDeclinedFragment mCallDeclineFragmentInstance;
    private String name = "";
    private String mobileNumber = "";

    private void CallDeclinedFragment() {
    }

    public static CallDeclinedFragment getInstance() {
        if (mCallDeclineFragmentInstance == null) {
            mCallDeclineFragmentInstance = new CallDeclinedFragment();
        }
        return mCallDeclineFragmentInstance;
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
        View view = inflater.inflate(R.layout.fragment_call_decline, container, false);
        final TextView tvTime = view.findViewById(R.id.tv_time);
        TextView tvName = view.findViewById(R.id.tv_name_d);
        TextView tvNumber = view.findViewById(R.id.tv_mobile_number_d);
        ImageButton btnEndCall = view.findViewById(R.id.btn_end);

        Bundle bundle = getArguments();

        if (bundle != null) {
            name = bundle.getString("name");
            mobileNumber = bundle.getString("mobile_number");

            if (name.isEmpty()) {
                name = "Unknown";
            }
            if (mobileNumber != null && mobileNumber.isEmpty())
                mobileNumber = "+91 9011020304";

            tvName.setText(name);
            tvNumber.setText(mobileNumber);
        }

        /*new CountDownTimer(20*60000, 1000) {

            public void onTick(long millisUntilFinished) {
                tvTime.setText("seconds remaining: " +new SimpleDateFormat("mm:ss:SS").format(new Date( millisUntilFinished)));
            }

            public void onFinish() {
                tvTime.setText("done!");
            }
        }.start();*/

        final int[] time = {60};
        new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                tvTime.setText("0:" + checkDigit(time[0]));
                time[0]--;
            }

            public void onFinish() {

                tvTime.setText("call finished");

                //Objects.requireNonNull(getActivity()).finish();
            }

        }.start();

        btnEndCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    Objects.requireNonNull(getActivity()).finish();
                }
            }
        });

        return view;
    }

    private String checkDigit(int number) {
        return (60 - number) <= 9 ? "0" + (60 - number) : String.valueOf(60 - number);
    }
    @Override
    public void onDestroy() {
        mContext = null;
        super.onDestroy();
    }
}
