package com.example.android.guardianfilmnews;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
public class GuardianFilmNewsActivity extends AppCompatActivity
        implements LoaderCallbacks<List<GuardianFilmNewsArticle>> {
    private static final String LOG_TAG = GuardianFilmNewsActivity.class.getName();
    /** URL for GuardianFilmNews data from the GUARDIAN_FILM dataset */
    private static final String GUARDIAN_FILM_REQUEST_URL =
            "https://content.guardianapis.com/search?";
    /**
     * Constant value for the GuardianFilmNews loader ID.
     */
    private static final int GuardianFilmNews_LOADER_ID = 1;
    /** Adapter for the list of GuardianFilmNewsArticles */
    private GuardianFilmNewsAdapter mAdapter;
    /** TextView that is displayed when the list is empty */
    private TextView mEmptyStateTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guardianfilmnews_activity);
        // Find a reference to the {@link ListView} in the layout
        ListView GuardianFilmNewsListView = (ListView) findViewById(R.id.list);
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        GuardianFilmNewsListView.setEmptyView(mEmptyStateTextView);
        // Create a new adapter that takes an empty list of articles as input
        mAdapter = new GuardianFilmNewsAdapter(this, new ArrayList<GuardianFilmNewsArticle>());
        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        GuardianFilmNewsListView.setAdapter(mAdapter);
        // Set an item click listener on the ListView, which sends an intent to a web browser
        // to open a website with more information about the selected GuardianFilmNews.
        GuardianFilmNewsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current GuardianFilmNews that was clicked on
                GuardianFilmNewsArticle currentGuardianArticle = mAdapter.getItem(position);
                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri GuardianFilmNewsUri = Uri.parse(currentGuardianArticle.getUrl());
                // Create a new intent to view the GuardianFilmNews URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, GuardianFilmNewsUri);
                //I added this Adapter Clear command to prevent the lists from doubling up everytime the user
                //goes to click an item and come back to the app
                mAdapter.clear();
                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });
        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();
            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(GuardianFilmNews_LOADER_ID, null, this);
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);
            // Update empty state with no connection error message
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }
    }
    @Override
    public Loader<List<GuardianFilmNewsArticle>> onCreateLoader(int i, Bundle bundle) {
        // parse breaks apart the URI string that's passed into its parameter
        Uri baseUri = Uri.parse(GUARDIAN_FILM_REQUEST_URL);
        // buildUpon prepares the baseUri that we just parsed so we can add query parameters to it
        Uri.Builder uriBuilder = baseUri.buildUpon();
        // Append query parameter and its value. For example, the `format=geojson`
        uriBuilder.appendQueryParameter("format", "json");
        uriBuilder.appendQueryParameter("section", "film");
        uriBuilder.appendQueryParameter("show-tags", "contributor");
        uriBuilder.appendQueryParameter("api-key", "d8b9ecc5-517a-40ad-8633-4c759e89ce35");
        // Create a new loader for the given URL
        return new GuardianFilmNewsLoader(this, uriBuilder.toString());
    }
    @Override
    public void onLoadFinished(Loader<List<GuardianFilmNewsArticle>> loader, List<GuardianFilmNewsArticle> articles) {
        // Hide loading indicator because the data has been loaded
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);
        // Set empty state text to display "No GuardianFilmNewsArticles found."
        mEmptyStateTextView.setText(R.string.no_news);
        // If there is a valid list of {@link GuardianFilmNews}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (articles != null && !articles.isEmpty()) {
           mAdapter.addAll(articles);
        }
    }
    @Override
    public void onLoaderReset(Loader<List<GuardianFilmNewsArticle>> loader) {
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }
}
