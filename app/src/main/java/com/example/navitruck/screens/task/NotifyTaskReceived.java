package com.example.navitruck.screens.task;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.util.Log;

import com.example.navitruck.R;
import com.example.navitruck.dto.Task;

public class NotifyTaskReceived extends Activity {

    private static final String TAG = "NotifyTaskReceived";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_new_task);

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            Task task = (Task) bundle.getSerializable("task");
            Log.e(TAG , task.getAddressFrom());
        }


    }

    @Override
    protected void onResume(){
        super.onResume();

        ToneGenerator toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
        toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP,1150);
    }


}
