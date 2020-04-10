package com.example.navitruck.screens.dialog;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;

public class DialogFragment extends androidx.fragment.app.DialogFragment {

    public DialogFragment() {

    }

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {

        ProgressDialog dialog = new ProgressDialog(getActivity());
        this.setStyle(STYLE_NO_INPUT, getTheme());
        dialog.setMessage("Loading..");
        dialog.setCancelable(false);

        return dialog;
    }
}
