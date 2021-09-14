package com.test;

//import cn.hutool.json.JSONUtil;
import com.fujieid.jap.core.JapUser;
import com.fujieid.jap.httpapi.util.SimpleAuthJsonUtil;
import org.junit.Test;

import java.util.HashMap;

public class JsonTest {
    @Test
    public void test(){
//        JapUser japUser = JSONUtil.toBean("{\n" +
//                "    \"username\":\"root\",\n" +
//                "    \"password\":\"23456\",\n" +
//                "    \"age\":18\n" +
//                "}", JapUser.class);
//        System.out.println(japUser);
    }


    @Test
    public void test2(){
        JapUser dahei = new JapUser().setUsername("dahei").setPassword("12");
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("wwd","kjkd");
        stringStringHashMap.put("get","bbb");
        String jsonStrByJapUserAndParams = SimpleAuthJsonUtil.getJsonStrByJapUserAndParams(dahei, stringStringHashMap);
        System.out.println(jsonStrByJapUserAndParams);
    }
}
