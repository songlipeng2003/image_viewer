package com.newmeishu.image_viewer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.newmeishu.image_viewer.adapter.ImageAdapter;

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

		ImageAdapter imageAdapter = new ImageAdapter(getApplicationContext(),
				dir);
		imageGridView.setAdapter(imageAdapter);
		imageGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long id) {
				Intent intent = new Intent(getApplicationContext(),
						ImageViewerActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("dir", dir);
				bundle.putString("dirName", dirName);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
	}

}
