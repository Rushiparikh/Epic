package com.example.rushi.epic_thrillon;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.firebase.storage.FirebaseStorage;

public class NotificationReceiving extends FirebaseMessagingService {

    public NotificationReceiving() {
    }
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        // ...

        // TODO(developer): Handle FCM messages here.
//        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
//        Log.d(">>", "From: " + remoteMessage.getFrom());
//
//        // Check if message contains a data payload.
//        if (remoteMessage.getData().size() > 0) {
//            Log.d(">>", "Message data payload: " + remoteMessage.getData());
//
////            if (/* Check if data needs to be processed by long running job */ true) {
////                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
////                scheduleJob();
////            } else {
////                // Handle message within 10 seconds
////                //handleNow();
////            }
//
//        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(">>", "Message Notification Body: " + remoteMessage.getNotification().getBody());


            sendNotification(remoteMessage);
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
    private void sendNotification(RemoteMessage remoteMessage) {
        RemoteMessage.Notification notification = remoteMessage.getNotification();
        Intent intent = new Intent(this,NotificationFragment.class);
        intent.putExtra("title",remoteMessage.getNotification().getTitle());
        intent.putExtra("body",remoteMessage.getNotification().getBody());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.logo))
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(notification.getTitle())
                .setContentText(notification.getBody())
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)
                ;

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }

}
