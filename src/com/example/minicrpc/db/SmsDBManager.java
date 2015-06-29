package com.example.minicrpc.db;

import java.util.List;

import com.example.minicrpc.App;
import com.example.minicrpc.databasedao.DaoSession;
import com.example.minicrpc.databasedao.SmsCategory;
import com.example.minicrpc.databasedao.SmsCategoryDao;
import com.example.minicrpc.databasedao.SmsContent;
import com.example.minicrpc.databasedao.SmsContentDao;
import com.example.minicrpc.databasedao.SmsSonCategory;
import com.example.minicrpc.databasedao.SmsSonCategoryDao;

import android.content.Context;

/**
 * 数据库管理，短信
 * @author michael.chen
 *
 */
public class SmsDBManager {

	private SmsDBManager(Context context) {
		App app = (App) context.getApplicationContext();
		mDaoSession = app.getDaoSessionInstance();
		
		this.mContext = context;
	}
	
	private static SmsDBManager mInstance;
	private DaoSession mDaoSession;
	private Context mContext;
	
	public synchronized static SmsDBManager getInstance(Context context) {
		if (mInstance == null) {
			mInstance = new SmsDBManager(context);
		}
		
		return mInstance;
	} 
	
	/**
	 * 增加父类别
	 * @param id
	 * @param categoryName
	 */
	public void addCategory(long id, String categoryName) {
		SmsCategory item = new SmsCategory(id, categoryName);
		SmsCategoryDao dao = mDaoSession.getSmsCategoryDao();
		dao.insert(item);
	}
	
	/**
	 * 增加子类别
	 * @param id
	 * @param categoryId
	 * @param categoryName
	 */
	public void addSonCategory(long id, long categoryId, String categoryName) {
		SmsSonCategory item = new SmsSonCategory(id, categoryName, categoryId);
		SmsSonCategoryDao dao = mDaoSession.getSmsSonCategoryDao();
		dao.insert(item);
	}
	
	/**
	 * 增加子类别
	 * @param id
	 * @param categoryId
	 * @param categoryName
	 */
	public void addSonCategory(SmsSonCategory item) {
		SmsSonCategoryDao dao = mDaoSession.getSmsSonCategoryDao();
		dao.insert(item);
	}
	
	/**
	 * 增加短信内容
	 * @param id
	 * @param categoryId
	 * @param sonCategoryId
	 * @param content
	 * @param hots
	 */
	public void addContent(long id, long categoryId, long sonCategoryId, String content, int hots) {
		SmsContent item = new SmsContent(id, categoryId, sonCategoryId, content, hots);
		SmsContentDao dao = mDaoSession.getSmsContentDao();
		dao.insert(item);
	}
	
	/**
	 * 增加短信内容
	 * @param item
	 */
	public void addContent(SmsContent item) {
		SmsContentDao dao = mDaoSession.getSmsContentDao();
		dao.insert(item);
	}
	
	/**
	 * 查找所有的父类别
	 * @return 
	 */
	public List<SmsCategory> queryAllCategory() {
		SmsCategoryDao dao = mDaoSession.getSmsCategoryDao();
		return dao.queryBuilder().list();
	}
	
	/**
	 * 返回指定key的父类别
	 * @param id
	 * @return
	 */
	public SmsCategory loadCategory(long id) {
		SmsCategoryDao dao = mDaoSession.getSmsCategoryDao();
		return dao.load(id);
	}
	
	/**
	 * 返回指定id的子类别
	 * @param id
	 * @return
	 */
	public SmsSonCategory loadSonCategory(long id) {
		SmsSonCategoryDao dao = mDaoSession.getSmsSonCategoryDao();
		return dao.load(id);
	}
	
	/**
	 * 查找所有的子类别
	 * @return
	 */
	public List<SmsSonCategory> queryAllSonCategory() {
		SmsSonCategoryDao dao = mDaoSession.getSmsSonCategoryDao();
		return dao.queryBuilder().list();
	}
	
	/**
	 * 查看父类别下的子类别
	 * @param father
	 * @return
	 */
	public List<SmsSonCategory> queryAllSonCategoryByFather(SmsCategory father) {
		return father.getSmsSonCategoryList();
	} 
	
	/**
	 * 返回所有的内容
	 * @return
	 */
	public List<SmsContent> queryAllContent() {
		SmsContentDao dao = mDaoSession.getSmsContentDao();
		return dao.queryBuilder().list();
	}
	
	/**
	 * 根据子类别返回内容列表
	 * @param item
	 * @return
	 */
	public List<SmsContent> queryAllContentBySonCategory(SmsSonCategory item) {
		return item.getSmsContentList();
	}
	
	/**
	 * 根据子类别id返回内容列表
	 * @param id
	 * @return
	 */
	public List<SmsContent> queryAllContentBYSonCategoryId(long id) {
		SmsSonCategory item = loadSonCategory(id);
		return item.getSmsContentList();
	}
	
	/**
	 * 根据父类别的id和子类别的位置返回子类别id
	 * @param category
	 * @param position
	 * @return
	 */
	public long querySonCategoryId(long categoryId, int position) {
		SmsCategory item = loadCategory(categoryId);
		return item.getSmsSonCategoryList().get(position).getSonCategoryId();
	}
	
	/**
	 * 根据父类别的id和子类别的位置返回该指定类别的短信内容
	 * @param categoryId
	 * @param position
	 * @return
	 */
	public List<SmsContent> querySmsContent(long categoryId, int position) {
		SmsCategory item = loadCategory(categoryId);
		return item.getSmsSonCategoryList().get(position).getSmsContentList();
	}
	
	/**
	 * 根据指定的父类别返回内容列表
	 * @param item
	 * @return
	 */
	public List<SmsContent> queryAllContentByCategory(SmsCategory item) {
		return item.getSmsContentList();
	}
	
	/**
	 * 根据指定的父类别id返回内容列表
	 * @param id
	 * @return
	 */
	public List<SmsContent> queryAllContentByCategoryId(long id) {
		return loadCategory(id).getSmsContentList();
	}
	
	/**
	 * 返回指定id的内容
	 * @param id
	 * @return
	 */
	public SmsContent loadContent(long id) {
		SmsContentDao dao = mDaoSession.getSmsContentDao();
		return dao.load(id);
	}
	
}
