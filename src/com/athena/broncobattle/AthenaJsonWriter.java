package com.athena.broncobattle;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;

public class AthenaJsonWriter extends AsyncTask<String, Void, String> {
		
		private String hostURL;
		String response;
		
		public AthenaJsonWriter(String hostURL){
			this.hostURL = hostURL;
		}

		@Override
		protected String doInBackground(String... parameters) {
		    DefaultHttpClient httpclient = new DefaultHttpClient();

		    HttpPost httpost = new HttpPost(hostURL + parameters[0]);

		    StringEntity se = null;
			try {
				se = new StringEntity(parameters[1]);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

		    httpost.setEntity(se);
		    httpost.setHeader("Accept", "application/json");
		    httpost.setHeader("Content-type", "application/json");

		    ResponseHandler<String> responseHandler = new BasicResponseHandler();
		    
		    try {
				response = httpclient.execute(httpost, responseHandler);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return response;
		}
		
		@Override
		protected void onPostExecute(String response){
			super.onPostExecute(response);
			System.out.println(response);
		}

	}
	

