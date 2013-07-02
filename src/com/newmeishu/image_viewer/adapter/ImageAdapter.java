package com.newmeishu.image_viewer.adapter;

import java.io.File;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.newmeishu.image_viewer.ui.GalleryImageView;

public class ImageAdapter extends BaseAdapter {
	// private LayoutInflater listContainer;// 视图容器
	private File[] files;
	private Context context;

	static class ListItemView { // 自定义控件集合
		public GalleryImageView image;
	}

	/**
	 * 实例化Adapter
	 * 
	 * @param context
	 * @param data
	 */
	public ImageAdapter(Context context, File dir) {
		this.context = context;
		// this.listContainer = LayoutInflater.from(context); // 创建视图容器并设置上下文
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
		File file = getItem(position);
		//
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inDither = false; // Disable Dithering mode
		opts.inPurgeable = true;
		opts.inSampleSize = 3;
		// opts.inJustDecodeBounds = true;
		Bitmap bmp = BitmapFactory.decodeFile(file.getAbsolutePath(), opts);

		GalleryImageView image = new GalleryImageView(context, opts.outWidth,
				opts.outHeight);

		image.setImageBitmap(bmp);

		// bmp.recycle();

		// GalleryImageView image = new GalleryImageView(context);
		//
		// String uri = "file://" + file.getAbsolutePath();
		// ImageLoader.getInstance().displayImage(uri, image, options);

		return image;
	}
}
