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
				map.put("imageView", R.drawable.ic_launcher);// ���ͼ����Դ��ID
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

		// ������������ImageItem <====> ��̬�����Ԫ�أ�����һһ��Ӧ
		SimpleAdapter itemsAdapter = new SimpleAdapter(this, // ûʲô����
				items,// ������Դ
				R.layout.dir_item,// night_item��XMLʵ��

				// ��̬������ImageItem��Ӧ������
				new String[] { "imageView", "textView" },

				// ImageItem��XML�ļ������һ��ImageView,����TextView ID
				new int[] { R.id.imageView, R.id.textView });
		// ��Ӳ�����ʾ
		imageGridView.setAdapter(itemsAdapter);
	}

}
