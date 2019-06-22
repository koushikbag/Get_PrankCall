package com.kbinfo.prankcall;

import android.content.Context;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class CallAcceptFragment extends Fragment {
    private Context mContext;
    private static CallAcceptFragment mCallAcceptFragmentInstance;
    private String name = "";
    private String mobileNumber = "";
    private Ringtone mRingtone;
    private Vibrator mVibrator;

    private void CallAcceptFragment() {
    }

    public static CallAcceptFragment getInstance() {
        if (mCallAcceptFragmentInstance == null) {
            mCallAcceptFragmentInstance = new CallAcceptFragment();
        }
        return mCallAcceptFragmentInstance;
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
        View view = inflater.inflate(R.layout.fragment_call_accept, container, false);
        TextView tvName = view.findViewById(R.id.tv_name);
        TextView tvNumber = view.findViewById(R.id.tv_mobile_number);
        ImageButton btnAccept = view.findViewById(R.id.btn_accept);
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

        Uri phoneNotification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        mRingtone = RingtoneManager.getRingtone(mContext, phoneNotification);
        mVibrator = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = {500, 500, 500, 500, 500};

        mRingtone.play();
        if (mVibrator.hasVibrator())
            mVibrator.vibrate(pattern, 0);

        /*if (Build.VERSION.SDK_INT >= 26) {
            mVibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            mVibrator.vibrate(pattern, -1);
        }*/

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment callDeclineFragment = CallDeclinedFragment.getInstance();
                Bundle bundle = new Bundle();
                bundle.putString("name", name);
                bundle.putString("mobile_number", mobileNumber);

                if (mRingtone.isPlaying()) {
                    mRingtone.stop();
                }

                if (mVibrator.hasVibrator())
                    mVibrator.cancel();

                callDeclineFragment.setArguments(bundle);
                if (getFragmentManager() != null) {
                    getFragmentManager().beginTransaction()
                            .add(R.id.fragment_container, callDeclineFragment)
                            .addToBackStack("decline")
                            .commit();
                }
            }
        });

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mRingtone.isPlaying()) {
            mRingtone.stop();
        }

        if (mVibrator.hasVibrator())
            mVibrator.cancel();
    }

    @Override
    public void onDestroy() {
        mContext = null;
        if (mRingtone.isPlaying()) {
            mRingtone.stop();
        }

        if (mVibrator.hasVibrator())
            mVibrator.cancel();
        super.onDestroy();
    }
}
