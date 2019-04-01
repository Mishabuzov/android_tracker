package com.example.tom.itistracker.tools;

import com.example.tom.itistracker.models.local.MenuItem;

/**
 * Created by Tom on 25.03.2018.
 */

public interface Constants {

    long READ_TIMEOUT = 60;

    long CONNECTION_TIMEOUT = 60;

    String DEFAULT_LOGIN_TYPE = "normal";

    int MIN_LOGIN_LENGTH = 2;

    int MIN_PASSWORD_LENGTH = 4;

    int MIN_EMAIL_LENGTH = 4;

    MenuItem FIRST_SCREEN_ITEM = MenuItem.SPRINTS;

    String NOTIFICATION_EXTRA = "notification";

    int MILLIS_TO_DAYS_DIVISION_VALUE = 1000 * 60 * 60 * 24;

}
