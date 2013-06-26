package com.newmeishu.image_viewer.adapter;

import java.io.File;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.newmeishu.image_viewer.R;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ImageAdapter extends BaseAdapter {
	private LayoutInflater listContainer;// 视图容器
	private File[] files;

	static class ListItemView { // 自定义控件集合
		public ImageView image;
	}

	/**
	 * 实例化Adapter
	 * 
	 * @param context
	 * @param data
	 */
	public ImageAdapter(Context context, File dir) {
		this.listContainer = LayoutInflater.from(context); // 创建视图容器并设置上下文
		if (dir == null || !dir.isDirectory()) {
			files = new File[0];
		} else {
			files = dir.listFiles();
		}
	}

	@Override
	public int getCount() {
		return files.length;
	}

	@Override
	public File getItem(int position) {
		return files[position];
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
			convertView = listContainer.inflate(R.layout.image_item, null);

			listItemView = new ListItemView();
			// 获取控件对象
			listItemView.image = (ImageView) convertView
					.findViewById(R.id.imageView);

			// 设置控件集到convertView
			convertView.setTag(listItemView);
		} else {
			listItemView = (ListItemView) convertView.getTag();
		}

		File file = getItem(position);
		String uri = "file://" + file.getAbsolutePath();
		ImageLoader.getInstance().displayImage(uri, listItemView.image);

		return convertView;
	}
}
