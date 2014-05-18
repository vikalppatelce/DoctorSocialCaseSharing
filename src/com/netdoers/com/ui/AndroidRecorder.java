/* HISTORY
 * CATEGORY 		:- ACTIVITY
 * DEVELOPER		:- VIKALP PATEL
 * AIM			    :- ADD IPD ACTIVITY
 * DESCRIPTION 		:- SAVE IPD
 * 
 * S - START E- END  C- COMMENTED  U -EDITED A -ADDED
 * --------------------------------------------------------------------------------------------------------------------
 * INDEX       DEVELOPER		DATE			FUNCTION		DESCRIPTION
 * --------------------------------------------------------------------------------------------------------------------
 * --------------------------------------------------------------------------------------------------------------------
 */
package com.netdoers.com.ui;

import java.io.File;
import java.io.IOException;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;

public class AndroidRecorder {

	String recordingPath;
	Context context;

	public String getRecordingPath() {
		return recordingPath;
	}

	public void setRecordingPath(String recordingPath) {
		this.recordingPath = recordingPath;
	}

	MediaRecorder recorder = null;

	public AndroidRecorder(Context context) {
		recorder = new MediaRecorder();
		this.context = context;
	}

	private String createRecorderPath() {
		if (this.recordingPath != null && !this.recordingPath.startsWith("/")) {
			this.recordingPath = "/" + this.recordingPath;
		}

		if (this.recordingPath != null && !this.recordingPath.contains(".")) {
			this.recordingPath += ".mp4";
		}

		return Environment.getExternalStorageDirectory().getAbsolutePath()
				+ "/SmartConsultant" + this.recordingPath;
	}

	public void startRecording(String recordingPath) throws IOException {
		this.recordingPath = recordingPath;
		this.recordingPath = createRecorderPath();
		String state = Environment.getExternalStorageState();
		if (!state.equals(Environment.MEDIA_MOUNTED)) {
			throw new IOException("SD Card is not mounted.  It is " + state	+ ".");
		}

		// make sure the directory we plan to store the recording in exists
		File directory = new File(this.recordingPath).getParentFile();
		if (!directory.exists() && !directory.mkdirs()) {
			throw new IOException("Path to file could not be created.");
		}
		//playSound();
		recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		/*recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
		recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);*/
		
		if (Build.VERSION.SDK_INT >= 10) {
		    recorder.setAudioSamplingRate(44100);
		    recorder.setAudioEncodingBitRate(96000);
		    recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
		    recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
		} else {
		    // older version of Android, use crappy sounding voice codec
		    recorder.setAudioSamplingRate(8000);
		    recorder.setAudioEncodingBitRate(12200);
		    recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		    recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		}

		recorder.setOutputFile(this.recordingPath);
		recorder.prepare();
		recorder.start();
	}
	
	
	public void playSound() throws IllegalArgumentException, SecurityException, IllegalStateException,IOException {
	    Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
	    MediaPlayer mMediaPlayer = new MediaPlayer();
	    mMediaPlayer.setDataSource(context, soundUri);
	    final AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
	    if (audioManager.getStreamVolume(AudioManager.STREAM_ALARM) != 0) {
	        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
	        mMediaPlayer.setLooping(false);
	        mMediaPlayer.prepare();
	        mMediaPlayer.start();
	    }
	}
	
	public void stopRecording() {
		recorder.stop();
		recorder.release();
	}
}