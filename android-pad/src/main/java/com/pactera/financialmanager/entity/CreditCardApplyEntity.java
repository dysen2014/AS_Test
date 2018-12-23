package com.pactera.financialmanager.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;
import com.lidroid.xutils.db.annotation.Transient;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by xh on 2015/12/11.
 */
@Table(name = "t_credit_card_apply")
public class CreditCardApplyEntity implements Serializable {

    @Id
    private long id;

    private String userId;

    /** common start **/
    private String recordBranCode;  // 录入机构

    private String recordClerkNo;   // 录入柜员编号

    private String custManger;      // 营销客户经理

    private String applyBranCode;   // 营销机构

    private String projectCode;    // 专案代码

    private int applyType;          // 0: 主卡  1：附卡  2：主卡+附卡

    private String isCreditCard = "0";  // 是否持有我行信用卡

    private String cardNo;          // 若持有我行信用卡，此处填卡号

    /** common end **/

    /**  master card start   **/

    /*  card relate start  */
    private String productCode;

    private String brandCode;

    private String billDate; // 5 15 25

    private String isDown = "1";    // 是否接受降级，0：否 1：是

    private String unitCode;    // 所属单位

    private String statementMode = "1";  // 寄送方式

    private String autoRepayFlag = "0";  // 自动还款 0：不开通  1：开通

    private String autoRepayAcctNo = ""; // 自动还款账号

    private String paymentMode = "1";   // 还款方式 0：最低还款额  1：全额还款
    /*  card relate end   */

    /*  customer info start   */
    private String maExternCustNo;    // 外部客户编码  客户号

    private String maStaffFlag = "0";       // 0：客户  1：员工

    private String maCustName;

    private String maPyName;

    private String maSex;  // 1:male 2:female

    private String maBirthday; // yyyy-MM-dd

    private String maCertType = "01";    // 01：身份证

    private String maCertNoId;    // 发证机关编码

    private String maCertNo;

    private String maMobileTel;

    private String maHomeTel = "000-000000";

    private String maEmail;

    private String maMaritalStatus = "90"; // 10: 未婚 20：已婚 40：离异 90: 未知

    private String maHasChildren;   // 0: 无子女  1：有子女

    private String maDegreeCode;  // 教育程度

    private String maHouseStatus;

    private Integer maHouseValue;

    private Integer maCarValue;

    /*  work relate start */
    private String maCorpName;    // 单位名称

    private String maCorpAttr;  // 单位性质

    private String maIndustryCode; //  行业类别

    private String maDeptDesc;    // 任职部门，非必输

    private String maDutyCode;    // 职务岗位

    private String maSpecLevel;   // 专业职称

    private String maCorpTel;   // 单位电话

    private Integer maWorkYears; // 工作年限

    private String maYearIncome; // 年收入
    /*  work relate end */

    /*  cust info start   */

    /* address start */
    private String maHomeAddr;

    private String maHomeZip;

    private String maCorpAddr;

    private String maCorpZipCode;

    private String maCensusAddr;

    private String maCensusZipCode;

    private String maStatementAddr; // 邮寄地址
    /* address end */


    /*  contacts start */
    private String maDireLinkmanName;    // 联系人姓名

    private String maDireLinkmanRela;    // 联系人关系

    private String maDireLinkmanMobileTel;   // 联系人电话

    private String maDireLinkmanAddr;    // 联系人地址

    private String maOthLinkmanName;    // 联系人姓名

    private String maOthLinkmanRela;    // 联系人关系

    private String maOthLinkmanMobileTel;   // 联系人电话

    private String maOthLinkmanAddr;    // 联系人地址
    /*  contacts end */

    /**  master card end   **/


    /**  supplementary card start   **/
    private String primaryCustName;     // 主卡持卡人姓名

    private String priCardNo;           // 关联主卡卡号

    private String pcRela;              // 与主卡持卡人关系

    private String suCustName;

    private String suPyName;

    private String suCertType = "01";    // 附卡证件类型

    private String suCertNo;             //  附卡证件号码

    private String suExternCustNo;       // 附卡持卡人外部客户编号

    private String suCertNoId;           // 附卡持卡人证件发证机关编码

    private String suCertNoName;         // 附卡持卡人证件发证机关名称

    private String suSex;                // 附卡性别 M:male F:female

    private String suBirthday;           // 附卡出生日期 yyyy-MM-dd

    private String suMobileTel;          // 移动电话

    private String suHomeTel = "000-000000";            // 住宅电话

    private String suHomeAddr;           // 住宅地址

    private String suHomeZip;            // 住宅地址邮编

    private Integer suApplyCredLimit;    // 申请信用额度 非必输，默认空

//    private String masterOwnerIDCard;
//    private String suEmail;

    /**  supplementary card end   **/


    private List<PicEntity> attachedPics;

    @Column(column = "last_update_time")
    private Date lastUpdateTime;

    @Column(column = "finished")
    private int finished;   // whether all blanks filled, if finished, upload operation could be done检验填充

    @Table(name = "t_pic")
    public static class PicEntity {
        @Id
        private long id;

        @Column(column = "type")
        private String type;

        @Column(column = "desc")
        private String desc;

        @Column(column = "content")
        private byte[] content;

        @Column(column = "eid")
        private long eid;

        @Transient //@Transient  1 表示该属性并非一个到数据库表的字段的映射, 2 将不需要序列化的属性前添加关键字transient，不能注解方法或者类
        private String base64Content;

        @JSONField(serialize = false)
        @Transient
        public boolean checked = true;    // this field is used for view list, not into db

        @JSONField(serialize = false)
        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        @JSONField(serialize = false)
        public byte[] getContent() {
            return content;
        }

        public void setContent(byte[] content) {
            this.content = content;
        }

        @JSONField(serialize = false)
        public long getEid() {
            return eid;
        }

        public void setEid(long eid) {
            this.eid = eid;
        }

        public String getBase64Content() {
            return base64Content;
        }

        public void setBase64Content(String base64Content) {
            this.base64Content = base64Content;
        }
    }


    public List<PicEntity> getAttachedPics() {
        return attachedPics;
    }

    public void addAttachedFile(String type, String desc, byte[] content) {
        if (attachedPics == null) {
            attachedPics = new ArrayList<PicEntity>(5);
        }

        PicEntity f = new PicEntity();
        f.setType(type);
        f.setDesc(desc);
        f.setContent(content);
        attachedPics.add(f);
    }

    public static String applyTypeDesc(int applyType) {
        // 0: 主卡  1：附卡  2：主卡+附卡
        switch (applyType) {
            case 0: {
                return "主卡";
            }
            case 1: {
                return "附卡";
            }
            case 2: {
                return "主附卡";
            }
        }
        return null;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public int getApplyType() {
        return applyType;
    }

    public void setApplyType(int applyType) {
        this.applyType = applyType;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getBrandCode() {
        return brandCode;
    }

    public void setBrandCode(String brandCode) {
        this.brandCode = brandCode;
    }

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getMaCustName() {
        return maCustName;
    }

    public void setMaCustName(String maCustName) {
        this.maCustName = maCustName;
    }

    public String getMaPyName() {
        return maPyName;
    }

    public void setMaPyName(String maPyName) {
        this.maPyName = maPyName;
    }



    public String getMaBirthday() {
        return maBirthday;
    }

    public void setMaBirthday(String maBirthday) {
        this.maBirthday = maBirthday;
    }

    public String getMaCertNo() {
        return maCertNo;
    }

    public void setMaCertNo(String maCertNo) {
        this.maCertNo = maCertNo;
    }

    public String getMaMobileTel() {
        return maMobileTel;
    }

    public void setMaMobileTel(String maMobileTel) {
        this.maMobileTel = maMobileTel;
    }

    public String getMaEmail() {
        return maEmail;
    }

    public void setMaEmail(String maEmail) {
        this.maEmail = maEmail;
    }

    public String getMaHomeAddr() {
        return maHomeAddr;
    }

    public void setMaHomeAddr(String maHomeAddr) {
        this.maHomeAddr = maHomeAddr;
    }

    public String getMaHomeZip() {
        return maHomeZip;
    }

    public void setMaHomeZip(String maHomeZip) {
        this.maHomeZip = maHomeZip;
    }

    public String getMaCorpAddr() {
        return maCorpAddr;
    }

    public void setMaCorpAddr(String maCorpAddr) {
        this.maCorpAddr = maCorpAddr;
    }

    public String getMaCorpZipCode() {
        return maCorpZipCode;
    }

    public void setMaCorpZipCode(String maCorpZipCode) {
        this.maCorpZipCode = maCorpZipCode;
    }

    public String getMaCensusAddr() {
        return maCensusAddr;
    }

    public void setMaCensusAddr(String maCensusAddr) {
        this.maCensusAddr = maCensusAddr;
    }

    public String getMaCensusZipCode() {
        return maCensusZipCode;
    }

    public void setMaCensusZipCode(String maCensusZipCode) {
        this.maCensusZipCode = maCensusZipCode;
    }

    public String getMaStatementAddr() {
        return maStatementAddr;
    }

    public void setMaStatementAddr(String maStatementAddr) {
        this.maStatementAddr = maStatementAddr;
    }

    public String getMaDireLinkmanRela() {
        return maDireLinkmanRela;
    }

    public void setMaDireLinkmanRela(String maDireLinkmanRela) {
        this.maDireLinkmanRela = maDireLinkmanRela;
    }

    public String getMaDireLinkmanMobileTel() {
        return maDireLinkmanMobileTel;
    }

    public void setMaDireLinkmanMobileTel(String mDireLinkmanMobileTel) {
        this.maDireLinkmanMobileTel = mDireLinkmanMobileTel;
    }

    public String getMaDireLinkmanAddr() {
        return maDireLinkmanAddr;
    }

    public void setMaDireLinkmanAddr(String maDireLinkmanAddr) {
        this.maDireLinkmanAddr = maDireLinkmanAddr;
    }

    public String getMaYearIncome() {
        return maYearIncome;
    }

    public void setMaYearIncome(String maYearIncome) {
        this.maYearIncome = maYearIncome;
    }

    public String getMaMaritalStatus() {
        return maMaritalStatus;
    }

    public void setMaMaritalStatus(String maMaritalStatus) {
        this.maMaritalStatus = maMaritalStatus;
    }

    public String getMaHasChildren() {
        return maHasChildren;
    }

    public void setMaHasChildren(String maHasChildren) {
        this.maHasChildren = maHasChildren;
    }

    public String getMaCorpAttr() {
        return maCorpAttr;
    }

    public void setMaCorpAttr(String maCorpAttr) {
        this.maCorpAttr = maCorpAttr;
    }

    public String getMaIndustryCode() {
        return maIndustryCode;
    }

    public void setMaIndustryCode(String maIndustryCode) {
        this.maIndustryCode = maIndustryCode;
    }

    public String getMaSpecLevel() {
        return maSpecLevel;
    }

    public void setMaSpecLevel(String maSpecLevel) {
        this.maSpecLevel = maSpecLevel;
    }

    public String getMaHouseStatus() {
        return maHouseStatus;
    }

    public void setMaHouseStatus(String maHouseStatus) {
        this.maHouseStatus = maHouseStatus;
    }

    public String getMaSex() {
        return maSex;
    }

    public void setMaSex(String maSex) {
        this.maSex = maSex;
    }

    public Integer getMaWorkYears() {
        return maWorkYears;
    }

    public void setMaWorkYears(Integer maWorkYears) {
        this.maWorkYears = maWorkYears;
    }

    public Integer getMaHouseValue() {
        return maHouseValue;
    }

    public void setMaHouseValue(Integer maHouseValue) {
        this.maHouseValue = maHouseValue;
    }

    public String getSuSex() {
        return suSex;
    }

    public void setSuSex(String suSex) {
        this.suSex = suSex;
    }

    public String getPriCardNo() {
        return priCardNo;
    }

    public void setPriCardNo(String priCardNo) {
        this.priCardNo = priCardNo;
    }

    public String getPcRela() {
        return pcRela;
    }

    public void setPcRela(String pcRela) {
        this.pcRela = pcRela;
    }

    public String getSuCustName() {
        return suCustName;
    }

    public void setSuCustName(String suCustName) {
        this.suCustName = suCustName;
    }

    public String getSuPyName() {
        return suPyName;
    }

    public void setSuPyName(String suPyName) {
        this.suPyName = suPyName;
    }

    public String getIsDown() {
        return isDown;
    }

    public void setIsDown(String isDown) {
        this.isDown = isDown;
    }

    public String getStatementMode() {
        return statementMode;
    }

    public void setStatementMode(String statementMode) {
        this.statementMode = statementMode;
    }

    public String getAutoRepayFlag() {
        return autoRepayFlag;
    }

    public void setAutoRepayFlag(String autoRepayFlag) {
        this.autoRepayFlag = autoRepayFlag;
    }

    public String getAutoRepayAcctNo() {
        return autoRepayAcctNo;
    }

    public void setAutoRepayAcctNo(String autoRepayAcctNo) {
        this.autoRepayAcctNo = autoRepayAcctNo;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getMaExternCustNo() {
        return maExternCustNo;
    }

    public void setMaExternCustNo(String maExternCustNo) {
        this.maExternCustNo = maExternCustNo;
    }

    public String getMaStaffFlag() {
        return maStaffFlag;
    }

    public void setMaStaffFlag(String maStaffFlag) {
        this.maStaffFlag = maStaffFlag;
    }

    public String getMaCertType() {
        return maCertType;
    }

    public void setMaCertType(String maCertType) {
        this.maCertType = maCertType;
    }

    public String getMaDireLinkmanName() {
        return maDireLinkmanName;
    }

    public void setMaDireLinkmanName(String maDireLinkmanName) {
        this.maDireLinkmanName = maDireLinkmanName;
    }

    public String getSuBirthday() {
        return suBirthday;
    }

    public void setSuBirthday(String suBirthday) {
        this.suBirthday = suBirthday;
    }

    public String getSuCertNo() {
        return suCertNo;
    }

    public void setSuCertNo(String suCertNo) {
        this.suCertNo = suCertNo;
    }

    public String getSuMobileTel() {
        return suMobileTel;
    }

    public void setSuMobileTel(String suMobileTel) {
        this.suMobileTel = suMobileTel;
    }

    public String getMaDegreeCode() {
        return maDegreeCode;
    }

    public void setMaDegreeCode(String maDegreeCode) {
        this.maDegreeCode = maDegreeCode;
    }

    public String getPrimaryCustName() {
        return primaryCustName;
    }

    public void setPrimaryCustName(String primaryCustName) {
        this.primaryCustName = primaryCustName;
    }

    public String getSuCertType() {
        return suCertType;
    }

    public void setSuCertType(String suCertType) {
        this.suCertType = suCertType;
    }

    public String getSuExternCustNo() {
        return suExternCustNo;
    }

    public void setSuExternCustNo(String suExternCustNo) {
        this.suExternCustNo = suExternCustNo;
    }

    public String getSuCertNoId() {
        return suCertNoId;
    }

    public void setSuCertNoId(String suCertNoId) {
        this.suCertNoId = suCertNoId;
    }

    public String getSuCertNoName() {
        return suCertNoName;
    }

    public void setSuCertNoName(String suCertNoName) {
        this.suCertNoName = suCertNoName;
    }

    public String getSuHomeTel() {
        return suHomeTel;
    }

    public void setSuHomeTel(String suHomeTel) {
        this.suHomeTel = suHomeTel;
    }

    public Integer getSuApplyCredLimit() {
        return suApplyCredLimit;
    }

    public void setSuApplyCredLimit(Integer suApplyCredLimit) {
        this.suApplyCredLimit = suApplyCredLimit;
    }

    public String getSuHomeAddr() {
        return suHomeAddr;
    }

    public void setSuHomeAddr(String suHomeAddr) {
        this.suHomeAddr = suHomeAddr;
    }

    public String getSuHomeZip() {
        return suHomeZip;
    }

    public void setSuHomeZip(String suHomeZip) {
        this.suHomeZip = suHomeZip;
    }

    public void setAttachedPics(List<PicEntity> attachedPics) {
        this.attachedPics = attachedPics;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public int getFinished() {
        return finished;
    }

    public void setFinished(int finished) {
        this.finished = finished;
    }

    public String getRecordBranCode() {
        return recordBranCode;
    }

    public void setRecordBranCode(String recordBranCode) {
        this.recordBranCode = recordBranCode;
    }

    public String getRecordClerkNo() {
        return recordClerkNo;
    }

    public void setRecordClerkNo(String recordClerkNo) {
        this.recordClerkNo = recordClerkNo;
    }

    public String getCustManger() {
        return custManger;
    }

    public void setCustManger(String custManger) {
        this.custManger = custManger;
    }

    public String getApplyBranCode() {
        return applyBranCode;
    }

    public void setApplyBranCode(String applyBranCode) {
        this.applyBranCode = applyBranCode;
    }

    public String getMaCertNoId() {
        return maCertNoId;
    }

    public void setMaCertNoId(String maCertNoId) {
        this.maCertNoId = maCertNoId;
    }

    public String getMaCorpName() {
        return maCorpName;
    }

    public void setMaCorpName(String maCorpName) {
        this.maCorpName = maCorpName;
    }

    public String getMaDeptDesc() {
        return maDeptDesc;
    }

    public void setMaDeptDesc(String maDeptDesc) {
        this.maDeptDesc = maDeptDesc;
    }

    public String getMaDutyCode() {
        return maDutyCode;
    }

    public void setMaDutyCode(String maDutyCode) {
        this.maDutyCode = maDutyCode;
    }

    public String getMaCorpTel() {
        return maCorpTel;
    }

    public void setMaCorpTel(String maCorpTel) {
        this.maCorpTel = maCorpTel;
    }

    public String getIsCreditCard() {
        return isCreditCard;
    }

    public void setIsCreditCard(String isCreditCard) {
        this.isCreditCard = isCreditCard;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getMaOthLinkmanName() {
        return maOthLinkmanName;
    }

    public void setMaOthLinkmanName(String maOthLinkmanName) {
        this.maOthLinkmanName = maOthLinkmanName;
    }

    public String getMaOthLinkmanRela() {
        return maOthLinkmanRela;
    }

    public void setMaOthLinkmanRela(String maOthLinkmanRela) {
        this.maOthLinkmanRela = maOthLinkmanRela;
    }

    public String getMaOthLinkmanMobileTel() {
        return maOthLinkmanMobileTel;
    }

    public void setMaOthLinkmanMobileTel(String maOthLinkmanMobileTel) {
        this.maOthLinkmanMobileTel = maOthLinkmanMobileTel;
    }

    public String getMaOthLinkmanAddr() {
        return maOthLinkmanAddr;
    }

    public void setMaOthLinkmanAddr(String maOthLinkmanAddr) {
        this.maOthLinkmanAddr = maOthLinkmanAddr;
    }

    public Integer getMaCarValue() {
        return maCarValue;
    }

    public void setMaCarValue(Integer maCarValue) {
        this.maCarValue = maCarValue;
    }

    public String getMaHomeTel() {
        return maHomeTel;
    }

    public void setMaHomeTel(String maHomeTel) {
        this.maHomeTel = maHomeTel;
    }

    @JSONField(serialize = false)
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
