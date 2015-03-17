package com.andersontao.mindmaze;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;

public class LevelParser {
	private Context context;

	/**
	 * @param args
	 */
	public LevelParser(Context context) {
		this.context = context;
	}

	public void init() throws IOException {
		int[][] ss = new int[13][13];
		InputStream input = null;
		input = context.getAssets().open("test.txt");
		int size = 0;
		try {
			size = input.available();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[] buffer = new byte[size];
		try {
			input.read(buffer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			input.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// byte buffer into a string
		String str = new String(buffer);
		// parse string

		String[] split = str.split("\\s+");
		int[] desiredOP = new int[split.length];
		int z = 0;
		for (String string : split) {
			desiredOP[z++] = Integer.parseInt(string);
		}
		// System.out.println(desiredOP);
		// put array into ss
		int n = 0;
		for (int i = 0; i < 13; i++) {
			for (int j = 0; j < 13; j++) {
				ss[i][j] = desiredOP[n++];

			}
		}

		printStage(13, ss);

	}

	private static void printStage(int STAGE_SIZE, int[][] ss) {
		for (int i = 0; i < STAGE_SIZE; i++) {
			for (int j = 0; j < STAGE_SIZE; j++) {
				System.out.print(ss[i][j] + "\t");
			}
			System.out.println("\n");
		}
	}

}
