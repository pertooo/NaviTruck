package com.example.navitruck.screens.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.DialogFragment;

import com.example.navitruck.R;

public class AlertDialogFragment extends DialogFragment {
    private Button closeBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.alert_dialog_fragment, container, false);

        initViews(v);
        setLinteners();

        return v;
    }

    private void initViews(View v){
       // closeBtn = v.findViewById(R.id.close_btn);
    }

    private void setLinteners(){
//        closeBtn.setOnClickListener(view -> {
//            getActivity().finish();
//        });
    }
}