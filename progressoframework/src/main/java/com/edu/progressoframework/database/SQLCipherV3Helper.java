package com.edu.progressoframework.database;

import android.content.Context;
import android.content.SharedPreferences;

import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteDatabaseHook;


/**
 * This class is used to Migrate the database from 2.x to 3.x
 */
public class SQLCipherV3Helper implements SQLiteDatabaseHook
{
    private static final String PREFS=
            "net.sqlcipher.database.SQLCipherV3Helper";

    private final Context context;

    public static void resetMigrationFlag(Context ctxt, String dbPath)
    {
        SharedPreferences prefs=
                ctxt.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        prefs.edit().putBoolean(dbPath, false).commit();
    }

    public SQLCipherV3Helper(Context context)
    {
        this.context = context;
    }


    @Override
    public void preKey(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void postKey(SQLiteDatabase sqLiteDatabase) {
        SharedPreferences prefs=
                context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        boolean isMigrated=prefs.getBoolean(sqLiteDatabase.getPath(), false);

        if (!isMigrated) {
            sqLiteDatabase.rawExecSQL("PRAGMA cipher_migrate;");
            prefs.edit().putBoolean(sqLiteDatabase.getPath(), true).commit();
        }
    }
}
