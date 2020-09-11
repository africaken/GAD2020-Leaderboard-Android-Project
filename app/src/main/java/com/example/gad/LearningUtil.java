package com.example.gad;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class LearningUtil {
    private static final String API = "api";

    /**
     * Tag for the log messages
     */
    private static final String LOG_TAG = LearningUtil.class.getSimpleName();
    /**
     * URL to query https://gadsapi.herokuapp.com api for TopLearner information
     */
    private static final String REQUEST_URL =
            "https://gadsapi.herokuapp.com/";

    /**
     * Create a private constructor because no one should ever create a {@link LearningUtil} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name Learners_Util (and an object instance of QueryUtils is not needed).
     */
    private LearningUtil() {
    }

    /**
     * Returns new URL object from the given string URL.
     */
    public static URL buildUrl (String title) {

        URL url = null;
        Uri uri = Uri.parse(REQUEST_URL).buildUpon()
                .appendPath(API)
                .appendPath(title)
                .build();
        try {
            url =  new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }


    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    public static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    public static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Return a list of {@ArrayLink topLearners} objects that has been built up from
     * parsing the given JSON response.
     */
    public static ArrayList<TopLearner> extractFeatureFromJson(String topLearnerJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(topLearnerJSON)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding TopLearner to
        ArrayList<TopLearner> topLearner = new ArrayList<>();

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // Create a JSONArray from the JSON response string
            JSONArray jsonTopLearnerArray = new JSONArray(topLearnerJSON);

            // Number of TopLearner contain in jsonTopLearnerArray
            int numberOfTopLearners = jsonTopLearnerArray.length();

            // For each topLearner in the jsonTopLearnerArray, create an {@link TopLearner} object
            for (int i = 0; i < numberOfTopLearners; i++) {

                // Get a single topLearner at position i within the list of TopLearners
                JSONObject currentTopLearner = jsonTopLearnerArray.getJSONObject(i);

                // Extract the value for the key called "name"
                String topLearnerName = currentTopLearner.getString("name");

                // Extract the value for the key called "hours"
                String topLearnerHours = currentTopLearner.getString("hours");

                // Extract the value for the key called "country"
                String topLearnerCountry = currentTopLearner.getString("country");

                // Extract the value for the key called "badgeUrl"
                String topLearnerBadgeUrl = currentTopLearner.getString("badgeUrl");

                // Create a new {@link TopLearner} object with topLearnerName, topLearnerHours,
                // topLearnerCountry and topLearnerBadgeUrl from the JSON response.
                TopLearner learner = new TopLearner(topLearnerName, topLearnerHours,
                        topLearnerCountry, topLearnerBadgeUrl);

                // Add the new {@link TopLearner} to the list of topLearner.
                topLearner.add(learner);
            }
        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            e.printStackTrace();
        }

        // Return the list of topLearner
        return topLearner;
    }
}
