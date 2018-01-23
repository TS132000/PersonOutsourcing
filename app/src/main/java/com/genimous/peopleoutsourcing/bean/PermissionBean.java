package com.genimous.peopleoutsourcing.bean;

/**
 * Created by wudi on 18/1/19.
 */

public class PermissionBean {
    /**权限名称*/
    String prrmissionStr;
    /**未开启权限名称*/
    String permissionFailStr;
    /**为开启权限提示*/
    String permissionFailDesc;

    /**s是否开启权限*/
    boolean isOpen;

    public String getPrrmissionStr() {
        return prrmissionStr;
    }

    public void setPrrmissionStr(String prrmissionStr) {
        this.prrmissionStr = prrmissionStr;
    }

    public String getPermissionFailStr() {
        return permissionFailStr;
    }

    public void setPermissionFailStr(String permissionFailStr) {
        this.permissionFailStr = permissionFailStr;
    }

    public String getPermissionFailDesc() {
        return permissionFailDesc;
    }

    public void setPermissionFailDesc(String permissionFailDesc) {
        this.permissionFailDesc = permissionFailDesc;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }


}
