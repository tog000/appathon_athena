package com.athena.broncobattle;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.graphics.Color;

/**
 * Created by tog on 2/25/14.
 */
public class Util {


    public static int darkenColor(int c, float amount){
        float[] hsv = new float[3];
        Color.colorToHSV(c, hsv);
        hsv[2] -= amount;
        return Color.HSVToColor(hsv);
    }

    public static int lightenColor(int c, float amount){
        float[] hsv = new float[3];
        Color.colorToHSV(c, hsv);
        hsv[2] += amount;
        return Color.HSVToColor(hsv);
    }

    public static int saturateColor(int c, float amount){
        float[] hsv = new float[3];
        Color.colorToHSV(c, hsv);
        hsv[1] += amount;
        return Color.HSVToColor(hsv);
    }
    
    static String getEmail(Context context) {
        AccountManager accountManager = AccountManager.get(context); 
        Account account = getAccount(accountManager);

        if (account == null) {
          return null;
        } else {
          return account.name;
        }
      }

      private static Account getAccount(AccountManager accountManager) {
        Account[] accounts = accountManager.getAccountsByType("com.google");
        Account account;
        if (accounts.length > 0) {
          account = accounts[0];      
        } else {
          account = null;
        }
        return account;
      }

}
