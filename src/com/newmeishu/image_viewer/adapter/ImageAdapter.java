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
	private LayoutInflater listContainer;// ��ͼ����
	private File[] files;

	static class ListItemView { // �Զ���ؼ�����
		public ImageView image;
	}

	/**
	 * ʵ����Adapter
	 * 
	 * @param context
	 * @param data
	 */
	public ImageAdapter(Context context, File dir) {
		this.listContainer = LayoutInflater.from(context); // ������ͼ����������������
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
		// �Զ�����ͼ
		ListItemView listItemView = null;

		if (convertView == null) {
			// ��ȡlist_item�����ļ�����ͼ
			convertView = listContainer.inflate(R.layout.image_item, null);

			listItemView = new ListItemView();
			// ��ȡ�ؼ�����
			listItemView.image = (ImageView) convertView
					.findViewById(R.id.imageView);

			// ���ÿؼ�����convertView
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
