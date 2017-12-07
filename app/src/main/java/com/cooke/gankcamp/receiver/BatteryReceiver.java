package com.cooke.gankcamp.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

/**
 * Created by kch on 2017/12/6.
 */

public class BatteryReceiver extends BroadcastReceiver {

    private TextView textView;
    public BatteryReceiver(TextView textView){
        this.textView = textView;
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (Intent.ACTION_BATTERY_CHANGED.equals(action)){
            int curBattery = intent.getIntExtra("level", 0);
            textView.setText(curBattery+"%");
        }
    }
}
