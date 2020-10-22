package com.nathan96169.toolshelper.dboperate;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.nathan96169.toolshelper.GlobalInitBase;
import com.nathan96169.toolshelper.SystemMe;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/28.
 * NULL：空值
 * integer： 带符号的整数，具体取决于存入数字的范围大小
 * smallint 16位整数
 * int 32位整数
 * <p>
 * real：浮点数，存储为8-bytes的浮点数
 * double 64位浮点数
 * float 32位浮点数
 * <p>
 * text：字符串文本
 * blob：二进制对象
 * char(n) n不能炒作254
 * varchar(n) n不能超过4000
 * <p>
 * date
 * time
 * limestamp
 String result = ODBHelper.getInstance().queryCommonInfo("brandsList");
 this.brandsList = DataBrands.fromJsonArray(ODBHelper.convertJsonArray(result));
 */

public class ODBHelper extends SQLiteOpenHelper {
    private static String DB_NAME            = "";//这个是Module名
    private static      int    DB_VERSION         = 2;
    //=============================================
    private static ODBHelper      _instance;
    public static ODBHelper getInstance() {
        if (_instance == null)
            _instance = new ODBHelper();
        return _instance;
    }
    public ODBHelper() {
        //第三个参数CursorFactory指定在执行查询时获得一个游标实例的工厂类,设置为null,代表使用系统默认的工厂类
        super(GlobalInitBase.getContext(), GlobalInitBase.getContext().getPackageName()+".db", null, DB_VERSION);
        DB_NAME = GlobalInitBase.getContext().getPackageName();//这个是包名
    }
    public String getDBName() {
        return DB_NAME;
    }
    //=============================================
    private void doCreateTable(SQLiteDatabase dbb){
        //数据列表,增删改查
        dbb.execSQL("CREATE TABLE IF NOT EXISTS datalistfunall (serialNum LONG PRIMARY KEY,dataType TEXT,datasJson TEXT)");
        //手动保存数据列表
        dbb.execSQL("CREATE TABLE IF NOT EXISTS savedatasmanual (id INTEGER PRIMARY KEY AUTOINCREMENT,insertTime LONG,datasJson TEXT)");
        //用户表 users
        dbb.execSQL("CREATE TABLE IF NOT EXISTS users (uid LONG PRIMARY KEY,token varchar(36))");
        //用户信息表 userinfo
        dbb.execSQL("CREATE TABLE IF NOT EXISTS userinfo (uidkey LONG PRIMARY KEY, valuee TEXT)");//uid+"UIOI,UIOI"+key
        //通用信息表 commoninfo
        dbb.execSQL("CREATE TABLE IF NOT EXISTS commoninfo (keyy TEXT PRIMARY KEY, valuee TEXT)");
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
//        Log.e("DBHelper", "onCreate 从没调用过");
        doCreateTable(db);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        Log.e("DBHelper", "onUpgrade");
        db.execSQL("DROP TABLE IF EXISTS " + "savedatasmanual");
        db.execSQL("DROP TABLE IF EXISTS " + "users");
        db.execSQL("DROP TABLE IF EXISTS " + "userinfo");
        db.execSQL("DROP TABLE IF EXISTS " + "commoninfo");
        doCreateTable(db);
    }
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }
    /**只能是单主渐，联合主渐无用*/
    @Override
    public void onOpen(SQLiteDatabase dbb) {
//        Log.e("DBHelper", "onOpen");
        super.onOpen(dbb);
    }
    public void onExit() {
        SQLiteDatabase db = getWritableDatabase();
        if (db != null) db.close();
    }
    public void execSQL(String sql) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sql);
    }
    public Cursor query(String tableName) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(tableName, null, null, null, null, null, null);
        return c;
    }
    //====================================datalistfunall==========================================
    /**查 dataType区分是哪类数据*/
    public List<DBDataList> queryDataList(long timeFrom,long timeTo,int limitFrom,int size,String type){
        Cursor cursor = null;
        List<DBDataList> result = new ArrayList<>();
        try {
            SQLiteDatabase db = getReadableDatabase();
            cursor = db.rawQuery("SELECT * FROM datalistfunall WHERE dataType='"+type+"' AND serialNum>"+timeFrom+" AND serialNum<"+timeTo+" LIMIT "+limitFrom+","+size,new String[]{});//" AND createTime<"+timeTo+
            while (cursor.getCount()>0 && cursor.moveToNext()) {
                DBDataList object = new DBDataList();
                long serialNum = cursor.getLong(0);
                String dataType = cursor.getString(1);
                String datasJson = cursor.getString(2);
                object.serialNum = serialNum;
                object.dataType = dataType;
                object.datasJson = datasJson;
                result.add(object);
            }
        } catch (Exception e) {
            Log.e("Exception",e.toString());
        }
        if(cursor!=null)cursor.close();
        return result;
    }
    /**改+增 dataType区分是哪类数据*/
    public void changeDataList(long serialNum,String type,String datasJson) {
        SQLiteDatabase db = getWritableDatabase();
        //replace 没有就插，有就改
        ContentValues cv = new ContentValues();
        cv.put("serialNum", serialNum);
        cv.put("dataType", type);
        cv.put("datasJson", datasJson);
        db.replace("datalistfunall", null, cv);
    }
    /**删 dataType区分是哪类数据**/
    public void deleteDataList(long ID){
        SQLiteDatabase db = getWritableDatabase();
        String[] args = {String.valueOf(ID)};
        db.delete("datalistfunall","serialNum=?",args);//"createTime=?", new String[]{""+timeID}
    }
    public void deleteDataListAll(){
        SQLiteDatabase db = getWritableDatabase();
        db.delete("datalistfunall", null,null);//"createTime>?", new String[]{"0"}
    }
    //====================================ManualData即时数据手动保存==========================================
    /**insert**/
    public void insertManualData(String datasJson) {//long id,
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("insertTime", System.currentTimeMillis());
        cv.put("datasJson", datasJson);
        db.insert("savedatasmanual", null, cv);
    }
    /**delete**/
    public void deleteManualData(long ID){
        SQLiteDatabase db = getWritableDatabase();
        db.delete("savedatasmanual","id="+ID,null);//"createTime=?", new String[]{""+timeID}
    }
    public void deleteManualDataAll(){
        SQLiteDatabase db = getWritableDatabase();
        db.delete("savedatasmanual", null,null);//"createTime>?", new String[]{"0"}
    }
    /**query* WHERE createTime>"+timeFrom+"*/
    public List<JsonObject> queryManualData(long timeFrom,long timeTo,int limitFrom,int size){
        Cursor cursor = null;
        List<JsonObject> result = new ArrayList<>();
        try {
            SQLiteDatabase db = getReadableDatabase();
            cursor = db.rawQuery("SELECT * FROM savedatasmanual WHERE insertTime>"+timeFrom+" AND insertTime<"+timeTo+" LIMIT "+limitFrom+","+size,new String[]{});//" AND createTime<"+timeTo+
            while (cursor.getCount()>0 && cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String value = cursor.getString(2);
                JsonObject object = convertJsonObject(value);
                object.addProperty("id",id);
                result.add(object);
            }
        } catch (Exception e) {
            Log.e("Exception",e.toString());
        }
        if(cursor!=null)cursor.close();
        return result;
    }
    //====================================增==========================================
    public void changeUserInfo(long uid, String key, String value) {
        if (uid == 0 || key == null || key.length() == 0) return;
        SQLiteDatabase db = getWritableDatabase();
        //replace 没有就插，有就改
        ContentValues cv = new ContentValues();
        cv.put("uidkey", uid+"UIOI,UIOI"+key);
        cv.put("valuee", value);
        db.replace("userinfo", "uidkey", cv);
    }
    public void changeCommonInfo(String key, String value) {
        if (key == null || key.length() == 0) return;
//        Log.e("DBHelper", "changeCommonInfo:" + key + "   " + str);
        SQLiteDatabase db = getWritableDatabase();
        //replace 没有就插，有就改
        ContentValues cv = new ContentValues();
        cv.put("keyy", key);
        cv.put("valuee", value);
        db.replace("commoninfo", "keyy", cv);
    }

    //====================================查==========================================
    public String queryUserInfo(long uid, String key) {
        Cursor cursor = null;
        String result = "";
        try {
            SQLiteDatabase db = getReadableDatabase();
            cursor = db.query("userinfo", new String[]{"valuee"}, "uidkey=?", new String[]{uid+"UIOI,UIOI"+key}, null, null, null);
            while (cursor.getCount()>0 && cursor.moveToNext()) {
                result = cursor.getString(0);
            }
        } catch (Exception e) {
            Log.e("Exception",e.toString());
        }
        if(cursor!=null)cursor.close();
        return result;
    }
    public String queryCommonInfo(String key) {
        Cursor cursor = null;
        String result = "";
        try {
            SQLiteDatabase db = getReadableDatabase();
            cursor = db.query("commoninfo", new String[]{"valuee"}, "keyy=?", new String[]{"" + key}, null, null, null);
            while (cursor.getCount()>0 && cursor.moveToNext()) {
                result = cursor.getString(0);
            }
        } catch (Exception e) {
            Log.e("Exception",e.toString());
        }
        if(cursor!=null)cursor.close();
        return result;
    }
    //=========================static==========================================
    public static JsonObject convertJsonObject(String json){
        if(TextUtils.isEmpty(json))return new JsonObject();
        try {
            Gson gson   = new Gson();
            JsonObject result = gson.fromJson(json, JsonObject.class);
            return result;
        }catch (Exception e){
            return new JsonObject();
        }
    }
    public static JsonArray convertJsonArray(String json){
        if(TextUtils.isEmpty(json))return new JsonArray();
        try {
            Gson       gson   = new Gson();
            JsonArray result = gson.fromJson(json, JsonArray.class);
            return result;
        }catch (Exception e){
            return new JsonArray();
        }
    }
    public static String convertString(JsonObject jsonObject){
        String result = null;
        try{
            result = new String(jsonObject.toString().getBytes(SystemMe.UTF8), SystemMe.UTF8);
        } catch (UnsupportedEncodingException e) {
//            Log.e("DBHelper","convertString JsonObject->不支持的编码:"+jsonObject.toString());
        }
        return result;
    }
    public static String convertString(JsonArray jsonArray){
        String result = null;
        try{
            result = new String(jsonArray.toString().getBytes(SystemMe.UTF8), SystemMe.UTF8);
        } catch (UnsupportedEncodingException e) {
//            Log.e("DBHelper","convertString JsonArray->不支持的编码"+jsonArray.toString());
        }
        return result;
    }

}