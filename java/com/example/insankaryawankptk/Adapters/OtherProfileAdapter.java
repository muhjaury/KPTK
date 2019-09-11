package com.example.insankaryawankptk.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.insankaryawankptk.Models.OtherProfileModel;
import com.example.insankaryawankptk.R;

import java.util.ArrayList;

/**
 * Created by muhjaury on 8/7/2019.
 * Email : muhjaury@gmail.com
 */

public class OtherProfileAdapter extends RecyclerView.Adapter<OtherProfileAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<OtherProfileModel> mExampleList;
    private OnItemClickListener mListener;

    public interface  OnItemClickListener {
        void onItemClick (int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public OtherProfileAdapter(Context context, ArrayList<OtherProfileModel> exampleList) {
        mContext = context;
        mExampleList = exampleList;
    }

    @NonNull
    @Override
    public OtherProfileAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_otherprofile, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OtherProfileAdapter.ViewHolder holder, int position) {
        OtherProfileModel currentItem = mExampleList.get(position);

        String Nama = currentItem.getNama();
        String Id = currentItem.getId();

        holder.nama.setText(Nama);
        holder.id.setText(Id);
    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nama, id;

        public ViewHolder(View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.otherprofile_name);
            id = itemView.findViewById(R.id.otherprofile_id);

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
