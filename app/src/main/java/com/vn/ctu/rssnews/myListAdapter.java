package com.vn.ctu.rssnews;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


public class myListAdapter extends BaseAdapter {

    Context context;
    List<RssItem> list;
    private int lastPosition = -1;

    public myListAdapter(Context context, List<RssItem> list) {
        super();
        this.context = context;
        this.list = list;

    }

    @Override
    public int getCount() {
        if (list != null) {
            return list.size();
        } else {
            return 0;
        }
    }

    @Override
    public RssItem getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = View.inflate(context, R.layout.list_item, null);
            holder = new ViewHolder();
            holder.textView_tieude = (TextView) convertView.findViewById(R.id.textView_tieude);
            holder.textView_xemtruoc = (TextView) convertView.findViewById(R.id.textView_xemtruoc);
            holder.textView_tg = (TextView) convertView.findViewById(R.id.textView_tg);
            holder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        RssItem item = getItem(position);
        holder.textView_tieude.setText(item.getTitle());
        holder.textView_xemtruoc.setText(item.getSummary());
        holder.textView_tg.setText(item.getPubDate());

        if (holder.imageView != null) {
            new ImageDownloaderTask(holder.imageView).execute(item.getImgUrl());
        }


        Animation animation = AnimationUtils.loadAnimation(context, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        convertView.startAnimation(animation);
        lastPosition = position;


        return convertView;
    }

    static class ViewHolder {
        TextView textView_tieude,
                textView_xemtruoc,
                textView_tg;
        ImageView imageView;

    }
}