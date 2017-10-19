package com.pactera.financialmanager.credit.main.service.study.bean;

/**
 * Created by admin on 2017-7-5.
 */

public class Filestudy {
    private String DocNo;
    private String AttachmentNo;
    private String FileName;
    private String Address;
    private String DocName;

    public Filestudy(String DocNo, String AttachmentNo, String FileName, String Address, String DocName){
        this.DocNo = DocNo;
        this.AttachmentNo = AttachmentNo;
        this.FileName = FileName;
        this.Address = Address;
        this.DocName = DocName;
    }

    public String getDocNo(){
        return DocNo;
    }
    public void setDocNo(){
        this.DocNo = DocNo;
    }

    public String getAttachmentNo(){
        return AttachmentNo;
    }

    public void setAttachmentNo(){
        this.AttachmentNo = AttachmentNo;
    }

    public String getFileName(){
        return FileName;
    }

    public void setFileName(){
        this.FileName = FileName;
    }

    public String getAddress(){
        return Address;
    }

    public void setAddress(){
        this.Address = Address;
    }

    public String getDocName(){
        return DocName;
    }

    public void setDocName(){
        this.DocName = DocName;
    }


}
