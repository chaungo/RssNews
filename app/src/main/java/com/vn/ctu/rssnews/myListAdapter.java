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
        return list.size();
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
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.list_item, null);
            new ViewHolder(convertView);
        }


        ViewHolder holder = (ViewHolder) convertView.getTag();

        RssItem item = getItem(position);

        holder.textView_tieude.setText(item.getTitle() + "");
        holder.textView_xemtruoc.setText(item.getSummary() + "");
        holder.textView_tg.setText(item.getPubDate() + "");

        if(item.getImage()==null){
            holder.imageView.setVisibility(View.GONE);
        }else {
            holder.imageView.setImageBitmap(item.getImage());
        }

        Animation animation = AnimationUtils.loadAnimation(context, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        convertView.startAnimation(animation);
        lastPosition = position;


        return convertView;
    }

    class ViewHolder {
        TextView textView_tieude,
                textView_xemtruoc,
                textView_tg;

        ImageView imageView;

        public ViewHolder(View view) {

            textView_tieude = (TextView) view.findViewById(R.id.textView_tieude);
            textView_xemtruoc = (TextView) view.findViewById(R.id.textView_xemtruoc);
            textView_tg = (TextView) view.findViewById(R.id.textView_tg);
            imageView = (ImageView) view.findViewById(R.id.imageView);


            view.setTag(this);
        }
    }
}