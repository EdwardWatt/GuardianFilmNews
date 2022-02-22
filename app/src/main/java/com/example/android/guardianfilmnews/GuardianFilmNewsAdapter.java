package com.example.android.guardianfilmnews;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

/**
 * An {@link GuardianFilmNewsAdapter} knows how to create a list item layout for each GuardianFilmNews
 * in the data source (a list of {@link GuardianFilmNewsArticle} objects).
 *
 * These list item layouts will be provided to an adapter view like ListView
 * to be displayed to the user.
 */
public class GuardianFilmNewsAdapter extends ArrayAdapter<GuardianFilmNewsArticle> {
    /**
     * Constructs a new {@link GuardianFilmNewsAdapter}.
     *
     * @param context                  of the app
     * @param GuardianFilmNewsArticles is the list of GuardianFilmNewsArticles, which is the data source of the adapter
     */
    public GuardianFilmNewsAdapter(Context context, List<GuardianFilmNewsArticle> GuardianFilmNewsArticles) {
        super(context, 0, GuardianFilmNewsArticles);
    }

    /**
     * Returns a list item view that displays information about the GuardianFilmNews at the given position
     * in the list of GuardianFilmNewsArticles.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.guardianfilmnews_list_item, parent, false);
        }
        // Find the GuardianFilmNews at the given position in the list of GuardianFilmNewss
        GuardianFilmNewsArticle currentGuardianArticle = getItem(position);
        // Get the original location string from the GuardianFilmNews object,
        // which can be in the format of "5km N of Cairo, Egypt" or "Pacific-Antarctic Ridge".
        String subjectTitle = currentGuardianArticle.getSubject();
        // Find the TextView with view ID location
        TextView subjectLineView = (TextView) listItemView.findViewById(R.id.subject_line);
        // Display the location of the current GuardianFilmNews in that TextView
        subjectLineView.setText(subjectTitle);
        String sectionTitle = currentGuardianArticle.getSection();
        TextView sectionLineView = (TextView) listItemView.findViewById(R.id.section_Id);
        sectionLineView.setText(sectionTitle);
        // Create several variables from time using the split command for the article
        String timeAndDate = currentGuardianArticle.getTime();
        String[] separateDate = timeAndDate.split("-");
        String dateYear = separateDate[0]; // this will contain year
        String dateMonth = separateDate[1]; // this will contain month
        String dateTemp = separateDate[2]; // this will contain more string to separate
        String[] separateDateAndTime = dateTemp.split("T");
        String dateDay = separateDateAndTime[0];// this will contain the day
        String timeTemp = separateDateAndTime[1];
        //This will separate into 3 sections but the third section is the seconds and won't be used
        String[] separateTime = timeTemp.split(":");
        String timeHour = separateTime[0];// this will contain the Hour
        String timeMinute = separateTime[1]; //this will contain the minute
        // Find the TextView with view ID date
        TextView dateView = (TextView) listItemView.findViewById(R.id.date);
        dateView.setText(dateDay + "-" + dateMonth + "-" + dateYear);
        // Display the date of the current GuardianFilmNews in that TextView
        // Find the TextView with view ID time
        TextView timeView = (TextView) listItemView.findViewById(R.id.time);
        timeView.setText(timeHour + ":" + timeMinute);
        // Return the list item view that is now showing the appropriate data
        TextView authorView = (TextView) listItemView.findViewById(R.id.author_line);
        authorView.setText("By: " + currentGuardianArticle.getContributor());
        // Return the list item view that is now showing the appropriate data

        return listItemView;
    }
}
