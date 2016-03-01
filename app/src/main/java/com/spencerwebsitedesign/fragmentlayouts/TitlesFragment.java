package com.spencerwebsitedesign.fragmentlayouts;

import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.spencerwebsitedesign.fragmentlayouts.CorvetteInfo;

/**
 * Created by cspencer1 on 3/1/2016.
 */
public class TitlesFragment extends ListFragment
{
    // boolean to determine whether in portrait mode or landscape mode
    boolean mDuelPane;

    // Currently selected item in the ListView
    int mCurCheckPosition = 0;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Bind the list view with the data - create an array adapter
        // An ArrayAdapter connects the array to our ListView
        // getActivity() returns a Context so we have the resources needed
        // We pass a default list item text view to put the data in and the array
        ArrayAdapter<String> connectArrayToListView = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_activated_1,
                CorvetteInfo.NAMES);

        // Connect the ListView to our data
        setListAdapter(connectArrayToListView);

        // Check to see if the details view exists
        // The id "details" is in layout-land/fragement_layout.xml within the FrameLayout
        View detailsFrame = getActivity().findViewById(R.id.details);

        // Set this, depending on whether in horiz or vert mode
        // True: Horizontal/Landscape Mode
        // False: Vertical/ Portrait Mode
        mDuelPane = detailsFrame != null && detailsFrame.getVisibility() == View.VISIBLE;


        // This method keeps the key value pairs stored, even if things are exited or moved
        // Save whatever value (which Corvette) was recently selected by the user
        // curChoice means cursor choice, what was selected
        // If the screen is rotated onSaveInstanceState() below will store the
        // hero most recently selected.
        // Get the value attached to curChoice and store it in mCurCheckPosition
        if (savedInstanceState != null)
        {
            // Restore last state for checked position.
            mCurCheckPosition = savedInstanceState.getInt("curChoice", 0);
        }

        // Horizontal Mode
        if (mDuelPane)
        {
            // Single means only one thing can be selected
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);

            // Stores the index in the array/list where the cursor was pointed
            showDetails(mCurCheckPosition);
        }
    }

    // This method is for storing info in case apps crash or the phone has to close resources
    // This is called automatically by Android, anytime the screen position changes
    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);

        outState.putInt("curChoice", mCurCheckPosition);
    }

    // Change Corvette history data
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        showDetails(position);
    }

    // index is the most recently clicked on item
    void showDetails(int index)
    {
        mCurCheckPosition = index;

        // If in Landscape Mode
        if(mDuelPane)
        {
            getListView().setItemChecked(index, true);

            DetailsFragment details = (DetailsFragment)getFragmentManager().findFragmentById(R.id.details);

            // If the index hasn't been assigned, then assign it
            if(details == null || details.getShowIndex() != index)
            {
                // Assign the current index to details
                details = DetailsFragment.newInstance(index);

                // We want to perform different transactions with our fragments
                FragmentTransaction ft = getFragmentManager().beginTransaction();

                // This will replace any other fragment with our details fragment
                ft.replace(R.id.details, details);

                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }
        }
        else // Portrait Mode
        {
            // We'll create a details activity and take up the whole screen with that activity
            // Declare what we plan to do
            Intent intent = new Intent();

            intent.setClass(getActivity(), DetailsActivity.class);

            // Pass along the indexes
            intent.putExtra("index", index);

            // And then start displaying that
            startActivity(intent);
        }
    }
}
