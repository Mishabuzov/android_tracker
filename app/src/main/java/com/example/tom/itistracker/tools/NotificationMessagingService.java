package com.example.tom.itistracker.tools;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.example.tom.itistracker.R;
import com.example.tom.itistracker.models.network.NotificationType;
import com.example.tom.itistracker.screens.navigation.NavigationActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import timber.log.Timber;

import static com.example.tom.itistracker.tools.Constants.NOTIFICATION_EXTRA;

public class NotificationMessagingService extends FirebaseMessagingService {

    private static final String NOTIFICATION_CHANNEL_ID = "default";

    private NotificationManager mNotificationManager;

    @Override
    public void onMessageReceived(RemoteMessage message) {
        initNotificationManager();
        initNotificationChannel();
        Intent intent = recognizeNotificationType(message.getNotification().getTag());
        displayNotification(message.getNotification().getBody(), intent);
    }

    @NonNull
    private Intent recognizeNotificationType(@Nullable final String notificationString) {
        Intent intent;
        NotificationType notificationType = NotificationType.getTypeByValue(notificationString);
        switch (notificationType) {
            case OVERDUE_SPRINT:
            case DEADLINE_IS_CLOSE:
            case BLOCKED_TASKS:
                intent = NavigationActivity.getNavigationIntent(this);
                intent.putExtra(NOTIFICATION_EXTRA, notificationType);
                break;
            case ADDING_MEMBER_WITH_MENTOR:
            case ADDING_MEMBER_WITHOUT_MENTOR:
            case DELETING_TEAM_MEMBER:
            case TEAM_MEMBER_HAS_LEFT_PROJECT:
            case REVIEW_NEWCOMER:
            case HIGH_PRIORITY_ISSUE_WITHOUT_EXECUTOR:
            default:
                intent = NavigationActivity.getNavigationIntent(this);
        }
        return intent;
    }

    private void displayNotification(@NonNull final String message,
                                     @NonNull final Intent intent) {
        Timber.d("Message received: %s", message);
        PendingIntent pendingIntent = PendingIntent
                .getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        //TODO: WARNING! Test it on the device with api < 26!!!
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("ItisTracker:")
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(soundUri)
                        .setVibrate(new long[]{0, 500, 100, 500})
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
