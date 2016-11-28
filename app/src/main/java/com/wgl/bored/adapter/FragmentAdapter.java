package com.wgl.bored.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.wgl.bored.R;
import com.wgl.bored.main.WebViewActivity;
import com.wgl.bored.main.mainfragment.MainModle;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author Biligle.
 */

public class FragmentAdapter extends RecyclerView.Adapter<FragmentAdapter.ViewHolder> implements OnRecyclerViewItemClickListener {

    private View view;
    private List<MainModle> data = new ArrayList<>();
    private Context context;

    public FragmentAdapter( Context context){
        this.context = context;
    }

    public void addData(List<MainModle> data) {
        this.data = data;
    }

    /**
     * 创建holder
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fragment1,parent,false);
        return new ViewHolder(view, this);
    }

    /**
     * 给holder初始化数据
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.messagetitle.setText(data.get(position).getTitle());
        holder.messagetime.setText(data.get(position).getTime());
        holder.messagecontent.setText(data.get(position).getContent());
        List<String> pictureList = data.get(position).getImageUrl();
        if(null != pictureList && pictureList.size() > 0){
            holder.linearLayout.setVisibility(View.VISIBLE);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            for(int i = 0; i < pictureList.size(); i++){
                ImageView imageView = new ImageView(context);
                imageView.setLayoutParams(params);
                holder.linearLayout.addView(imageView);
                setGlideInfo(pictureList.get(i),imageView);
            }
        }else{
            holder.linearLayout.setVisibility(View.GONE);
        }
        view.setTag(position);
    }

    /**
     * 获取视图个数
     * @return
     */
    @Override
    public int getItemCount() {
        return data.size();
    }

    /**
     * 实现监听接口
     * @param view
     * @param mainModle
     */
    @Override
    public void onItemClick(View view, MainModle mainModle) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra("url", mainModle.getUrl());
        context.startActivity(intent);
    }

    /**
     * holder以及item的监听
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView messagetitle, messagetime, messagecontent, messageinfo;
        public LinearLayout linearLayout;

        public ViewHolder(View view, final OnRecyclerViewItemClickListener mlistener) {
            super(view);
            messagetitle = (TextView) view.findViewById(R.id.messagetitle);
            messagetime = (TextView) view.findViewById(R.id.messagetime);
            messagecontent = (TextView) view.findViewById(R.id.messagecontent);
            messageinfo = (TextView) view.findViewById(R.id.messageinfo);
            linearLayout = (LinearLayout) view.findViewById(R.id.linear);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mlistener.onItemClick(v, data.get(ViewHolder.this.getAdapterPosition() - 1));
                }
            });
        }
    }

    public void setGlideInfo(String url, final ImageView imageView) {

        Glide.with(context).load(url).error(R.mipmap.main_1).crossFade().into(new GlideDrawableImageViewTarget(imageView) {
            @Override
            public void onResourceReady(GlideDrawable drawable, GlideAnimation anim) {
                super.onResourceReady(drawable, anim);
                //在这里添加一些图片加载完成的操作
            }

            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                super.onLoadFailed(e, errorDrawable);
                Glide.with(context).load(R.mipmap.main_1).error(R.mipmap.main_1).into(imageView);
            }
        });
    }
}

interface OnRecyclerViewItemClickListener {
    void onItemClick(View view, MainModle mainModle);
}
