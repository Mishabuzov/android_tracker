package com.example.tom.itistracker.tools;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.example.tom.itistracker.R;
import com.example.tom.itistracker.screens.base.activities.StartActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import timber.log.Timber;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String NOTIFICATION_CHANNEL_ID = "default";

    private NotificationManager mNotificationManager;

    @Override
    public void onMessageReceived(RemoteMessage message) {
        initNotificationManager();
        initNotificationChannel();
        sendMyNotification(message.getNotification().getBody());
    }

    private void sendMyNotification(String message) {
        Timber.d("Message received: %s", message);
        //On click of notification it redirect to this Activity
        Intent intent = new Intent(this, StartActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        //TODO: WARNING! Test it on the device with api < 26!!!
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("My Firebase Push notification")
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(soundUri)
                .setContentIntent(pendingIntent);

        mNotificationManager.notify(0, notificationBuilder.build());
    }

    private void initNotificationManager() {
        if (mNotificationManager == null) {
            mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
    }

    public void initNotificationChannel() {
        if (Build.VERSION.SDK_INT < 26) {
            return;
        }
        if (mNotificationManager.getNotificationChannel(NOTIFICATION_CHANNEL_ID) != null) {
            //Channel already created. Exit.
            return;
        }
        NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID,
                "Notification channel",
                NotificationManager.IMPORTANCE_DEFAULT);
        channel.setDescription("Channel for notifications");
        mNotificationManager.createNotificationChannel(channel);
    }

}
