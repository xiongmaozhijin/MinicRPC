package com.example.minicrpc.db;

import java.util.List;

import android.content.Context;

import com.example.minicrpc.App;
import com.example.minicrpc.databasedao.DaoSession;
import com.example.minicrpc.databasedao.LuckyDog;
import com.example.minicrpc.databasedao.LuckyDogDao;
import com.example.minicrpc.utils.MinicRPCLog;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * 数据库管理，生日
 * @author michael.chen
 *
 */
public class DBManager {

	private static final String TAG = "DBManager";
	private static final boolean DEBUG = MinicRPCLog.DEBUG;
	private static DBManager mInstance;
	private static Context mContext;
	private DaoSession mDaoSession;

	private DBManager(Context context) {
		App app = (App) context.getApplicationContext();
		mDaoSession = app.getDaoSessionInstance();
		
		this.mContext = context;
	}
	
	/**
	 * 得到数据库管理实例，单例
	 * @param context
	 * @return
	 */
	public static synchronized DBManager getInstance(Context context ) {
		if (mInstance == null ) {
			mInstance = new DBManager(context);
			mContext = context;
		}
		
		return mInstance;
	}
	
	/**
	 * 得到所有的生日记录
	 * @return
	 */
	public List<LuckyDog> getAllLuckyDogs() {
		LuckyDogDao luckyDogDao = mDaoSession.getLuckyDogDao();
		return luckyDogDao.queryBuilder().list();
	}
	
	public LuckyDog loadByKey(long id) {
		LuckyDogDao luckyDogDao = mDaoSession.getLuckyDogDao();
		return luckyDogDao.load(id);
	}
	
	public void addLuckyDog(LuckyDog luckyDog) {
		LuckyDogDao luckyDogDao = mDaoSession.getLuckyDogDao();
		luckyDogDao.insert(luckyDog);
		if (DEBUG) {
			MinicRPCLog.d(TAG, "%s", "add luckydog");
		}
		
	}
	
	
	public void deleteLuckDog(long id) {
		LuckyDogDao l = mDaoSession.getLuckyDogDao();
		l.deleteByKey(id);
		
		if (DEBUG) {
			MinicRPCLog.d(TAG, "%s", "delete luckydog");
		}
	}
	
	public void updateOrInsert(LuckyDog luckyDog) {
		LuckyDogDao l = mDaoSession.getLuckyDogDao();
		l.insertOrReplace(luckyDog);
		
		if (DEBUG) {
			MinicRPCLog.d(TAG, "%s", "updateOrInsert luckydog");
		}
	}
	
	
	public void updateLuckyDog(LuckyDog luckyDog) {
		LuckyDogDao l = mDaoSession.getLuckyDogDao();
		l.update(luckyDog);
		
		if (DEBUG) {
			MinicRPCLog.d(TAG, "%s", "updateLuckyDog");
		}
	}
	
	
	
	
	
	
	
	
	
}
