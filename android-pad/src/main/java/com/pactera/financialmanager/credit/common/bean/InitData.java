package com.pactera.financialmanager.credit.common.bean;

import com.pactera.financialmanager.credit.common.bean.http.AfterTheLoan;
import com.pactera.financialmanager.credit.common.bean.http.BeanHeader;
import com.pactera.financialmanager.credit.common.bean.http.ExpirationReminder;
import com.pactera.financialmanager.credit.common.bean.http.OverdueCustomer;
import com.pactera.financialmanager.credit.common.bean.search.LoanAfterBean;
import com.pactera.financialmanager.credit.common.bean.warn.WarnCrmInfoBean;
import com.pactera.financialmanager.credit.main.service.search.KeyPersonBean;
import com.pactera.financialmanager.util.LogUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dysen on 2017/7/12.
 */

public class InitData {

	//====================================模拟生成数据==========================================

	public static List<String> getTopHeaderData(List<BeanHeader> listHeader) {
		List<String> mList = new ArrayList<>();
		for (int i = 0; i < listHeader.size(); i++) {

			mList.add(listHeader.get(i).getNAME());
		}
		return mList;
	}

	public static List<String> getTopHeaderDataAfter(List<BeanHeader.AfterManagementBean> listHeader) {
		List<String> mList = new ArrayList<>();
		for (int i = 0; i < listHeader.size(); i++) {

			mList.add(listHeader.get(i).getNAME());
		}
		return mList;
	}

	public static List<String> getLeftHeaderData(int listSize) {
		List<String> mList = new ArrayList<>();
		for (int i = 1; i <= listSize; i++) {

			mList.add(i + "");
		}
		return mList;
	}

	/**
	 * 客户违约提醒
	 */
	public static class DefaultWarn {

		public static List<List<Object>> getCellDataDK(List<BeanHeader> listHeader, List<WarnCrmInfoBean.DefaultInfoBean.Daikuan> list) {

			List<List<Object>> mList = new ArrayList<>();
			for (int i = 0; i < list.size(); i++) {//行数

				List<Object> mListChild = new ArrayList<>();
				Map<String, Object> map = new HashMap<>();

					map.put("CST_NAME", list.get(i).getCST_NAME());
					map.put("LOAN_ACC", list.get(i).getLOAN_ACC());
					map.put("PROD_NAME", list.get(i).getPROD_NAME());
					map.put("OVERDUE_AMOUNT", list.get(i).getOVERDUE_AMOUNT());
					map.put("OVERDUE_DT", list.get(i).getOVERDUE_DT());

				for (int j = 0; j < listHeader.size(); j++) {//列数

                    mListChild.add(map.get(listHeader.get(j).getID()));
				}
				mList.add(mListChild);

			}
			return mList;
		}

		public static List<List<Object>> getCellDataXYK(List<BeanHeader> listHeader, List<WarnCrmInfoBean.DefaultInfoBean.XinYongKa> list) {

			List<List<Object>> mList = new ArrayList<>();
			for (int i = 0; i < list.size(); i++) {//行数

				List<Object> mListChild = new ArrayList<>();
				Map<String, Object> map = new HashMap<>();

					map.put("CST_NAME", list.get(i).getCST_NAME());
					map.put("CREDIT_CARD", list.get(i).getCREDIT_CARD());
					map.put("OVERDUE_AMOUNT", list.get(i).getOVERDUE_AMOUNT());
					map.put("OVERDUE_DT", list.get(i).getOVERDUE_DT());

				for (int j = 0; j < listHeader.size(); j++) {//列数

                    mListChild.add(map.get(listHeader.get(j).getID()));
				}
				mList.add(mListChild);
			}
			return mList;
		}
	}

	/**
	 * 客户异动提醒
	 */
	public static class TransactionWarn {

		public static List<List<Object>> getCellDataYEBZ(List<BeanHeader> listHeader, List<WarnCrmInfoBean.TransactionInfoBean.YEBZ> list) {

			List<List<Object>> mList = new ArrayList<>();
			for (int i = 0; i < list.size(); i++) {//行数

				List<Object> mListChild = new ArrayList<>();
				Map<String, Object> map = new HashMap<>();

				map.put("CST_NAME", list.get(i).getCST_NAME());
				map.put("LOAN_ACC", list.get(i).getLOAN_ACC());
				map.put("YHBAL", list.get(i).getYHBAL());
				map.put("AMOUNT_BAL", list.get(i).getAMOUNT_BAL());
				map.put("BACKDATE", list.get(i).getBACKDATE());

				for (int j = 0; j < listHeader.size(); j++) {//列数

                    mListChild.add(map.get(listHeader.get(j).getID()));
				}
				mList.add(mListChild);

			}
			return mList;
		}
		public static List<List<Object>> getCellDataZHYD(List<BeanHeader> listHeader, List<WarnCrmInfoBean.TransactionInfoBean.TRANSACTION> list) {

			List<List<Object>> mList = new ArrayList<>();
			for (int i = 0; i < list.size(); i++) {//行数

				List<Object> mListChild = new ArrayList<>();
				Map<String, Object> map = new HashMap<>();

				map.put("CST_NAME", list.get(i).getCST_NAME());
				map.put("LOAN_ACC", list.get(i).getLOAN_ACC());
				map.put("TRANS_TYPE", list.get(i).getTRANS_TYPE());
				map.put("TRADE_DT", list.get(i).getTRADE_DT());

				for (int j = 0; j < listHeader.size(); j++) {//列数

                    mListChild.add(map.get(listHeader.get(j).getID()));
				}
				mList.add(mListChild);

			}
			return mList;
		}
		public static List<List<Object>> getCellDataZJDB(List<BeanHeader> listHeader, List<WarnCrmInfoBean.TransactionInfoBean.ZJDB> list) {

			List<List<Object>> mList = new ArrayList<>();
			for (int i = 0; i < list.size(); i++) {//行数

				List<Object> mListChild = new ArrayList<>();
				Map<String, Object> map = new HashMap<>();

				map.put("CST_NAME", list.get(i).getCST_NAME());
				map.put("LOAN_ACC", list.get(i).getLOAN_ACC());
				map.put("TRADE_TIME", list.get(i).getTRADE_TIME());
				map.put("BAL", list.get(i).getBAL());
				map.put("CST_NAME", list.get(i).getCST_NAME());

				for (int j = 0; j < listHeader.size(); j++) {//列数

                    mListChild.add(map.get(listHeader.get(j).getID()));
				}
				mList.add(mListChild);

			}
			return mList;
		}
		public static List<List<Object>> getCellDataZJDR(List<BeanHeader> listHeader, List<WarnCrmInfoBean.TransactionInfoBean.ZJDR> list) {

			List<List<Object>> mList = new ArrayList<>();
			for (int i = 0; i < list.size(); i++) {//行数

				List<Object> mListChild = new ArrayList<>();
				Map<String, Object> map = new HashMap<>();

				map.put("RR", list.get(i).getRR());
				map.put("BAL", list.get(i).getBAL());
				map.put("TRADE_DT", list.get(i).getTRADE_DT());
				map.put("CST_NAME", list.get(i).getCST_NAME());

				for (int j = 0; j < listHeader.size(); j++) {//列数

                    mListChild.add(map.get(listHeader.get(j).getID()));
				}
				mList.add(mListChild);

			}
			return mList;
		}

	}

	/**
	 * 客户关怀提醒
	 */
	public static class CareWarn {

		public static List<List<Object>> getCellDataKHSR(List<BeanHeader> listHeader, List<WarnCrmInfoBean.CareInfoBean.KHSR> list) {

			List<List<Object>> mList = new ArrayList<>();
			for (int i = 0; i < list.size(); i++) {//行数

				List<Object> mListChild = new ArrayList<>();
				Map<String, Object> map = new HashMap<>();

				map.put("RR", list.get(i).getRR());
				map.put("BIRTHDAY", list.get(i).getBIRTHDAY());
				map.put("NL", list.get(i).getNL());
				map.put("CUST_LEVEL", list.get(i).getCUST_LEVEL());
				map.put("CST_NAME", list.get(i).getCST_NAME());
				map.put("SR", list.get(i).getSR());

				for (int j = 0; j < listHeader.size(); j++) {//列数

                    mListChild.add(map.get(listHeader.get(j).getID()));
				}
				mList.add(mListChild);

			}
			return mList;
		}
		public static List<List<Object>> getCellDataKHSJJ(List<BeanHeader> listHeader, List<WarnCrmInfoBean.CareInfoBean.kHSJJ> list) {

			List<List<Object>> mList = new ArrayList<>();
			for (int i = 0; i < list.size(); i++) {//行数

				List<Object> mListChild = new ArrayList<>();
				Map<String, Object> map = new HashMap<>();

				map.put("RR", list.get(i).getRR());
				map.put("LAST_LEVEL", list.get(i).getLAST_LEVEL());
				map.put("EVENT_TYPE", list.get(i).getEVENT_TYPE());
				map.put("CURRENT_LEVEL", list.get(i).getCURRENT_LEVEL());
				map.put("CST_NAME", list.get(i).getCST_NAME());

				for (int j = 0; j < listHeader.size(); j++) {//列数

                    mListChild.add(map.get(listHeader.get(j).getID()));
				}
				mList.add(mListChild);

			}
			return mList;
		}

	}

	/**
	 * 金融需求提醒
	 */
	public static class FinancialNeedsWarn {

		public static List<List<Object>> getCellDataCK(List<BeanHeader> listHeader, List<WarnCrmInfoBean.FinancialNeedsInfoBean.CK> list) {

			List<List<Object>> mList = new ArrayList<>();
			for (int i = 0; i < list.size(); i++) {//行数

				List<Object> mListChild = new ArrayList<>();
				Map<String, Object> map = new HashMap<>();

				map.put("CST_NAME", list.get(i).getCST_NAME());
				map.put("CHLTYP", list.get(i).getCHLTYP());
				map.put("APPOINT_AMOUNT", list.get(i).getAPPOINT_AMOUNT());
				map.put("APPOINT_DATE", list.get(i).getAPPOINT_DATE());

				for (int j = 0; j < listHeader.size(); j++) {//列数

                    mListChild.add(map.get(listHeader.get(j).getID()));
				}
				mList.add(mListChild);

			}
			return mList;
		}
		public static List<List<Object>> getCellDataLC(List<BeanHeader> listHeader, List<WarnCrmInfoBean.FinancialNeedsInfoBean.LC> list) {

			List<List<Object>> mList = new ArrayList<>();
			for (int i = 0; i < list.size(); i++) {//行数

				List<Object> mListChild = new ArrayList<>();
				Map<String, Object> map = new HashMap<>();

				map.put("CST_NAME", list.get(i).getCST_NAME());
				map.put("CHLTYP", list.get(i).getCHLTYP());
				map.put("APPOINT_AMOUNT", list.get(i).getAPPOINT_AMOUNT());
				map.put("APPOINT_DATE", list.get(i).getAPPOINT_DATE());

				for (int j = 0; j < listHeader.size(); j++) {//列数

                    mListChild.add(map.get(listHeader.get(j).getID()));
				}
				mList.add(mListChild);

			}
			return mList;
		}
		public static List<List<Object>> getCellDataDK(List<BeanHeader> listHeader, List<WarnCrmInfoBean.FinancialNeedsInfoBean.DK> list) {

			List<List<Object>> mList = new ArrayList<>();
			for (int i = 0; i < list.size(); i++) {//行数

				List<Object> mListChild = new ArrayList<>();
				Map<String, Object> map = new HashMap<>();

				map.put("CST_NAME", list.get(i).getCST_NAME());
				map.put("CHLTYP", list.get(i).getCHLTYP());
				map.put("APPOINT_AMOUNT", list.get(i).getAPPOINT_AMOUNT());
				map.put("APPOINT_DATE", list.get(i).getAPPOINT_DATE());

				for (int j = 0; j < listHeader.size(); j++) {//列数

                    mListChild.add(map.get(listHeader.get(j).getID()));
				}
				mList.add(mListChild);

			}
			return mList;
		}
		public static List<List<Object>> getCellDataDZCP(List<BeanHeader> listHeader, List<WarnCrmInfoBean.FinancialNeedsInfoBean.DZCP> list) {

			List<List<Object>> mList = new ArrayList<>();
			for (int i = 0; i < list.size(); i++) {//行数

				List<Object> mListChild = new ArrayList<>();
				Map<String, Object> map = new HashMap<>();

				map.put("CST_NAME", list.get(i).getCST_NAME());
				map.put("CHLTYP", list.get(i).getCHLTYP());
				map.put("XQLX", list.get(i).getXQLX());
				map.put("APPOINT_AMOUNT", list.get(i).getAPPOINT_AMOUNT());
				map.put("APPOINT_DATE", list.get(i).getAPPOINT_DATE());

				for (int j = 0; j < listHeader.size(); j++) {//列数

                    mListChild.add(map.get(listHeader.get(j).getID()));
				}
				mList.add(mListChild);
			}
			return mList;
		}
	}

	/**
	 * 产品到期提醒
	 */
	public static class ProductExpiresWarn {

		public static List<List<Object>> getCellDataDQCK(List<BeanHeader> listHeader, List<WarnCrmInfoBean.ProductInfoBean.DQCK> list) {

			List<List<Object>> mList = new ArrayList<>();
			for (int i = 0; i < list.size(); i++) {//行数

				List<Object> mListChild = new ArrayList<>();
				Map<String, Object> map = new HashMap<>();

				map.put("RR", list.get(i).getRR());
				map.put("EVENT_BAL", list.get(i).getEVENT_BAL());
				map.put("EXPIRE_DT", list.get(i).getEXPIRE_DT());
				map.put("CST_NAME", list.get(i).getCST_NAME());
				map.put("ACC_DEP_ACC", list.get(i).getACC_DEP_ACC());

				for (int j = 0; j < listHeader.size(); j++) {//列数

                    mListChild.add(map.get(listHeader.get(j).getID()));
				}
				mList.add(mListChild);

			}
			return mList;
		}
		public static List<List<Object>> getCellDataLCCP(List<BeanHeader> listHeader, List<WarnCrmInfoBean.ProductInfoBean.LCCP> list) {

			List<List<Object>> mList = new ArrayList<>();
			for (int i = 0; i < list.size(); i++) {//行数

				List<Object> mListChild = new ArrayList<>();
				Map<String, Object> map = new HashMap<>();

				map.put("CST_NAME", list.get(i).getCST_NAME());
				map.put("PROD_NAME", list.get(i).getPROD_NAME());
				map.put("EXPECTED_YIELD", list.get(i).getEXPECTED_YIELD());
				map.put("EVENT_BAL", list.get(i).getEVENT_BAL());
				map.put("EXPIRE_DT", list.get(i).getEXPIRE_DT());

				for (int j = 0; j < listHeader.size(); j++) {//列数

                    mListChild.add(map.get(listHeader.get(j).getID()));
				}
				mList.add(mListChild);

			}
			return mList;
		}
		public static List<List<Object>> getCellDataDKCP(List<BeanHeader> listHeader, List<WarnCrmInfoBean.ProductInfoBean.DKCP> list) {

			List<List<Object>> mList = new ArrayList<>();
			for (int i = 0; i < list.size(); i++) {//行数

				List<Object> mListChild = new ArrayList<>();
				Map<String, Object> map = new HashMap<>();

				map.put("RR", list.get(i).getRR());
				map.put("EVENT_BAL", list.get(i).getEVENT_BAL());
				map.put("LOAN_ACC", list.get(i).getLOAN_ACC());
				map.put("EXPIRE_DT", list.get(i).getEXPIRE_DT());
				map.put("PROD_NAME", list.get(i).getPROD_NAME());
				map.put("CST_NAME", list.get(i).getCST_NAME());

				for (int j = 0; j < listHeader.size(); j++) {//列数

                    mListChild.add(map.get(listHeader.get(j).getID()));
				}
				mList.add(mListChild);

			}
			return mList;
		}
	}

	/**
	 * 逾期提醒
	 */
	public static class OverDueWarn {

		public static List<List<Object>> getCellData(List<BeanHeader> listHeader, List<OverdueCustomer> list) {

			List<List<Object>> mList = new ArrayList<>();
			for (int i = 0; i < list.size(); i++) {//行数

				List<Object> mListChild = new ArrayList<>();
				Map<String, Object> map = new HashMap<>();

				map.put("DueBillNO", list.get(i).getDueBillNO());
				map.put("CustomerID", list.get(i).getCustomerID());
				map.put("ConTractNo", list.get(i).getConTractNo());
				map.put("CustomerName", list.get(i).getCustomerName());
				map.put("BusinessName1", list.get(i).getBusinessName1());
				map.put("BusinessSum", list.get(i).getBusinessSum());
				map.put("Balance", list.get(i).getBalance());
				map.put("ActualMaturity", list.get(i).getActualMaturity());
				map.put("InterestBalance", list.get(i).getInterestBalance());
				map.put("OfficeTel", list.get(i).getOfficeTel());
				for (int j = 0; j < listHeader.size(); j++) {//列数

                    mListChild.add(map.get(listHeader.get(j).getID()));
				}
				mList.add(mListChild);

			}
			return mList;
		}

	}

	/**
	 * 到期提醒
	 */
	public static class ExpirationWarn {

		public static List<List<Object>> getCellData(List<BeanHeader> listHeader, List<ExpirationReminder> list) {

			List<List<Object>> mList = new ArrayList<>();
			for (int i = 0; i < listHeader.size(); i++) {
				LogUtils.i("id======================"+listHeader.get(i).getID());
			}
			for (int i = 0; i < list.size(); i++) {//行数
				List<Object> mListChild = new ArrayList<>();

				Map<String, Object> map = new HashMap<>();
//				InterestBalance2;ClassifyResult;CustomerName;BusinessName;BusinessSum;ActualBusinessRate;OrgName;BalanceSum;UserName;
// ActualPutOutDate;Balance;OverDueBalance;InterestBalance1;ActualMaturity;OfficeTel;
				//CustomerName OfficeTel BusinessName BusinessSum Balance ActualPutOutDate
				// ActualMaturity OverDueBalance BalanceSum
				// InterestBalance1 InterestBalance2 ClassifyResult ActualBusinessRate UserName OrgName
				map.put("CustomerName", list.get(i).getCustomerName());

				map.put("BusinessSum", list.get(i).getBusinessSum());
				map.put("BusinessName", list.get(i).getBusinessName());
				map.put("Balance", list.get(i).getBalance());
				map.put("ActualMaturity", list.get(i).getActualMaturity());
				map.put("OfficeTel", list.get(i).getOfficeTel());
				for (int j = 0; j < listHeader.size(); j++) {//列数

                    mListChild.add(map.get(listHeader.get(j).getID()));
				}
				mList.add(mListChild);
			}
			return mList;
		}
	}

	/**
	 * 贷后提醒
	 */
	public static class AfterWarn {

		public static List<List<Object>> getCellData(List<BeanHeader> listHeader, List<AfterTheLoan> list) {

			List<List<Object>> mList = new ArrayList<>();
			for (int i = 0; i < list.size(); i++) {//行数
				List<Object> mListChild = new ArrayList<>();
				Map<String, Object> map = new HashMap<>();

				map.put("DueBillNO", list.get(i).getDueBillNO());
				map.put("CustomerID", list.get(i).getCustomerID());
				map.put("ConTractNo", list.get(i).getConTractNo());
				map.put("CustomerName", list.get(i).getCustomerName());
				map.put("BusinessName1", list.get(i).getBusinessName1());
				map.put("BusinessSum", list.get(i).getBusinessSum());
				map.put("Balance", list.get(i).getBalance());
				map.put("ActualMaturity", list.get(i).getActualMaturity());
				map.put("InterestBalance", list.get(i).getInterestBalance());
				map.put("OfficeTel", list.get(i).getOfficeTel());
				for (int j = 0; j < listHeader.size(); j++) {//列数

                    mListChild.add(map.get(listHeader.get(j).getID()));
				}
				mList.add(mListChild);
			}
			return mList;
		}
	}

	/******************************************************************************************/

	public static class KeyPerson {

		public static List<List<String>> getCellDataCustomer(List<BeanHeader> listHeader, List<KeyPersonBean.Customer> listCustomer) {

			List<List<String>> mList = new ArrayList<>();

				for (int i = 0; i < listCustomer.size(); i++) {//行数
					List<String> mListChild = new ArrayList<>();
					Map<String, Object> map = new HashMap<>();

					map.put("CertName", listCustomer.get(i).getCertName());
					map.put("CertID", listCustomer.get(i).getCertID());
					map.put("CustomerName", listCustomer.get(i).getCustomerName());
					map.put("RelationName", listCustomer.get(i).getRelationName());
					map.put("UserName", listCustomer.get(i).getUserName());
					map.put("OrgName", listCustomer.get(i).getOrgName());
					map.put("InputDate", listCustomer.get(i).getInputDate());
					map.put("UpdateDate", listCustomer.get(i).getUpdateDate());
                    for (int j = 0; j < listHeader.size(); j++) {//列数

                        mListChild.add((String) map.get(listHeader.get(j).getID()));
                    }
                    mList.add(mListChild);
				}
			return mList;
		}
		public static List<List<String>> getCellDataBusiness(List<BeanHeader> listHeader, List<KeyPersonBean.Business> listBusiness) {

			List<List<String>> mList = new ArrayList<>();

				for (int i = 0; i < listBusiness.size(); i++) {//行数
					List<String> mListChild = new ArrayList<>();
                    Map<String, Object> map = new HashMap<>();

					map.put("CertName", listBusiness.get(i).getCertName());
					map.put("CertID", listBusiness.get(i).getCertID());
					map.put("CustomerName", listBusiness.get(i).getCustomerName());
					map.put("RelationName", listBusiness.get(i).getRelationName());
					map.put("Telephone", listBusiness.get(i).getTelephone());
					map.put("OfficeTime", listBusiness.get(i).getOfficeTime());
					map.put("PractitionerTime", listBusiness.get(i).getPractitionerTime());
					map.put("OrgName", listBusiness.get(i).getOrgName());
					map.put("UserName", listBusiness.get(i).getUserName());
                    for (int j = 0; j < listHeader.size(); j++) {//列数

                        mListChild.add((String) map.get(listHeader.get(j).getID()));
                    }
                    mList.add(mListChild);
				}

			return mList;
		}
	}

	public static class LoanAfter {

		public static List<List<String>> getCellData(List<BeanHeader> listHeader, List<LoanAfterBean> listData) {

			List<List<String>> mList = new ArrayList<>();

			for (int i = 0; i < listData.size(); i++) {//行数
				List<String> mListChild = new ArrayList<>();
                Map<String, Object> map = new HashMap<>();

				map.put("SerialNo", listData.get(i).getSerialNo());
				map.put("BusinessType", listData.get(i).getBusinessType());
				map.put("ArtificialNo", listData.get(i).getArtificialNo());
				map.put("OccurTypeName", listData.get(i).getOccurTypeName());
				map.put("BusinessTypeName", listData.get(i).getBusinessTypeName());
				map.put("Currency", listData.get(i).getCurrency());
				map.put("BusinessSum", listData.get(i).getBusinessSum());
				map.put("ActualPutOutSum", listData.get(i).getActualPutOutSum());
				map.put("BailRatio", listData.get(i).getBailRatio());
				map.put("Balance", listData.get(i).getBalance());
				map.put("OverdueBalance", listData.get(i).getOverdueBalance());
				map.put("PutOutDate", listData.get(i).getPutOutDate());
				map.put("Maturity", listData.get(i).getMaturity());
				map.put("OperateOrgName", listData.get(i).getOperateOrgName());
                for (int j = 0; j < listHeader.size(); j++) {//列数

                    mListChild.add((String) map.get(listHeader.get(j).getID()));
                }
            mList.add(mListChild);
			}
			return mList;
		}
	}
}
