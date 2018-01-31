package com.xfinity.ui.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.toolbox.JsonObjectRequest;
import com.xfinity.R;
import com.xfinity.ui.list.adapters.TopicsListAdapter;
import com.xfinity.model.RelatedTopicsItem;
import com.xfinity.AppController;
import com.xfinity.util.ConnectivityReceiver;

import java.util.List;

/**
 * Created by rashmi on 1/30/2018.
 */

public class TopicsListFragment extends Fragment implements ITopicsView,
        ConnectivityReceiver.ConnectivityReceiverListener {

    private static final String TAG = TopicsListFragment.class.getSimpleName();
    private RecyclerView mRecyclerView;

    private TopicsListAdapter mListAdapter;
    private JsonObjectRequest totalResponse;
    private View view;
    ITopicsPresenter iTopicsPresenter;

    public TopicsListAdapter getListAdapter(){
        return mListAdapter;
    }
    public RecyclerView getRecyclerView(){
        return mRecyclerView;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.list_fragment,container,false);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        iTopicsPresenter = new TopicsPresenter(this);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

       iTopicsPresenter.getData();
    }


    @Override
    public void onResume() {
        super.onResume();
        // register connection status listener
        AppController.getInstance().setConnectivityListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    /**
     * Callback will be triggered when there is change in
     * network connection
     */
    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showToastMessage(isConnected);
    }



    private void checkConnection() {
        boolean isConnected  =  ConnectivityReceiver.isConnected();
        showToastMessage(isConnected);
    }



    @Override
    public void showToastMessage(boolean isConnected) {
        String message = "";
        if (!isConnected){
            message  =  "Sorry! Not connected to internet";
            Toast.makeText(getContext(),message,Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void setRecyclerViewAdapter(List<RelatedTopicsItem> mRelatedTopicsItemList) {
        mListAdapter  = new TopicsListAdapter(this.getContext(),mRelatedTopicsItemList);
        Log.d(TAG+" -list",mRelatedTopicsItemList.toString());
        mRecyclerView.setAdapter(mListAdapter);
        mListAdapter.notifyDataSetChanged();
    }
}
