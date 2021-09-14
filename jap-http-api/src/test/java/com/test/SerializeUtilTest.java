package com.test;

import com.fujieid.jap.httpapi.subject.DigestWwwAuthenticateSubject;
import com.fujieid.jap.httpapi.util.SubjectSerializeUtil;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

public class SerializeUtilTest {

    @Test
    public void deSerialize() throws IllegalAccessException {
        DigestWwwAuthenticateSubject subject = new DigestWwwAuthenticateSubject().setAlgorithm("http").setDomain("www.yzh.im").setNonce("Miho");
        String serialize = SubjectSerializeUtil.serialize(subject);
        System.out.println(serialize);
    }

    @Test
    public void serialize() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        DigestWwwAuthenticateSubject digestWWWAuthenticateSubject = (DigestWwwAuthenticateSubject) SubjectSerializeUtil
                .deSerialize(DigestWwwAuthenticateSubject.class, "\"realm\"=\"null\",\"domain\"=\"www.yzh.im\",\"nonce\"=\"Miho\",\"opaque\"=\"null\",\"algorithm\"=\"http\",\"stale\"=\"null\",\"qop\"=\"null\"");
        System.out.println(digestWWWAuthenticateSubject);
    }
}
