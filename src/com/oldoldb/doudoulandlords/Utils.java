package com.oldoldb.doudoulandlords;


public class Utils {

	public static boolean inTouchArea(int x, int y, int l, int t, int r, int b)
	{
		return x>=l && x<=r && y>=t && y<=b;
	}
	
	
}
