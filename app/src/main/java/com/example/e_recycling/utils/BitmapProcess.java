package com.example.e_recycling.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

public class BitmapProcess {

	public BitmapProcess() {
	}

	private static int calculateInSampleSize(
            BitmapFactory.Options options, int i, int j) {
		int k1;
		label0: {
			int k = options.outHeight;
			int l = options.outWidth;
			int i1 = 1;
			if (k > j || l > i) {
				int j1 = Math.round((float) k / (float) j);
				k1 = Math.round((float) l / (float) i);
				if (j1 >= k1) {
					break label0;
				}
				i1 = j1;
			}
			return i1;
		}
		return k1;
	}

	public static Bitmap decodeSampledBitmapFromPah(String s, int i, int j) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(s, options);
		options.inSampleSize = calculateInSampleSize(options, i, j);
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(s, options);
	}

	public static Bitmap RotateBitmap(Bitmap source, float angle) {
		Matrix matrix = new Matrix();
		matrix.postRotate(angle);
		return Bitmap.createBitmap(source, 0, 0, source.getWidth(),
				source.getHeight(), matrix, true);
	}
}
