package com.quickben22.bitcoinlotto;


import android.app.Application;
import android.content.Context;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import java.util.HashMap;

/**
 * Created by rista on 21-03-2018.
 */

public class AnalyticsHelper {

    private static HashMap<TrackerName, Tracker> sTrackers = new HashMap<TrackerName, Tracker>();

    public static synchronized Tracker getTracker(Context context) {
        TrackerName    trackerId=TrackerName.APP_TRACKER;
        if (!sTrackers.containsKey(trackerId)) {

            GoogleAnalytics analytics = GoogleAnalytics.getInstance(context);
            Tracker t = null;

            switch (trackerId) {
                case APP_TRACKER:
                    t = analytics.newTracker(R.xml.global_tracker);
                    break;
            }

            sTrackers.put(trackerId, t);
        }
        return sTrackers.get(trackerId);
    }

    public enum TrackerName {
        APP_TRACKER, // App specific tracker ID
    }
}
