package com.newmeishu.image_viewer.adapter;

import java.io.File;

import android.content.Context;
import android.graphics.Bitmap.CompressFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.newmeishu.image_viewer.R;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

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

		File cacheDir = StorageUtils.getCacheDirectory(context);
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context)
				.memoryCacheExtraOptions(480, 800)
				// default = device screen dimensions
				.discCacheExtraOptions(480, 800, CompressFormat.JPEG, 75)
				.threadPoolSize(3)
				// default
				.threadPriority(Thread.NORM_PRIORITY - 1)
				// default
				.tasksProcessingOrder(QueueProcessingType.FIFO)
				// default
				.denyCacheImageMultipleSizesInMemory()
				.memoryCache(new LruMemoryCache(2 * 1024 * 1024))
				.memoryCacheSize(2 * 1024 * 1024)
				.discCache(new UnlimitedDiscCache(cacheDir))
				// default
				.discCacheSize(50 * 1024 * 1024).discCacheFileCount(100)
				.discCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
				.imageDownloader(new BaseImageDownloader(context)) // default
				.imageDecoder(new BaseImageDecoder()) // default
				.defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
				.enableLogging().build();

		ImageLoader.getInstance().init(config);
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
			String uri = "file://" + file.getAbsolutePath();
			ImageLoader.getInstance().displayImage(uri, listItemView.image);
		}

		return convertView;
	}
}
