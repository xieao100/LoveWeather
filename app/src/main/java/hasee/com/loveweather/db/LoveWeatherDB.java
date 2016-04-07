package hasee.com.loveweather.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import hasee.com.loveweather.model.City;
import hasee.com.loveweather.model.County;
import hasee.com.loveweather.model.Province;

/**
 * 封装数据库常用的操作
 * Created by Hasee on 2016/4/7.
 */
public class LoveWeatherDB {

    /**
     * 数据库的名字
     */
    public static final String DB_NAME = "love_weather";
    /**
     * 数据库版本
     */
    public static final int VERSON = 1;

    private static LoveWeatherDB loveWeatherDB;

    private SQLiteDatabase db;

    /**
     * 构造方法私有化
     */
    private LoveWeatherDB(Context context) {
        LoveWeatherOpenHelper dbOpenHelper = new LoveWeatherOpenHelper(context, DB_NAME, null, VERSON);
        db = dbOpenHelper.getWritableDatabase();
    }

    /**
     * 获取LoveWeather的实例
     */
    public synchronized static LoveWeatherDB getInstance(Context context) {
        if (loveWeatherDB != null) {
            loveWeatherDB = new LoveWeatherDB(context);
        }
        return loveWeatherDB;
    }

    /**
     * 数据库两大核心功能 存与取
     * Province省 数据的存于取
     */
    public void saveProvince(Province province) {
        //当省不为空的时候将其实例存储
        if (province != null) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("province_name", province.getProvinceName());
            contentValues.put("province_code", province.getProvinceCode());
            //通过数据库实例化对象插入数据
            db.insert("Province", null, contentValues);
        }
    }

    /**
     * 加载省级数据
     */
    public List<Province> loadProvince() {
        //创建一个list集合，泛型是Province
        List<Province> list = new ArrayList<Province>();
        //创建一个cursor
        Cursor cursor = db.query("Province", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Province province = new Province();
                province.setId(cursor.getInt(cursor.getColumnIndex("id")));
                province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
                province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
                list.add(province);
            } while (cursor.moveToNext());
        }
        return list;
    }

    /**
     * 将City实例存储到数据库中
     */
    public void saveCity(City city) {
        if (city != null) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("city_name", city.getCityName());
            contentValues.put("city_code", city.getCityCode());
            contentValues.put("province_id", city.getProvinceId());
            db.insert("City", null, contentValues);
        }
    }

    /**
     * 从数据库中读取某个省下所有城市的信息
     */
    public List<City> loadCity(int provinceId) {
        //创建集合用来存储城市数据
        List<City> list = new ArrayList<City>();
        //创建游标
        Cursor cursor = db.query("City",null,"province_id = ?",new String[]{String.valueOf(provinceId)},null,null,null);
        if (cursor.moveToFirst()){
            do {
                City city = new City();
                city.setId(cursor.getInt(cursor.getColumnIndex("id")));
                city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
                city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
                city.setId(provinceId);
                list.add(city);
            }while (cursor.moveToNext());
        }
        return list;
    }
    /**
     * 将County实例存储到数据库
     */
    public void saveCounty(County county){
        if (county!=null){
            ContentValues contentValues = new ContentValues();
            contentValues.put("county_name",county.getCountyName());
            contentValues.put("county_code",county.getCountyCode());
            contentValues.put("city_id",county.getCityId());
            db.insert("County",null,contentValues);
        }
    }
    /**
     * 从数据库读取某个城市下所有县的信息
     */
    public List<County> loadCounties(int cityId){
        //新建集合用来存储数据
        List<County> list = new ArrayList<County>();
        //创建游标
        Cursor cursor = db.query("County",null,"city_id = ?",new String[]{String.valueOf(cityId)},null,null,null);
        //移动游标循环读取
        if (cursor.moveToFirst()){
            do {
                County county = new County();
                county.setId(cursor.getInt(cursor.getColumnIndex("city_id")));
                county.setCountyName(cursor.getString(cursor.getColumnIndex("city_name")));
                county.setCountyCode(cursor.getString(cursor.getColumnIndex("city_code")));
                county.setCityId(cityId);
                //将county添加到集合中
                list.add(county);
            }while (cursor.moveToNext());
        }
        return list;
    }
}
