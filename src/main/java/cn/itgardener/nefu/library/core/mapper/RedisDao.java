/*
 * Copyright (c) 2014-2018 www.itgardener.cn. All rights reserved.
 */

package cn.itgardener.nefu.library.core.mapper;


import java.util.List;

/**
 * @author : Jimi
 * @date : 2018/10/28
 * @since : Java 8
 */

public interface RedisDao {

    /**
     * 获取队列内容
     *
     * @param key   键
     * @param start 开始
     * @param end   结束 0 到 -1 代表所有值
     * @return
     */
    List<String> getList(String key, long start, long end);

    /**
     * 获取队列长度
     *
     * @param key 键
     * @return
     */
    Long getListSize(String key);

    /**
     * 把单值加入队列
     *
     * @param key   关键词
     * @param value 内容
     */
    boolean pushValue(String key, String value);

    /**
     * 把list加入队列
     *
     * @param key
     * @param list
     */
    boolean pushList(String key, List<String> list);

    /**
     * 单值出队
     *
     * @param key 键
     */
    String popValue(String key);

    /**
     * 根据value值,移除list中的数据
     *
     * @param key   键
     * @param value 移除的内容
     */
    boolean removeListValue(String key, String value);

    /**
     * 递减
     *
     * @param key    键
     * @param number 减多少
     * @return
     */
    long dec(String key, long number);

    /**
     * 普通缓存放入
     *
     * @param key
     * @param value
     * @return
     */
    boolean set(String key, String value);

    /**
     * 普通缓存取出
     *
     * @param key
     * @return
     */
    String get(String key);

    /**
     * 递增
     *
     * @param key    键
     * @param number 增值
     * @return 结果
     */
    long inc(String key, long number);

    /**
     * 添加记录到SET中
     *
     * @param key
     * @param value
     * @return
     */
    boolean add(String key, String value);

    /**
     * 查询set中是否包含value
     *
     * @param key
     * @param value
     * @return
     */
    boolean isMember(String key, String value);

    /**
     * 移除数据库中key值
     *
     * @param key
     * @return
     */
    boolean remove(String key);

    /**
     * 同步MySQL和Redis
     * @return
     */
    boolean updateRedis();

    void pushHash(String key, String filed, String value);

    String getHash(String key, String filed);

}
