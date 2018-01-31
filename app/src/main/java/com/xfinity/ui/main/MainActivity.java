package com.xfinity.ui.main;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.xfinity.R;
import com.xfinity.ui.list.adapters.TopicsListAdapter;
import com.xfinity.model.Topic;
import com.xfinity.ui.details.DetailsFragment;
import com.xfinity.ui.list.TopicsListFragment;

import static com.xfinity.util.Constants.DETAILS_FRAGMENT;
import static com.xfinity.util.Constants.LIST_FRAGMENT;

public class MainActivity extends AppCompatActivity  implements IMainView,
        TopicsListAdapter.TopicsClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private FragmentManager mFragmentManager;
    IMainPresenter iMainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iMainPresenter = new MainPresenter(this);
        if (findViewById(R.id.container) != null) {
            mFragmentManager = getSupportFragmentManager();
            mFragmentManager.beginTransaction()
                    .replace(R.id.container, new TopicsListFragment(), LIST_FRAGMENT)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.toggle_view:
                iMainPresenter.onMenuItemToggleSelected();
               /* TopicsListFragment topicsListFragment =((TopicsListFragment)mFragmentManager.findFragmentByTag(LIST_FRAGMENT));
                //boolean isSwitched= mTopicsListAdapter.toggleItemViewType();
                boolean isSwitched= topicsListFragment.getListAdapter().toggleItemViewType();
                //mRecyclerView.setLayoutManager(isSwitched?new LinearLayoutManager(this):new GridLayoutManager(this,2));
                topicsListFragment.getRecyclerView().setLayoutManager(isSwitched?new LinearLayoutManager(this):new GridLayoutManager(this,2));
                //mRecyclerView.setLayoutManager(isSwitched?new LinearLayoutManager(this):new GridLayoutManager(this,2));
                topicsListFragment.getListAdapter().notifyDataSetChanged();
                //mTopicsListAdapter.notifyDataSetChanged();*/
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTopicItemListener(Topic topic) {

        DetailsFragment detailsFragment = (DetailsFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_detail);
        if (detailsFragment != null) {
            detailsFragment.updateDetails(topic);

        } else {
            DetailsFragment dynamicDetailsFragment = new DetailsFragment();
            Bundle bundle = new Bundle();
            bundle.putString(Topic.TITLE, topic.getTitle());
            bundle.putString(Topic.DESCRIPTION, topic.getDescription());
            bundle.putString(Topic.IMAGE_URL, topic.getImageUrl());
            dynamicDetailsFragment.setArguments(bundle);
            Log.i(TAG, bundle.toString());
            mFragmentManager.beginTransaction()
                    .replace(R.id.container, dynamicDetailsFragment, DETAILS_FRAGMENT)
                    .addToBackStack(DETAILS_FRAGMENT)
                    .commit();

        }

    }

    @Override
    public void toggleList() {
        TopicsListFragment topicsListFragment = ((TopicsListFragment) mFragmentManager.findFragmentByTag(LIST_FRAGMENT));
        //boolean isSwitched= mTopicsListAdapter.toggleItemViewType();
        boolean isSwitched = topicsListFragment.getListAdapter().toggleItemViewType();
        //mRecyclerView.setLayoutManager(isSwitched?new LinearLayoutManager(this):new GridLayoutManager(this,2));
        topicsListFragment.getRecyclerView().setLayoutManager(isSwitched ? new LinearLayoutManager(this) : new GridLayoutManager(this, 2));
        //mRecyclerView.setLayoutManager(isSwitched?new LinearLayoutManager(this):new GridLayoutManager(this,2));
        topicsListFragment.getListAdapter().notifyDataSetChanged();
        //mTopicsListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onTopicSelected(Topic topic) {
        DetailsFragment detailsFragment = (DetailsFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_detail);
        if (detailsFragment != null) {
            // detailsFragment.updateDetails(title,description,image);
            detailsFragment.updateDetails(topic);

        } else {
            DetailsFragment dynamicDetailsFragment = new DetailsFragment();
            Bundle bundle = new Bundle();
            bundle.putString(Topic.TITLE, topic.getTitle());
            bundle.putString(Topic.DESCRIPTION, topic.getDescription());
            bundle.putString(Topic.IMAGE_URL, topic.getImageUrl());
            dynamicDetailsFragment.setArguments(bundle);
            Log.i(TAG, bundle.toString());
            mFragmentManager.beginTransaction()
                    .replace(R.id.container, dynamicDetailsFragment, DETAILS_FRAGMENT)
                    .addToBackStack(DETAILS_FRAGMENT)
                    .commit();

        }
    }
}
