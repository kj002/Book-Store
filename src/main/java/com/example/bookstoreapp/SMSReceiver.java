package com.example.bookstoreapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SMSReceiver extends BroadcastReceiver {
    public static final String SMS_FILTER = "SMS_FILTER";
    public static final String SMS_MSG_KEY = "SMS_MSG_KEY";

    @Override
    public void onReceive(Context context, Intent intent) {
        // Use Telephony class to extract incoming messages from the intent
        SmsMessage[] messages = Telephony.Sms.Intents.getMessagesFromIntent(intent);

        for (SmsMessage currMessage : messages) {
            String message = currMessage.getDisplayMessageBody();

            // For each new message, send a broadcast of it to MainActivity
            Intent msgIntent = new Intent();
            msgIntent.setAction(SMS_FILTER);
            msgIntent.putExtra(SMS_MSG_KEY, message);
            context.sendBroadcast(msgIntent);

            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        }
    }
}
