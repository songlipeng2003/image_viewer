package com.newmeishu.image_viewer.adapter;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.newmeishu.image_viewer.R;

public class ImageAdapter extends BaseAdapter {
	private List<String> listItems;// 数据集合
	private LayoutInflater listContainer;// 视图容器
	private String dir;
	private Context context;

	static class ListItemView { // 自定义控件集合
		public ImageView image;
	}

	/**
	 * 实例化Adapter
	 * 
	 * @param context
	 * @param data
	 */
	public ImageAdapter(Context context, String dir) {
		this.context = context;
		this.dir = dir;
		this.listContainer = LayoutInflater.from(context); // 创建视图容器并设置上下文
		this.listItems = new ArrayList<String>();
		String str[] = {};
		try {
			str = context.getAssets().list("images/" + dir);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (String s : str) {
			listItems.add(s);
		}
	}

	@Override
	public int getCount() {
		return listItems.size();
	}

	@Override
	public String getItem(int position) {
		return listItems.get(position);
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

		// 设置文字和图片
		String fileName = listItems.get(position);

		try {
			// get input stream
			InputStream inputStream = context.getAssets().open(
					"images/" + dir + "/" + fileName);
			// load image as Drawable
			Drawable d = Drawable.createFromStream(inputStream, null);
			// set image to ImageView
			listItemView.image.setImageDrawable(d);
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		return convertView;
	}

	public List<String> getListItems() {
		return listItems;
	}

	public void setListItems(List<String> listItems) {
		this.listItems = listItems;
	}

}
