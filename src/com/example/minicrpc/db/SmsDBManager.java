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
 * ���ݿ��������
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
	 * ���Ӹ����
	 * @param id
	 * @param categoryName
	 */
	public void addCategory(long id, String categoryName) {
		SmsCategory item = new SmsCategory(id, categoryName);
		SmsCategoryDao dao = mDaoSession.getSmsCategoryDao();
		dao.insert(item);
	}
	
	/**
	 * ���������
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
	 * ���������
	 * @param id
	 * @param categoryId
	 * @param categoryName
	 */
	public void addSonCategory(SmsSonCategory item) {
		SmsSonCategoryDao dao = mDaoSession.getSmsSonCategoryDao();
		dao.insert(item);
	}
	
	/**
	 * ���Ӷ�������
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
	 * ���Ӷ�������
	 * @param item
	 */
	public void addContent(SmsContent item) {
		SmsContentDao dao = mDaoSession.getSmsContentDao();
		dao.insert(item);
	}
	
	/**
	 * �������еĸ����
	 * @return 
	 */
	public List<SmsCategory> queryAllCategory() {
		SmsCategoryDao dao = mDaoSession.getSmsCategoryDao();
		return dao.queryBuilder().list();
	}
	
	/**
	 * ����ָ��key�ĸ����
	 * @param id
	 * @return
	 */
	public SmsCategory loadCategory(long id) {
		SmsCategoryDao dao = mDaoSession.getSmsCategoryDao();
		return dao.load(id);
	}
	
	/**
	 * ����ָ��id�������
	 * @param id
	 * @return
	 */
	public SmsSonCategory loadSonCategory(long id) {
		SmsSonCategoryDao dao = mDaoSession.getSmsSonCategoryDao();
		return dao.load(id);
	}
	
	/**
	 * �������е������
	 * @return
	 */
	public List<SmsSonCategory> queryAllSonCategory() {
		SmsSonCategoryDao dao = mDaoSession.getSmsSonCategoryDao();
		return dao.queryBuilder().list();
	}
	
	/**
	 * �鿴������µ������
	 * @param father
	 * @return
	 */
	public List<SmsSonCategory> queryAllSonCategoryByFather(SmsCategory father) {
		return father.getSmsSonCategoryList();
	} 
	
	/**
	 * �������е�����
	 * @return
	 */
	public List<SmsContent> queryAllContent() {
		SmsContentDao dao = mDaoSession.getSmsContentDao();
		return dao.queryBuilder().list();
	}
	
	/**
	 * ��������𷵻������б�
	 * @param item
	 * @return
	 */
	public List<SmsContent> queryAllContentBySonCategory(SmsSonCategory item) {
		return item.getSmsContentList();
	}
	
	/**
	 * ���������id���������б�
	 * @param id
	 * @return
	 */
	public List<SmsContent> queryAllContentBYSonCategoryId(long id) {
		SmsSonCategory item = loadSonCategory(id);
		return item.getSmsContentList();
	}
	
	/**
	 * ���ݸ�����id��������λ�÷��������id
	 * @param category
	 * @param position
	 * @return
	 */
	public long querySonCategoryId(long categoryId, int position) {
		SmsCategory item = loadCategory(categoryId);
		return item.getSmsSonCategoryList().get(position).getSonCategoryId();
	}
	
	/**
	 * ���ݸ�����id��������λ�÷��ظ�ָ�����Ķ�������
	 * @param categoryId
	 * @param position
	 * @return
	 */
	public List<SmsContent> querySmsContent(long categoryId, int position) {
		SmsCategory item = loadCategory(categoryId);
		return item.getSmsSonCategoryList().get(position).getSmsContentList();
	}
	
	/**
	 * ����ָ���ĸ���𷵻������б�
	 * @param item
	 * @return
	 */
	public List<SmsContent> queryAllContentByCategory(SmsCategory item) {
		return item.getSmsContentList();
	}
	
	/**
	 * ����ָ���ĸ����id���������б�
	 * @param id
	 * @return
	 */
	public List<SmsContent> queryAllContentByCategoryId(long id) {
		return loadCategory(id).getSmsContentList();
	}
	
	/**
	 * ����ָ��id������
	 * @param id
	 * @return
	 */
	public SmsContent loadContent(long id) {
		SmsContentDao dao = mDaoSession.getSmsContentDao();
		return dao.load(id);
	}
	
}
