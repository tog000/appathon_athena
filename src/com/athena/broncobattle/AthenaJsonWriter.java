package com.athena.broncobattle;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.content.Context;
import android.os.AsyncTask;

public class AthenaJsonWriter extends AsyncTask<String, Void, String> {
		
		private Context mContext;
		private HashMap<String,String> parameters;
		private JsonEventListener listener;
		private String type;
		
		
		public void addJsonEventListener(JsonEventListener listener, String type){
			this.listener = listener;
			this.type=type;
		}
		
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

			try {
				String url = MainActivity.HOST;
				for(String param : urlParameters){
					url+="/"+param;
				}
	
				HttpPost httpPost = new HttpPost(url);
			    
			    HttpParams httpParams = new BasicHttpParams();
				HttpConnectionParams.setConnectionTimeout(httpParams, 30000);
				HttpConnectionParams.setSoTimeout(httpParams, 30000);
				
				DefaultHttpClient httpClient = new DefaultHttpClient(httpParams);
				
				List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
	            for(Entry<String, String> entry : parameters.entrySet()){
	            	postParameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
	            }
	
			    
	            UrlEncodedFormEntity encodedEntity = new UrlEncodedFormEntity(postParameters);
			    httpPost.setEntity(encodedEntity);
			    
			    httpPost.setHeader("Accept", "text/plain");
			    //httpPost.setHeader("Content-type", "text/plain");
	
			    ResponseHandler<String> responseHandler = new BasicResponseHandler();
			    
				String response = (String) httpClient.execute(httpPost, responseHandler);
				
				return response;
				
			}catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(String response){
			super.onPostExecute(response);
			System.out.println(response);
			listener.onJsonFinished(response,type);
		}

	}
	

