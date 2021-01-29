package com.fujieid.jap.core;


import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @date 2021-01-29 17:06
 * @since 1.0.0
 */
public class CacheTest {

    @Test
    public void test() throws InterruptedException {
        //创建缓存，默认4毫秒过期
        TimedCache<String, String> timedCache = CacheUtil.newTimedCache(4);
        //实例化创建
        //TimedCache<String, String> timedCache = new TimedCache<String, String>(4);

        timedCache.put("key1", "value1", 5 * 1000);//1毫秒过期
        timedCache.put("key2", "value2", 10 * 1000);
        timedCache.put("key3", "value3");//默认过期(4毫秒)

        //启动定时任务，每5毫秒秒检查一次过期
//        timedCache.schedulePrune(5);

        //等待5毫秒
        TimeUnit.SECONDS.sleep(5);

        //5毫秒后由于value2设置了5毫秒过期，因此只有value2被保留下来
        String value1 = timedCache.get("key1");//null
        System.out.println(value1);
        String value2 = timedCache.get("key2");//value2
        System.out.println(value2);

        //5毫秒后，由于设置了默认过期，key3只被保留4毫秒，因此为null
        String value3 = timedCache.get("key3");//null
        System.out.println(value3);

        //取消定时清理
//        timedCache.cancelPruneSchedule();
    }
}
