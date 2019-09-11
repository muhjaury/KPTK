package com.example.insankaryawankptk.Adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.insankaryawankptk.Models.DiklatModel;
import com.example.insankaryawankptk.R;

import java.util.ArrayList;

/**
 * Created by muhjaury on 7/16/2019.
 * Email : muhjaury@gmail.com
 */
public class DiklatAdapter extends RecyclerView.Adapter<DiklatAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<DiklatModel> mExampleList;

    public DiklatAdapter(Context context, ArrayList<DiklatModel> exampleList) {
        mContext = context;
        mExampleList = exampleList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.rv_diklatview, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        DiklatModel currentItem = mExampleList.get(i);

        String txtTitle = currentItem.getDiklatTitle();
        String txtTempat = currentItem.getDiklatTempat();
        String txtDesc = currentItem.getDiklatDesc();
        String txtStart = currentItem.getDiklatStart();
        String txtEnd = currentItem.getDiklatEnd();
        String txtOrganizer = currentItem.getDiklatOrganizer();

        viewHolder.mtxtTitle.setText(txtTitle);
        viewHolder.mtxtTempat.setText(txtTempat);
        viewHolder.mtxtDesc.setText(txtDesc);
        viewHolder.mtxtStart.setText(txtStart);
        viewHolder.mtxtEnd.setText(txtEnd);
        viewHolder.mtxtOrganizer.setText(txtOrganizer);


    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mtxtTitle, mtxtTempat, mtxtDesc, mtxtStart, mtxtEnd, mtxtOrganizer;

        public ViewHolder(View itemView) {
            super(itemView);
            mtxtTitle = itemView.findViewById(R.id.DiklatTitle);
            mtxtTempat = itemView.findViewById(R.id.DiklatTempat);
            mtxtDesc = itemView.findViewById(R.id.DiklatDesc);
            mtxtStart = itemView.findViewById(R.id.DiklatStart);
            mtxtEnd = itemView.findViewById(R.id.DiklatEnd);
            mtxtOrganizer = itemView.findViewById(R.id.DiklatOrganizer);
        }
    }

}
