package com.oldoldb.doudoulandlords;

import java.net.ContentHandler;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class TestUtils {

	public static void showTest(Context context, String text)
	{
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}
	
	public static void showLog(String text)
	{
		Log.d("DOUDOU", text);
	}
}
