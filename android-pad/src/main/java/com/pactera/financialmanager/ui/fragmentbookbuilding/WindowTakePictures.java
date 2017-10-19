package com.pactera.financialmanager.ui.fragmentbookbuilding;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.pactera.financialmanager.R;
import com.pactera.financialmanager.util.BitmapUtils;

/**
 * 选择图片
 * @author whooo
 *
 */
public class WindowTakePictures extends View {
	private View view;
	private Context context;
	private Activity activity;

	private RelativeLayout rlCamera, rlPhotoAlbum, rlCancel,rlDelate;

	public WindowTakePictures(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		this.context = context;
		this.activity = (Activity) context;
		// TODO Auto-generated constructor stub
	}

	public WindowTakePictures(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		this.activity = (Activity) context;
		// TODO Auto-generated constructor stub
	}

	public WindowTakePictures(Context context) {
		super(context);
		this.context = context;
		this.activity = (Activity) context;
		// TODO Auto-generated constructor stub
	}

	public View getWindowView() {
		view = View.inflate(context, R.layout.window_take_picture, null);
		setupView(view);
		addListener();
		return view;
	}

	private void addListener() {
		rlCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// cancel
				view.setVisibility(View.GONE);
			}
		});
		rlCamera.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// camera
				BitmapUtils.getPictureFromCamera(activity);
				view.setVisibility(View.GONE);
			}
		});
		rlPhotoAlbum.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// photo album
				BitmapUtils.getPictureFromPhotoAlbum(activity);
				view.setVisibility(View.GONE);
			}
		});
		rlDelate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				BitmapUtils.deletePic(activity);
				view.setVisibility(View.GONE);
			}
		});
	}

	private void setupView(View view) {
		rlCamera = (RelativeLayout) view
				.findViewById(R.id.rl_window_from_camera);
		rlCancel = (RelativeLayout) view.findViewById(R.id.rl_window_cancel);
		rlPhotoAlbum = (RelativeLayout) view
				.findViewById(R.id.rl_window_from_photoalbum);
		rlDelate = (RelativeLayout) view
				.findViewById(R.id.rl_window_delete);
	}
}
