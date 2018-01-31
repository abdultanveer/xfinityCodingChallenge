package com.xfinity.ui.list;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ansari on 1/31/2018.
 */

public interface ITopicsPresenter {

    public void parseJsonResponse(JSONObject response) throws JSONException ;

    public void getData();


}
