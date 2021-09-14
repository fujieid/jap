package com.test;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.fujieid.jap.httpapi.subject.DigestAuthorizationSubject;
import com.fujieid.jap.httpapi.subject.DigestWwwAuthenticateSubject;
import com.fujieid.jap.httpapi.util.DigestUtil;
import com.fujieid.jap.httpapi.util.DigestMD5Util;
import com.fujieid.jap.httpapi.util.SubjectSerializeUtil;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

/**
 * @author zhihai.yu (mvbbb(a)foxmail.com)
 * @version 1.0.0
 * @since 1.0.0
 */
public class DigestAuthTest {
    @Test
    public void testLogin() throws IllegalAccessException, NoSuchMethodException, InstantiationException, InvocationTargetException {
        HttpResponse execute = HttpRequest.get("http://localhost:8088/api/v1/source1").execute();
        String header = execute.header("WWW-Authenticate");
        DigestWwwAuthenticateSubject wwwAuthenticateSubject = ((DigestWwwAuthenticateSubject) SubjectSerializeUtil.deSerialize(DigestWwwAuthenticateSubject.class, header.replaceFirst("Digest ", "")));
        String nonce = null;
        String realm = null;
        String ha1 = null;
        String ha2 = null;
        String responseStr = null;
        String nc = "00000006";
        String cnonce = "eaca84936697a948";

        nonce = wwwAuthenticateSubject.getNonce();
        realm = wwwAuthenticateSubject.getRealm();
        ha1 = DigestMD5Util.encode("root",realm,"23456");
        ha2 = DigestMD5Util.encode("GET","/api/v1/source1");
        //response = MD5("A1:nonce:nc:cNonce:qop:A2");
        responseStr = DigestMD5Util.encode(ha1,nonce,nc,cnonce,"auth",ha2);


        DigestAuthorizationSubject digestAuthorizationSubject = new DigestAuthorizationSubject()
                .setUsername("root")
                .setRealm(wwwAuthenticateSubject.getRealm())
                .setNonce(wwwAuthenticateSubject.getNonce())
                .setUri("/api/v1/source1")
                .setResponse(responseStr)
                .setQop("auth")
                .setNc(nc)
                .setCnonce(cnonce);


        String authStr = "Digest "+ SubjectSerializeUtil.serialize(digestAuthorizationSubject);
        System.out.println(authStr);
        HttpRequest result = HttpRequest
                .get("http://localhost:8088/api/v1/source1")
                .header("Authorization", authStr);
        HttpResponse execute1 = result.execute();
        System.out.println(execute1);
    }

    @Test
    public void test2(){
        String authStr = "Digest username=\"root\", realm=\"sureness_realm\", nonce=\"835b9fd18d9781c838aa4636a2404ef5\", uri=\"/api/v1/source1\", response=\"c49ef2466bfc9cc4996094d9d49718b2\", qop=auth, nc=00000006, cnonce=\"eaca84936697a948\"";
        HttpRequest authorization = HttpRequest
                .get("http://localhost:8088/api/v1/source1")
                .header("Authorization",
                        authStr);
        System.out.println(authStr);
        HttpResponse execute = authorization.execute();
        System.out.println(execute);

    }

    @Test
    public void test3(){
        String ha1 = DigestMD5Util.encode("root","sureness_realm","23456");
        String ha2 = DigestMD5Util.encode("GET","/api/v1/source1");
        String nonce = "835b9fd18d9781c838aa4636a2404ef5";
        String nc = "00000006";
        String cnonce = "eaca84936697a948";
        String qop = "auth";
        String response = DigestMD5Util.encode(ha1,nonce,nc,cnonce,qop,ha2);
        System.out.println(response);
    }

    @Test
    public void test4(){
        String cnonce = DigestUtil.getCnonce();
        System.out.println(cnonce);
    }

    @Test
    public void test5(){
        String nc = DigestUtil.getNc();
        System.out.println(nc);
    }
}
