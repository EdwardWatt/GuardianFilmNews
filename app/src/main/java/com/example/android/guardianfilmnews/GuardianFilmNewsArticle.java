package com.example.android.guardianfilmnews;
/**
 * An {@link GuardianFilmNewsArticle} object contains information related to a single GuardianFilmNews.
 */
public class GuardianFilmNewsArticle {
    /** Section name of the GuardianFilmNews */
    private String mSection;
    /** Subject of the GuardianFilmNews */
    private String mSubject;
    /** Time of the GuardianFilmNews */
    private String mTime;
    /** Website URL of the GuardianFilmNews */
    private String mUrl;
    private String mContributor;

    /**
     * Constructs a new {@link GuardianFilmNewsArticle} object.
     *
     * @param section is the section of the Guardian News
     * @param subject name the GuardianFilmNews article
     * @param time is the time and date stamp when the article was posted
     * @param url is the website URL to find more details about the Film News from Guardian
     *            @param contributor is the name the GuardianFilmNews article author
     */
    public GuardianFilmNewsArticle(String section, String subject, String time, String url, String contributor) {
        mSection = section;
        mSubject = subject;
        mTime = time;
        mUrl = url;
        mContributor = contributor;
    }
    /**
     * Returns the magnitude of the GuardianFilmNews.
     */
    public String getSection() {
        return mSection;
    }
    /**
     * Returns the location of the GuardianFilmNews.
     */
    public String getSubject() {
        return mSubject;
    }
    /**
     * Returns the time of the GuardianFilmNews.
     */
    public String getTime() {
        return mTime;
    }
    /**
     * Returns the website URL to find more information about the GuardianFilmNews.
     */
    public String getUrl() {
        return mUrl;
    }
    public String getContributor() {
        return mContributor;
    }
}