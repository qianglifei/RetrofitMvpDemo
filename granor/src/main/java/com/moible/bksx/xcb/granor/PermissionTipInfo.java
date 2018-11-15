package com.moible.bksx.xcb.granor;

import java.io.Serializable;

public class PermissionTipInfo implements Serializable{
    private String title;
    private String content;
    private String cancel;
    private String ensure;

    public PermissionTipInfo(String title, String content, String cancel, String ensure) {
        this.title = title;
        this.content = content;
        this.cancel = cancel;
        this.ensure = ensure;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCancel() {
        return cancel;
    }

    public void setCancel(String cancel) {
        this.cancel = cancel;
    }

    public String getEnsure() {
        return ensure;
    }

    public void setEnsure(String ensure) {
        this.ensure = ensure;
    }
}
