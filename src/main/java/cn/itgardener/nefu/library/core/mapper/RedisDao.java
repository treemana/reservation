/*
 * Copyright (c) 2014-2019 www.itgardener.cn. All rights reserved.
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
     * 把单值加入队列
     *
     * @param key   关键词
     * @param value 内容
     * @return status
     */
    boolean listRightPush(String key, String value);

    /**
     * 单值出队
     *
     * @param key 键
     * @return
     */
    String listLiftPop(String key);

    /**
     * 目标键 -1
     *
     * @param key 键
     * @return 操作后的值
     */
    Long stringDecr(String key);

    /**
     * 目标键 +1
     *
     * @param key 键
     * @return 状态
     */
    boolean stringIncr(String key);

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

    void putHash(String hashName, String key, String value);

    String getHash(String hashName, String key);

    /**
     * 删除所有的key
     *
     * @return true or false
     */
    boolean removeAllKey();

    /**
     * 将 value 添加到 set 后计算 set 长度
     *
     * @param setName setName
     * @param value   value
     * @return 结果集
     */
    List<Object> addAndSize(String setName, String value);

    /**
     * @param setName setName
     * @return SET size
     */
    Long getSetSize(String setName);

    /**
     * @param setName setName
     * @param value   value
     */
    void setRemove(String setName, String value);

    /**
     * 按照 String 格式 存储 K-V
     *
     * @param key   key
     * @param value value
     */
    void putMap(String key, String value);

    /**
     * 按照 String 格式 存储 K-V
     *
     * @param key key
     * @return value
     */
    String getMap(String key);
}
