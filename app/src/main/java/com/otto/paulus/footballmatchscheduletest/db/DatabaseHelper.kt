package com.otto.paulus.footballmatchscheduletest.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.otto.paulus.footballmatchscheduletest.R
import org.jetbrains.anko.db.*

class DatabaseHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, ctx.getString(R.string.database_name), null, 1) {
    companion object {
        private var instance: DatabaseHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): DatabaseHelper {
            if (instance == null) {
                instance = DatabaseHelper(ctx.applicationContext)
            }
            return instance as DatabaseHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Here you create tables
        db.createTable(FavoriteMatch.TABLE_FAVORITE_MATCH, true,
                FavoriteMatch.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                FavoriteMatch.MATCH_ID to TEXT + UNIQUE,
                FavoriteMatch.MATCH_DATE to TEXT,
                FavoriteMatch.HOME_TEAM_ID to TEXT,
                FavoriteMatch.HOME_TEAM_NAME to TEXT,
                FavoriteMatch.HOME_SCORE to TEXT,
                FavoriteMatch.AWAY_TEAM_ID to TEXT,
                FavoriteMatch.AWAY_TEAM_NAME to TEXT,
                FavoriteMatch.AWAY_SCORE to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Here you can upgrade tables, as usual
        db.dropTable(FavoriteMatch.TABLE_FAVORITE_MATCH, true)
    }
}

// Access property for Context
val Context.database: DatabaseHelper
    get() = DatabaseHelper.getInstance(applicationContext)