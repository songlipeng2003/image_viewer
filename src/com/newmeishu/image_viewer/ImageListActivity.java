package com.newmeishu.image_viewer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.SimpleAdapter;

public class ImageListActivity extends Activity {

	private GridView imageGridView;

	private String dir;
	private String dirName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_list);

		imageGridView = (GridView) findViewById(R.id.imageGridView);
		dir = getIntent().getExtras().getString("dir");
		dirName = getIntent().getExtras().getString("dirName");

		ArrayList<HashMap<String, Object>> items = new ArrayList<HashMap<String, Object>>();

		try {
			String str[] = this
					.createPackageContext("com.newmeishu.image_viewer",
							Context.CONTEXT_IGNORE_SECURITY).getAssets()
					.list("images/" + dir);

			for (String s : str) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("imageView", R.drawable.ic_launcher);// 添加图像资源的ID
				map.put("textView", s);
				items.add(map);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		imageGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
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
		imageGridView.setAdapter(itemsAdapter);
	}

}
