package com.athena.broncobattle;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ProgressBar;

public class AchievementFragment extends DialogFragment {
	
  
    /** The system calls this only when creating the layout in a dialog. */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // The only reason you might override this method when using onCreateView() is
        // to modify any dialog characteristics. For example, the dialog includes a
        // title by default, but your custom layout might not need it. So here you can
        // remove the dialog title, but you must call the superclass to get the Dialog.
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        
        builder.setView(inflater.inflate(R.layout.dialog_fragment, null))
        // Add action buttons
               .setNegativeButton("CLOSE", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       AchievementFragment.this.close();
                   }
               });      
        startTimeout();
        
        return builder.create();

    }
    
    public void close(){
    	timer.cancel();
    	ProgressBar p = (ProgressBar) this.getDialog().findViewById(R.id.progressBar1);
    	p.setProgress(0);
    	this.getDialog().cancel();
    }
    
    public void updateBar(float progress){
    	ProgressBar p = (ProgressBar) this.getDialog().findViewById(R.id.progressBar1);
    	p.setProgress((int)(100*progress));
    }
    
    private CountDownTimer timer; 
    public void startTimeout(){
    	
    	//final AchievementFragment instance = this;
    	
    	timer = new CountDownTimer(5000, 200) {

    	     public void onTick(long millisUntilFinished) {
    	    	 updateBar(millisUntilFinished/5000f);
    	     }

    	     public void onFinish() {
    	    	 updateBar(0f);
    	    	 close();
    	     }
    	  }.start();
    }
    

}
