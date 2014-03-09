package com.athena.broncobattle;

import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.Visibility;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class ImageDownloader extends AsyncTask<String, Void, Bitmap> {
	ImageView bmImage;
	ProgressBar progressBar;

	public ImageDownloader(ImageView bmImage, ProgressBar progressBar) {
		this.bmImage = bmImage;
		this.progressBar = progressBar;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	protected Bitmap doInBackground(String... urls) {
		String urldisplay = urls[0];
		Bitmap mIcon11 = null;
		try {
			InputStream in = new java.net.URL(urldisplay).openStream();
			mIcon11 = BitmapFactory.decodeStream(in);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mIcon11;
	}

	@Override
	protected void onPostExecute(Bitmap result) {
		super.onPostExecute(result);
		if (progressBar!= null){
			progressBar.setVisibility(View.INVISIBLE);
		}
		bmImage.setImageBitmap(result);
		bmImage.setVisibility(View.VISIBLE);
	}

}
