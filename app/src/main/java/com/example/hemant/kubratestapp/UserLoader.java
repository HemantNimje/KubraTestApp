package com.example.hemant.kubratestapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by Hemant on 2/16/2018.
 */

public class UserLoader extends AsyncTaskLoader<List<User>> {

    /* Query URL */
    private String mUrl;

    /* Constructor */
    public UserLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    /* To start loader force load it */
    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<User> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        // Perform network request and extract list of users
        List<User> result = QueryUtils.fetchUserData(mUrl);
        return result;
    }
}
