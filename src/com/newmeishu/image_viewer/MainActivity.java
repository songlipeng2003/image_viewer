package com.newmeishu.image_viewer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.newmeishu.image_viewer.adapter.DirAdapter;

public class MainActivity extends Activity {

	private GridView dirGridView;

	private String[] dir_list;
	private String[] dir_names;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		dir_list = getResources().getStringArray(R.array.dir_list);
		dir_names = getResources().getStringArray(R.array.dir_names);
		dirGridView = (GridView) findViewById(R.id.dirGridView);
		DirAdapter dirAdapter = new DirAdapter(getApplicationContext(),
				dir_list, dir_names);
		dirGridView.setAdapter(dirAdapter);
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
	}
}
