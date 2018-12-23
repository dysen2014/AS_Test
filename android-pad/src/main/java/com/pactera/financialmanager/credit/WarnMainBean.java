package com.pactera.financialmanager.credit;

import com.pactera.financialmanager.ui.model.CreditFwMenu;
import com.pactera.financialmanager.ui.model.CreditTxMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dysen on 11/2/2017.
 */

public class WarnMainBean {

    public String title;
    public int titleColor;
    public List<WarnMainBean.Content> list = new ArrayList<>();


    public WarnMainBean(String mTitle, int mTitleColor, List<WarnMainBean.Content> mWarnMainBeans) {
        title = mTitle;
        list = mWarnMainBeans;
        titleColor = mTitleColor;
    }

    public static class Content{
        int imgId;
        String name, nameInfo, numId,name_picture;

        public String getNumId() {
            return numId;
        }

        public void setNumId(String numId) {
            this.numId = numId;
        }

        public String getName_picture() {
            return name_picture;
        }

        public void setName_picture(String name_picture) {
            this.name_picture = name_picture;
        }

        public Content(int imgId,  String name, String nameInfo,String menu_name, String numId) {
            this.imgId = imgId;
            this.name = name;
            this.nameInfo = nameInfo;
            this.numId = numId;
            this.name_picture = menu_name;

        }

        public int getImgId() {
            return imgId;
        }

        public Content setImgId(int imgId) {
            this.imgId = imgId;
            return this;
        }

        public String getName() {
            return name;
        }

        public Content setName(String name) {
            this.name = name;
            return this;
        }

        public String getNameInfo() {
            return nameInfo;
        }

        public Content setNameInfo(String nameInfo) {
            this.nameInfo = nameInfo;
            return this;
        }
    }


   public class CreditMenu{

        private List<CreditTxMenu> creditTxMenus;
        private List<CreditFwMenu> creditFwMenus;

       public List<CreditTxMenu> getCreditTxMenus() {
           return creditTxMenus;
       }

       public void setCreditTxMenus(List<CreditTxMenu> creditTxMenus) {
           this.creditTxMenus = creditTxMenus;
       }

       public List<CreditFwMenu> getCreditFwMenus() {
           return creditFwMenus;
       }

       public void setCreditFwMenus(List<CreditFwMenu> creditFwMenus) {
           this.creditFwMenus = creditFwMenus;
       }
   }

}
