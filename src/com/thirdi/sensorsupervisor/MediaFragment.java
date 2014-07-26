package com.thirdi.sensorsupervisor;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MediaFragment extends Fragment {
	/**
	 * Media fragment that let's you open either microphone or camera.
	 * 
	 * mCameraButton : Button to open the camera.
	 * mAudioButton : Button to open the microphone.
	 * mFragmentManager : FragmentManager to be able to begin fragment transactions.
	 * mFragmentTransaction : FragmentTransaction to change the current fragment with camera or audio fragments.
	 * 
	 */
	
	Button mCameraButton;
    Button mAudioButton;
    AudioFragment mAudioFragment;
    CameraFragment mCameraFragment;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    
    public MediaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Initialize member variables.
        mAudioFragment = new AudioFragment();
        mCameraFragment = new CameraFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Inflate view.
        return inflater.inflate(R.layout.fragment_media, container, false);
    }


    @Override
    public void onStart() {
        super.onStart();
        //Initialize member variables.
        mFragmentManager=getFragmentManager();
        mCameraButton =(Button)getView().findViewById(R.id.button2);
        mAudioButton =(Button)getView().findViewById(R.id.button);
        //Set a click listener to camera button to bring camera fragment when needed.
        mCameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //This method is invoked whenever you click the button.
                
                //Change current fragment with the camera fragment.
            	mFragmentTransaction = mFragmentManager.beginTransaction();
            	mFragmentTransaction.replace(R.id.container, mCameraFragment);
            	mFragmentTransaction.commit();
            }
        });
        //Set a click listener to audio button to bring microphone fragment when needed.
        mAudioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //This method is invoked whenever you click the button.
                
                //Change current fragment with the audio fragment.
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.container,mAudioFragment);
                mFragmentTransaction.commit();
            }
        });

    }
}
