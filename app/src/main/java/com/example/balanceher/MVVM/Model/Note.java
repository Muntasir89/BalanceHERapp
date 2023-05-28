package com.example.balanceher.MVVM.Model;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.TimeUtils;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.balanceher.BroadcastReceiver.AlarmBroadcastReceiver;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

@Entity(tableName = "my_notes")
public class Note {
    String title;
    String details;
    long milliseconds;

    @PrimaryKey(autoGenerate=true)
    private int id;

    public Note(String title, String details, long milliseconds) {
        this.title = title;
        this.details = details;
        this.milliseconds = milliseconds;
    }

    public long getMilliseconds() {
        return milliseconds;
    }

    public void setMilliseconds(long milliseconds) {
        this.milliseconds = milliseconds;
    }

    public int getId() { return id; }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void schedule(Context context){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_MUTABLE);
        if(milliseconds<=System.currentTimeMillis())
            milliseconds += 24*60*60;
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, milliseconds, pendingIntent);
    }
}
