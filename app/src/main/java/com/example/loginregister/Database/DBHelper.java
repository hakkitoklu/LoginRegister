//https://github.com/mitchtabian/SaveReadWriteDeleteSQLite/tree/master/SaveAndDisplaySQL/app/src/main/java/com/tabian/saveanddisplaysql


package com.example.loginregister.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.example.loginregister.Objects.Data;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME="database";

    public DBHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Data.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String query="DROP TABLE IF EXISTS "+Data.TABLE_NAME;
        db.execSQL(query);
        this.onCreate(db);

    }
    public long addData(String column1,String column2,String column3,String column4,String column5,
                        String item1,String item2,String item3,String item4,String item5){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(column1,item1);
        contentValues.put(column2,item2);
        contentValues.put(column3,item3);
        contentValues.put(column4,item4);
        contentValues.put(column5,item5);

        long result=db.insert(Data.TABLE_NAME,null,contentValues);
        db.close();
        return result;
    }

    public Data getData(long id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor=db.query(Data.TABLE_NAME,
                new String[]{Data.COLUMN_ID, Data.COLUMN_PLATFORM, Data.COLUMN_USERNAME, Data.COLUMN_PASSWORD,
                        Data.COLUMN_SCKEY, Data.COLUMN_IVKEY},Data.COLUMN_ID+ "=?",
                new String[]{String.valueOf(id)}, null , null, null, null);
        if(cursor !=null)
            cursor.moveToFirst();

        Data data=new Data(
                cursor.getInt(cursor.getColumnIndex(Data.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(Data.COLUMN_PLATFORM)),
                cursor.getString(cursor.getColumnIndex(Data.COLUMN_USERNAME)),
                cursor.getString(cursor.getColumnIndex(Data.COLUMN_PASSWORD)),
                cursor.getString(cursor.getColumnIndex(Data.COLUMN_SCKEY)),
                cursor.getString(cursor.getColumnIndex(Data.COLUMN_IVKEY)));
        cursor.close();
        return data;
    }

    public List<Data> getAllData(){
        List<Data> datas=new ArrayList<>();
        String selectQuery="SELECT * FROM " + Data.TABLE_NAME + " ORDER BY " +
                Data.COLUMN_ID + " ASC";
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery(selectQuery,null);

        if (cursor.moveToFirst()){
            do{
                Data data=new  Data();
                data.setId(cursor.getInt(cursor.getColumnIndex(Data.COLUMN_ID)));
                data.setUsername(cursor.getString(cursor.getColumnIndex(Data.COLUMN_PLATFORM)));
                data.setUsername(cursor.getString(cursor.getColumnIndex(Data.COLUMN_USERNAME)));
                data.setPassword(cursor.getString(cursor.getColumnIndex(Data.COLUMN_PASSWORD)));
                data.setSecretkey(cursor.getString(cursor.getColumnIndex(Data.COLUMN_SCKEY)));
                data.setIv(cursor.getString(cursor.getColumnIndex(Data.COLUMN_IVKEY)));

                datas.add(data);
            }while (cursor.moveToNext());
        }
        db.close();
        return datas;
    }

    public void deleteData(Data data){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(Data.TABLE_NAME, Data.COLUMN_ID + " = ?",
                new String[]{String.valueOf(data.getId())});
        db.close();

    }

    public int getDataCount() {
        String countQuery = "SELECT  * FROM " + Data.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        // return count
        return count;
    }

    public boolean updateData(String s1,String s2,String s3,String s4){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(Data.COLUMN_PASSWORD,s2);
        contentValues.put(Data.COLUMN_SCKEY,s3);
        contentValues.put(Data.COLUMN_IVKEY,s4);
        db.update(Data.TABLE_NAME,contentValues, Data.COLUMN_ID + " = ?",new String[] {s1});
        return true;

    }
}