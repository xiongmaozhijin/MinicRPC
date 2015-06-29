package com.example.minicrpc.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import android.content.Context;

import com.example.minicrpc.databasedao.LuckyDog;
import com.example.minicrpc.db.DBManager;
import com.example.minicrpc.dto.UtilsTranstran;
import com.example.minicrpc.entity.LuckyPeople;

public class MinicBiz {
	
	private static final String TAG = "MinicBiz";
	private static final boolean DEBUG = MinicRPCLog.DEBUG;


	private MinicBiz() {}
	
	
	private static List<LuckyPeople> getAllDataFromDatabase(Context context) {

		List<LuckyDog> list = DBManager.getInstance(context).getAllLuckyDogs();
		List<LuckyPeople> l = UtilsTranstran.fromLuckyDog(list);
	
		if (DEBUG) {
			MinicRPCLog.d(TAG, "data:%s", l.toString());
		}			 
		
		return l;
	}
	
	
	public static List<LuckyPeople> getAllData(Context context) {
		
		if (DEBUG) {
			MinicRPCLog.i(TAG, "date getTime is %s.", "getAllData");
		}
		
		return getAllDataFromDatabase(context);
	}


	private static List<LuckyPeople> getAllDataMinic() {
		List<LuckyPeople> list = new ArrayList<LuckyPeople>();
		Random random = new Random();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d", Locale.getDefault());
		
		Date date1 = new Date();
		
		for (int i=0; i<15; i++) {
			int month = random.nextInt(12) + 1;
			int day = random.nextInt(27) + 1;
			Date date = new Date();
			try {
//				date = DateFormat.getDateInstance().parse("2015-" + month + "-" + day);
				date = sdf.parse("2015-" + month + "-" + day);
			} catch (ParseException e) {
				e.printStackTrace();
				MinicRPCLog.e(TAG, "parse error: %s", e.toString());
			}
			
			if (i%4 == 0) {
				date = new Date();
			}
			
			LuckyPeople l = new LuckyPeople(1, "path-"+i, "name-"+i, i%2, date, "tel-"+i, "note-"+i);
			list.add(l);
		}
		
		
		return list;
	}
	
}
