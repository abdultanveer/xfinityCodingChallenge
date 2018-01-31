package com.xfinity.ui.list;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.xfinity.BuildConfig;
import com.xfinity.ui.list.adapters.TopicsListAdapter;
import com.xfinity.model.Icon;
import com.xfinity.model.RelatedTopicsItem;
import com.xfinity.AppController;
import com.xfinity.util.ConnectivityReceiver;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.xfinity.util.Constants.SIMPSONS_URL;
import static com.xfinity.util.Constants.WIRE_URL;

/**
 * Created by Ansari on 1/31/2018.
 */

public class TopicsPresenter implements  ITopicsPresenter{

    public static String TAG = TopicsPresenter.class.getSimpleName();
    ITopicsView iTopicsView;
    private JsonObjectRequest totalResponse;
    private List<RelatedTopicsItem> mRelatedTopicsItemList;
    private TopicsListAdapter mListAdapter;




    public TopicsPresenter(TopicsListFragment topicsListFragment){
        mRelatedTopicsItemList = new ArrayList<>();
        iTopicsView = topicsListFragment;
    }

    @Override
    public void parseJsonResponse(JSONObject response) throws JSONException {
        JSONArray relatedTopics = response.getJSONArray("RelatedTopics");
        for(int i = 0;i<relatedTopics.length();i++){
            JSONObject item = relatedTopics.getJSONObject(i);
            JSONObject icon2 = item.getJSONObject("Icon");
            String url = icon2.getString("URL");
            Icon icon = new Icon(url);
            String text = item.getString("Text");
            RelatedTopicsItem relatedTopicsItem = new RelatedTopicsItem(text,icon);
            mRelatedTopicsItemList.add(relatedTopicsItem);
        }
        iTopicsView.setRecyclerViewAdapter(mRelatedTopicsItemList);
    }

    @Override
    public void getData() {
        if(BuildConfig.FLAVOR.equals("simpsons_flavor")) {
            // loadSimpsonsData();
            loadData(SIMPSONS_URL);
        }
        else if(BuildConfig.FLAVOR.equals("wire_flavor")) {
            //loadWireData();
            loadData(WIRE_URL);

        }
        if(AppController.getInstance()!= null)
            AppController.getInstance().addToRequestQueue(totalResponse);
    }

    private void loadData(String url){
        {
            // Checking if internet connection exists or not
            checkConnection();
            totalResponse  = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    // Log.d(TAG,response.toString());
                    try {
                        parseJsonResponse(response);
                    }
                    catch (Exception e) {
                        System.out.print(e);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG,"Volley error response");
                }
            });
        }
    }

    private void checkConnection() {
        boolean isConnected  =  ConnectivityReceiver.isConnected();
        iTopicsView.showToastMessage(isConnected);
        //showToastMessage(isConnected);
    }
}
