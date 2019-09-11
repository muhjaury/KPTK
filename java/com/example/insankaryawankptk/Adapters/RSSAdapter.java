package com.example.insankaryawankptk.Adapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.insankaryawankptk.Models.RSSModel;
import com.example.insankaryawankptk.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by muhjaury on 6/26/2019
 * Email : muhjaury@gmail.com
 **/

public class RSSAdapter extends RecyclerView.Adapter<RSSAdapter.ViewHolder> {
    private Context context;
    private ArrayList<RSSModel> mExampleList;
    private OnItemClickListener mListener;

    public interface  OnItemClickListener {
        void onItemClick (int position);
    }

    public void setOnItemClickListener (OnItemClickListener listener){
        mListener = listener;
    }

    public RSSAdapter(Context context, ArrayList<RSSModel> exampleList) {
        this.context = context;
        mExampleList = exampleList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.rv_rssview, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        RSSModel currentItem = mExampleList.get(position);

        String imageView = currentItem.getImageView();
        String txtTitle = currentItem.getTxtTitle();
        String txtDesc = currentItem.getTxtDesc();

        holder.mtxtTitle.setText(txtTitle);
        holder.mtxtDesc.setText(txtDesc);
        Picasso.get().load(imageView).fit().centerInside().into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mtxtTitle, mtxtDesc;
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            mtxtTitle = itemView.findViewById(R.id.txtTitle);
            mtxtDesc = itemView.findViewById(R.id.txtDesc);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
