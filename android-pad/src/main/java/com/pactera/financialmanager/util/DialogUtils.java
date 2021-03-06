package com.pactera.financialmanager.util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.View;

import com.pactera.financialmanager.R;

/**
 * dialog工具类
 * 
 * @author wy
 * 
 */
public class DialogUtils {
	private static ProgressDialog dialog;
	private static AlertDialog alertDialog;
	private static Dialog d;

	public static void showDialog(Context context, String message) {
		dialog = new ProgressDialog(context);
		dialog.setMessage(message);
		dialog.setCancelable(false);
		dialog.show();
	}
	public static void showDialog(Context context, String message,Boolean isCancel) {
		dialog = new ProgressDialog(context);
		dialog.setMessage(message);
		dialog.setCancelable(isCancel);
		dialog.show();
	}

	public static void showDialog(Context context) {
		d = new Dialog(context, R.style.new_circle_progress);
		d.setContentView(R.layout.flush_view);
		d.show();
	}

	public static void showCloseDialog(Context context, View view) {
		d = new Dialog(context, R.style.new_circle_progress);
		d.setContentView(view);
		d.show();
	}

	public static void dismissDialog() {
		if (dialog != null && dialog.isShowing()) {
			dialog.dismiss();
		}
		if (d != null && d.isShowing()) {
			d.dismiss();
		}
		if (alertDialog != null && alertDialog.isShowing()) {
			alertDialog.dismiss();
		}
	}
}
