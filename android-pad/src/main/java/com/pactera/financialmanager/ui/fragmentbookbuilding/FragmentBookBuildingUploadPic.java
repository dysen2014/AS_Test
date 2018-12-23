package com.pactera.financialmanager.ui.fragmentbookbuilding;

import android.support.v4.app.FragmentManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ScrollView;

import com.alibaba.fastjson.JSON;
import com.pactera.financialmanager.R;
import com.pactera.financialmanager.adapter.SpinnerAdapter;
import com.pactera.financialmanager.adapter.SpinnerAdapter.CallBackItemClickListener;
import com.pactera.financialmanager.ui.login.LogoActivity;
import com.pactera.financialmanager.ui.ParentFragment;
import com.pactera.financialmanager.ui.bookbuildingfrocompany.ActivityBaseInfo;
import com.pactera.financialmanager.ui.model.AssessRetValEntity;
import com.pactera.financialmanager.ui.service.HConnection;
import com.pactera.financialmanager.ui.service.HRequest;
import com.pactera.financialmanager.ui.service.HResponse;
import com.pactera.financialmanager.ui.service.HttpConnSoap;
import com.pactera.financialmanager.util.BitmapUtils;
import com.pactera.financialmanager.util.Constants.requestType;
import com.pactera.financialmanager.util.InterfaceInfo;
import com.pactera.financialmanager.util.NewCatevalue;
import com.pactera.financialmanager.util.RoundImageView;
import com.pactera.financialmanager.util.Tool;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * 这是第六步，客户评价信息的fragment
 */

public class FragmentBookBuildingUploadPic extends ParentFragment implements OnClickListener {
	private FragmentManager fm;
	private RoundImageView[] imgUploadPic = new RoundImageView[6];
	private ImageView[] imgCheckIcon = new ImageView[6];
	private Button btnComplete;

	private final int companyAssessCheckFlag = 101, companyAssessEditFlag = 102;
	private HConnection HCon;
	private AssessRetValEntity assessEntity;
	private EditText etInfo1, etInfo2, etInfo4;
	private CheckBox etInfo3;
	private String peizhiFangxiangId, xuqiufenleiId;

	public static FragmentBookBuildingUploadPic bookBuildingUploadPic;
	public static final int PiCInfo = 3;// 图片信息
	public static final int DeleteInfo = 4;// 删除图片
	public static final int UpPiC = 5;// 上传图片
	public static final int returnDelete = 6;// 返回删除结果
	private HttpConnSoap soapCon;
	private int picID = 0;// 图片索引
	// 存储返回的文件ID
	private String[] fileid = { "", "", "", "", "", "" };

	ScrollView BookBuildingUploadPic_con;

	/**
	 * 图片选择管理
	 */

	PopupWindow popupWindow;
	View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_uploadcustomerpic, null);
		fm = getFragmentManager();
		setupView(view);
		setListener();

		sendRequestForCustomerAssess();// 查询客户评价信息
		return view;
	}

	// 查询客户评估信息
	private void sendRequestForCustomerAssess() {

		String custId = ActivityBaseInfo.custID;

		HCon = RequestInfo(this, requestType.Search, InterfaceInfo.BookBuildingUploadPic_Get, custId,
				companyAssessCheckFlag);

	}

	// 编辑客户评估信息
	private void editInfoForCustomerAssess() {

		String shuomingStr = etInfo4.getText().toString().trim();
		// if (Tool.checkInputStr(peizhiStr)) {
		// toast("请选择培植方向", 0).show();
		// return;
		// }
		// if (Tool.checkInputStr(fenleiStr)) {
		// toast("请选择需求分类", 0).show();
		// return;
		// }
		// if (Tool.checkInputStr(shuomingStr)) {
		// toast("请输入说明信息", 0).show();
		// return;
		// }
		String custid = ActivityBaseInfo.custID;
		String reqStr = "{custid:\"" + custid + "\",cultivale:\"" // 培植方向
				+ peizhiFangxiangId + "\",req_cate:\"" // 需求分类
				+ xuqiufenleiId + "\",is_care:\"" // 是否关注0，关注；1，不关注；
				+ (etInfo3.isChecked() ? "0" : "1") + "\",des:\"" // 说明
				+ shuomingStr + "\"}";

		HCon = RequestInfo(this, requestType.Update, InterfaceInfo.BookBuildingUploadPic_Update, reqStr,
				companyAssessEditFlag);

	}

	@Override
	public void onConnected(Message msg) {
		super.onConnected(msg);
		JSONObject tmpJsonObject;
		switch (connectionIndex) {
		case companyAssessCheckFlag:// 查询客户评价信息
			HResponse companyAssessCheckRes = HCon.getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
			if (companyAssessCheckRes == null || companyAssessCheckRes.dataJsonObject == null) {
				return;
			}
			tmpJsonObject = companyAssessCheckRes.dataJsonObject;

			readRetJson(tmpJsonObject, connectionIndex);
			break;
		case companyAssessEditFlag:// 编辑客户评价信息
			HResponse companyAssessEditRes = HCon.getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
			if (companyAssessEditRes == null || companyAssessEditRes.dataJsonObject == null) {
				return;
			}
			tmpJsonObject = companyAssessEditRes.dataJsonObject;

			readRetJson(tmpJsonObject, connectionIndex);
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
				toast("获取图片失败！");
				return;
			}
			// 将图片显示在界面
			// customerfragment_photo[picID].setImageBitmap(photo);
			// 将图片显示在界面
			imgUploadPic[picID].setVisibility(View.VISIBLE);
			imgUploadPic[picID].setImageBitmap(photo);
			imgCheckIcon[picID].setVisibility(View.GONE);

			if (fileid[picID] != "") {
				deletePic(fileid[picID]);
			}

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
			nameValuePairs.add(new BasicNameValuePair("spareOne", ActivityBaseInfo.custID));
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
				soapCon = new HttpConnSoap(FragmentBookBuildingUploadPic.this, request, nameValuePairs);
				soapCon.setIndex(UpPiC);
			} catch (Exception e) {
				e.printStackTrace();
			}

			break;
		case DeleteInfo:

			imgUploadPic[picID].setVisibility(View.GONE);
			imgCheckIcon[picID].setVisibility(View.VISIBLE);
			if (fileid[picID] != "") {
				deletePic(fileid[picID]);
			}

			break;
		case UpPiC:
			HResponse ress = soapCon.getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
			if (ress == null || ress.dataJsonObject == null) {
				return;
			}
			tmpJsonObject = ress.dataJsonObject;
			readJson(tmpJsonObject, connectionIndex);
			break;
		default:
			break;
		}
	}

	// 解析上传图片的返回JSON
	private void readJson(JSONObject tmpJsonObject, int connectionIndex) {
		// TODO Auto-generated method stub
		String retCode = Tool.getJsonValue(tmpJsonObject, "retCode");

		if (retCode.equals("0000")) {
			if (connectionIndex == UpPiC) {

				fileid[picID] = Tool.getJsonValue(tmpJsonObject, "fileid");

				toast("图片上传成功！");
			} else if (connectionIndex == returnDelete) {
				Log.i("1111111", "图片删除成功!");
			}

		}
	}

	private void readRetJson(JSONObject tmpJsonAssessCheckObject, int connectionIndex) {
		String retCode = Tool.getJsonValue(tmpJsonAssessCheckObject, "retCode");

		if ("0000".equals(retCode)) {
			switch (connectionIndex) {
			case companyAssessCheckFlag:

				assessEntity = (AssessRetValEntity) JSON.parseObject(tmpJsonAssessCheckObject.toString(),
						AssessRetValEntity.class);
				showAssessInfo();
				break;
			case companyAssessEditFlag:// 编辑
				// 编辑成功之后就退出
				toast("建档完成！");

				getActivity().finish();
				break;
			default:
				break;
			}
		} else {
			toast(retCode);
			getActivity().finish();
		}

	}

	// 将查询到的数据展示出来
	private void showAssessInfo() {
		String isCare = assessEntity.getIs_care();// 是否关注
		String Des = assessEntity.getDes();// 说明

		peizhiFangxiangId = assessEntity.getCultivale();
		xuqiufenleiId = assessEntity.getReq_cate();
		String cultiVale = NewCatevalue.getLabel(getActivity(), NewCatevalue.cultivate_dire, peizhiFangxiangId);// 培植方向
		String reqCate = NewCatevalue.getLabel(getActivity(), NewCatevalue.req_cate_type, xuqiufenleiId);// 需求分类

		etInfo1.setText(TextUtils.isEmpty(cultiVale) ? "" : cultiVale);
		etInfo2.setText(TextUtils.isEmpty(reqCate) ? "" : reqCate);
		// 目前返回的isCare的值不知道，根据这个值再设置了
		Log.i("....isCare....", "isCare:" + isCare);
		if ("0".equals(isCare)) {// 关注
			etInfo3.setChecked(true);
		} else if ("1".equals(isCare)) {// 不关注
			etInfo3.setChecked(false);
		}
		etInfo4.setText(Des);
	}

	private void setListener() {
		etInfo1.setOnClickListener(this);
		etInfo2.setOnClickListener(this);
		btnComplete.setOnClickListener(this);

		for (int i = 0; i < imgCheckIcon.length; i++) {
			imgCheckIcon[i].setOnClickListener(this);
		}
		for (int i = 0; i < imgUploadPic.length; i++) {
			imgUploadPic[i].setOnClickListener(this);
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
		popupWindow.showAtLocation(BookBuildingUploadPic_con, Gravity.BOTTOM, 0, 0);

	}

	private void setupView(View view) {
		etInfo1 = (EditText) view.findViewById(R.id.et_info1);
		etInfo2 = (EditText) view.findViewById(R.id.et_info2);
		etInfo3 = (CheckBox) view.findViewById(R.id.et_info3);
		etInfo4 = (EditText) view.findViewById(R.id.et_info4);

		imgUploadPic[0] = (RoundImageView) view.findViewById(R.id.iv_ibd_identityupload_iv1);
		imgUploadPic[1] = (RoundImageView) view.findViewById(R.id.iv_ibd_identityupload_iv2);
		imgUploadPic[2] = (RoundImageView) view.findViewById(R.id.iv_ibd_identityupload_iv3);
		imgUploadPic[3] = (RoundImageView) view.findViewById(R.id.iv_ibd_identityupload_iv4);
		imgUploadPic[4] = (RoundImageView) view.findViewById(R.id.iv_ibd_identityupload_iv5);
		imgUploadPic[5] = (RoundImageView) view.findViewById(R.id.iv_ibd_identityupload_iv6);

		imgCheckIcon[0] = (ImageView) view.findViewById(R.id.iv_check_identityupload_iv1);
		imgCheckIcon[1] = (ImageView) view.findViewById(R.id.iv_check_identityupload_iv2);
		imgCheckIcon[2] = (ImageView) view.findViewById(R.id.iv_check_identityupload_iv3);
		imgCheckIcon[3] = (ImageView) view.findViewById(R.id.iv_check_identityupload_iv4);
		imgCheckIcon[4] = (ImageView) view.findViewById(R.id.iv_check_identityupload_iv5);
		imgCheckIcon[5] = (ImageView) view.findViewById(R.id.iv_check_identityupload_iv6);
		BookBuildingUploadPic_con = (ScrollView) view.findViewById(R.id.BookBuildingUploadPic_con);
		btnComplete = (Button) view.findViewById(R.id.btn_complete);
		bookBuildingUploadPic = this;
	}

	@Override
	public void onResume() {
		if (fm == null) {
			fm = getFragmentManager();
		}
		super.onResume();
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

	/**
	 * 删除图片
	 *
	 * @param 文件
	 */
	private void deletePic(String fileid) {
		// TODO Auto-generated method stub
		// HCon = RequestInfo(this, requestType.Delete,
		// InterfaceInfo.CusArchiving_DeletePic, fileid, returnDelete);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_complete:
			editInfoForCustomerAssess();
			break;
		case R.id.et_info1:
			SpinnerAdapter.showSelectDialog(getActivity(), NewCatevalue.cultivate_dire, etInfo1,
					new CallBackItemClickListener() {
						@Override
						public void CallBackItemClick(String spinnerID) {
							peizhiFangxiangId = spinnerID;
						}
					});
			break;
		case R.id.et_info2:
			SpinnerAdapter.showSelectDialog(getActivity(), NewCatevalue.req_cate_type, etInfo2,
					new CallBackItemClickListener() {
						@Override
						public void CallBackItemClick(String spinnerID) {
							xuqiufenleiId = spinnerID;
						}
					});
			break;
		case R.id.iv_check_identityupload_iv1:
		case R.id.iv_ibd_identityupload_iv1:
			picID = 0;
			showWindow();
			break;
		case R.id.iv_check_identityupload_iv2:
		case R.id.iv_ibd_identityupload_iv2:
			picID = 1;
			showWindow();
			break;
		case R.id.iv_check_identityupload_iv3:
		case R.id.iv_ibd_identityupload_iv3:
			picID = 2;
			showWindow();
			break;
		case R.id.iv_check_identityupload_iv4:
		case R.id.iv_ibd_identityupload_iv4:
			picID = 3;
			showWindow();
			break;
		case R.id.iv_check_identityupload_iv5:
		case R.id.iv_ibd_identityupload_iv5:
			picID = 4;
			showWindow();
			break;
		case R.id.iv_check_identityupload_iv6:
		case R.id.iv_ibd_identityupload_iv6:
			picID = 5;
			showWindow();
			break;
		default:
			break;
		}
	}

}
