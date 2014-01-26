package com.jml.example.app.adaptor;

/**
 * Created by jose on 25/01/2014.
 *
 * @author jose
 * @version 0.1.0
 * @since 1
 */

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.jml.example.app.R;
import com.jml.example.app.data.ItemRow;

import java.util.List;

import util.AndroidVolleySingleton;

public class ExampleListAdapter extends ArrayAdapter<ItemRow> {

    private final Activity context;

    static class ViewHolder {
        public TextView text1;
        public NetworkImageView image;

    }


    public ExampleListAdapter(Activity context, List<ItemRow> itemRows) {
        super(context, R.layout.layout_row, itemRows);
        this.context = context;

    }


    @Override public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(R.layout.layout_row, null);
            viewHolder = new ViewHolder();
            viewHolder.text1 = (TextView) convertView.findViewById(R.id.rowText);
            viewHolder.image = (NetworkImageView) convertView.findViewById(R.id.rowImage);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ItemRow item = getItem(position);

        if (item != null) {

            ImageLoader mImageLoader = AndroidVolleySingleton.getInstance(getContext()).getImageLoader();

            viewHolder.text1.setText(item.getText());

            viewHolder.image.setImageUrl(item.getUrl(), mImageLoader);
        }

        return convertView;
    }
}
