package com.newmeishu.image_viewer.adapter;

import java.io.File;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.newmeishu.image_viewer.R;

public class DirAdapter extends BaseAdapter {
	private LayoutInflater listContainer;// 视图容器
	private File[] files;

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
	public DirAdapter(Context context, File dir) {
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
		File file = getItem(position);

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
		listItemView.title.setText(file.getName());

		if (file.isDirectory()) {
			listItemView.image.setImageResource(R.drawable.floder);
		} else {
			//
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inDither = false; // Disable Dithering mode
			opts.inPurgeable = true;
			opts.inSampleSize = 5;
			// opts.inJustDecodeBounds = true;
			Bitmap bmp = BitmapFactory.decodeFile(file.getAbsolutePath(), opts);

			listItemView.image.setImageBitmap(bmp);
		}

		return convertView;
	}
}
