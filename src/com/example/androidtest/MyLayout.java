package com.example.androidtest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.NinePatchDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.graphics.Shader;

public class MyLayout extends LinearLayout {
	int mPosition = -1;
	int mPosition2 = -1;
	Paint mPaint = new Paint();
	BitmapDrawable mTextureD;
	BitmapDrawable mShadowD;
	NinePatchDrawable mUrlD;
	NinePatchDrawable mUrl2D;
	Bitmap mTexture = BitmapFactory.decodeResource(getResources(), R.drawable.address_bar_texture_port);
	Bitmap mShadow = BitmapFactory.decodeResource(getResources(), R.drawable.address_bar_bg_shadow);
	Bitmap mCurve = BitmapFactory.decodeResource(getResources(), R.drawable.address_bar_bg_curve);
	Bitmap mCurve2 = BitmapFactory.decodeResource(getResources(), R.drawable.tabs_full_normal);
	Bitmap mCurve3 = BitmapFactory.decodeResource(getResources(), R.drawable.tabs_full_pressed);
	Rect mCurveRect = null;
	Rect mCurveRect2 = null;
	boolean mTabTouched = false;
	boolean mUrlTouched = false;

	public MyLayout(Context context) {
		super(context);
		init();
	}

	private int getPosition() {
		if (mPosition == -1) {
			View stop = findViewById(R.id.textbox);
			int l = stop.getRight() - 40;
			mPosition = l;
		}
		return mPosition;
	}

	private Rect getCurveRect() {
		if (mCurveRect == null) {
			int p = getPosition();
			int w = mCurve.getWidth() * getHeight() / mCurve.getHeight();
			mCurveRect = new Rect(p, 0, p + w, getHeight());
		}
		return mCurveRect;
	}

	private Rect getCurveRect2() {
		if (mCurveRect2 == null) {
			int p = getPosition() + 0;
			int w = mCurve2.getWidth() * getHeight() / mCurve2.getHeight() + 6;
			mCurveRect2 = new Rect(p - 6, 0, p + w, getHeight());
		}
		return mCurveRect2;
	}

	private void init() {
		setWillNotDraw(false);

		mTextureD = new BitmapDrawable(getResources(), mTexture);
		mTextureD.setTileModeXY(Shader.TileMode.REPEAT, Shader.TileMode.CLAMP);

		mShadowD = new BitmapDrawable(getResources(), mShadow);
		mShadowD.setTileModeXY(Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);

		mUrlD = (NinePatchDrawable) getResources().getDrawable(R.drawable.address_bar_url_default);
		mUrl2D = (NinePatchDrawable) getResources().getDrawable(R.drawable.address_bar_url_pressed);
	}

	public void onFinishInflate() {
		View newTab = findViewById(R.id.newtab);
		newTab.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				int action = event.getAction() & MotionEvent.ACTION_MASK;
				if (action == MotionEvent.ACTION_DOWN) {
					mTabTouched = true;
					invalidate();
				} else if (action == MotionEvent.ACTION_UP) {
					mTabTouched = false;					
					invalidate();
				}
				return false;
			}
		});

		View textboxInner = findViewById(R.id.textbox_inner);
		final View textbox = findViewById(R.id.textbox);
		textboxInner.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				int action = event.getAction() & MotionEvent.ACTION_MASK;
				if (action == MotionEvent.ACTION_DOWN) {
					mUrlTouched = true;
					invalidate();
				} else if (action == MotionEvent.ACTION_UP) {
					mUrlTouched = false;
					invalidate();
				}
				return false;
			}
		});
	}

	public MyLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	@Override
	public void onDraw(Canvas canvas) {
		canvas.drawColor(Color.BLACK);

		mTextureD.setBounds(0, 0, getPosition(), getHeight());
		mTextureD.draw(canvas);
		
		if (mTabTouched) canvas.drawBitmap(mCurve3, null, getCurveRect2(), null);
		else canvas.drawBitmap(mCurve2, null, getCurveRect2(), null);

		canvas.drawBitmap(mCurve, null, getCurveRect(), null);

		int size = 8;
		if (mUrlTouched) {
			mUrl2D.setBounds(size+50, size+3, getPosition()-2*size+50, getHeight()-2*size+3);
			mUrl2D.draw(canvas);
		} else {
			mUrlD.setBounds(size+50, size+3, getPosition()-2*size+50, getHeight()-2*size+3);
			mUrlD.draw(canvas);
		}

		mShadowD.setBounds(0, getHeight() - mShadow.getHeight(), getWidth(), getHeight());
		mShadowD.draw(canvas);
	}
}
