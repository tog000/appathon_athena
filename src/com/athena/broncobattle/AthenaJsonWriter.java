package com.athena.broncobattle;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.content.Context;
import android.os.AsyncTask;

public class AthenaJsonWriter extends AsyncTask<String, Void, String> {
		
		private Context mContext;
		String response;
		
		
		public AthenaJsonWriter(Context context){
			this.mContext = context;
		}

		@Override
		protected String doInBackground(String... parameters) {

			String url = MainActivity.HOST;
			for(String param : parameters){
				url+="/"+param;
			}

		    HttpPost httpost = new HttpPost(url);
		    
		    HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, 30000);
			HttpConnectionParams.setSoTimeout(httpParams, 30000);
		    DefaultHttpClient httpClient = new DefaultHttpClient(httpParams);

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
				response = httpClient.execute(httpost, responseHandler);
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
	

