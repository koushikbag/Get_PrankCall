package com.kbinfo.prankcall;

import android.content.Context;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class CallAcceptFragment extends Fragment {
    private Context mContext;
    private static CallAcceptFragment mCallAcceptFragmentInstance;
    private String name = "";
    private String mobileNumber = "";

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
        Button btnAccept = view.findViewById(R.id.btn_accept);
        Button btnDecline = view.findViewById(R.id.btn_decline);
        Bundle bundle = getArguments();

        if (bundle != null) {
            name = bundle.getString("name");
            mobileNumber = bundle.getString("mobile_number");

            tvName.setText(name);
            tvNumber.setText(mobileNumber);
        }

        Uri phoneNotification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        final Ringtone r = RingtoneManager.getRingtone(mContext, phoneNotification);
        final Vibrator vibe = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = {500, 500, 500, 500, 500};

        r.play();
        vibe.vibrate(pattern, 0);
        /*if (Build.VERSION.SDK_INT >= 26) {
            vibe.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            vibe.vibrate(pattern, -1);
        }*/

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment callDeclineFragment = CallDeclinedFragment.getInstance();
                Bundle bundle = new Bundle();
                bundle.putString("name", name);
                bundle.putString("mobile_number", mobileNumber);

                if (r.isPlaying()) {
                    r.stop();
                }

                vibe.cancel();

                callDeclineFragment.setArguments(bundle);
                if (getFragmentManager() != null) {
                    getFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, callDeclineFragment)
                            .commit();
                }
            }
        });

        btnDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (r.isPlaying()) {
                    r.stop();
                }
                vibe.cancel();

                if (getFragmentManager() != null) {
                    getFragmentManager().popBackStack();
                }
            }
        });

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mContext = null;
        mCallAcceptFragmentInstance = null;
    }
}
