package com.pactera.financialmanager.ui.customermanager;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.net.Uri;
import android.provider.MediaStore;

import com.pactera.financialmanager.util.*;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.os.Message;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.pactera.financialmanager.R;
import com.pactera.financialmanager.adapter.SpinnerAdapter;
import com.pactera.financialmanager.adapter.SpinnerAdapter.CallBackItemClickListener;
import com.pactera.financialmanager.ui.LogoActivity;
import com.pactera.financialmanager.ui.ParentActivity;
import com.pactera.financialmanager.ui.ParentFragment;
import com.pactera.financialmanager.ui.fragmentbookbuilding.WindowTakePictures;
import com.pactera.financialmanager.ui.model.CustomerEvaluation;
import com.pactera.financialmanager.ui.service.HConnection;
import com.pactera.financialmanager.ui.service.HRequest;
import com.pactera.financialmanager.ui.service.HResponse;
import com.pactera.financialmanager.ui.service.HttpConnSoap;

/**
 * 客户评价fragment
 */

public class CustomerFragment extends ParentFragment implements OnClickListener {

	TextView customerfragment_cultivale;// 培植方向
	TextView customerfragment_req_cate;// 需求分类
	ImageView customerfragment_is_care;// 是否关注
	EditText customerfragment_des;// 说明
	Button customerfragment_ok;// 完成客户建档
	ImageView[] customerfragment_photo = new ImageView[6];// 上传照片按钮

	public static CustomerFragment theCustomerFragment;

	// 标记位
	int isCare = isTrue;

	private String cultivaleID = "";// 培植方向码值
	private String req_cateID = "";// 是否关注码值

	private HConnection creatHCon;
	private HConnection HCon;

	private final int creatInfo = 1;
	private final int getInfo = 2;
	public static final int PiCInfo = 3;
	public static final int DeleteInfo = 4;
	public static final int UpPiC = 5;
	public static final int returnDelete = 6;

	private HttpConnSoap soapCon;

	private int picID = 0;
	// 存储返回的文件ID
	private String[] fileid = { "", "", "", "", "", "" };

	// 默认图片
	private int defaultPic = R.drawable.archiving_photo;

	ScrollView CustomerFragment_con;
	/**
	 * 图片选择管理
	 */
	PopupWindow popupWindow;
	View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_archiving_customer, null);
		setView(view);
		setListener();
		getData();
		return view;
	}

	/**
	 * 获取数据
	 */
	private void getData() {
		// TODO Auto-generated method stub
		HCon = RequestInfo(CustomerFragment.this, Constants.requestType.Search, InterfaceInfo.Customer_Get,
				PersonArchiving.custID, getInfo);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.customerfragment_ok:
			creatData();
			break;
		case R.id.customerfragment_is_care:
			isCare = isCare == isTrue ? isFalse : isTrue;
			setSelect(isCare);
			break;
		case R.id.customerfragment_img_1:
			picID = 0;
			showWindow();
			break;
		case R.id.customerfragment_img_2:
			picID = 1;
			showWindow();
			break;
		case R.id.customerfragment_img_3:
			picID = 2;
			showWindow();
			break;
		case R.id.customerfragment_img_4:
			picID = 3;
			showWindow();
			break;
		case R.id.customerfragment_img_5:
			picID = 4;
			showWindow();
			break;
		case R.id.customerfragment_img_6:
			picID = 5;
			showWindow();
			break;

		case R.id.customerfragment_cultivale:
			SpinnerAdapter.showSelectDialog(getActivity(), NewCatevalue.cultivate_dire, customerfragment_cultivale,
					new CallBackItemClickListener() {

						@Override
						public void CallBackItemClick(String spinnerID) {
							// TODO Auto-generated method stub
							cultivaleID = spinnerID;
						}
					});
			break;
		case R.id.customerfragment_req_cate:
			SpinnerAdapter.showSelectDialog(getActivity(), NewCatevalue.req_cate_type, customerfragment_req_cate,
					new CallBackItemClickListener() {

						@Override
						public void CallBackItemClick(String spinnerID) {
							// TODO Auto-generated method stub
							req_cateID = spinnerID;
						}
					});
			break;
		default:
			break;
		}
	}

	// 上传数据
	private void creatData() {
		// TODO Auto-generated method stub
		CustomerEvaluation newEva = new CustomerEvaluation();
		newEva.cultivale = cultivaleID;// 培植方向
		newEva.req_cate = req_cateID;// 需求分类
		newEva.is_care = String.valueOf(isCare);// 是否关注
		newEva.des = Tool.getTextValue(customerfragment_des);// 说明
		newEva.custid = PersonArchiving.custID;// 客户ID

		creatHCon = RequestInfo(CustomerFragment.this, Constants.requestType.Update, InterfaceInfo.Customer_Update,
				newEva.toString(), creatInfo);
	}

	@Override
	public void onConnected(Message msg) {
		super.onConnected(msg);
		HResponse res;
		JSONObject tmpJsonObject;
		switch (connectionIndex) {
		case HConnection.RESPONSE_ERROR:
			break;
		case creatInfo:
			res = creatHCon.getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);

			if (res == null || res.dataJsonObject == null) {
				return;
			}
			tmpJsonObject = res.dataJsonObject;
			readJson(tmpJsonObject, connectionIndex);
			break;
		case getInfo:
			res = HCon.getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);

			if (res == null || res.dataJsonObject == null) {
				return;
			}
			tmpJsonObject = res.dataJsonObject;
			readJson(tmpJsonObject, connectionIndex);
			break;
		case PiCInfo:
			Log.i("111111", "PiCInfo:" + PiCInfo);
			Bundle extras = msg.getData();
			// 以当前时间命名文件
			String filename = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".jpg";
			byte[] byteOut1 = null;
			Bitmap photo = null;
			if (extras.containsKey("data")) {
				Log.i("111111", extras.toString());
				Uri thePhoneUri = (Uri) extras.getParcelable("data");// 图片链接
				try {
					if (thePhoneUri != null) {
						photo = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), thePhoneUri);
					} else {
						photo = BitmapFactory.decodeStream(
								getActivity().getContentResolver().openInputStream(BitmapUtils.CROP_TMP_FILE_URI));

					}
					// Log.i("1111111", "photo:" + photo.toString());
				} catch (Exception e) {
					// Log.i("1111111", e.toString());
					break;
				}

				// resize
				// photo = ThumbnailUtils.extractThumbnail(photo, 265, 265);
			}

			if (photo != null) {
				byteOut1 = BitmapUtils.compressImage(photo, 100 * 1024);
			} else {
				// toast("获取图片失败！");
				Toast.makeText(getActivity(), "获取图片失败！", Toast.LENGTH_SHORT).show();
				return;
			}
			// 将图片显示在界面
			customerfragment_photo[picID].setImageBitmap(photo);

			/**
			 * 上传操作
			 */
			String userCode = "";
			String seriNo = "DLXJT06FF18P";
			String chnlCode = "02";// 默认安卓客户端
			String brCode = "";

			if (LogoActivity.user != null) {
				userCode = LogoActivity.user.getUserCode();
				brCode = LogoActivity.user.getBrCode();
				seriNo = LogoActivity.user.getImei();
			}

			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("userCode", userCode));
			nameValuePairs.add(new BasicNameValuePair("brCode", brCode));
			nameValuePairs.add(new BasicNameValuePair("seriNo", seriNo));
			nameValuePairs.add(new BasicNameValuePair("chnlCode", chnlCode));
			nameValuePairs.add(new BasicNameValuePair("transCode", InterfaceInfo.CusArchiving_UpPic));
			nameValuePairs.add(new BasicNameValuePair("spareOne", PersonArchiving.custID));

			// 01： 证件附件，02： 其他附件
			String theType = "";
			if (picID < 3) {
				theType = "01";
			} else {
				theType = "02";
			}

			nameValuePairs.add(new BasicNameValuePair("spareTwo", theType));
			nameValuePairs.add(new BasicNameValuePair("files", Base64.encodeToString(byteOut1, Base64.DEFAULT)));
			nameValuePairs.add(new BasicNameValuePair("fileName", filename));

			HRequest request = new HRequest(InterfaceInfo.CusArchiving_UpPic, "getJSON");
			try {
				HttpConnSoap.isShowLoadingProcess = false; // 不显示loading
				soapCon = new HttpConnSoap(CustomerFragment.this, request, nameValuePairs);
				soapCon.setIndex(UpPiC);
			} catch (Exception e) {
				e.printStackTrace();
			}

			break;
		case DeleteInfo:

			customerfragment_photo[picID].setImageResource(defaultPic);
			fileid[picID] = "";

			break;
		case UpPiC:
			HResponse ress = soapCon.getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
			if (ress == null || ress.dataJsonObject == null) {
				return;
			}
			tmpJsonObject = ress.dataJsonObject;
			readJson(tmpJsonObject, connectionIndex);
			break;
		}

	}

	// /**
	// * 删除图片
	// *
	// * @param 文件
	// */
	// private void deletePic(String fileid) {
	// // TODO Auto-generated method stub
	// HCon = RequestInfo(this, requestType.Delete,
	// InterfaceInfo.CusArchiving_DeletePic, fileid, returnDelete);
	// }

	private void readJson(JSONObject tmpJsonObject, int connectionIndex) {
		// TODO Auto-generated method stub
		String retCode = "";

		if (tmpJsonObject.has("retCode")) { // 返回标志
			try {
				retCode = tmpJsonObject.getString("retCode");
				Log.i("1111111", "index:" + connectionIndex + ",retCode:" + retCode);
			} catch (JSONException e1) {
				e1.printStackTrace();
				return;
			}
		}

		if (retCode.equals("0000")) {
			if (connectionIndex == creatInfo) {
				Message msg = ((ParentActivity) getActivity()).handler.obtainMessage();
				Bundle data = new Bundle();
				data.putString("value", "" + PersonArchiving.CustomerIndex);
				msg.setData(data);
				((ParentActivity) getActivity()).handler.sendMessage(msg);
			} else if (connectionIndex == getInfo) {

				customerfragment_des.setText(Tool.getJsonValue(tmpJsonObject, "des"));
				cultivaleID = Tool.getJsonValue(tmpJsonObject, "cultivale");
				customerfragment_cultivale
						.setText(NewCatevalue.getLabel(getActivity(), NewCatevalue.cultivate_dire, cultivaleID));

				req_cateID = Tool.getJsonValue(tmpJsonObject, "req_cate");
				customerfragment_req_cate
						.setText(NewCatevalue.getLabel(getActivity(), NewCatevalue.req_cate_type, req_cateID));

				Tool.setSelect(customerfragment_is_care, Tool.getJsonValue(tmpJsonObject, "is_care"));

			} else if (connectionIndex == UpPiC) {

				fileid[picID] = Tool.getJsonValue(tmpJsonObject, "fileid");

				Toast.makeText(getActivity(), "图片上传成功！", Toast.LENGTH_SHORT).show();
			} else if (connectionIndex == returnDelete) {

				Log.i("1111111", "图片删除成功!");
			}

		}
	}

	private void setListener() {
		// TODO Auto-generated method stub
		customerfragment_ok.setOnClickListener(this);
		customerfragment_is_care.setOnClickListener(this);
		for (int i = 0; i < customerfragment_photo.length; i++) {
			customerfragment_photo[i].setOnClickListener(this);
		}
		customerfragment_cultivale.setOnClickListener(this);
		customerfragment_req_cate.setOnClickListener(this);
	}

	private void setView(View view) {
		// TODO Auto-generated method stub
		customerfragment_cultivale = (TextView) view.findViewById(R.id.customerfragment_cultivale);// 培植方向
		customerfragment_req_cate = (TextView) view.findViewById(R.id.customerfragment_req_cate);// 需求分类
		customerfragment_is_care = (ImageView) view.findViewById(R.id.customerfragment_is_care);// 是否关注
		customerfragment_des = (EditText) view.findViewById(R.id.customerfragment_des);// 说明
		customerfragment_ok = (Button) view.findViewById(R.id.customerfragment_ok);// 完成客户建档
		customerfragment_photo[0] = (ImageView) view.findViewById(R.id.customerfragment_img_1);
		customerfragment_photo[1] = (ImageView) view.findViewById(R.id.customerfragment_img_2);
		customerfragment_photo[2] = (ImageView) view.findViewById(R.id.customerfragment_img_3);
		customerfragment_photo[3] = (ImageView) view.findViewById(R.id.customerfragment_img_4);
		customerfragment_photo[4] = (ImageView) view.findViewById(R.id.customerfragment_img_5);
		customerfragment_photo[5] = (ImageView) view.findViewById(R.id.customerfragment_img_6);
		CustomerFragment_con = (ScrollView) view.findViewById(R.id.CustomerFragment_con);
		theCustomerFragment = this;
	}

	private void setSelect(int theflag) {
		if (theflag == isTrue) {
			customerfragment_is_care.setImageResource(R.drawable.cusarchiving_swatch_on);
		} else {
			customerfragment_is_care.setImageResource(R.drawable.cusarchiving_swatch_off);
		}
	}

	private void showWindow() {
		// WindowManager manager = (WindowManager)
		// getActivity().getSystemService(
		// Context.WINDOW_SERVICE);
		view = new WindowTakePictures(getActivity()).getWindowView();
		// view.measure(MeasureSpec.AT_MOST, MeasureSpec.EXACTLY);
		// 测量view的高度
		// int viewHeight = view.getMeasuredHeight();
		int viewHeight = Tool.getScreenHeight(getActivity());
		viewHeight = viewHeight * 312 / 1230;
		Log.i("info", "viewHeight=" + viewHeight);

		popupWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT, viewHeight);
		// 设置焦点在弹窗上
		popupWindow.setFocusable(true);
		popupWindow.showAtLocation(CustomerFragment_con, Gravity.BOTTOM, 0, 0);
	}

	@Override
	public void onDestroyView() {
		Log.i("1111111", "onDestroyView");
		// 移除图片上传view
		if (popupWindow != null && view != null) {

			popupWindow.dismiss();
			view.destroyDrawingCache();
		}
		super.onDestroyView();
	}
}