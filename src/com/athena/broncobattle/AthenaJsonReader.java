package com.athena.broncobattle;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class AthenaJsonReader extends AsyncTask<String, Void, String> {

	private Context mContext;
	private JsonEventListener listener;
	private String type;
	
	
	public void addJsonEventListener(JsonEventListener listener, String type){
		this.listener = listener;
		this.type=type;
	}

	public AthenaJsonReader(Context context) {
		mContext = context;
	}

	@Override
	protected String doInBackground(String... parameters) {
		try {
			String url = MainActivity.HOST;
			for(String param : parameters){
				url+="/"+param;
			}
			
			HttpParams httpParams = new BasicHttpParams();

			HttpConnectionParams.setConnectionTimeout(httpParams, 30000);
			HttpConnectionParams.setSoTimeout(httpParams, 30000);

			DefaultHttpClient httpClient = new DefaultHttpClient(httpParams);

			HttpGet httpGet = new HttpGet(url);
			httpGet.setHeader("Content-type", "application/json");
			@SuppressWarnings("rawtypes")
			ResponseHandler responseHandler = new BasicResponseHandler();
			@SuppressWarnings("unchecked")
			String response = (String) httpClient.execute(httpGet, responseHandler);

			return response;
			
		//} catch (JSONException e) {
		//	e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(String json) {
		super.onPostExecute(json);
		listener.onJsonFinished(json,type);
	}
}
