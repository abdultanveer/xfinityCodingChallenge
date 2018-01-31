package com.xfinity.ui.list;

import com.xfinity.model.RelatedTopicsItem;

import java.util.List;

/**
 * Created by Ansari on 1/31/2018.
 */

public interface ITopicsView {
    public void showToastMessage(boolean isConnected);
    public void setRecyclerViewAdapter(List<RelatedTopicsItem> mRelatedTopicsItemList);
}
