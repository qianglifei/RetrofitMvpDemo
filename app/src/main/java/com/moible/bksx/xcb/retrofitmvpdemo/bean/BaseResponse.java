package com.moible.bksx.xcb.retrofitmvpdemo.bean;

import com.google.gson.annotations.SerializedName;

public class BaseResponse<T>{

    /**
     * returnCode : 200
     * returnMsg : 登录成功
     * returnData : {"userId":"000015","userName":"飞哥","userSex":"1","userDeftime":"20180928191824","userPwd":"cfcd208495d565ef66e7dff9f98764da","userKey":"","userCaid":"","userCardid":"","userDuty":"","userHost":"","userIp":"","userEmail":"","userMp":"","userMmask":"","userNickname":"fg","userPhone":"","userAddress":"","userZip":"","userSaid":"000000","userOrder":"","userComment":"10000000001","userDId":"0000000002","userDName":"黄村镇","userDDesc":"","userDAddress":"","userDPhone":"","userDDeftime":"20180624172533","userDLrid":"110115005000","userDRpname":"","userDEemail":"","userDCpname":"","userDSaid":"000000","userDOrder":"","userDLid":"","userDComment":"","myRolePO":[],"myObjectPO":[],"sessionId":"fb29e4d35bc800","userDregioncode":""}
     * pageCount : null
     * rowsCount : null
     * startNum : null
     * state : success
     *
     */

    @SerializedName("returnCode")
    private int returnCode;
    @SerializedName("returnMsg")
    private String returnMsg;
    @SerializedName("returnData")
    private T returnData;
    @SerializedName("pageCount")
    private String pageCount;
    @SerializedName("rowsCount")
    private String rowsCount;
    @SerializedName("startNum")
    private String startNum;
    @SerializedName("state")
    private String state;

    public int getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(int returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public T getReturnData() {
        return returnData;
    }

    public void setReturnData(T returnData) {
        this.returnData = returnData;
    }

    public Object getPageCount() {
        return pageCount;
    }

    public void setPageCount(String pageCount) {
        this.pageCount = pageCount;
    }

    public Object getRowsCount() {
        return rowsCount;
    }

    public void setRowsCount(String rowsCount) {
        this.rowsCount = rowsCount;
    }

    public Object getStartNum() {
        return startNum;
    }

    public void setStartNum(String startNum) {
        this.startNum = startNum;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "returnCode=" + returnCode +
                ", returnMsg='" + returnMsg + '\'' +
                ", returnData=" + returnData +
                ", pageCount='" + pageCount + '\'' +
                ", rowsCount='" + rowsCount + '\'' +
                ", startNum='" + startNum + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
