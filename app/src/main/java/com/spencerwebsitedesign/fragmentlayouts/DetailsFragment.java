package com.spencerwebsitedesign.fragmentlayouts;

import android.app.Fragment;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * Created by cspencer1 on 3/3/2016.
 */
public class DetailsFragment extends Fragment
{
    public static DetailsFragment newInstance(int index)
    {
        DetailsFragment f = new DetailsFragment();

        // Bundles are used to pass data
        // Our key is index
        Bundle args = new Bundle();

        // Get the index (pointer to last selected Corvette, and keep this one showing the same thing
        args.putInt("index", index);

        // Assign the key value to this fragment
        f.setArguments(args);

        return f;
    }

    // An easy way to get the index we're using
    public int getShownIndex()
    {
        return getArguments().getInt("index", 0);
    }

    // Layout inflater puts a fragment on the screen
    // ViewGroup - what you want to attach the fragment to
    // Bundle - has the keys and the values that we want to pass between activities
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Create a scroll view for Corvette data
        ScrollView scroller = new ScrollView(getActivity());

        TextView text = new TextView(getActivity());

        // Apply padding. TypedValue can hold multiple values. In this case, it will hold
        // dimension data. COMPLEX_UNIT_DIP - is defining the unit type we're going to use
        // DIP - device independent pixels
        // This uses specific unit and specific padding but also takes in the specifics of the
        // device that this is running on
        int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getActivity().getResources().getDisplayMetrics());

        text.setPadding(padding, padding, padding, padding);

        scroller.addView(text);

        text.setText(CorvetteInfo.HISTORY[getShownIndex()]);

        return scroller;
    }
}
