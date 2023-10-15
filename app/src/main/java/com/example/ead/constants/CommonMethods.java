package com.example.ead.constants;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class CommonMethods {
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String convertToDate(String Bdate) {
        // Parse the ISO 8601 string to an Instant
        Instant instant = Instant.parse(Bdate);

        // Convert the Instant to a date in the desired format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String Adate = instant.atZone(ZoneId.systemDefault()).format(formatter);

        return Adate;
    }
}
