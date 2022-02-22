package com.example.android.guardianfilmnews;
import android.content.AsyncTaskLoader;
import android.content.Context;
import java.util.List;
/**
 * Loads a list of GuardianFilmNewss by using an AsyncTask to perform the
 * network request to the given URL.
 */
public class GuardianFilmNewsLoader extends AsyncTaskLoader<List<GuardianFilmNewsArticle>> {
    /** Query URL */
    private String mUrl;
    /**
     * Constructs a new {@link GuardianFilmNewsLoader}.
     *
     * @param context of the activity
     * @param url to load data from
     */
    public GuardianFilmNewsLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }
    @Override
    protected void onStartLoading() {
        forceLoad();
    }
    /**
     * This is on a background thread.
     */
    @Override
    public List<GuardianFilmNewsArticle> loadInBackground() {
        if (mUrl == null) {
            return null;
        }
        // Perform the network request, parse the response, and extract a list of GuardianFilmNewss.
        List<GuardianFilmNewsArticle> Articles = QueryUtils.fetchGuardianFilmNewsData(mUrl);
        return Articles;
    }
}