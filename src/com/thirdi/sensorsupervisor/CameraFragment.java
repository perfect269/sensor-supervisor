package com.thirdi.sensorsupervisor;

import android.app.Fragment;
import android.content.Context;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class CameraFragment extends Fragment {
	private Camera mCamera;
    private CameraPreview mCameraPreview;

    public CameraFragment() {
    	
	}
    
    @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCamera = getCameraInstance();
        mCameraPreview = new CameraPreview(getActivity(), mCamera);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    		Bundle savedInstanceState) {
    	return inflater.inflate(R.layout.fragment_camera, container, false);
    }
    
    @Override
    public void onStart() {
    	super.onStart();
        FrameLayout preview = (FrameLayout) getView().findViewById(R.id.surface);
        preview.addView(mCameraPreview);
    }

    private boolean checkCameraHardware(Context context) {
        //TODO: Add camera hardware check.
    	return true;
    }

    public static Camera getCameraInstance() {
        Camera camera = null;
        try {
            camera = Camera.open();
        }
        catch (Exception e){
            Log.e("CameraExample", e.toString());
        }
        return camera;
    }
}
