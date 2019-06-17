package com.kbinfo.prankcall;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CallDeclinedFragment extends Fragment {
    private Context mContext;
    private static CallDeclinedFragment mCallDeclineFragmentInstance;

    private void CallDeclinedFragment(){}

    public static CallDeclinedFragment getInstance(){
        if (mCallDeclineFragmentInstance == null){
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

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mContext = null;
        mCallDeclineFragmentInstance = null;
    }
}
