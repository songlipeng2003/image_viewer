package com.newmeishu.image_viewer;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Gallery;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher.ViewFactory;

import com.newmeishu.image_viewer.adapter.ImageAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;

public class ImageViewerActivity extends Activity {
	private Gallery gallery;
	private ImageSwitcher imageSwitcher;

	private String path;

	private File dir;

	private int downX, upX;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_viewer);
		Intent intent = getIntent();
		path = intent.getStringExtra("path");
		dir = new File(path);

		gallery = (Gallery) findViewById(R.id.gallery);
		imageSwitcher = (ImageSwitcher) findViewById(R.id.imageSwitcher);

		gallery.setAdapter(new ImageAdapter(this, dir));
		gallery.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> adapter, View arg1,
					int position, long id) {
				File file = (File) adapter.getItemAtPosition(position);
				String uri = "file://" + file.getAbsolutePath();
				ImageSize targetSize = new ImageSize(1024, 1024);
				ImageLoader.getInstance().loadImage(uri, targetSize,
						new SimpleImageLoadingListener() {
							@Override
							public void onLoadingComplete(String imageUri,
									View view, Bitmap loadedImage) {
								imageSwitcher
										.setImageDrawable(new BitmapDrawable(
												loadedImage));
							}
						});
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		imageSwitcher.setInAnimation(AnimationUtils.loadAnimation(
				getApplicationContext(), android.R.anim.fade_in));
		imageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(
				getApplicationContext(), android.R.anim.fade_out));
		imageSwitcher.setFactory(new ViewFactory() {
			@Override
			public View makeView() {
				ImageView i = new ImageView(ImageViewerActivity.this);
				i.setBackgroundColor(0xFF000000);
				i.setScaleType(ImageView.ScaleType.FIT_CENTER);
				i.setLayoutParams(new ImageSwitcher.LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
				return i;
			}
		});
		imageSwitcher.setOnTouchListener(new OnTouchListener() {
			/*
			 * ��ImageSwitcher�ؼ��ϻ��������л�ͼƬ
			 */
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					downX = (int) event.getX();// ȡ�ð���ʱ������
					return true;
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					upX = (int) event.getX();// ȡ���ɿ�ʱ������
					int index = 0;
					if (upX - downX > 100)// �����ϵ��ң�����ǰһ��
					{
						// ����ǵ�һ����ȥ��β��
						if (gallery.getSelectedItemPosition() == 0)
							index = gallery.getCount() - 1;
						else
							index = gallery.getSelectedItemPosition() - 1;
					} else if (downX - upX > 100)// �����ϵ��󣬼�����һ��
					{
						// ����������ȥ����һ
						if (gallery.getSelectedItemPosition() == (gallery
								.getCount() - 1))
							index = 0;
						else
							index = gallery.getSelectedItemPosition() + 1;
					}
					// �ı�galleryͼƬ��ѡ���Զ�����ImageSwitcher��setOnItemSelectedListener
					gallery.setSelection(index, true);
					return true;
				}
				return false;
			}

		});
	}
}
