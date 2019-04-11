/*
 * Copyright (c) 2014-2019 www.itgardener.cn. All rights reserved.
 */

package cn.itgardener.nefu.library.core.model.vo;

/**
 * @author : Jimi
 * @date : 2018/11/4
 * @since : Java 8
 */
public class UserVo {

    /**
     * 学号
     */
    private String studentId;

    /**
     * 验证码
     */
    private String verifyCode;

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }
}
