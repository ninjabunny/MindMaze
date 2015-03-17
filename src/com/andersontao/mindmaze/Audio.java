package com.andersontao.mindmaze;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;

public class Audio {
	private static Audio instance;
	private Context context;

	private Audio(Context context) {
		this.context = context;
	}

	public void playJump() {
		MediaPlayer mp = MediaPlayer.create(context.getApplicationContext(),
				R.raw.jump);
		mp.start();
		mp.setOnCompletionListener(new OnCompletionListener() {
			public void onCompletion(MediaPlayer mp) {
				mp.release();
			};
		});
	}

	public static Audio getInstance(Context context) {
		if (instance == null) {
			instance = new Audio(context);
		}
		return instance;
	}
}