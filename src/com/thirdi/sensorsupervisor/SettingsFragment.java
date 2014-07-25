package com.thirdi.sensorsupervisor;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.Switch;

public class SettingsFragment extends Fragment {
	private Spinner mDataSpinner;
	private Switch mDataSwitch;
	private Switch mCameraSwitch;
	
	public SettingsFragment() {
		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_setting, container, false);
		mDataSpinner = (Spinner) view.findViewById(R.id.data_spinner);
		mDataSwitch = (Switch) view.findViewById(R.id.data_switch);
		mCameraSwitch = (Switch) view.findViewById(R.id.camera_switch);
		mCameraSwitch.setChecked(CameraPreview.SHOW_CAMERA);
		mDataSwitch.setChecked(SensorFragment.SHOW_DATA);
		
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(view.getContext(),
				R.array.data_array, android.R.layout.simple_spinner_dropdown_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mDataSpinner.setAdapter(adapter);
		mDataSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				switch (position) {
					case 1:
						SensorFragment.SAVE_LOCATION = "Database";
						break;
					case 2:
						SensorFragment.SAVE_LOCATION = "File";
						break;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				
			}
		});
		
		mDataSwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				SensorFragment.SHOW_DATA = isChecked;
			}
		});
		
		mCameraSwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				CameraPreview.SHOW_CAMERA = isChecked;
			}
		});
		
		return view;
	}
}
