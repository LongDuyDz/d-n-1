package com.example.project_myvntour.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_myvntour.Database.SelectAll;
import com.example.project_myvntour.Mode.KhachSan;
import com.example.project_myvntour.Mode.LoaiKhachSanj;
import com.example.project_myvntour.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterLoaiKhachSanj extends RecyclerView.Adapter<AdapterLoaiKhachSanj.ViewHolder> {
    private List<LoaiKhachSanj> list;
    private Context mContext;
    private int row_index = -1;
    private boolean selected = true;
    private boolean check = true;
    private UpdateRecyclerView mUpdateRecyclerView;
   private List<KhachSan> listKhachSan;
   private SelectAll mSelectAll;
    public interface UpdateRecyclerView {
        public void callbacksChanged(int position , List<KhachSan> list );
        public void callbacksChanged2(int position , List<KhachSan> list );
    }

    public AdapterLoaiKhachSanj (Context context , List<LoaiKhachSanj> list ,UpdateRecyclerView mUpdateRecyclerView ){
        this.mContext = context;
        this.list = list;
        this.mUpdateRecyclerView = mUpdateRecyclerView;
        mSelectAll = new SelectAll(context);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {

       View view =LayoutInflater.from(parent.getContext()).inflate(R.layout.itemloaikhachsan, parent, false);
       return new ViewHolder(view);
    }
    @SuppressLint ("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull  AdapterLoaiKhachSanj.ViewHolder holder, int position) {
        LoaiKhachSanj loai = list.get(position);
        holder.tvTenLoai.setText(loai.getTenLoaiKhachSanj());

        if(check){
            // trueyefn list nhà ở all
            listKhachSan = new ArrayList<>();
            listKhachSan = mSelectAll.getListKhachSanByHotel(1);
            mUpdateRecyclerView.callbacksChanged(position , listKhachSan);
            mUpdateRecyclerView.callbacksChanged2(position , listKhachSan);
            check = false;


        }

        holder.itemView.setOnClickListener(v->{
            row_index =position;
            notifyDataSetChanged();

            if(position == 0){
                // trueyefn list nhà ở all
                listKhachSan = new ArrayList<>();
                listKhachSan = mSelectAll.getListKhachSanByHotel(1);
                mUpdateRecyclerView.callbacksChanged(position , listKhachSan);
                mUpdateRecyclerView.callbacksChanged2(position , listKhachSan);

            }else if(position == 1) {
                listKhachSan = new ArrayList<>();
              listKhachSan = mSelectAll.getListKhachSanByHotel(2);
                mUpdateRecyclerView.callbacksChanged(position , listKhachSan);
                mUpdateRecyclerView.callbacksChanged2(position , listKhachSan);
            }
            else if(position == 2) {
                listKhachSan = new ArrayList<>();
                listKhachSan = mSelectAll.getListKhachSanByHotel(3);
                mUpdateRecyclerView.callbacksChanged(position , listKhachSan);
                mUpdateRecyclerView.callbacksChanged2(position , listKhachSan);
            }
            else if(position == 3) {
                listKhachSan = new ArrayList<>();
                listKhachSan = mSelectAll.getListKhachSanByHotel(4);
                mUpdateRecyclerView.callbacksChanged(position , listKhachSan);
                mUpdateRecyclerView.callbacksChanged2(position , listKhachSan);
            }




        });
        if(selected){
            if(position == 0){
                holder.itemView.setBackgroundResource(R.drawable.gradient2);
                holder.tvTenLoai.setTextColor(Color.parseColor("#FFFFFF"));
            }
            selected = false;
        }else{
            if(row_index==position){
                holder.itemView.setBackgroundResource(R.drawable.gradient2);
                holder.tvTenLoai.setTextColor(Color.parseColor("#FFFFFF"));
            }
            else
            {
                holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                holder.tvTenLoai.setTextColor(Color.parseColor("#858585"));
            }
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTenLoai;
        public ViewHolder(@NonNull  View itemView) {
            super(itemView);


            tvTenLoai = (TextView)itemView. findViewById(R.id.tvTenLoai);

        }
    }
}
