/*
 * Copyright (c) 2014-2018 www.itgardener.cn. All rights reserved.
 */

package cn.itgardener.nefu.library.core.model.vo;

/**
 * @author : chenchenT
 * @date : 2018/10/29
 * @since : Java 8
 */
public class BookCaseVo {

    private Integer systemId;

    /**
     * 页码
     */
    private Integer page;

    /**
     * 编号范围
     */
    private String id;

    /**
     * 左范围
     */
    private String l;

    /**
     * 右范围
     */
    private String r;

    /**
     * 学生学号
     */
    private String studentId;

    /**
     * 拥有者ID
     */
    private Integer userId;

    /**
     * 柜子当前状态 0 开放 1 占用或预留
     */
    private Integer status;

    /**
     * 柜子位置 2_1 2楼1区域 || 3_2 3楼2区域
     */
    private String location;

    /**
     * 验证码
     */
    private String verifyCode;

    public Integer getSystemId() {
        return systemId;
    }

    public void setSystemId(Integer systemId) {
        this.systemId = systemId;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getL() {
        return l;
    }

    public void setL(String l) {
        this.l = l;
    }

    public String getR() {
        return r;
    }

    public void setR(String r) {
        this.r = r;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }
}
