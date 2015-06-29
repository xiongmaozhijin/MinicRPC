package com.example.minicrpc;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.example.minicrpc.databasedao.DaoMaster;
import com.example.minicrpc.databasedao.DaoMaster.DevOpenHelper;
import com.example.minicrpc.databasedao.DaoSession;
import com.example.minicrpc.utils.MinicRPCLog;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import de.greenrobot.dao.query.QueryBuilder;

public class App extends Application {
	
	private static final String TAG = "App";
	private static final boolean DEBUG = MinicRPCLog.DEBUG;
	private static ImageLoader mImageLoader;
	private static String IMAGE_PATH_LOAD;
	private static String IMAGE_PATH_REAL;
	
	private static DaoSession mDaoSession;
	
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		if (DEBUG) {
			MinicRPCLog.d(TAG, "%s", "onCreate");
		}
		
		ImageLoaderConfiguration cg = ImageLoaderConfiguration.createDefault(getApplicationContext());
		mImageLoader  = ImageLoader.getInstance();
		mImageLoader.init(cg);
		
		//��ȡͼƬ�ļ�λ��
		IMAGE_PATH_REAL = IMAGE_PATH_LOAD = getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath();
		IMAGE_PATH_LOAD = IMAGE_PATH_LOAD.replaceFirst("^/[s|S]torage", "file:///mnt");
		IMAGE_PATH_LOAD = IMAGE_PATH_LOAD + "/";
		
	}

	/**
	 * ���ͼƬ���ص���
	 * @return
	 */
	public ImageLoader getImageLoader() {
		return mImageLoader;
	}
	
	/**
	 * ��ȡͼƬ���Ŀ¼
	 * {@literal /mnt/sdcard/Android/data/package_name/files/Pictures/}
	 * @return
	 */
	public String getImageDir() {
		return IMAGE_PATH_LOAD;
	}
	
	
	public String getImageDirReal() {
		return IMAGE_PATH_REAL;
	}
	
	/**
	 * ��ȡgreenDAO���� DaoSession
	 * @return
	 */
	public synchronized DaoSession getDaoSessionInstance() {
		if (mDaoSession == null) {
			DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(getApplicationContext(), "birthday-db", null);
			SQLiteDatabase db = devOpenHelper.getWritableDatabase();
			mDaoSession = new DaoMaster(db).newSession();
			
			//�򿪵���
			QueryBuilder.LOG_SQL = true;
			QueryBuilder.LOG_VALUES = true;
		}
		
		return mDaoSession;
		
	}
	
}



