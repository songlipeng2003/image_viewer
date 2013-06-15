package com.newmeishu.image_viewer;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.SimpleAdapter;

public class MainActivity extends Activity {

	private GridView dirGridView;

	private String[] dir_list;
	private String[] dir_names;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		dirGridView = (GridView) findViewById(R.id.dirGridView);

		ArrayList<HashMap<String, Object>> items = new ArrayList<HashMap<String, Object>>();

		dir_list = getResources().getStringArray(R.array.dir_list);
		dir_names = getResources().getStringArray(R.array.dir_names);

		for (int i = 0; i < dir_list.length; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("imageView", R.drawable.ic_launcher);// 添加图像资源的ID
			map.put("textView", dir_names[i]);
			items.add(map);
		}

		dirGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long id) {
				Intent intent = new Intent(getApplicationContext(),
						ImageListActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("dir", dir_list[position]);
				bundle.putString("dirName", dir_names[position]);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});

		// 生成适配器的ImageItem <====> 动态数组的元素，两者一一对应
		SimpleAdapter itemsAdapter = new SimpleAdapter(this, // 没什么解释
				items,// 数据来源
				R.layout.dir_item,// night_item的XML实现

				// 动态数组与ImageItem对应的子项
				new String[] { "imageView", "textView" },

				// ImageItem的XML文件里面的一个ImageView,两个TextView ID
				new int[] { R.id.imageView, R.id.textView });
		// 添加并且显示
		dirGridView.setAdapter(itemsAdapter);
	}
}
