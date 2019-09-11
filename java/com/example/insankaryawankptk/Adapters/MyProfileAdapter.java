package com.example.insankaryawankptk.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.insankaryawankptk.Models.MyProfileModel;
import com.example.insankaryawankptk.R;

import java.util.ArrayList;

/**
 * Created by muhjaury on 8/7/2019.
 * Email : muhjaury@gmail.com
 */
public class MyProfileAdapter extends RecyclerView.Adapter<MyProfileAdapter.ViewHolder>{

    private Context mContext;
    private ArrayList<MyProfileModel> mExampleList;

    public MyProfileAdapter(Context context, ArrayList<MyProfileModel> exampleList) {
        mContext = context;
        mExampleList = exampleList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rv_myprofile, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        MyProfileModel currentItem = mExampleList.get(i);

        String txtNama = currentItem.getNama();
        String txtTipe = currentItem.getJenis_user();

        viewHolder.nama.setText(txtNama);
        viewHolder.tipe.setText(txtTipe);
    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nama, tipe;

        public ViewHolder(View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.myprofile_name);
            tipe = itemView.findViewById(R.id.myprofile_type);
        }
    }
}
