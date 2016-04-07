package hasee.com.loveweather.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 爱天气的数据库操作帮助类
 * Created by Hasee on 2016/4/7.
 */
public class LoveWeatherOpenHelper extends SQLiteOpenHelper {

    /**
     * Province表建表语句
     */
    public static final String CREATE_PROVINCE = "create table Province ("
            + "id integer primary key autoincrement, "
            + "province_name text, "
            + "province_code text)";
    /**
     * City表建表语句
     */
    public static final String CREATE_CITY = "create table City ("
            + "id integer primary key autoincrement, "
            + "city_name text, "
            + "city_code text, "
            + "province_id integer)";
    /**
     * County表建表语句
     */
    public static final String CREATE_COUNTY = "create table County ("
            + "id integer primary key autoincrement, "
            + "county_name text, "
            + "county_code text, "
            + "city_id integer)";

    //构造方法进行初始化
    public LoveWeatherOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /**
     * 重写的默认的数据库创建方法
     *
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建省的数据库表
        db.execSQL(CREATE_PROVINCE);
        //创建市的数据库表
        db.execSQL(CREATE_CITY);
        //创建县的数据库表
        db.execSQL(CREATE_COUNTY);
    }

    /**
     * 重写默认的数据库升级方法
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
