package com.bcr.greendaosimpleexample;

import android.app.Application;
import android.content.Context;

import com.bcr.greendaosimpleexample.data.DaoMaster;
import com.bcr.greendaosimpleexample.data.DaoSession;

import org.greenrobot.greendao.database.Database;

/**
 * Created by dot on 10/27/17.
 */

public class App extends Application {

    public static Context context;

    /** A flag to show how easily you can switch from standard SQLite to the encrypted SQLCipher. */
    public static final boolean ENCRYPTED = true;

    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        this.context = this;
        setupDAO();
    }

    private void setupDAO() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, ENCRYPTED ? "greendao-db-encrypted" : "greendao-db");
        Database db = ENCRYPTED ? helper.getEncryptedWritableDb("super-secret") : helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

}
