package com.newmeishu.image_viewer.ui;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Gallery;

import com.newmeishu.image_viewer.MainActivity;

public class ImageGallery extends Gallery {
	private GestureDetector gestureScanner;
	private GalleryImageView imageView;

	public ImageGallery(Context context) {
		super(context);

	}

	public ImageGallery(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public ImageGallery(Context context, AttributeSet attrs) {
		super(context, attrs);

		gestureScanner = new GestureDetector(new MySimpleGesture());
		this.setOnTouchListener(new OnTouchListener() {

			float baseValue;
			float originalScale;

			// ��дonTouch����ʵ������
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				View view = ImageGallery.this.getSelectedView();
				if (view instanceof GalleryImageView) {
					imageView = (GalleryImageView) view;

					if (event.getAction() == MotionEvent.ACTION_DOWN) {
						baseValue = 0;
						originalScale = imageView.getScale();
					}
					if (event.getAction() == MotionEvent.ACTION_MOVE) {
						// �����϶�
						if (event.getPointerCount() == 2) {
							float x = event.getX(0) - event.getX(1);
							float y = event.getY(0) - event.getY(1);
							float value = (float) Math.sqrt(x * x + y * y);// ��������ľ���
							// System.out.println("value:" + value);
							if (baseValue == 0) {
								baseValue = value;
							} else {
								float scale = value / baseValue;// ��ǰ�����ľ��������ָ����ʱ�����ľ��������Ҫ���ŵı�����
								// scale the image
								imageView.zoomTo(originalScale * scale, x
										+ event.getX(1), y + event.getY(1));

							}
						}
					}
				}
				return false;
			}

		});
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		View view = ImageGallery.this.getSelectedView();
		if (view instanceof GalleryImageView) {
			imageView = (GalleryImageView) view;

			float v[] = new float[9];
			Matrix m = imageView.getImageMatrix();
			m.getValues(v);
			// ͼƬʵʱ��������������
			float left, right;
			// ͼƬ��ʵʱ����
			float width, height;
			width = imageView.getScale() * imageView.getImageWidth();
			height = imageView.getScale() * imageView.getImageHeight();
			// �����߼�Ϊ�ƶ�ͼƬ�ͻ���gallery�������߼������û����������˽�ķǳ������������´���
			if ((int) width <= MainActivity.screenWidth
					&& (int) height <= MainActivity.screenHeight)// ���ͼƬ��ǰ��С<��Ļ��С��ֱ�Ӵ������¼�
			{
				super.onScroll(e1, e2, distanceX, distanceY);
			} else {
				left = v[Matrix.MTRANS_X];
				right = left + width;
				Rect r = new Rect();
				imageView.getGlobalVisibleRect(r);

				if (distanceX > 0)// ���󻬶�
				{
					if (r.left > 0) {// �жϵ�ǰImageView�Ƿ���ʾ��ȫ
						super.onScroll(e1, e2, distanceX, distanceY);
					} else if (right < MainActivity.screenWidth) {
						Log.i("screenWidth", "MainActivity.screenWidth1:"
								+ MainActivity.screenWidth);
						super.onScroll(e1, e2, distanceX, distanceY);
					} else {
						imageView.postTranslate(-distanceX, -distanceY);
					}
				} else if (distanceX < 0)// ���һ���
				{
					if (r.right < MainActivity.screenWidth) {
						Log.i("screenWidth", "MainActivity.screenWidth2:"
								+ MainActivity.screenWidth);
						super.onScroll(e1, e2, distanceX, distanceY);
					} else if (left > 0) {
						super.onScroll(e1, e2, distanceX, distanceY);
					} else {
						imageView.postTranslate(-distanceX, -distanceY);
					}
				}
				// if (distanceX > 0)// ���󻬶�
				// {
				// if (r.left > 0) {// �жϵ�ǰImageView�Ƿ���ʾ��ȫ
				// super.onScroll(e1, e2, distanceX, distanceY);
				// } else if (right <= MainActivity.screenWidth) {
				// super.onScroll(e1, e2, distanceX, distanceY);
				// } else {
				// imageView.postTranslate(-distanceX, -distanceY);
				// }
				// } else if (distanceX < 0)// ���һ���
				// {
				// if (r.right == 0) {
				// super.onScroll(e1, e2, distanceX, distanceY);
				// } else {
				// imageView.postTranslate(-distanceX, -distanceY);
				// }
				// }
			}

		} else {
			super.onScroll(e1, e2, distanceX, distanceY);
		}
		return false;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// return false;// ��������ȫ����fling
		int kEvent;
		if (isScrollingLeft(e1, e2)) {
			// Check if scrolling left
			kEvent = KeyEvent.KEYCODE_DPAD_LEFT;
		} else {
			// Otherwise scrolling right
			kEvent = KeyEvent.KEYCODE_DPAD_RIGHT;
		}
		onKeyDown(kEvent, null);
		return true;
	}

	private boolean isScrollingLeft(MotionEvent e1, MotionEvent e2) {
		return e2.getX() > e1.getX();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		gestureScanner.onTouchEvent(event);
		switch (event.getAction()) {
		case MotionEvent.ACTION_UP:
			// �ж����±߽��Ƿ�Խ��
			View view = ImageGallery.this.getSelectedView();
			if (view instanceof GalleryImageView) {
				imageView = (GalleryImageView) view;
				float width = imageView.getScale() * imageView.getImageWidth();
				float height = imageView.getScale()
						* imageView.getImageHeight();
				if ((int) width <= MainActivity.screenWidth
						&& (int) height <= MainActivity.screenHeight)// ���ͼƬ��ǰ��С<��Ļ��С���жϱ߽�
				{
					break;
				}
				float v[] = new float[9];
				Matrix m = imageView.getImageMatrix();
				m.getValues(v);
				float top = v[Matrix.MTRANS_Y];
				float bottom = top + height;
				if (top > 0) {
					imageView.postTranslateDur(-top, 200f);
				}
				Log.i("lyc", "bottom:" + bottom);
				if (bottom < MainActivity.screenHeight) {
					imageView.postTranslateDur(MainActivity.screenHeight
							- bottom, 200f);
				}
			}
			break;
		}
		return super.onTouchEvent(event);
	}

	private class MySimpleGesture extends SimpleOnGestureListener {
		// �����µĵڶ���Touch downʱ����
		public boolean onDoubleTap(MotionEvent e) {
			View view = ImageGallery.this.getSelectedView();
			if (view instanceof GalleryImageView) {
				imageView = (GalleryImageView) view;
				if (imageView.getScale() > imageView.getScaleRate()) {
					imageView.zoomTo(imageView.getScaleRate(),
							MainActivity.screenWidth / 2,
							MainActivity.screenHeight / 2, 200f);
					// imageView.layoutToCenter();
				} else {
					imageView.zoomTo(1.0f, MainActivity.screenWidth / 2,
							MainActivity.screenHeight / 2, 200f);
				}
			} else {
			}
			// return super.onDoubleTap(e);
			return true;
		}
	}
}
