package com.example.ead.constants;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

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

    public static String convertToDateNormal(String inputDate) {
        try {
            // Create a SimpleDateFormat object to parse the input date
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
            Date date = inputFormat.parse(inputDate);

            // Create another SimpleDateFormat object to format the date in the desired format
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
            String outputDate = outputFormat.format(date);

            return outputDate;
        } catch (ParseException e) {
            e.printStackTrace();
            return null; // Handle the parsing error
        }
    }
}
