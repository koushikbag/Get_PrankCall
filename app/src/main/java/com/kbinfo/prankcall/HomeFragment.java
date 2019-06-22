package com.kbinfo.prankcall;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.kbinfo.prankcall.util.SharedPref;

public class HomeFragment extends Fragment {

    private Context mContext;
    private static HomeFragment mHomeFragmentInstance;

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

        btnStartCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getFragmentManager() != null) {
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

                    Fragment callAcceptFragment = CallAcceptFragment.getInstance();
                    Bundle bundle = new Bundle();
                    bundle.putString("name", etName.getText().toString());
                    bundle.putString("mobile_number", etMobileNumber.getText().toString());

                    callAcceptFragment.setArguments(bundle);
                    getFragmentManager().beginTransaction()
                            .add(R.id.fragment_container, callAcceptFragment)
                            .addToBackStack("accept")
                            .commit();
                }
            }
        });

        /*cbName.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    SharedPref.setName(mContext, etName.getText().toString());
                } else {
                    SharedPref.setName(mContext, "NA");
                }
            }
        });

        cbNumber.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    SharedPref.setNumber(mContext, etMobileNumber.getText().toString());
                } else {
                    SharedPref.setNumber(mContext, "NA");
                }
            }
        });*/

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
