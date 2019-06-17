package com.kbinfo.prankcall;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class HomeFragment extends Fragment {

    private Context mContext;
    private static HomeFragment mHomeFragmentInstance;

    private void HomeFragment(){}

    public static HomeFragment getInstance(){
        if (mHomeFragmentInstance == null){
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
        Button btnStartCall = view.findViewById(R.id.btn_call_me);

        btnStartCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getFragmentManager() != null) {
                    Fragment callAcceptFragment = CallAcceptFragment.getInstance();
                    Bundle bundle = new Bundle();
                    bundle.putString("name", etName.getText().toString());
                    bundle.putString("mobile_number", etMobileNumber.getText().toString());

                    callAcceptFragment.setArguments(bundle);
                    getFragmentManager().beginTransaction()
                            .add(R.id.fragment_container, callAcceptFragment)
                            .commit();
                }
            }
        });
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mContext = null;
    }
}
