package com.example.minicrpc.databasedao;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import com.example.minicrpc.databasedao.LuckyDog;
import com.example.minicrpc.databasedao.SmsCategory;
import com.example.minicrpc.databasedao.SmsSonCategory;
import com.example.minicrpc.databasedao.SmsContent;

import com.example.minicrpc.databasedao.LuckyDogDao;
import com.example.minicrpc.databasedao.SmsCategoryDao;
import com.example.minicrpc.databasedao.SmsSonCategoryDao;
import com.example.minicrpc.databasedao.SmsContentDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig luckyDogDaoConfig;
    private final DaoConfig smsCategoryDaoConfig;
    private final DaoConfig smsSonCategoryDaoConfig;
    private final DaoConfig smsContentDaoConfig;

    private final LuckyDogDao luckyDogDao;
    private final SmsCategoryDao smsCategoryDao;
    private final SmsSonCategoryDao smsSonCategoryDao;
    private final SmsContentDao smsContentDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        luckyDogDaoConfig = daoConfigMap.get(LuckyDogDao.class).clone();
        luckyDogDaoConfig.initIdentityScope(type);

        smsCategoryDaoConfig = daoConfigMap.get(SmsCategoryDao.class).clone();
        smsCategoryDaoConfig.initIdentityScope(type);

        smsSonCategoryDaoConfig = daoConfigMap.get(SmsSonCategoryDao.class).clone();
        smsSonCategoryDaoConfig.initIdentityScope(type);

        smsContentDaoConfig = daoConfigMap.get(SmsContentDao.class).clone();
        smsContentDaoConfig.initIdentityScope(type);

        luckyDogDao = new LuckyDogDao(luckyDogDaoConfig, this);
        smsCategoryDao = new SmsCategoryDao(smsCategoryDaoConfig, this);
        smsSonCategoryDao = new SmsSonCategoryDao(smsSonCategoryDaoConfig, this);
        smsContentDao = new SmsContentDao(smsContentDaoConfig, this);

        registerDao(LuckyDog.class, luckyDogDao);
        registerDao(SmsCategory.class, smsCategoryDao);
        registerDao(SmsSonCategory.class, smsSonCategoryDao);
        registerDao(SmsContent.class, smsContentDao);
    }
    
    public void clear() {
        luckyDogDaoConfig.getIdentityScope().clear();
        smsCategoryDaoConfig.getIdentityScope().clear();
        smsSonCategoryDaoConfig.getIdentityScope().clear();
        smsContentDaoConfig.getIdentityScope().clear();
    }

    public LuckyDogDao getLuckyDogDao() {
        return luckyDogDao;
    }

    public SmsCategoryDao getSmsCategoryDao() {
        return smsCategoryDao;
    }

    public SmsSonCategoryDao getSmsSonCategoryDao() {
        return smsSonCategoryDao;
    }

    public SmsContentDao getSmsContentDao() {
        return smsContentDao;
    }

}
