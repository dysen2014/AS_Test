package com.pactera.financialmanager.ui.credit;

import android.text.TextUtils;
import com.pactera.financialmanager.entity.CreditCardApplyEntity;

import java.util.regex.Pattern;

/**
 * Created by xh on 2015/12/25.
 */
public class CreditInputChecker {

    private CreditCardApplyEntity e;

    private static final Pattern PATTERN_PINYIN = Pattern.compile("^[A-Z]+( [A-Z]+)+$");

    public CreditInputChecker(CreditCardApplyEntity e) {
        this.e = e;
    }

    public String check() {

        String emptyCheckResult;
        emptyCheckResult = checkEmpty(e.getRecordClerkNo(), "柜员号不能为空");

        if (emptyCheckResult != null) {
            return emptyCheckResult;
        }

                // 申请主卡 或者 同时申请
        if (e.getApplyType() == 0 || e.getApplyType() == 2) {

            emptyCheckResult = checkEmpty(
                    e.getProductCode(), "产品代码不能为空",
                    e.getBrandCode(), "品牌代码不能为空",
                    e.getBillDate(), "账单日期不能为空",
                    e.getProjectCode(), "专案代码不能为空",

                    e.getMaPyName(), "拼音不能为空",
                    e.getMaCertNoId(), "发证机关编码不能为空",
                    e.getMaMobileTel(), "手机号不能为空",
                    e.getMaEmail(), "邮箱不能为空",

                    e.getMaCorpAddr(), "单位地址不能为空",
                    e.getMaCorpZipCode(), "单位邮编不能为空",

                    e.getMaStatementAddr(), "请选择邮寄地址",

                    e.getMaDireLinkmanName(), "直属联系人不能为空",
                    e.getMaDireLinkmanRela(), "直属联系人关系不能为空",
                    e.getMaDireLinkmanMobileTel(), "直属联系人联系方式不能为空",
                    e.getMaDireLinkmanAddr(), "直属联系人地址不能为空",

                    // 其他联系人 4 blanks

                    e.getMaDegreeCode(), "请选择受教育程度",
                    e.getMaMaritalStatus(), "婚姻状况不能为空",
                    e.getMaYearIncome(), "年收入不能为空",
                    e.getMaWorkYears() == null ? "" : String.valueOf(e.getMaWorkYears()), "工龄不能为空",
                    e.getMaCorpName(), "单位名称不能为空",
                    e.getMaCorpTel(), "单位电话不能为空",
                    e.getMaCorpAttr(), "单位性质不能为空",
                    e.getMaIndustryCode(), "行业类别不能为空",
                    e.getMaSpecLevel(), "专业职称不能为空",
                    e.getMaDutyCode(), "职务岗位不能为空",
                    e.getMaHouseStatus(), "住宅性质不能为空",
                    e.getMaHouseValue() == null ? "" : String.valueOf(e.getMaHouseValue()), "住宅价值不能为空"
//                    e.getMaCarValue() == null ? "" : String.valueOf(e.getMaCarValue()), "车辆价值不能为空"
            );

            if (emptyCheckResult != null) {
                return emptyCheckResult;
            }

            if ("0003".equals(e.getProductCode()) && TextUtils.isEmpty(e.getUnitCode())) {
                return "单位不能为空";
            }
            if (TextUtils.isEmpty(e.getMaPyName()) || !PATTERN_PINYIN.matcher(e.getMaPyName()).matches()) {
                return "拼音输入有误";
            }

//            if (!bothEmptyOrNot(e.getMaCensusAddr(), e.getMaCensusZipCode())) {
//                return "户籍地址和邮编状态不一致";
//            }

            if (!bothEmptyOrNot(e.getMaHomeAddr(), e.getMaHomeZip())) {
                return "家庭地址和邮编状态不一致";
            }

            if (!bothEmptyOrNot(e.getMaCorpAddr(), e.getMaCorpZipCode())) {
                return "工作地址和邮编状态不一致";
            }

            if (TextUtils.isEmpty(e.getMaHomeAddr()) && TextUtils.isEmpty(e.getMaCorpAddr())) {
                return "家庭地址和工作地址至少填写一个";
            }
        }

        // 申请附卡 或者 同时申请
        if (e.getApplyType() == 1 || e.getApplyType() == 2) {

            emptyCheckResult = checkEmpty(
                    e.getPrimaryCustName(), "主卡持卡人姓名不能为空",
                    e.getPcRela(), "请选择与主卡持卡人关系",
                    e.getSuHomeZip(), "住宅地址邮编不能为空",
                    e.getSuHomeAddr(), "住宅地址不能为空",
                    e.getSuCertNoId(), "发证机关编码不能为空",
                    e.getSuMobileTel(), "移动电话不能为空"
            );

            if (emptyCheckResult != null) {
                return emptyCheckResult;
            }

            // 单独申请副卡时校验，同时申请时不校验
            if (e.getApplyType() == 1) {
                if ((emptyCheckResult = checkEmpty(e.getPriCardNo(), "关联主卡卡号不能为空")) != null) {
                    return emptyCheckResult;
                }
            }

            if (TextUtils.isEmpty(e.getSuPyName()) || !PATTERN_PINYIN.matcher(e.getSuPyName()).matches()) {
                return "拼音输入有误";
            }
        }

        return null;
    }

    private String checkEmpty(String... s) {
        for (int i = 0; i < s.length; i += 2) {
            if (TextUtils.isEmpty(s[i])) {
                return s[i + 1];
            }
        }
        return null;
    }

    private boolean bothEmptyOrNot(String addr, String zipcode) {
        return !(TextUtils.isEmpty(addr) ^ TextUtils.isEmpty(zipcode));
    }
}
