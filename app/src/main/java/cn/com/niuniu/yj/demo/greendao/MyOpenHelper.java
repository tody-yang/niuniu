package cn.com.niuniu.yj.demo.greendao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.greenrobot.greendao.database.Database;

/**
 * <h1>自定义数据库操作类</h1>
 * <p>详细功能说明
 *
 * @author: niuniu
 * @date: 2017/10/23.
 */

public class MyOpenHelper extends DaoMaster.OpenHelper {
    public MyOpenHelper(Context context, String name) {
        super(context, name);
    }

    public MyOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {

        Log.i("greenDAO", "Upgrading schema from version " + oldVersion + " to " + newVersion + " by dropping all tables");
        /*
         * 有几个表就放几个
         */
        MigrationHelper.getInstance().migrate(db, UserDao.class);
    }
}
