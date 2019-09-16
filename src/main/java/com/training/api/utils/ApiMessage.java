package com.training.api.utils;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class handle message of API
 *
 */
public class ApiMessage {

    @JsonProperty("error")
    private String error;

    @JsonProperty("error_description")
    private String errorDescription;

    public ApiMessage(String error, String errorDescription) {
        this.error = error;
        this.errorDescription = errorDescription;
    }


    public static ApiMessage error400() {
        return new ApiMessage("400",
                "Thiếu thông số bắt buộc, giá trị không hợp lệ hoặc request không đúng định dạng" );
    }

    public static ApiMessage error404() {
        return new ApiMessage("404", "Cố gắng thao tác một tài nguyên không tồn tại" );
    }

    public static ApiMessage error500() {
        return new ApiMessage("500", "Một lỗi xảy ra ở phía máy chủ" );
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }


}
