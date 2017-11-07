package net.pinaz993.studenttracker;

import android.content.Context;
import android.content.SharedPreferences;

import org.joda.time.Duration;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;

/**
 * Handles the settings for the app. Uses SharedPreferences to do so.
 * Created by Patrick Shannon on 10/11/2017.
 */

public class SettingsHandler {
    public final Duration attendanceIntervalDuration;
    protected boolean daily = false;
    protected boolean weekly = false;

    private final Context context;
    private final SharedPreferences.Editor editor;
    private final SharedPreferences settings;

    private final String ATTENDANCE_MODE_DAILY;
    private final String ATTENDANCE_MODE_WEEKLY;
    private final String ATTENDANCE_MODE_DEFAULT;
    private final String ATTENDANCE_MODE_KEY;

    private final String IS_FIRST_TIME_LAUNCH;

    public SettingsHandler(Context context) {
        this.context = context;
        settings = context.getSharedPreferences(
                context.getString(R.string.settings_key), Context.MODE_PRIVATE);
        editor = settings.edit();
        ATTENDANCE_MODE_DAILY = context.getString(R.string.attendance_mode_daily);
        ATTENDANCE_MODE_WEEKLY = context.getString(R.string.attendance_mode_weekly);
        ATTENDANCE_MODE_DEFAULT = ATTENDANCE_MODE_DAILY;
        ATTENDANCE_MODE_KEY = context.getString(R.string.attendance_mode_key);
        String attendanceMode = settings.getString(ATTENDANCE_MODE_KEY, ATTENDANCE_MODE_DEFAULT);

        IS_FIRST_TIME_LAUNCH = context.getString(R.string.is_first_time_launch);

        //<editor-fold desc="Set Attendance Mode">
        if(Objects.equals(attendanceMode, context.getString(R.string.attendance_mode_daily))) {
            attendanceIntervalDuration = new Duration(86400000); //Number of milliseconds in a day
            daily = true;
        }
        else if (Objects.equals(attendanceMode, context.getString(R.string.attendance_mode_weekly))) {
            attendanceIntervalDuration = new Duration(604800000);
            weekly = false;
        }
        else {
            attendanceIntervalDuration = new Duration(86400000); //Number of milliseconds in a day
            daily = true;
            editor.putString(ATTENDANCE_MODE_KEY, ATTENDANCE_MODE_DEFAULT);
            editor.apply();
        }
        //</editor-fold>
    }

    public String[] getAttendanceModeChoices(){
        return new String[] {
                context.getString(R.string.attendance_mode_daily),
                context.getString(R.string.attendance_mode_weekly)
        };
    }

    public void setAttendanceMode(String s){
        if(Arrays.asList(ATTENDANCE_MODE_DAILY, ATTENDANCE_MODE_WEEKLY).contains(s)) {
            editor.putString(ATTENDANCE_MODE_KEY, s);
        }
        else {
            editor.putString(ATTENDANCE_MODE_KEY, ATTENDANCE_MODE_DEFAULT);
        }
    }

    public boolean isFirstTimeLaunch() {
        // By default, assume this is the first time the app is being launched.
        return settings.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    public void setIsFirstTimeLaunch(boolean b){
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, b);
    }
}