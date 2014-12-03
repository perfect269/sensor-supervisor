package com.thirdi.sensorsupervisor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Fragment;
import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.PictureCallback;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
//TODO: Clean, comment, add a way to control FPS and create videos.
public class CameraFragment extends Fragment {
	public static int RECORDER_FPS = 24; 
	private Camera mCamera;
    private CameraPreview mCameraPreview;
    private Button mPictureButton, mVideoButton;
    private MediaRecorder recorder;
    private final static String TAG = "Camera";
    private PictureCallback mPicture = new PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {

            File pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE);
            if (pictureFile == null){
                Log.d(TAG, "Error creating media file, check storage permissions.");
                return;
            }

            try {
                FileOutputStream fos = new FileOutputStream(pictureFile);
                fos.write(data);
                fos.close();
            } catch (FileNotFoundException e) {
                Log.d(TAG, "File not found: " + e.getMessage());
            } catch (IOException e) {
                Log.d(TAG, "Error accessing file: " + e.getMessage());
            }
        }
    };
    
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    public CameraFragment() {
    	
	}
    
    @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCamera = getCameraInstance();
        mCamera.setDisplayOrientation(90);
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
        mPictureButton = (Button) getView().findViewById(R.id.picture_button);
        mVideoButton = (Button) getView().findViewById(R.id.video_button);
        
        mPictureButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mCamera.autoFocus(new AutoFocusCallback() {
					
					@Override
					public void onAutoFocus(boolean arg0, Camera arg1) {
						// TODO Auto-generated method stub
						
					}
				});
				mCamera.takePicture(null, null, mPicture);
			}
		});
        
        mVideoButton.setOnClickListener(new OnClickListener() {
			
        	boolean is_recording = false;
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (!is_recording) {
					mVideoButton.setText("RECORDING");
					try {
						mCamera.setPreviewDisplay(null);
					} catch (java.io.IOException ioe) {
						Log.d(TAG, "IOException nullifying preview display: " + ioe.getMessage());
					}
					mCamera.stopPreview();
					mCamera.unlock();
					recorder = new MediaRecorder();
					recorder.setCamera(mCamera);
					recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
					recorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
					recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
					recorder.setVideoSize(640, 480);
					//recorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH));
					recorder.setVideoFrameRate(RECORDER_FPS);
					recorder.setOrientationHint(90);
					recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
					recorder.setVideoEncoder(MediaRecorder.VideoEncoder.MPEG_4_SP);
					recorder.setPreviewDisplay(mCameraPreview.getSurface());
					recorder.setOutputFile(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES).getAbsolutePath() + "/" + System.currentTimeMillis() + ".mp4");
					try {
						recorder.prepare();
						recorder.start();
						is_recording = true;
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					//recorder.stop();
					recorder.reset();
					recorder.release();
					mVideoButton.setText("Video");
					is_recording = false;
				}
			}
		});
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
    
    @Override
    public void onDestroy() {
    	super.onDestroy();
    	mCamera.release();
    }

    /** Create a file Uri for saving an image or video */
    private static Uri getOutputMediaFileUri(int type){
          return Uri.fromFile(getOutputMediaFile(type));
    }

    /** Create a File for saving an image or video */
    private static File getOutputMediaFile(int type){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                  Environment.DIRECTORY_PICTURES), "SensorSupervisor");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d(TAG, "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
            "IMG_"+ timeStamp + ".jpg");
        } else if(type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
            "VID_"+ timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }
    
}
