package com.xfinity.ui.details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xfinity.R;

/**
 * Created by Ansari on 2/1/2018.
 */

public class DetailsPresenter implements IDetailsPresenter {

    IDetailsView iDetailsView;

    public DetailsPresenter(DetailsFragment detailsFragment) {
         iDetailsView = detailsFragment;
    }

    @Override
    public void viewReady(Bundle arguments) {
        iDetailsView.loadDetailsFragment(arguments);
    }

}
