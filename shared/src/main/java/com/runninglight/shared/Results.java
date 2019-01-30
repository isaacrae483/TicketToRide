package com.runninglight.shared;

import com.google.gson.annotations.SerializedName;

public class Results {

    @SerializedName("success") private boolean success;
    @SerializedName("data") private Object data;
    @SerializedName("errorInfo") private String errorInfo;

    public Results(boolean success, Object data, String errorInfo) {
        this.success = success;
        this.data = data;
        this.errorInfo = errorInfo;
    }

    public boolean isSuccess() {
        return success;
    }

    public Object getData() {
        return data;
    }

    public String getErrorInfo() {
        return errorInfo;
    }
}
