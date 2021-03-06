package com.xfinity.ui.list.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xfinity.R;
import com.xfinity.model.RelatedTopicsItem;
import com.xfinity.model.Topic;

import java.util.List;

import static com.xfinity.util.Constants.GRID_ITEM;
import static com.xfinity.util.Constants.LIST_ITEM;

/**
 * Created by rashmi on 1/30/2018.
 */
public class TopicsListAdapter extends RecyclerView.Adapter<TopicsListAdapter.CustomViewHolder>{

    private List<RelatedTopicsItem> mRelatedTopicsItemList;
    private Context mContext;
    private boolean isSwitchView = true;
    private String title,description;
    private TopicsClickListener mTopicsClickListener;

    //Custom interface to pass data between the fragments.
    public interface TopicsClickListener{
        void onTopicItemListener(Topic topic);
    }

    public TopicsListAdapter(Context context, List<RelatedTopicsItem> relatedTopicsItemList) {
        mContext=context;
        mRelatedTopicsItemList = relatedTopicsItemList;
        mTopicsClickListener=(TopicsClickListener)context;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        public TextView titleView,descriptionView;
        public ImageView mImageView;
        public CustomViewHolder(View itemView) {
            super(itemView);
            //titleView=itemView.findViewById(R.id.text_title);
            descriptionView=itemView.findViewById(R.id.text_description);
            mImageView=itemView.findViewById(R.id.image_simpsons_list);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RelatedTopicsItem relatedTopicsItem=mRelatedTopicsItemList.get(getAdapterPosition());
                    String[] titleText=relatedTopicsItem.getText().split("-");
                    title=titleText[0];
                    description=titleText[1];
                    Topic topic = new Topic(title,description,relatedTopicsItem.getIcon().getURL());
                    //mTopicsClickListener.onTopicItemListener(title,description,relatedTopicsItem.getIcon().getURL());
                    mTopicsClickListener.onTopicItemListener(topic);

                }
            });
        }
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if(viewType==LIST_ITEM){
            view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        }
        else {
            view= LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item,parent,false);
        }
        // View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.simpsons_list_item,parent,false);
        return new CustomViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        if(isSwitchView)
            return LIST_ITEM;
        else
            return GRID_ITEM;
    }

    public boolean toggleItemViewType () {
        isSwitchView = !isSwitchView;
        return isSwitchView;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        RelatedTopicsItem relatedTopicsItem=mRelatedTopicsItemList.get(position);

        getTitleAndDescription(relatedTopicsItem);

        holder.descriptionView.setText(title);
        if(relatedTopicsItem.getIcon().getURL()==null || relatedTopicsItem.getIcon().getURL().isEmpty()){
            holder.mImageView.setImageResource(R.mipmap.ic_launcher);
        }else {
            Picasso.with(mContext)
                    .load(relatedTopicsItem.getIcon().getURL())
                    .into(holder.mImageView);
        }
    }

    private void getTitleAndDescription(RelatedTopicsItem relatedTopicsItem) {
        String[] titleText=relatedTopicsItem.getText().split("-");
        title=titleText[0];
        description=titleText[1];
    }

    @Override
    public int getItemCount() {
        return mRelatedTopicsItemList.size();
    }

}
