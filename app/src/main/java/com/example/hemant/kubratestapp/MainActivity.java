package com.example.hemant.kubratestapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<User>> {

    public static final String LOG_TAG = MainActivity.class.getName();

    private static final String USER_DATA_REQUEST_URL = "https://mobile-code-test.ifactornotifi.com/json/users";

    private static final int USER_LOADER_ID = 0;

    // adapter for user
    private UserAdapter mAdapter;

    // empty textview to ahow the error in case no results are retrieved
    private TextView mEmptyStateTextView;

    // progress bar to show up while the data is being fetched from the server
    private ProgressBar mProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find reference to listView
        ListView userListView = findViewById(R.id.list);

        // Find the EmptyTextView and set it to the listview
        mEmptyStateTextView = findViewById(R.id.empty_view);
        userListView.setEmptyView(mEmptyStateTextView);

        // Show the progress bar
        mProgressBar = findViewById(R.id.loading_spinner);

        // Create a new adapter that takes empty list of user as input
        mAdapter = new UserAdapter(this, new ArrayList<User>());

        // Set the adapter to the listView
        userListView.setAdapter(mAdapter);

        // Check if the device is connected to the internet
        if (isConnectedToNetwork()) {
            // Interact with the loaders using loader manager
            getLoaderManager().initLoader(USER_LOADER_ID, null, this);
        } else {
            /* Hide the progress bar */
            mProgressBar.setVisibility(View.GONE);

            /* Notify no internet connection */
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }
    }

    /* Check if the device is connected to the network or not */
    public boolean isConnectedToNetwork() {
        ConnectivityManager cm = (ConnectivityManager)
                getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        return isConnected;
    }

    /* Override the Loader methods */

    @Override
    public Loader<List<User>> onCreateLoader(int id, Bundle args) {

        /* Create uri for the data request url */
        Uri uri = Uri.parse(USER_DATA_REQUEST_URL);
        Uri.Builder builder = uri.buildUpon();

        return new UserLoader(this, builder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<User>> loader, List<User> data) {
        /* Hide the loading indicator */
        mProgressBar.setVisibility(View.GONE);

        // Set empty state text to display "No user found."
        mEmptyStateTextView.setText(R.string.no_user);

        // Clear the adapter of previous user data
        mAdapter.clear();

        // update listview
        if (data != null && !data.isEmpty()) {
            mAdapter.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<User>> loader) {
        // On loader reset clear the adapter
        mAdapter.clear();
    }
}
