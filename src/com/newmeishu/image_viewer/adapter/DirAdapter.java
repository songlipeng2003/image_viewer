package com.newmeishu.image_viewer.adapter;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.newmeishu.image_viewer.R;

public class DirAdapter extends BaseAdapter {
	private LayoutInflater listContainer;// 视图容器
	private Context context;
	private String[] dirList;
	private String[] dirNames;

	static class ListItemView { // 自定义控件集合
		public ImageView image;
		public TextView title;
	}

	/**
	 * 实例化Adapter
	 * 
	 * @param context
	 * @param data
	 */
	public DirAdapter(Context context, String[] dirList, String[] dirNames) {
		this.context = context;
		this.dirList = dirList;
		this.dirNames = dirNames;
		this.listContainer = LayoutInflater.from(context); // 创建视图容器并设置上下文
	}

	@Override
	public int getCount() {
		return dirList.length;
	}

	@Override
	public String getItem(int position) {
		return dirList[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// 自定义视图
		ListItemView listItemView = null;

		if (convertView == null) {
			// 获取list_item布局文件的视图
			convertView = listContainer.inflate(R.layout.dir_item, null);

			listItemView = new ListItemView();
			// 获取控件对象
			listItemView.image = (ImageView) convertView
					.findViewById(R.id.imageView);
			listItemView.title = (TextView) convertView
					.findViewById(R.id.titleTextView);

			// 设置控件集到convertView
			convertView.setTag(listItemView);
		} else {
			listItemView = (ListItemView) convertView.getTag();
		}

		// 设置文字和图片
		String dir = dirList[position];
		String dirName = dirNames[position];
		listItemView.title.setText(dirName);

		try {

			String[] files = context.getAssets().list("images/" + dir);

			if (files != null && files.length >= 0) {
				// get input stream
				InputStream inputStream = context.getAssets().open(
						"images/" + dir + "/" + files[0]);
				// load image as Drawable
				Drawable d = Drawable.createFromStream(inputStream, null);
				// set image to ImageView
				listItemView.image.setImageDrawable(d);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		return convertView;
	}
}
