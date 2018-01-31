package com.xfinity.ui.details;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xfinity.R;
import com.xfinity.model.Topic;

import static com.xfinity.util.Constants.DEFAULT_DESC;
import static com.xfinity.util.Constants.DEFAULT_TITLE;

/**
 * Created by rashmi on 1/31/2018.
 */

public class DetailsFragment extends Fragment implements IDetailsView {

    private TextView titleView;
    private TextView descriptionView;
    private ImageView imageView;
    IDetailsPresenter iDetailsPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         iDetailsPresenter = new DetailsPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_fragment,container,false);
        titleView = view.findViewById(R.id.details_title);
        descriptionView = view.findViewById(R.id.details_description);
        imageView = view.findViewById(R.id.details_image);
        return view;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        iDetailsPresenter.viewReady(getArguments());
    }




    public void updateDetails(Topic topic) {
        this.titleView.setText(topic.getTitle());
        descriptionView.setText(topic.getDescription());
        if (topic.getImageUrl() !=  null && !topic.getImageUrl().isEmpty()) {
            Picasso.with(getContext())
                    .load(topic.getImageUrl())
                    .into(imageView);
        }
    }

    @Override
    public void loadDetailsFragment(Bundle arguments) {
        if(arguments  ==  null){
            titleView.setText(DEFAULT_TITLE);
            descriptionView.setText(DEFAULT_DESC);
        }
        else {
            titleView.setText(arguments.get(Topic.TITLE).toString());
            descriptionView.setText(arguments.get(Topic.DESCRIPTION).toString());
            String url_image = arguments.get(Topic.IMAGE_URL).toString();
            if(url_image!= null || !url_image.isEmpty()){
                Picasso.with(getContext())
                        .load(url_image)
                        .into(imageView);
            }
        }
    }
}
