package com.datasoft_iot.tausif.sharedhomev1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.datasoft_iot.tausif.sharedhomev1.R;


public class CustomGrid extends BaseAdapter {
    private Context mContext;
    private final String[] number_0f_sensor;
    private final String[] sensor_name;
    private final int[] ImageId;

    public CustomGrid(Context c, String[] number_0f_sensor, int[] ImageId, String[] sensor_name) {
        mContext = c;
        this.ImageId = ImageId;
        this.number_0f_sensor = number_0f_sensor;
        this.sensor_name = sensor_name;
    }

    public class ViewHolder {
        ImageView img ;
        TextView textViewNumberOfSensor,textViewSensorName;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return number_0f_sensor.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        /*View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            grid = new View(mContext);
            grid = inflater.inflate(R.layout.grid_single, null);
            TextView textView = (TextView) grid.findViewById(R.id.grid_text);
            ImageView imageView = (ImageView) grid.findViewById(R.id.grid_image);
            textView.setText(number_0f_sensor[position]);
            imageView.setImageResource(ImageId[position]);
        } else {
            grid = (View) convertView;
        }

        return grid;*/


        ViewHolder holder;
        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.grid_single, parent, false);
            holder = new ViewHolder();
            holder.img = (ImageView) convertView.findViewById(R.id.grid_image);

           // holder.img2 = (ImageView) convertView.findViewById(R.id.grid_image_status);
            holder.textViewNumberOfSensor = (TextView) convertView.findViewById(R.id.tvApplianceName);
//            holder.textViewSensorName = (TextView) convertView.findViewById(R.id.grid_sensor_name);

            //holder.lb2 = (TextView) convertView.findViewById(R.id.tvApplianceStatus);
            convertView.setTag(holder);
//
//            if (isApartmentAppliance){
//                holder.img2.setVisibility(View.GONE);
//               // holder.lb2.setVisibility(View.GONE);
//            } else {
//                holder.img2.setVisibility(View.VISIBLE);
//                //holder.lb2.setVisibility(View.GONE);
//            }

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ImageView imageView = holder.img;
        TextView textViewSensorName = holder.textViewSensorName;
        TextView textView = holder.textViewNumberOfSensor;

        imageView.setImageResource(ImageId[position]);
        textView.setText(number_0f_sensor[position]);
        textViewSensorName.setText(sensor_name[position]);

        return convertView;

    }
}
