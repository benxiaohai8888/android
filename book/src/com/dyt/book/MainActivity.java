package com.dyt.book;

import java.util.ArrayList;

import com.dyt.bean.Book;
import com.dyt.dao.MyAdapter;
import com.dyt.dao.MyDataBase;
import com.example.ibook.R;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;

/*
 * 这个类主要包括五个点击事件，分别为
 * 1，ListView的长按点击事件，用来AlertDialog来判断是否删除数据。
 * 2，ListView的点击事件，跳转到第二个界面，用来修改数据
 * 3，新建便签按钮的点击事件，跳转到第二界面，用来新建便签
 * 4，menu里的退出事件，用来退出程序
 * 5，menu里的新建事件，用来新建便签
 */
public class MainActivity extends Activity {

	Button bt;
	ListView lv;
	LayoutInflater inflater;
	ArrayList<Book> array;
	MyDataBase mdb;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		lv=(ListView) findViewById(R.id.listView1);
		bt=(Button) findViewById(R.id.button1);
		inflater=getLayoutInflater();

		mdb=new MyDataBase(this);
		//得到笔记数据
		array=mdb.getArray();
		
		MyAdapter adapter=new MyAdapter(inflater,array);
		//显示数据
		lv.setAdapter(adapter);
		/*
		 * 点击listView里面的item,进入到第二个页面，用来修改笔记
		 */
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub				
				Intent intent=new Intent(getApplicationContext(),SecondAtivity.class);
				intent.putExtra("ids",array.get(position).getIds() );
				startActivity(intent);
				MainActivity.this.finish();
			}
		});
		
		/*
		 * 长按判断是否删除数据
		 */
		lv.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					final int position, long id) {
				// TODO Auto-generated method stub
				new AlertDialog.Builder(MainActivity.this)
				.setTitle("你确定要删除吗？")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						mdb.toDelete(array.get(position).getIds());
						array=mdb.getArray();
						MyAdapter adapter=new MyAdapter(inflater,array);
						lv.setAdapter(adapter);
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub

					}
				})

				.create().show();
				return true;
			}
		});
		/*
		 * 添加按钮点击事件，用来新建笔记
		 */
		bt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(getApplicationContext(),SecondAtivity.class);
				startActivity(intent);
				MainActivity.this.finish();
			}
		});					
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.item1:
			Intent intent=new Intent(getApplicationContext(),SecondAtivity.class);
			startActivity(intent);
			this.finish();
			break;
		case R.id.item2:
			this.finish();
			break;
		default:
			break;
		}
		return true;

	}


}
