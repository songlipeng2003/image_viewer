package com.newmeishu.image_viewer;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.newmeishu.image_viewer.adapter.DirAdapter;

public class MainActivity extends Activity {
	private static final String IMAGE_DIR = "/DCIM/";

	private static final String TAG = "activity";

	private GridView dirGridView;

	private DirAdapter dirAdapter;

	private String path;

	private File dir;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Intent intent = getIntent();
		path = intent.getStringExtra("path");
		if (path == null) {
			path = Environment.getExternalStorageDirectory() + IMAGE_DIR;
		}
		Log.d(TAG, path);
		dir = new File(path);
		dirAdapter = new DirAdapter(getApplicationContext(), dir);
		dirGridView = (GridView) findViewById(R.id.dirGridView);
		dirGridView.setAdapter(dirAdapter);
		dirGridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long id) {
				Intent intent = null;
				File file = dirAdapter.getItem(position);
				if (file.isDirectory()) {
					intent = new Intent(getApplicationContext(),
							MainActivity.class);
				} else {
					intent = new Intent(getApplicationContext(),
							ImageViewerActivity.class);
				}

				Bundle bundle = new Bundle();
				bundle.putString("path", file.getAbsolutePath());
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
	}
}
