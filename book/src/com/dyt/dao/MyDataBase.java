package com.dyt.dao;

import java.util.ArrayList;

import com.dyt.bean.Book;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
/*
 * 数据库操作类
 * 实现数据的增，删，改，查
 */
public class MyDataBase {
	
	Context context;
	MyOpenHelper myHelper;
	SQLiteDatabase myDatabase;
	/*
	 * 实例化这个类的同时，创建数据库
	 */
	public MyDataBase(Context con){
		this.context=con;
		myHelper=new MyOpenHelper(context);
	}
	/*
	 * 得到填充ListView用的array数据，从数据库里查找后解析。第一个界面调用
	 */
	public ArrayList<Book> getArray(){
		ArrayList<Book> array=new ArrayList<Book>();
		ArrayList<Book> array1=new ArrayList<Book>();
		myDatabase=myHelper.getWritableDatabase();
		Cursor cursor=myDatabase.rawQuery("select ids,title,times from mybook" , null);
		cursor.moveToFirst();
		while(!cursor.isAfterLast()){
			int id=cursor.getInt(cursor.getColumnIndex("ids"));
			String title=cursor.getString(cursor.getColumnIndex("title"));
			String times=cursor.getString(cursor.getColumnIndex("times"));
			Book book=new Book(id, title, times);
			array.add(book);
			cursor.moveToNext();
		}
		myDatabase.close();
		for (int i = array.size(); i >0; i--) {
			array1.add(array.get(i-1));
		}
		return array1;		
	}
	
	/*
	 * 返回可能要修改的数据，第二个界面调用。
	 */
	public Book getTiandCon(int id){
		myDatabase=myHelper.getWritableDatabase();
		Cursor cursor=myDatabase.rawQuery("select title,content from mybook where ids='"+id+"'" , null);
		cursor.moveToFirst();
		String title=cursor.getString(cursor.getColumnIndex("title"));
		String content=cursor.getString(cursor.getColumnIndex("content"));
		Book cun=new Book(title,content);
		myDatabase.close();
		return cun;
	}
	/*
	 * 第二个界面调用，用来修改笔记
	 */
	public void toUpdate(Book book){
		myDatabase=myHelper.getWritableDatabase();
		myDatabase.execSQL("update mybook set title='"+ book.getTitle()+"',times='"+book.getTimes()+"',content='"+book.getContent() +"' where ids='"+ book.getIds()+"'");
		myDatabase.close();
	}
	/*
	 *第二个界面调用，用来增加新的笔记
	 */
	public void toInsert(Book book){
		myDatabase=myHelper.getWritableDatabase();
		myDatabase.execSQL("insert into mybook(title,content,times)values('"+ book.getTitle()+"','"+book.getContent()+"','"+book.getTimes()+"')");
		myDatabase.close();
	}
	/*
	 * 第一个界面调用，长按点击后选择删除笔记
	 */
	public void toDelete(int ids){
		myDatabase=myHelper.getWritableDatabase();
		myDatabase.execSQL("delete  from mybook where ids="+ids+"");
		myDatabase.close();
	}
}
