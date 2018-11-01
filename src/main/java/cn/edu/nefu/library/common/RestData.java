/*
 * Copyright © 2014-2018 www.itgardener.cn. All rights reserved.
 */

package cn.edu.nefu.library.common;

import com.fasterxml.jackson.annotation.JsonInclude;
/**
 * @author : Jimi
 * @date : 2018/10/27
 * @since : Java 8
 */
public class RestData {
    private int code = 0;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object data;

    /**
     * 分页信息
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Page page;

    public RestData(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public RestData(Object data) {
        this.code = 0;
        this.data = data;
    }

    public RestData(Object data, Page page) {
        this.code = 0;
        this.data = data;
        this.page = page;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }
}
