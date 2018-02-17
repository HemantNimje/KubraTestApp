package com.example.hemant.kubratestapp;

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
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hemant on 2/16/2018.
 */

public final class QueryUtils {

    /* LOG TAG for tags */
    public static final String LOG_TAG = QueryUtils.class.getName();


    private QueryUtils() {

    }

    public static List<User> fetchUserData(String requestUrl) {

        /**
         * Sleep the main thread to show the loading spinner
         */
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Create URL Object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to URL and receive JSON response
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error making HTTP request", e);
        }

        // Extract JSON response and create List of user object
        List<User> users = extractFeaturesFromJson(jsonResponse);

        // Return the list of users
        return users;
    }


    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }


    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
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
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the user data JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }


    /**
     * Convert the InputStream into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
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
     * Return List<User> object by parsing out the information about first user from the userDataJSON string.
     */
    private static List<User> extractFeaturesFromJson(String userDataJSON) {

        // If JSON string is empty return null
        if (TextUtils.isEmpty(userDataJSON)) {
            return null;
        }

        // Create list of users where we can add user data
        List<User> users = new ArrayList<>();

        // Parse the JSON response
        try {
            // Convert the userDataString to JSONArrayObject
            JSONArray userDataArray = new JSONArray(userDataJSON);

            for (int i = 0; i <= userDataArray.length(); i++) {

                // Get user data at position i
                JSONObject currentUser = userDataArray.getJSONObject(i);

                // Get the user name
                String username = currentUser.getString("username");

                // Get "address" object
                JSONObject address = currentUser.getJSONObject("address");

                // Get "street" of the address
                String street = address.getString("street");

                // Get "suite" of the address
                String suite = address.getString("suite");

                // Get "city" of the address
                String city = address.getString("city");

                // Get 'zipcode" of the address
                String zipcode = address.getString("zipcode");

                // Create a complete address
                String completeAddress = street + "," + suite + "," + city + "," + zipcode;

                // Create new user object from name and address
                User user = new User(username, completeAddress);

                // Add the newly created user object the the users list.
                users.add(user);
            }

        } catch (JSONException e) {
            // Log message to show if the exception occurred while parsing JSON response
            Log.e("QueryUtils", "Problem parsing the user data JSON results", e);
        }

        // Return list of users
        return users;
    }
}
