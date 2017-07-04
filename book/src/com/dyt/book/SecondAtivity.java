package com.dyt.book;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.dyt.bean.Book;
import com.dyt.dao.MyDataBase;
import com.dyt.dao.MyOpenHelper;
import com.example.ibook.R;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/*
 *用来编辑笔记
 *isSave()用来保存数据
 */
public class SecondAtivity extends Activity {

	EditText ed1,ed2;
	Button bt1;
	MyDataBase myDatabase;
	Book book;
	int ids;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);

		ed1=(EditText) findViewById(R.id.editText1);
		ed2=(EditText) findViewById(R.id.editText2);
		bt1=(Button) findViewById(R.id.button1);

		myDatabase=new MyDataBase(this);

		Intent intent=this.getIntent();
		ids=intent.getIntExtra("ids", 0);
		//默认为0，不为0,则为修改数据时跳转过来的
		if(ids!=0){
			//得到要修改的数据
			book=myDatabase.getTiandCon(ids);
			ed1.setText(book.getTitle());
			ed2.setText(book.getContent());
		}		
		//保存按钮的点击事件，调用isSave()方法；
		bt1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				isSave();
			}
		});
	}

	//保存方法
	private void isSave(){
		SimpleDateFormat   formatter   =   new   SimpleDateFormat   ("yyyy.MM.dd  HH:mm:ss");     
		Date   curDate   =   new   Date(System.currentTimeMillis());//获取当前时间     
		String times   =   formatter.format(curDate);      
		String title=ed1.getText().toString();
		String content=ed2.getText().toString();

		//修改笔记
		if(ids!=0){
			book=new Book(title,ids, content, times);
			//修改方法
			myDatabase.toUpdate(book);
			Intent intent=new Intent(SecondAtivity.this,MainActivity.class);
			startActivity(intent);
			SecondAtivity.this.finish();
		}

		//新建笔记
		else{
			book=new Book(title,content,times);
			myDatabase.toInsert(book);
			Intent intent=new Intent(SecondAtivity.this,MainActivity.class);
			startActivity(intent);
			SecondAtivity.this.finish();
		}
	}

}
