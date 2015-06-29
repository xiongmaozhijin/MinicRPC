package com.example.minicrpc.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.minicrpc.R;

/**
 * 自定义组合控件，titlebar
 * @author michael.chen
 *
 */
public class CommonTitleBar extends LinearLayout {

	private boolean mHasLeftButton;
	private boolean mHasRightButton;
	private String mLeftButtonText;
	private String mRightButtonText;
	private String mTitleText;

	private Button mBtnLeft;
	private Button mBtnRight;
	private TextView mTxv;
	
	private Drawable mBtnLeftBackground;
	private Drawable mBtnRightBackground;
	
	
	public CommonTitleBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	public CommonTitleBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
		init(context, attrs);
		
	}

	protected void init(Context context, AttributeSet attrs) {
		TypedArray typedArray = null;
		
		//get custom attrs
		try {
			typedArray = context.obtainStyledAttributes(attrs, R.styleable.CommonTitleBar);
			mHasLeftButton = typedArray.getBoolean(R.styleable.CommonTitleBar_has_left_button, false);
			mHasRightButton = typedArray.getBoolean(R.styleable.CommonTitleBar_has_right_button, false);
			mLeftButtonText = typedArray.getString(R.styleable.CommonTitleBar_left_button_text);
			mRightButtonText = typedArray.getString(R.styleable.CommonTitleBar_right_button_text);
			mTitleText = typedArray.getString(R.styleable.CommonTitleBar_title);
			mBtnLeftBackground = typedArray.getDrawable(R.styleable.CommonTitleBar_left_button_background);
			mBtnRightBackground = typedArray.getDrawable(R.styleable.CommonTitleBar_right_button_background);
			
		} finally {
			if (typedArray != null) {
				typedArray.recycle();
			}
		}
		
		
		//load widget layout
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.widget_common_titlebar, this, true);
		
		mBtnLeft = (Button) findViewById(R.id.btn_left);
		mBtnRight = (Button) findViewById(R.id.btn_right);
		mTxv = (TextView) findViewById(R.id.txv_title);
		
		// init data
		if (!mHasLeftButton) {
			mBtnLeft.setVisibility(View.INVISIBLE);
		} else {
			mBtnLeft.setText(mLeftButtonText + "");
			if (mBtnLeftBackground != null) {
				mBtnLeft.setBackground(mBtnLeftBackground);
			}
		}
		if (!mHasRightButton) {
			mBtnRight.setVisibility(View.INVISIBLE);
		} else {
			mBtnRight.setText(mRightButtonText);
			if (mBtnRightBackground != null) {
				mBtnRight.setBackground(mBtnRightBackground);
			}
		}
		mTxv.setText(mTitleText);
	}
	
	public void setOnLeftBtnClickListener(View.OnClickListener listener) {
		if (mBtnLeft != null) {
			mBtnLeft.setOnClickListener(listener);
		}
	}
	
	public void setOnRightBtnClickListener(View.OnClickListener l) {
		if (mBtnRight != null) {
			mBtnRight.setOnClickListener(l);
		}
	}
	
	
	

	public boolean ismHasLeftButton() {
		return mHasLeftButton;
	}

	public void setmHasLeftButton(boolean mHasLeftButton) {
		this.mHasLeftButton = mHasLeftButton;
		mBtnLeft.setVisibility(mHasLeftButton ? View.VISIBLE : View.INVISIBLE);
	}

	public boolean ismHasRightButton() {
		return mHasRightButton;
	}

	public void setmHasRightButton(boolean mHasRightButton) {
		this.mHasRightButton = mHasRightButton;
		mBtnRight.setVisibility(mHasRightButton ? View.VISIBLE : View.INVISIBLE);
		
	}

	public String getmLeftButtonText() {
		return mLeftButtonText;
	}

	public void setmLeftButtonText(String mLeftButtonText) {
		this.mLeftButtonText = mLeftButtonText;
		mBtnLeft.setText(mLeftButtonText);
	}

	public String getmRightButtonText() {
		return mRightButtonText;
	}

	public void setmRightButtonText(String mRightButtonText) {
		this.mRightButtonText = mRightButtonText;
		mBtnRight.setText(mRightButtonText);
	}

	public String getmTitleText() {
		return mTitleText;
	}

	public void setmTitleText(String mTitleText) {
		this.mTitleText = mTitleText;
		mTxv.setText(mTitleText);
	}
	

}

