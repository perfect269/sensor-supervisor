package com.thirdi.sensorsupervisor;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class WifiAdapter extends BaseAdapter {
	/**
	 * Adapter for listing available wifi routers.
	 * 
	 * mActivity : Activity that this adapter works on.
	 * mScanResults : List of scan results.
	 * mInflater : LayoutInflater to inflate layouts.
	 */
	
	private final Activity mActivity;
    private final List<ScanResult> mScanResults;
    private final LayoutInflater mInflater;

    public WifiAdapter(Activity activity, List<ScanResult> scanResults) {
        mActivity = activity;
        mScanResults = scanResults;
        mInflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        //This method is being used to retrieve row count of our listview.
        return mScanResults.size();

    }

    @Override
    public Object getItem(int i) {
        //This method is being used to retrieve the Object that will be used to create the i'th row.
        return mScanResults.get(i);
    }

    @Override
    public long getItemId(int i) {
        //This method is being used to retrieve the id of the i'th element.
        //It's rather used to change the way we list item's (e.g. reverse it)
        return i;
    }

    private static class ViewHolder {
        /**
         * A view holder class to save view elements in it.
         * In a listview, Android doesn't create all the rows at once.
         * Rather it creates the ones we see, and as we move puts views that we aren't seeing anymore on
         *     recycle stack. And if a view exists there instead of inflating it again, it just takes it from there.
         * A viewholder helps us to further that idea and stop the system from recreating view variables again and again.
         * At the expense of just a little bit of memory, we save cpu cycles, which is much more important.
         * And by doing that we increase our UI's responsiveness.
         */
        TextView ssid, bssid, capabilities, freq, level, timestamp;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        //Retrive scanResult for this row.
        ScanResult scanResult = mScanResults.get(i);
        ViewHolder holder;

        //If this view wasn't inflated before, inflate it and create it's holder.
        //Otherwise, since we store the holder as view's tag, retrieve it from view.
        if (view == null) {
            holder = new ViewHolder();
            view = mInflater.inflate(R.layout.wifi_list_item, null);
            holder.ssid = (TextView) view.findViewById(R.id.ssidView);
            holder.bssid = (TextView) view.findViewById(R.id.bssidView);
            holder.capabilities = (TextView) view.findViewById(R.id.capabilitiesView);
            holder.freq = (TextView) view.findViewById(R.id.frequencyView);
            holder.level = (TextView) view.findViewById(R.id.levelView);
            holder.timestamp = (TextView) view.findViewById(R.id.timestampView);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        //Link data to view elements.(Take the text and show it.)
        holder.ssid.setText(scanResult.SSID);
        holder.bssid.setText(scanResult.BSSID);
        holder.capabilities.setText(scanResult.capabilities);
        holder.freq.setText(String.valueOf(scanResult.frequency));
        holder.level.setText(String.valueOf(scanResult.level));
        holder.timestamp.setText(String.valueOf(scanResult.timestamp));

        //This row is set, return it to show it.
        return view;
    }

}
