
package com.geekytheory.androidyuvimage;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.BlurMaskFilter.Blur;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity {

	public Bitmap bmp, bmpOut;
	public ImageView img;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		img = (ImageView) findViewById(R.id.image);
		Button btn = (Button) findViewById(R.id.button);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				bmp = BitmapFactory.decodeResource(getResources(),
						R.drawable.lena256);
				bmpOut = doYUV(bmp);
				img.setImageBitmap(bmpOut);
			}
		});
	}

	public static Bitmap doYUV(Bitmap src) {

		// Constant factors for RGB->YUV transformation
		// Y components
		final double Y_RED = 0.299;
		final double Y_GREEN = 0.587;
		final double Y_BLUE = 0.114;
		// U components
		final double U_RED = -0.147;
		final double U_GREEN = -0.289;
		final double U_BLUE = 0.436;
		// V components
		final double V_RED = 0.615;
		final double V_GREEN = -0.515;
		final double V_BLUE = -0.100;

		// Create new bitmap with the same settings as source bitmap
		Bitmap bmOut = Bitmap.createBitmap(src.getWidth(), src.getHeight(),
				src.getConfig());
		// Color info
		int A, R, G, B, Y, U, V;
		int pixelColor;
		// Image size
		int height = src.getHeight();
		int width = src.getWidth();
		// Scan through every pixel
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				// Get one pixel
				pixelColor = src.getPixel(x, y);
				// Saving alpha channel -> It's not necessary
				// A = Color.alpha(pixelColor);
				// Getting red, green and blue colors
				R = Color.red(pixelColor);
				G = Color.green(pixelColor);
				B = Color.blue(pixelColor);
				// Using RGB->YUV matrix for Y component
				Y = R = G = B = (int) (Y_RED * R + Y_GREEN * G + Y_BLUE * B);
				// Y compoment is the luminance. I just need a grayscale image
				bmOut.setPixel(x, y, Color.rgb(Y, Y, Y));
			}
		}
		// Return grayscale bitmap
		return bmOut;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
