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

public class SkillUtil {
    private static final String API = "api";

    /**
     * Tag for the log messages
     */
    private static final String LOG_TAG = SkillUtil.class.getSimpleName();

    /**
     * Create a private constructor because no one should ever create a {@link SkillUtil} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name Learners_Util (and an object instance of QueryUtils is not needed).
     */
    private SkillUtil() {
    }

    /**
     * URL to query https://gadsapi.herokuapp.com api for TopSkillLearner information
     */
    private static final String REQUEST_URL =
            "https://gadsapi.herokuapp.com/";

    /**
     * Returns new URL object from the given string URL.
     */
    public static URL buildUrl (String title) {

//        Best Practice Codes Starts
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
     * Return a list of {@ArrayLink topSkillLearners} objects that has been built up from
     * parsing the given JSON response.
     */
    public static ArrayList<TopSkill> extractFeatureFromJson(String topSkillJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(topSkillJSON)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding TopSkillLearner to
        ArrayList<TopSkill> topSkill = new ArrayList<>();

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // Create a JSONArray from the JSON response string
            JSONArray jsonTopSkillArray = new JSONArray(topSkillJSON);

            // Number of TopSkillLearner contain in jsonTopSkillArray
            int numberOfTopSkill = jsonTopSkillArray.length();

            // For each topSkillLearner in the jsonTopSkillArray, create an {@link TopSkillLearner} object
            for (int i = 0; i < numberOfTopSkill; i++) {

                // Get a single topSkillLearner at position i within the list of TopSkillLearners
                JSONObject currentTopSkill = jsonTopSkillArray.getJSONObject(i);

                // Extract the value for the key called "name"
                String topSkillName = currentTopSkill.getString("name");

                // Extract the value for the key called "score"
                String topSkillScore = currentTopSkill.getString("score");

                // Extract the value for the key called "country"
                String topSkillCountry = currentTopSkill.getString("country");

                // Extract the value for the key called "badgeUrl"
                String topSkillBadgeUrl = currentTopSkill.getString("badgeUrl");

                // Create a new {@link TopLearner} object with topLearnerName, topLearnerHours,
                // topLearnerCountry and topLearnerBadgeUrl from the JSON response.
                TopSkill skillLearner = new TopSkill(topSkillName, topSkillScore,
                        topSkillCountry, topSkillBadgeUrl);

                // Add the new {@link TopLearner} to the list of topLearner.
                topSkill.add(skillLearner);
            }
        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            e.printStackTrace();
        }

        // Return the list of topSkillLearner
        return topSkill;
    }
}
