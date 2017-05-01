package com.example.sreer.geekspad.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kalirajkalimuthu on 4/30/17.
 */

public class SpinnerUtil {
    public static List<String> getStates( Context context,String country) {
        List<String> states = new ArrayList<>();
        if (country != null)
        try {
            InputStream countriesFile = context.getAssets().open(country);
            BufferedReader in = new BufferedReader(new InputStreamReader(countriesFile));
            String state = null;
            while ((state = in.readLine()) != null)
                states.add(state);
        } catch (IOException e) {
            Log.e("Error", "Invalid Country", e);
        }

    return states;
    }
}
