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

	public static float screenWidth = 480f;
	public static float screenHeight = 800f;

	private GridView dirGridView;

	private DirAdapter dirAdapter;

	private String path;

	private File dir;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		screenWidth = getWindow().getWindowManager().getDefaultDisplay()
				.getWidth();
		screenHeight = getWindow().getWindowManager().getDefaultDisplay()
				.getHeight();

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

				Bundle bundle = new Bundle();
				if (file.isDirectory()) {
					intent = new Intent(getApplicationContext(),
							MainActivity.class);
					bundle.putString("path", file.getAbsolutePath());
				} else {
					intent = new Intent(getApplicationContext(),
							ImageViewerActivity.class);
					bundle.putString("path", dir.getAbsolutePath());
				}

				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
	}
}
