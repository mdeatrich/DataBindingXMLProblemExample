/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.deatrich.app.databindingxmlproblemexample.utilities;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public final class DateUtils {

    private static final String TAG = DateUtils.class.getSimpleName();


    /* Milliseconds in a day */
    public static final long DAY_IN_MILLIS = TimeUnit.DAYS.toMillis(1);
    

    public static String formatDateStringMedium (Date aDate) {
        if (aDate != null)
            return DateFormat.getDateInstance(DateFormat.MEDIUM).format(aDate);
        else
            return "";

    }

    public static String formatReportDate (Date aDate) {
        if (aDate != null)
            return new SimpleDateFormat ("EEEE, MMMM d, yyyy", Locale.getDefault()).format(aDate);

        else
            return "";

    }


    public static String formatMonthCommaYear (Date aDate, boolean abbreviatedMonth) {
        String monthStr;

        if (abbreviatedMonth)
             monthStr = "MMM";
        else
            monthStr = "MMMM";

        if (aDate != null)
            return new SimpleDateFormat (monthStr + ", yyyy", Locale.getDefault()).format(aDate);

        else
            return "";

    }



    public static String formatDayOfMonth (Date aDate) {
        if (aDate != null)
            return new SimpleDateFormat ("d", Locale.getDefault()).format(aDate);

        else
            return "";

    }

    public static String formatDayOfWeek (Date aDate) {
        if (aDate != null)
            return new SimpleDateFormat ("EEE", Locale.getDefault()).format(aDate);

        else
            return "";
    }

    public static Calendar getCalendarAtMidnightToday(){
        Calendar cal = Calendar.getInstance();

        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        return cal;
    }

    public static Date getDateAtMidnightToday () {
        Calendar today = Calendar.getInstance();
        return getDateAtMidnight(today.getTime());
    }

    public static Date getDateAtMidnight (Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime (date);
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.HOUR_OF_DAY, 0);

        return cal.getTime();

    }



}