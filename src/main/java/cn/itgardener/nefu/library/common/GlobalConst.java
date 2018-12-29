/*
 * Copyright (c) 2014-2018 www.itgardener.cn. All rights reserved.
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
     * 2 黑名单
     */
    public static final int USER_DISABLE = 2;

    /**
     * 上传文件路径
     */
    public static final String UPLOAD_PATH = "/tmp/";
}
