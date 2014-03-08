package com.athena.broncobattle;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map.Entry;

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
		private HashMap<String,String> parameters;
		
		
		public AthenaJsonWriter(Context context){
			this.mContext = context;
			this.parameters = new HashMap<String,String>();
		}
		
		public void addNamedParameter(String name, String value){
			parameters.put(name, value);
		}
		
		public void clearParameters(){
			parameters.clear();
		}

		@Override
		protected String doInBackground(String... urlParameters) {

			String url = MainActivity.HOST;
			for(String param : urlParameters){
				url+="/"+param;
			}

		    HttpPost httpPost = new HttpPost(url);
		    
		    HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, 30000);
			HttpConnectionParams.setSoTimeout(httpParams, 30000);
		    DefaultHttpClient httpClient = new DefaultHttpClient(httpParams);
		    
		    for(Entry<String, String> entry : parameters.entrySet()){
		    	httpParams.setParameter(entry.getKey(), entry.getValue());
		    }

		    /*
		    StringEntity se = null;
			try {
				se = new StringEntity(urlParameters[1]);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

		    httpPost.setEntity(se);
		    */
		    //httpPost.setHeader("Accept", "application/json");
		    //httpPost.setHeader("Content-type", "application/json");

		    ResponseHandler<String> responseHandler = new BasicResponseHandler();
		    
		    try {
				response = httpClient.execute(httpPost, responseHandler);
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
	

