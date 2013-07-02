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
	private LayoutInflater listContainer;// ��ͼ����
	private File[] files;

	static class ListItemView { // �Զ���ؼ�����
		public ImageView image;
		public TextView title;
	}

	/**
	 * ʵ����Adapter
	 * 
	 * @param context
	 * @param data
	 */
	public DirAdapter(Context context, File dir) {
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
		File file = getItem(position);

		if (convertView == null) {
			// ��ȡlist_item�����ļ�����ͼ
			convertView = listContainer.inflate(R.layout.dir_item, null);

			listItemView = new ListItemView();
			// ��ȡ�ؼ�����
			listItemView.image = (ImageView) convertView
					.findViewById(R.id.imageView);
			listItemView.title = (TextView) convertView
					.findViewById(R.id.titleTextView);

			// ���ÿؼ�����convertView
			convertView.setTag(listItemView);
		} else {
			listItemView = (ListItemView) convertView.getTag();
		}

		// �������ֺ�ͼƬ
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
