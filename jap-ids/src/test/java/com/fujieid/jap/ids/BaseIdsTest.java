package com.fujieid.jap.ids;

import com.fujieid.jap.ids.config.IdsConfig;
import com.fujieid.jap.ids.config.JwtConfig;
import com.fujieid.jap.ids.context.IdsContext;
import com.xkcoding.json.JsonUtil;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.when;

/**
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @date 2021-04-19 10:32
 * @since 1.0.0
 */
public class BaseIdsTest {


    @Mock
    protected HttpServletRequest httpServletRequestMock;
    @Mock
    protected HttpServletResponse httpServletResponseMock;
    @Mock
    protected HttpSession httpsSessionMock;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        // Arrange
        when(httpServletRequestMock.getSession()).thenReturn(httpsSessionMock);

        // 注册 JAP IDS 上下文
        JapIds.registerContext(new IdsContext()
            .setUserService(new IdsUserServiceImpl())
            .setClientDetailService(new IdsClientDetailServiceImpl())
            .setIdentityService(new IdsIdentityServiceImpl())
            .setIdsConfig(new IdsConfig()
                .setIssuer("http://www.baidu.com")
                .setJwtConfig(new JwtConfig()
                    .setJwksKeyId("jap-jwk-keyid")
                    .setJwksJson("{\n" +
                        "    \"keys\": [\n" +
                        "        {\n" +
                        "            \"p\": \"v5G0QPkr9zi1znff2g7p5K1ac1F2KNjXmk31Etl0UrRBwHiTxM_MkkldGlxnXWoFL4_cPZZMt_W14Td5qApknLFOh9iRWRPwqlFgC-eQzUjPeYvxjRbtV5QUHtbzrDCLjLiSNyhsLXHyi_yOawD2BS4U6sBWMSJlL2lShU7EAaU\",\n" +
                        "            \"kty\": \"RSA\",\n" +
                        "            \"q\": \"s2X9UeuEWky_io9hFAoHZjBxMBheNAGrHXtWat6zlg2tf_SIKpZ7Xs8C_-kr9Pvj-D428QsOjFZE-EtNBSXoMrvlMk7fGDl9x1dHvLS9GSitkXH2-Wthg8j0j0nfAmyEt94jP-XEkYic1Ok7EfBOPuvL21HO7YuB-cOff9ZGvBk\",\n" +
                        "            \"d\": \"Rj-QBeBdx85VIHkwVY1T94ZeeC_Z6Zw-cz5lk5Msw0U9QhSTWo28-d2lYjK7dhQn-E19JhTbCVE11UuUqENKZmO__yRgO1UJaj2x6vWMtgJptah7m8lI-QW0w6TnVxAHWfRPpKSEfbN4SpeufYf5PYhmmzT0A954Z2o0kqS4iHd0gwNAovOXaxriGXO1CcOQjBFEcm0BdboQZ7CKCoJ1D6S0xZpVFSJg-1AtagY5dzStyekzETO2tQSmVw4ogIoJsIbu3aYwbukmCoULQfJ36D0mPzrTG5oocEbbuCps_vH72VjZORHHAl4hwritFT_jD2bdQHSNMGukga8C0L1WQQ\",\n" +
                        "            \"e\": \"AQAB\",\n" +
                        "            \"use\": \"sig\",\n" +
                        "            \"kid\": \"jap-jwk-keyid\",\n" +
                        "            \"qi\": \"Asr5dZMDvwgquE6uFnDaBC76PY5JUzxQ5wY5oc4nhIm8UxQWwYZTWq-HOWkMB5c99fG1QxLWQKGtsguXfOXoNgnI--yHzLZcXf1XAd0siguaF1cgQIqwRUf4byofE6uJ-2ZON_ezn6Uvly8fDIlgwmKAiiwWvHI4iLqvqOReBgs\",\n" +
                        "            \"dp\": \"oIUzuFnR6FcBqJ8z2KE0haRorUZuLy38A1UdbQz_dqmKiv--OmUw8sc8l3EkP9ctvzvZfVWqtV7TZ4M3koIa6l18A0KKEE0wFVcYlwETiaBgEWYdIm86s27mKS1Og1MuK90gz800UCQx6_DVWX41qAOEDWzbDFLY3JBxUDi-7u0\",\n" +
                        "            \"alg\": \"RS256\",\n" +
                        "            \"dq\": \"MpNSM0IecgapCTsatzeMlnaZsmFsTWUbBJi86CwYnPkGLMiXisoZxcS-p77osYxB3L5NZu8jDtVTZFx2PjlNmN_34ZLyujWbDBPDGaQqm2koZZSnd_GZ8Dk7GRpOULSfRebOMTlpjU3iSPPnv0rsBDkdo5sQp09pOSy5TqTuFCE\",\n" +
                        "            \"n\": \"hj8zFdhYFi-47PO4B4HTRuOLPR_rpZJi66g4JoY4gyhb5v3Q57etSU9BnW9QQNoUMDvhCFSwkz0hgY5HqVj0zOG5s9x2a594UDIinKsm434b-pT6bueYdvM_mIUEKka5pqhy90wTTka42GvM-rBATHPTarq0kPTR1iBtYao8zX-RWmCbdumEWOkMFUGbBkUcOSJWzoLzN161WdYr2kJU5PFraUP3hG9fPpMEtvqd6IwEL-MOVx3nqc7zk3D91E6eU7EaOy8nz8echQLl6Ps34BSwEpgOhaHDD6IJzetW-KorYeC0r0okXhrl0sUVE2c71vKPVVtueJSIH6OwA3dVHQ\"\n" +
                        "        }\n" +
                        "    ]\n" +
                        "}")
                )
            )
        );
    }
}
