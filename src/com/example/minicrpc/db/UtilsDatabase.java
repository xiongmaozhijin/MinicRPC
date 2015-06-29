package com.example.minicrpc.db;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.minicrpc.R;
import com.example.minicrpc.databasedao.SmsCategory;
import com.example.minicrpc.databasedao.SmsContent;
import com.example.minicrpc.databasedao.SmsSonCategory;
import com.example.minicrpc.utils.MinicRPCLog;
import com.example.minicrpc.utils.MinicUtils;

public class UtilsDatabase {
	
	private UtilsDatabase() {}
	
	private static final String LOCAL_DB_FILENAME = "local.db";
	private static final String TABLE = "ZSMSCONTENT";
	
	public static final String RENQI = "renqi";
	public static final String CONTENT = "content";
	
	public static final String CONFIG = "config";
	
	/**
	 * �Ƿ�����ݼ��ص����ݿ���
	 */
	public static final String ISLOADTODATABASE = "isloadtodatabase";
	
	/**
	 * �Ƿ����ļ�
	 */
	private static final String ISDATABASECONFIG = "isdatabaseconfig";
	private static final String TAG = "UtilsDatabase";
	private static final boolean DEBUG = MinicRPCLog.DEBUG;
	
	/**
	 * ��ȡ�������ݿ�����ݺ�����
	 * @param context
	 * @return
	 */
	public static List<Map<String, String>> getLocalData(Context context) {
		if (DEBUG) {
			MinicRPCLog.d(TAG, "��ȡ�������ݿ�����ݺ�����");
		}
		
		List<Map<String, String>> data = new ArrayList<Map<String,String>>();
		
		File file = getFile(context, LOCAL_DB_FILENAME);
		SQLiteDatabase qb = SQLiteDatabase.openOrCreateDatabase(file, null);
		Cursor cursor = null;
		cursor = qb.query(TABLE, new String[]{"ZCONTENT", "ZPOPULARITY"}, null, null, null, null, null);
		
		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
			Map<String, String> item = new HashMap<String, String>();
			String content = cursor.getString(0);
			int hots = cursor.getInt(1);
			item.put(CONTENT, content);
			item.put(RENQI, hots+"");
			data.add(item);
		}
		
		if (cursor != null) {
			cursor.close();
		}
		
		if (qb != null) {
			qb.close();
		}
		
		return data;
	}
	
	/**
	 * �õ���ŵ�λ��
	 * @param context
	 * @param filename
	 * @return
	 */
	private static File getFile(Context context, String filename) {
//		File cacheDir = context.getExternalCacheDir();
//		return new File(cacheDir, filename);
		String p1 = "/data/data/com.example.minicrpc/databases";
		File f = new File(p1);
		if (!f.exists()) {
			f.mkdir();
		}
	
		return new File(f, filename);
	}
	
	/**
	 * �����ļ�
	 * @param context
	 */
	private static void copyfile(Context context) {
		if (DEBUG) {
			MinicRPCLog.d(TAG, "�������ݿ��ļ�");
		}
		InputStream is = context.getResources().openRawResource(R.raw.wishdata);
		File file = getFile(context, LOCAL_DB_FILENAME);
		MinicUtils.saveFile(file.getAbsolutePath(), is);
	}
	
	/**
	 * �����ļ�
	 * @param context
	 */
	public static void copyfileManager(Context context) {
		SharedPreferences sp = context.getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
		boolean isConfig = sp.getBoolean(ISDATABASECONFIG, false);
		if (!isConfig) {
			copyfile(context);
			sp.edit().putBoolean(ISDATABASECONFIG, true).commit();
		}
		
	}
	
	/**
	 * �����ݸ���ת�Ƶ�������
	 * @param context
	 * @param data
	 */
	public static void copyDataToDatabase(Context context, List<Map<String, String>> data) {
		initCategory(context);
		initSonCategory(context);
		initContent(data, context);
	}
	
	/**
	 * ��ʼ��������
	 * @param context
	 */
	private static void initCategory(Context context) {
		if (DEBUG) {
			MinicRPCLog.d(TAG, "initCategory");
		}
		String names[] = context.getResources().getStringArray(R.array.sms_bless);
		SmsDBManager manager = SmsDBManager.getInstance(context);
		for (int i=0; i<names.length; i++) {
			manager.addCategory(i+1, names[i]);
		}
	} 
	
	/**
	 * ��ʼ��������
	 * @param context
	 */
	private static void initSonCategory(Context context) {
		if (DEBUG) {
			MinicRPCLog.d(TAG, "initSonCategory");
		}
		
		SmsDBManager manager = SmsDBManager.getInstance(context);
		
		String names[][] = new String[][]{
				context.getResources().getStringArray(R.array.qingpenghaoyou),
				context.getResources().getStringArray(R.array.yuyanwenzi),
				context.getResources().getStringArray(R.array.fenggeleixing),
				context.getResources().getStringArray(R.array.jierizhufu)
		};

		int k = 1;
		for (int i=0; i<names.length; i++) {
			String[] namesitem = names[i];
			for (int j=0; j<namesitem.length; j++) {
				SmsSonCategory item = new SmsSonCategory(k++, namesitem[j], Long.valueOf(i+1));
				manager.addSonCategory(item);
			}
		}
	}
	
	private static void initContent(List<Map<String, String>> data, Context context) {
		if (DEBUG) {
			MinicRPCLog.d(TAG, "initContent");
		}
		
		int[] indexs = new int[]{
				0, 70,70, 155,155, 201,201, 242,242, 278,278, 321,321, 386,386, 447,447, 527,
				527, 583,583, 622,622, 659,659, 696,696, 728,728, 761,761, 793,793, 829,829, 854,1297, 1317,
				1317, 1350,1350, 1394,1394, 1417,1417, 1441,1441, 1464,1464, 1526,1526, 1554,1554, 1590,1590, 1613,1613, 1623,
				854, 882,882, 941,941, 985,985, 1028,1028, 1078,1078, 1119,1119, 1151,1151, 1177,1177, 1195,1195, 1213,
				1213, 1235,1235, 1251,1251, 1264,1264, 1284,1284, 1297,1623, 1657,1657, 1703,1703, 1756,1756, 1798,1798, 1838,
				1838, 1878,1878, 1927,1927, 1967,1967, 2016,2016, 2056,2056, 2096,2096, 2146,2146, 2196,2196, 2253,2253, 2303,
				2303, 2351
		};
		int[] subCategoryDiff = new int[]{
				18, 11, 15, 16
		};
		int idFather = 1;	//����
		int idSonIndex = 0;	//only use for taher category
		int idSon = 0;
		
		for (int i=0; i<indexs.length; i+=2) {
			List<Map<String, String>> sublist = data.subList(indexs[i], indexs[i+1]-1);
			idSon++;		//begin with 1
			idSonIndex++;
			if (idSonIndex > subCategoryDiff[idFather-1]) {
				idFather++;
				idSonIndex = 0;
			}
			
			addItems(idFather, idSon, sublist, context);
		}
		
	}

	/**
	 * ���ӵ����ݿ���
	 * @param idFather	�����
	 * @param idSon		�����
	 * @param sublist	��ͼ�б�
	 */
	private static void addItems(int idFather, int idSon,
			List<Map<String, String>> sublist, Context context) {
		
		if (DEBUG) {
			MinicRPCLog.d(TAG, "�����ӷ���, �����ࣺ%d, �ӷ���: %d, ��Ŀ��С: %d", idFather, idSon, sublist.size() );
		}
		
		SmsDBManager manager = SmsDBManager.getInstance(context);
		for (Map<String, String> map : sublist) {
			String content = map.get(UtilsDatabase.CONTENT);
			String hots_ = map.get(UtilsDatabase.RENQI);
			int hots = Integer.parseInt(hots_);					
			SmsContent item = new SmsContent(null, Long.valueOf(idFather), Long.valueOf(idSon), content, hots);
			manager.addContent(item);
		}
	}
	
	
}
