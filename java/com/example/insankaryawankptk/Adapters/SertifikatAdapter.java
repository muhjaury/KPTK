package com.example.insankaryawankptk.Adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.insankaryawankptk.Models.SertifikatModel;
import com.example.insankaryawankptk.R;

import java.util.ArrayList;

/**
 * Created by muhjaury on 7/17/2019.
 * Email : muhjaury@gmail.com
 */
public class SertifikatAdapter extends RecyclerView.Adapter<SertifikatAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<SertifikatModel> mExampleList;
    private OnItemClickListener mListener;

    public interface  OnItemClickListener {
        void onItemClick (int position);
    }

    public void setOnItemClickListener (OnItemClickListener listener){
        mListener = listener;
    }

    public SertifikatAdapter(Context context, ArrayList<SertifikatModel> exampleList) {
        mContext = context;
        mExampleList = exampleList;
    }

    @NonNull
    @Override
    public SertifikatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.rv_sertifikatview, viewGroup, false);
        return new SertifikatAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        SertifikatModel currentItem = mExampleList.get(i);

        String sertifikatTitle = currentItem.getSertifikatTitle();
        String sertifikatURL = currentItem.getSertifikatURL();

        viewHolder.sertifikatTitle.setText(sertifikatTitle);
        viewHolder.sertifikatURL.setText(sertifikatURL);
    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView sertifikatTitle, sertifikatURL;

        public ViewHolder(View itemView) {
            super(itemView);
            sertifikatTitle = itemView.findViewById(R.id.SertifikatTitle);
            sertifikatURL = itemView.findViewById(R.id.SertifikatURL);

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
