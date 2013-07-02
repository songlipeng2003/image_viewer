package com.newmeishu.image_viewer;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.FloatMath;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Gallery;

import com.newmeishu.image_viewer.adapter.ImageAdapter;
import com.newmeishu.image_viewer.ui.ImageGallery;

public class ImageViewerActivity extends Activity implements OnTouchListener {
	private ImageGallery gallery;

	private String path;

	private File dir;

	float beforeLenght = 0.0f; // 两触点距离
	float afterLenght = 0.0f; // 两触点距离
	boolean isScale = false;
	float currentScale = 1.0f;// 当前图片的缩放比率

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_viewer);
		Intent intent = getIntent();
		path = intent.getStringExtra("path");
		if (path == null) {
			path = Environment.getExternalStorageDirectory() + "/DCIM/Camera/";
		}
		dir = new File(path);

		gallery = (ImageGallery) findViewById(R.id.gallery);
		gallery.setVerticalFadingEdgeEnabled(false);// 取消竖直渐变边框
		gallery.setHorizontalFadingEdgeEnabled(false);// 取消水平渐变边框
		gallery.setAdapter(new ImageAdapter(this, dir));
		// gallery.setOnTouchListener(this);
		// gallery.setOnItemSelectedListener(new GalleryChangeListener());
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {

		// Log.i("","touched---------------");
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_POINTER_DOWN:// 多点缩放
			beforeLenght = spacing(event);
			if (beforeLenght > 5f) {
				isScale = true;
			}
			break;
		case MotionEvent.ACTION_MOVE:
			if (isScale) {
				afterLenght = spacing(event);
				if (afterLenght < 5f)
					break;
				float gapLenght = afterLenght - beforeLenght;
				if (gapLenght == 0) {
					break;
				} else if (Math.abs(gapLenght) > 5f) {
					// FrameLayout.LayoutParams params =
					// (FrameLayout.LayoutParams) gallery.getLayoutParams();
					float scaleRate = gapLenght / 854;// 缩放比例
					// Log.i("",
					// "scaleRate："+scaleRate+" currentScale:"+currentScale);
					// Log.i("", "缩放比例：" +
					// scaleRate+" 当前图片的缩放比例："+currentScale);
					// params.height=(int)(800*(scaleRate+1));
					// params.width=(int)(480*(scaleRate+1));
					// params.height = 400;
					// params.width = 300;
					// gallery.getChildAt(0).setLayoutParams(new
					// Gallery.LayoutParams(300, 300));
					Animation myAnimation_Scale = new ScaleAnimation(
							currentScale, currentScale + scaleRate,
							currentScale, currentScale + scaleRate,
							Animation.RELATIVE_TO_SELF, 0.5f,
							Animation.RELATIVE_TO_SELF, 0.5f);
					// Animation myAnimation_Scale = new
					// ScaleAnimation(currentScale, 1+scaleRate, currentScale,
					// 1+scaleRate);
					myAnimation_Scale.setDuration(100);
					myAnimation_Scale.setFillAfter(true);
					myAnimation_Scale.setFillEnabled(true);
					// gallery.getChildAt(0).startAnimation(myAnimation_Scale);

					// gallery.startAnimation(myAnimation_Scale);
					currentScale = currentScale + scaleRate;
					// gallery.getSelectedView().setLayoutParams(new
					// Gallery.LayoutParams((int)(480), (int)(800)));
					// Log.i("",
					// "===========:::"+gallery.getSelectedView().getLayoutParams().height);
					// gallery.getSelectedView().getLayoutParams().height=(int)(800*(currentScale));
					// gallery.getSelectedView().getLayoutParams().width=(int)(480*(currentScale));
					gallery.getSelectedView().setLayoutParams(
							new Gallery.LayoutParams(
									(int) (480 * (currentScale)),
									(int) (854 * (currentScale))));
					// gallery.getSelectedView().setLayoutParams(new
					// Gallery.LayoutParams((int)(320*(scaleRate+1)),
					// (int)(480*(scaleRate+1))));
					// gallery.getSelectedView().startAnimation(myAnimation_Scale);
					// isScale = false;
					beforeLenght = afterLenght;
				}
				return true;
			}
			break;
		case MotionEvent.ACTION_POINTER_UP:
			isScale = false;
			break;
		}

		return false;
	}

	/**
	 * 就算两点间的距离
	 */
	private float spacing(MotionEvent event) {
		float x = event.getX(0) - event.getX(1);
		float y = event.getY(0) - event.getY(1);
		return FloatMath.sqrt(x * x + y * y);
	}
}
