/*
 * Copyright (c) 2014-2018 www.itgardener.cn. All rights reserved.
 */

package cn.edu.nefu.library.common.util;

import cn.edu.nefu.library.common.Page;

/**
 * @author : Hunter
 * @date : 18-3-24
 * @since : Java 8
 */
public class PageUtil {

    public static String getLimit(int page, int pageSize) {
        int start = (page - 1) * pageSize;
        return start + "," + pageSize;
    }

    public static Page checkPage(Page page) {
        Page rtv = new Page();
        int nowPage = page.getNowPage();
        int totalSize = page.getTotalSize();
        int pageSize = page.getPageSize();
        int totalPage = 0 == totalSize % pageSize ? totalSize / pageSize : totalSize / pageSize + 1;
        if (1 > totalPage) {
            totalPage = 1;
        }
        if (1 > nowPage) {
            nowPage = 1;
        } else if (nowPage > totalPage) {
            nowPage = totalPage;
        }
        rtv.setTotalPage(totalPage);
        rtv.setNowPage(nowPage);
        rtv.setTotalSize(totalSize);
        rtv.setPageSize(pageSize);
        return rtv;
    }
}
