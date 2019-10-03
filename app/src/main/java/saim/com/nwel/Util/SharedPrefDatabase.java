package saim.com.nwel.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPrefDatabase {

    public static final String KEY_LOGIN = "KEY_LOGIN";
    public static final String KEY_USER_EMAIL = "KEY_USER_EMAIL";
    public static final String KEY_USER_PASSWORD = "KEY_USER_PASSWORD";


    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;
    public SharedPrefDatabase(Context ctx) {
        this.context = ctx;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        editor = sharedPreferences.edit();
    }

    public void StoreLogin(int data){
        editor.putInt(KEY_LOGIN, data);
        editor.commit();
    }
    public int RetriveLogin(){
        int text = sharedPreferences.getInt(KEY_LOGIN, 0);
        return text;
    }

    public void StoreUserEmail(String data){
        editor.putString(KEY_USER_EMAIL, data);
        editor.commit();
    }
    public String RetriveUserEmail(){
        String text = sharedPreferences.getString(KEY_USER_EMAIL, null);
        return text;
    }

    public void StoreUserPassword(String data){
        editor.putString(KEY_USER_PASSWORD, data);
        editor.commit();
    }
    public String RetriveUserPassword(){
        String text = sharedPreferences.getString(KEY_USER_PASSWORD, null);
        return text;
    }


}
