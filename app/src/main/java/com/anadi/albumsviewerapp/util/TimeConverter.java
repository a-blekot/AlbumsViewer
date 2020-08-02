package com.anadi.albumsviewerapp.util;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.SimpleTimeZone;

import timber.log.Timber;

public class TimeConverter {
    // 1000 millisec * 60 sec * 60 min * 3 hours
    private static final SimpleTimeZone TIME_ZONE_UA = new SimpleTimeZone(1000 * 60 * 60 * 3, "GMT");

    public static String convert(String time) {
        Date date;

        SimpleDateFormat startFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.UK);
        DateFormat destFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.UK);
        //destFormat.setTimeZone(TIME_ZONE_UA);

        try {
            date = startFormat.parse(time);
        } catch (ParseException e) {
            Timber.d(e);
            Timber.d(Arrays.toString(e.getStackTrace()));
            date = new Date();
        }

        if (date == null) {
            date = new Date();
            Timber.d("date = null");
        }

        return destFormat.format(date);
    }
}
