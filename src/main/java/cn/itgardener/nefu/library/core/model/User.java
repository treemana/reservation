/*
 * Copyright (c) 2014-2018 www.itgardener.cn. All rights reserved.
 */

package cn.itgardener.nefu.library.core.model;

/**
 * @author : Jimi
 * @date : 2018/10/27
 * @since : Java 8
 */
public class User {

    private Integer systemId;

    /**
     * 学号 or 账号
     */
    private String studentId;
    /**
     *  密码
     */
    private String userPassword;
    /**
     * 姓名
     */
    private String studentName;

    /**
     * 用户类型 0 学生 1 老师 2 黑名单
     */
    private Integer type;

    /**
     * token
     */
    private String token;

    public User() {
    }

    public User(String token) {
        this.token = token;
    }

    public Integer getSystemId() {
        return systemId;
    }

    public void setSystemId(Integer systemId) {
        this.systemId = systemId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
