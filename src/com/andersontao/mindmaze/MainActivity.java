package com.andersontao.mindmaze;

import java.util.Random;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {
	MediaPlayer mpz;
	TextView gestureEvent;
	final int STAGE_SIZE = 13;
	// int[][] ss = new int[STAGE_SIZE][STAGE_SIZE];
	int[][] ss = { { -1, -1, -1, -1, -1, -1 }, { -1, 0, 0, 0, -1, -1 },
			{ -1, -3, -1, 0, -1, -1 }, { -1, -1, -1, 0, -1, -1 },
			{ -1, 0, 0, 0, -1, -1 }, { -1, -1, -1, -1, -1, -1 } };
	Random rand = new Random();
	final int STARTING_POSITION_X = rand.nextInt(STAGE_SIZE - 2) + 1;
	final int STARTING_POSITION_Y = rand.nextInt(STAGE_SIZE - 2) + 1;
	int currentPositionX = STARTING_POSITION_X;
	int currentPositionY = STARTING_POSITION_Y;
	int endingPositionX = 2;
	int endingPositionY = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Remove title bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		// Remove notification bar
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}

		// set start position
		currentPositionX = 4;
		currentPositionY = 1;

		endingPositionX = 2;
		endingPositionY = 1;
		printStage(6, ss);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private static void printStage(int STAGE_SIZE, int[][] ss) {
		for (int i = 0; i < STAGE_SIZE; i++) {
			for (int j = 0; j < STAGE_SIZE; j++) {
				System.out.print(ss[i][j] + "\t");
			}
			System.out.println("\n");
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		return gestureDetector.onTouchEvent(event);
	}

	SimpleOnGestureListener simpleOnGestureListener = new SimpleOnGestureListener() {

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			String swipe = "";
			float sensitvity = 100;

			// TODO Auto-generated method stub
			if ((e1.getX() - e2.getX()) > sensitvity) {
				swipe += "Swipe Left\n";
				movement("left");
			} else if ((e1.getY() - e2.getY()) > sensitvity) {
				swipe += "Swipe Up\n";
				movement("up");
			} else if ((e2.getX() - e1.getX()) > sensitvity) {
				swipe += "Swipe Right\n";
				movement("right");
			} else if ((e2.getY() - e1.getY()) > sensitvity) {
				swipe += "Swipe Down\n";
				movement("down");
			}
			// turned off to not change title
			// gestureEvent.setText(swipe);

			return super.onFling(e1, e2, velocityX, velocityY);
		}

		private void movement(String s) {
			int tempCurrentPositionX = currentPositionX;
			int tempCurrentPositionY = currentPositionY;
			if (s == "left") {
				tempCurrentPositionY--;
			}
			if (s == "right") {
				tempCurrentPositionY++;
			}
			if (s == "up") {
				tempCurrentPositionX--;
			}
			if (s == "down") {
				tempCurrentPositionX++;
			}

			if (ss[tempCurrentPositionX][tempCurrentPositionY] == -3) {
				// win!
				MediaPlayer mp = MediaPlayer.create(getApplicationContext(),
						R.raw.win);
				mp.start();
				mp.setOnCompletionListener(new OnCompletionListener() {
					public void onCompletion(MediaPlayer mp) {
						mp.release();
					};
				});
				Toast.makeText(getApplicationContext(), "TODO: gotoCredits",
						Toast.LENGTH_LONG).show();

			}
			if (ss[tempCurrentPositionX][tempCurrentPositionY] == -1) {
				MediaPlayer mp = MediaPlayer.create(getApplicationContext(),
						R.raw.fail);
				mp.start();
				mp.setOnCompletionListener(new OnCompletionListener() {
					public void onCompletion(MediaPlayer mp) {
						mp.release();
					};
				});
			} else {
				MediaPlayer mp = MediaPlayer.create(getApplicationContext(),
						R.raw.jump);
				mp.start();
				mp.setOnCompletionListener(new OnCompletionListener() {
					public void onCompletion(MediaPlayer mp) {
						mp.release();
					};
				});
				currentPositionX = tempCurrentPositionX;
				currentPositionY = tempCurrentPositionY;
			}
			System.out.println("I am at X= " + currentPositionX + " and Y= "
					+ currentPositionY);
		}
	};

	@SuppressWarnings("deprecation")
	GestureDetector gestureDetector = new GestureDetector(
			simpleOnGestureListener);

}
