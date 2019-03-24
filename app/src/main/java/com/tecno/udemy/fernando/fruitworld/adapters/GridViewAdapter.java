package com.tecno.udemy.fernando.fruitworld.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tecno.udemy.fernando.fruitworld.R;
import com.tecno.udemy.fernando.fruitworld.models.Fruit;

import java.util.ArrayList;

public class GridViewAdapter extends BaseAdapter {
    private ArrayList<Fruit> fruits;
    private Context context;


    public GridViewAdapter(Context context, ArrayList<Fruit> fruits) {
        this.fruits = fruits;
        this.context = context;
    }

    @Override
    public int getCount() {
        return fruits.size();
    }

    @Override
    public Object getItem(int position) {
        return fruits.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView =inflater.inflate(R.layout.grid_item, null);
            holder = new ViewHolder();
            holder.imageIcon = convertView.findViewById(R.id.imageView_icon);
            holder.txtName = convertView.findViewById(R.id.textview_nombre);
            holder.txtOrigin = convertView.findViewById(R.id.textview_origen);
            convertView.setTag(holder);
        }else
            holder = (ViewHolder) convertView.getTag();

        Fruit theFruit = fruits.get(position);

        holder.imageIcon.setImageResource(theFruit.getIcon());
        holder.txtName.setText(theFruit.getName());
        holder.txtOrigin.setText(theFruit.getOrigin());

        return convertView;
    }

    private static class ViewHolder{
        ImageView imageIcon;
        TextView txtName;
        TextView txtOrigin;
    }
}
