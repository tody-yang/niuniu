package cn.com.niuniu.yj.demo.greendao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import cn.com.niuniu.yj.demo.entity.KeyEntity;

/**
 * <h1>自定义数据库管理类</h1>
 * <p>详细功能说明
 *
 * @author: niuniu
 * @date: 2017/10/23.
 */

public class MyDbManager {

    // 是否加密
    public static final boolean ENCRYPTED = true;

    private static MyDbManager mDbManager;
    private static DaoMaster.DevOpenHelper mDevOpenHelper;
    private static DaoMaster mDaoMaster;
    private static DaoSession mDaoSession;

    private Context mContext;


    private MyDbManager(Context context) {
        this.mContext = context;
        // 初始化数据库信息
        mDevOpenHelper = new DaoMaster.DevOpenHelper(context, KeyEntity.DB_NAME);
        getDaoMaster(context);
        getDaoSession(context);
    }

    public static MyDbManager getInstance(Context context) {
        if (null == mDbManager) {
            synchronized (MyDbManager.class) {
                if (null == mDbManager) {
                    mDbManager = new MyDbManager(context);
                }
            }
        }
        return mDbManager;
    }

    /**
     * 获取可读数据库
     *
     * @param context
     * @return
     */
    public static SQLiteDatabase getReadableDatabase(Context context) {
        if (null == mDevOpenHelper) {
            getInstance(context);
        }
        return mDevOpenHelper.getReadableDatabase();
    }

    /**
     * 获取可写数据库
     *
     * @param context
     * @return
     */
    public static SQLiteDatabase getWritableDatabase(Context context) {
        if (null == mDevOpenHelper) {
            getInstance(context);
        }
        return mDevOpenHelper.getWritableDatabase();
    }

    /**
     * 获取DaoMaster
     *
     * 判断是否存在数据库，如果没有则创建数据库
     * @param context
     * @return
     */
    public static DaoMaster getDaoMaster(Context context) {
        if (null == mDaoMaster) {
            synchronized (MyDbManager.class) {
                if (null == mDaoMaster) {
                    MyOpenHelper helper = new MyOpenHelper(context,KeyEntity.DB_NAME,null);
                    mDaoMaster = new DaoMaster(helper.getWritableDatabase());
                }
            }
        }
        return mDaoMaster;
    }

    /**
     * 获取DaoMaster
     *
     * @param context
     * @return
     */
//    public static DaoMaster getDaoMaster(Context context) {
//        if (null == mDaoMaster) {
//            synchronized (DbManager.class) {
//                if (null == mDaoMaster) {
//
//                    mDaoMaster = new DaoMaster(getWritableDatabase(context));
//                }
//            }
//        }
//        return mDaoMaster;
//    }

    /**
     * 获取DaoSession
     *
     * @param context
     * @return
     */
    public static DaoSession getDaoSession(Context context) {
        if (null == mDaoSession) {
            synchronized (MyDbManager.class) {
                mDaoSession = getDaoMaster(context).newSession();
            }
        }

        return mDaoSession;
    }

}
