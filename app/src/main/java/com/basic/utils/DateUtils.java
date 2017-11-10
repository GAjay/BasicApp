package com.basic.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Date utils is the class to hold the various date manipulation methods.
 */
public class DateUtils {

    /** return date in application dd-MMM,YY format.
     * @param inputDate : input date need to be convert
     * @return : converted date
     */
    public static String getDateTime(String inputDate){
        SimpleDateFormat sdfInput = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        SimpleDateFormat sdfOutput = new SimpleDateFormat("dd MMM,yy HH:mm", Locale.ENGLISH);

        try {
            Date date = sdfInput.parse(inputDate);
            String date1 = sdfOutput.format(date);

            return date1;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /** return date in application dd-MMM,YY format.
     * @param inputDate : input date need to be convert
     * @return : converted date
     */
    public static String getDate(String inputDate){
        SimpleDateFormat sdfInput = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        SimpleDateFormat sdfOutput = new SimpleDateFormat("dd MMM,yy", Locale.ENGLISH);

        try {
            Date date = sdfInput.parse(inputDate);
            String date1 = sdfOutput.format(date);
            return date1;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    /** convert device time in server time.
     * @return : return server time.
     */
    public static String getCurrentTimeInServerTimeZone(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        //sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

        long timestamp = new Date().getTime();
        //timestamp = timestamp+60*60*1000;
        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp);
        final String timeString = sdf.format(cal.getTime());

        return timeString;
    }


    /*public static String getTimeInServerTimeZone(String date){
        SimpleDateFormat sdfInput = new SimpleDateFormat("dd-MM-yyyy hh:mm", Locale.ENGLISH);

        SimpleDateFormat sdfOutput = new SimpleDateFormat("yyyy-MM-dd hh:mm", Locale.ENGLISH);
        sdfOutput.setTimeZone(TimeZone.getTimeZone("GMT"));

        Date inputDate = null;
        try {
            inputDate = sdfInput.parse(date);

            long timestamp = inputDate.getTime();
            timestamp = timestamp+60*60*1000;
            final Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(timestamp);
            final String timeString = sdfOutput.format(cal.getTime());

            return timeString;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }*/

    /** Convert server time into device local time zone
     * @param inputTime : server time received by web services.
     * @return : local time in user time zone.
     */
    public static String convertToDeviceTime(String inputTime){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.ENGLISH);
        try {
            Date date = sdf.parse(inputTime);
            long millis = date.getTime()-60*60*1000;

            TimeZone tz = TimeZone.getDefault();
            millis = millis+tz.getRawOffset();

            //String localTime = sdf.format(millis);
            String localTime =sdf.format(date);
            return localTime;

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date getDateInDate(String inputDate){

        SimpleDateFormat sdfInput = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH);
        Date date = null;
        try {
            date = sdfInput.parse(inputDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }

       /* try {
           date = sdfInput.parse(inputDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }*/
        return date;
    }


    public static Date getDateFormatin(String inputDate){

        SimpleDateFormat sdfInput = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        Date date = null;
        try {
            date = sdfInput.parse(inputDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }

       /* try {
           date = sdfInput.parse(inputDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }*/
        return date;
    }
    public static Date getDateFormat(String inputDate){

        SimpleDateFormat sdfInput = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date date = null;
        try {
            date = sdfInput.parse(inputDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }

       /* try {
           date = sdfInput.parse(inputDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }*/
        return date;
    }



    public static String getTimeInServerTimeZone(String date){
        date = date.replace("T", " ");
        String outputDate;
        SimpleDateFormat sdfInput = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.US);

        SimpleDateFormat sdfOutput = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US);
        //sdfOutput.setTimeZone(TimeZone.getTimeZone("GMT"));

        Date inputDate = null;
        try {
            inputDate = sdfInput.parse(date);

           /* long timestamp = inputDate.getTime();
            timestamp = timestamp+60*60*1000;
            final Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(timestamp);
            final String timeString = sdfOutput.format(cal.getTime());*/
            final String timeString = sdfOutput.format(inputDate);
            outputDate = timeString.replace(" ","T");
            return outputDate;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getCurrentDate() {
        final Calendar c = Calendar.getInstance();
        int mToYear = c.get(Calendar.YEAR);
        int mToMonth = c.get(Calendar.MONTH);
        int mToDay = c.get(Calendar.DAY_OF_MONTH);
        int mToHour = c.get(Calendar.HOUR_OF_DAY);
        int mToMinute = c.get(Calendar.MINUTE);
        String curTime = mToDay + "-" + (mToMonth + 1) + "-" + mToYear+" "+mToHour+":"+mToMinute;
        String outputDate;
        SimpleDateFormat sdfInput = new SimpleDateFormat("dd-MM-yyyy hh:mm", Locale.ENGLISH);

        SimpleDateFormat sdfOutput = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH);
        //sdfOutput.setTimeZone(TimeZone.getTimeZone("GMT"));

        Date inputDate = null;
        try {
            inputDate = sdfInput.parse(curTime);

            long timestamp = inputDate.getTime();
            timestamp = timestamp+60*60*1000;
            final Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(timestamp);
            //final String timeString = sdfOutput.format(cal.getTime());
            final String timeString = sdfOutput.format(inputDate);
            outputDate = timeString.replace(" ","T");
            return outputDate;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


   /* public static String convertToDeviceTime(String inputTime){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss",Locale.ENGLISH);
        try {
            Date date = sdf.parse(inputTime);
            long millis = date.getTime()-60*60*1000;

            TimeZone tz = TimeZone.getDefault();
            millis = millis+tz.getRawOffset();

            String localTime = sdf.format(millis);
            return localTime;

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }*/
}
