/*
 * Copyright (c) 2014-2019 www.itgardener.cn. All rights reserved.
 */

package cn.itgardener.nefu.library.common;

/**
 * @author : Hunter
 * @date : 18-11-30 下午2:57
 * @since : Java 8
 */
public class GlobalConst {

    // BookCase.status
    /**
     * 0 空闲
     */
    public static final int BC_ENABLE = 0;

    /**
     * 1 被使用
     */
    public static final int BC_DISENABLE = 1;

    /**
     * 2 预留
     */
    public static final int BC_PREORDER = 2;

    // Config.configKey
    /**
     * 0 学生
     */
    public static final int USER_STUDENT = 0;

    /**
     * 1 管理员
     */
    public static final int USER_ADMIN = 1;

    /**
     * 3 查
     */
    public static final int USER_OTHER_ADMIN = 3;

    /**
     * 2 黑名单
     */
    public static final int USER_DISABLE = 2;

    /**
     * 上传文件路径
     */
    public static final String UPLOAD_PATH = "/tmp/";


    /* redis key start */
    /**
     * 所有预约学生学号 set 的 key
     */
    public static final String ALL_RESERVATION_SET = "AllReservationSet";

    /**
     * 此次可预约柜子总数
     */
    public static final String TOTAL = "total";

    /**
     * 排队队列
     */
    public static final String QUEUE_LIST = "queue_list";

    /**
     * 预约:学号-位置对应关系
     */
    public static final String LOCATION_HASH = "location_hash";

    /**
     * 验证码 HASH
     */
    public static final String CAPTCHA_HASH = "captcha_hash";
}
