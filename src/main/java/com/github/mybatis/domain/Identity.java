package com.github.mybatis.domain;

public class Identity {
    @Override
    public String toString() {
        return "Identity [idType=" + idType + ", idNo=" + idNo + "]";
    }

    private String idType;
    private String idNo;

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }
}
