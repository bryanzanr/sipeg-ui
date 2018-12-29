package id.ac.ui.cs.mobileprogramming.bryanza.employmee.utilities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.TextView;
import android.widget.Toast;
import id.ac.ui.cs.mobileprogramming.bryanza.employmee.R;

import java.util.Locale;

public class ContactReceiver extends BroadcastReceiver {

    private TextView sample_text;

    public ContactReceiver(TextView sample_text){
        this.sample_text = sample_text;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        //        listener.doSometbing();
        Bundle extras = intent.getExtras();

        String strMessage = "";

        if ( extras != null )
        {
            Object[] smsextras = (Object[]) extras.get( "pdus" );

            for ( int i = 0; i < smsextras.length; i++ )
            {
                SmsMessage smsmsg;
                if(Build.VERSION.SDK_INT <= 22) {
                    smsmsg = SmsMessage.createFromPdu((byte[])smsextras[i]);
                }
                else {
                    smsmsg = SmsMessage.createFromPdu((byte[])smsextras[i], String.format(Locale.US, null));
                }

                String strMsgBody = smsmsg.getMessageBody().toString();
                String strMsgSrc = smsmsg.getOriginatingAddress();

                strMessage += "SMS from " + strMsgSrc + " : " + strMsgBody;

//                Log.i(TAG, strMessage);
                if(strMsgBody.equalsIgnoreCase(context.getString(R.string.trigger))){
                    Toast.makeText(context, strMessage, Toast.LENGTH_LONG).show();
                    sample_text.setText(strMsgBody);
                }
            }

        }
//        if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION.equals(intent.getAction())) {
//            for (SmsMessage smsMessage : Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
//                String messageBody = smsMessage.getMessageBody();
//                Toast.makeText(context, messageBody, Toast.LENGTH_LONG).show();
//            }
//        }
    }
}
