package com.panda.common.util;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * Description
 * <p>
 * DATE 2017/12/8.
 *
 * @author zhanglijian.
 */
public class RedisUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisUtil.class);

    private static final Long DEFAULT_TIMEOUT = 1L;

    /**
     * the redis util
     */
    private RedisUtil() {

    }

    /**
     * key-value查询
     *
     * @param redisTemplate the  redisTemplate
     * @param key           the key
     * @param clazz         the clazz
     * @param supplier      the supplier
     * @param <T>           the t
     * @return the t
     */
    public static <T> T get(final RedisTemplate redisTemplate, final String key,
                            final Class<T> clazz, final Supplier<T> supplier) {
        return get(redisTemplate, key, clazz, supplier, DEFAULT_TIMEOUT, TimeUnit.MINUTES);
    }

    /**
     * key-value查询
     *
     * @param redisTemplate the  redisTemplate
     * @param key           the key
     * @param clazz         the clazz
     * @param supplier      the supplier
     * @param timeout       the timeout
     * @param unit          the unit
     * @param <T>           the t
     * @return the t
     */
    public static <T> T get(final RedisTemplate redisTemplate, final String key,
                            final Class<T> clazz, final Supplier<T> supplier,
                            final Long timeout, final TimeUnit unit) {
        String value;
        try {
            value = (String) redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            LOGGER.error("get key: " + key + " cahce exception : " + e.getMessage(), e);
            value = null;
        }
        if (!StringUtils.hasLength(value) && Objects.nonNull(supplier)) {
            try {
                T o = supplier.get();
                if (Objects.nonNull(o)) {
                    value = JSON.toJSONString(o);
                    if (Objects.isNull(timeout) || Objects.isNull(unit)) {
                        redisTemplate.opsForValue().set(key, value);
                    } else {
                        redisTemplate.opsForValue().set(key, value, timeout, unit);
                    }
                }
                return o;
            } catch (Exception e) {
                LOGGER.error("Load cache failed: " + e.getMessage(), e);
            }
        }
        return JSON.parseObject(value, clazz);
    }

    public static <T> List<T> getList(final RedisTemplate redisTemplate, final String key,
                                      final Class<T> clazz, final Supplier<List<T>> supplier) {
        return getList(redisTemplate, key, clazz, supplier, DEFAULT_TIMEOUT, TimeUnit.MINUTES);
    }

    /**
     * key-value查询
     *
     * @param redisTemplate the  redisTemplate
     * @param key           the key
     * @param clazz         the clazz
     * @param supplier      the supplier
     * @param timeout       the timeout
     * @param unit          the unit
     * @param <T>           the t
     * @return the t
     */
    public static <T> List<T> getList(final RedisTemplate redisTemplate, final String key,
                                      final Class<T> clazz, final Supplier<List<T>> supplier,
                                      final Long timeout, final TimeUnit unit) {
        String value;
        try {
            value = (String) redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            LOGGER.error("get key: " + key + " cahce exception : " + e.getMessage(), e);
            value = null;
        }
        if (StringUtils.hasLength(value) && Objects.nonNull(supplier)) {
            try {
                List<T> o = supplier.get();
                if (Objects.nonNull(o)) {
                    value = JSON.toJSONString(o);
                    if (Objects.isNull(timeout) || Objects.isNull(unit)) {
                        redisTemplate.opsForValue().set(key, value);
                    } else {
                        redisTemplate.opsForValue().set(key, value, timeout, unit);
                    }
                }
                return o;
            } catch (Exception e) {
                LOGGER.error("Load cache failed: " + e.getMessage(), e);
            }
        }
        return JSON.parseArray(value, clazz);
    }

    /**
     * key-list查询
     *
     * @param redisTemplate the  redisTemplate
     * @param key           the key
     * @param clazz         the clazz
     * @param supplier      the supplier
     * @param <T>           the t
     * @return the list<T>
     */
    public static <T> List<T> lGet(final RedisTemplate redisTemplate, final String key,
                                   final Class<T> clazz, final Supplier<List<T>> supplier) {
        return lGet(redisTemplate, key, clazz, supplier, DEFAULT_TIMEOUT, TimeUnit.MINUTES);
    }

    /**
     * key-list查询
     *
     * @param redisTemplate the  redisTemplate
     * @param key           the key
     * @param clazz         the clazz
     * @param supplier      the supplier
     * @param timeout       the timeout
     * @param unit          the unit
     * @param <T>           the t
     * @return the list<T>
     */
    public static <T> List<T> lGet(final RedisTemplate redisTemplate, final String key,
                                   final Class<T> clazz, final Supplier<List<T>> supplier,
                                   final Long timeout, final TimeUnit unit) {
        List<T> value;
        try {
            value = (List<T>) redisTemplate.opsForList().range(key, 0, -1);
        } catch (Exception e) {
            LOGGER.error("get key: " + key + " cahce exception : " + e.getMessage(), e);
            value = null;
        }
        if ((Objects.isNull(value) || value.isEmpty()) && Objects.nonNull(supplier)) {
            try {
                List<T> o = supplier.get();
                if (Objects.nonNull(o)) {
                    redisTemplate.opsForList().leftPushAll(key, o);
                    if (Objects.nonNull(timeout) && Objects.nonNull(unit)) {
                        redisTemplate.expire(key, timeout, unit);
                    }
                }
                return o;
            } catch (Exception e) {
                LOGGER.error("Load cache failed: " + e.getMessage(), e);
            }
        }

        return value;
    }

    /**
     * delete key
     *
     * @param redisTemplate the redisTemplate
     * @param key           the key
     */
    public static void delete(final RedisTemplate redisTemplate, final String key) {
        for (int i = 0; i < 3; i++) {
            try {
                redisTemplate.delete(key);
                return;
            } catch (Exception e) {
                LOGGER.error("delete cache failed: " + key + " " + e.getMessage(), e);
            }
        }
    }
}
