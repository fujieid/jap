/*
 * Copyright (c) 2020-2040, 北京符节科技有限公司 (support@fujieid.com & https://www.fujieid.com).
 * <p>
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/lgpl.html
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fujieid.jap.ids.util;

import com.fujieid.jap.ids.config.IdsConfig;
import com.fujieid.jap.ids.config.JwtConfig;
import com.fujieid.jap.ids.model.UserInfo;
import com.fujieid.jap.ids.model.enums.JwtVerificationType;
import com.fujieid.jap.ids.model.enums.TokenSigningAlg;
import org.junit.Test;

import java.util.Map;

/**
 * https://mkjwk.org/
 */
public class JwtUtilTest {

    String clientId = "xxxxxxx";
    UserInfo userInfo = new UserInfo();
    String nonce = "asdasd";

    String issuer = "http://www.baidu.com";
    Long tokenExpireIn = 365 * 24 * 60 * 60L;
    String rs256JwksJson = "{\n" +
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
        "}";
    String rs384JwksJson = "{\n" +
        "    \"keys\": [\n" +
        "        {\n" +
        "            \"p\": \"83Ql5zOr0s0xxfoXHs77rd5vwG2_gwudv_jwF-gCOeuA8LOsCyBPFMoqr5JG8ebvLvtDCbThUj7xuZ49fGFD6KCHXMlt9NRtJKguzVn7wuiVjyF1VD0W_Dk4Ay_0X6hpTa39sRM7U3mxt5PgCYOOsKd6QsexNUdqawnE6fRivxM\",\n" +
        "            \"kty\": \"RSA\",\n" +
        "            \"q\": \"w2z94Lo3ai1JnTf9jLynEy65zclB9zvAmTm7r6HMfak0oWgPrM_jfiLo-lO8eSd3CdQGaEcGucKoM8fYNLxq6cBisNcm02sk7KAfkjsIzW7JLRbT3ORjG20-fzyG0q0UbijYbEZnkX4D-3AwR-myvPiT1ywYaMUdWel3ZC2nFR8\",\n" +
        "            \"d\": \"uQGDQOPwYn4_vqLc5nnNyJxPdbltnpM6zAqg65K2k7E9gT7_9u8v1y-GYvrQsELgVcAxGVmURPQctjyqigiSkUvw-_4U4AY3_ZLAqUZ-CFhzkoZhhsnXlZJHjvWWtZqEUsWVzALYK8GiSfcdOpKBNeHiCIgw3d4fcCmGZUlDSAhVPT2CJosGhSIP7K2B9y9ythgDQ_R67FhrtFTZQWml9XkxXFXekhHsNtXVjIXRRmZeKcPfn94-eOnIhApVaVHgFJcJGH94Xbbh-nqs5xy4ObOm8NdGf8-ufDN2zcmr2sLdyhewdEi80JK1DGPP5gQhZfxshonX7GE3Fx5o0hovoQ\",\n" +
        "            \"e\": \"AQAB\",\n" +
        "            \"use\": \"sig\",\n" +
        "            \"kid\": \"jap-jwk-keyid\",\n" +
        "            \"qi\": \"fMFk60XjrRKxro17rI5MOtgvriNu4BDARSxOQ8c4eDRoM6Wy4g6vUwi6Bj7qM39id8z1sfUnryFJyVzxuSSzLlgawwaLM_LGwep4AkToz0l9Mmm1avl0p_oQl05ywSo0SquGatdiwHaHa80bDyzsA3ebI31w2Rrmjr81nDiUz9k\",\n" +
        "            \"dp\": \"PaZoAshe9p7nv865FCAuM7Vkb0JbgP_sDrUnd6ZVCf3NRSb6pkakQAuCC7vrI07ruuX97_NSK9WsuOiNgXXQEJS2MpT_t0Qj72h3kaD71Du5w-khIRfnPi_vMz9tjtvC7tzkpXbNSzJCAs77qO0bsTh4CXkwMuHG3Rw4NVahuuk\",\n" +
        "            \"alg\": \"RS384\",\n" +
        "            \"dq\": \"MsOdL0MwIeShurVQp75ZqCH7IfmlqRNcdHEK0BS3iezqPwNJDxrxfVKUMnKOAuq9gVASWgQZOyfViZ3gC9Ll8tKG0GkTLNgoP09Y5CNxpeuhVpUXc8nf9L_r_CE85H0RUYxKq9WeEa0qW6ZI5GVQiMYJoVtS--Q4O6Lp4Jv7SwE\",\n" +
        "            \"n\": \"udkfTpoTbOSKfQMnLqSinnFLENRY3sJqKgYD6rsKanLmDHrvWrACSQPJ9ANXiEklK5ryAVxASNygxDxGeC4R3ofPT6MlgXjLr01vxUM9DW4DHpVQsnm7H8aaxC_9J-ddcVTyylKTqVWZBIai3gBsRZOKgpExRwkf2x5RnS1lDiWtvCO_R_CJawYYopw26XiqSGC_Y9UNysGcBK8pCMx0VhROexEtqD_gl6Lr7LaH9AdftGRihcVSMeoLdqu4CVaVwUiYoNWkGvb9qv0hBnlevG94NiTui5Lyr5sHp8g9DlIqAy7ehPYuXeNdcDY5i3hkl7pLkBy8nhk4mUnNrgayTQ\"\n" +
        "        }\n" +
        "    ]\n" +
        "}";
    String rs512JwksJson = "{\n" +
        "    \"keys\": [\n" +
        "        {\n" +
        "            \"p\": \"8sGhs7HKVhhMbR_SgLWncF5rqmbyDE4vNit-rugcCExJJo5ZvGzxHJ_UQfJvDEB1V90hr2loUhmqkWNd3WLtmr89_yoenlt2MN1lDX_lFL01uEmzmjcDhdL5GgxWg-FykJeR0Os_qJd4rpcODVKQjO_VQxNQwaTAhs__hHn2QdM\",\n" +
        "            \"kty\": \"RSA\",\n" +
        "            \"q\": \"qpaibUW1G35-gXniwAoNJMa2-b_LiOMBk4_DCmEIE8m4hRy3hTvXgbqtrMv7Xbd0BR_tQujciwovDaQE9ltOnN3zlKqe9v5xqGpsRTWQ_jLUN-Pq-Ae9BAb3BmA_sJnnojVp_DkfLSI_0VIyIIrjlPnvnrVtBCuiMdQNgGd0HqE\",\n" +
        "            \"d\": \"c0VcVI7VfRFmdCBkeEmuNGV301cjqlDH4w3cbRQPCHh31kjFeRCbrUtLYGO4m9YBKGMEReu1Uvjz4p3orR0wDanFgqtRVis6dpZD0xIhdGU5emOpAY5K05nNOS7MNEBntoO41jLpHo3-0pGqBMyYnOFFpZ0Jgoq_n9X_cGsBZvcnjEoNM_YnnshoVVNbCrfcxa5UWwuIstl6sSm0PDxtXon-Ydv2MAxTA5ujZy1agCcjjcxXw8thpP6ZJk_graeY0KHIAydNp-6xg0xosnHbydLCA6Iiz1qlRoPr9Oo5KB-RQdWdJFRFFPteEt8pcHKtJZ_ytCe4vYBs0EeC1sLuQQ\",\n" +
        "            \"e\": \"AQAB\",\n" +
        "            \"use\": \"sig\",\n" +
        "            \"kid\": \"jap-jwk-keyid\",\n" +
        "            \"qi\": \"pCxLgf9KQiNeo8cw3BmX0s9RWaMPL9kaJcE-4kwbIzKSO1oLNd-4a43WnyFb5P7VLnbnR9AXu__DpMVKQKWL5SPkevmNGVf97pc36YkaKh379lF_tpDRgyt9Z0bpykIu_rsTi3bemIvPzcTM6Bgg7flo16k62SOFH9pc5sTurrU\",\n" +
        "            \"dp\": \"AvxKptEa8fAekIEBr7-MLZ-bp17Yvzn-7qWeSzxji96UT7sUc3LLjFSS4bS_lOD-EHSRw3yCYfAa3urf7qcW0P5lHsw_0CbDz1oJsh7OjHC_RmLxqIXgrzanBaD9N2YAaLLUgkNCZyplu4_0BknrqTAR6V9FcPw7uey48cImOy0\",\n" +
        "            \"alg\": \"RS512\",\n" +
        "            \"dq\": \"kUBgH4EaW8XSe_bHv1MPq__T70aDTRRV1Eq1_VFvqkG57wXrsfOpZZoJpbeuWjcKAA8WXEGhAHb0Z74AR7CpeGJ4tF6vqoovRwMPG8MnqXqoPsq_2N_l7tbrYa90q6_wjqrCivQseqbOBjLh4dnBPKmwgcfjgoiQu7LeqDXupuE\",\n" +
        "            \"n\": \"ocNs3Do6B0506SP6w2POw5LZmCNtmm1IrkLiduDewapB2Z7fziDLpNhPkgK2ouAFFAO7LVmPoRd4xojO1FZxCXR2-hFggI86lOtaTptoln9v-6_J6dbVrOPjk9BLiEiOKr44xJJbbnesMvt4SX2kc_sUkQ9cP7hxa1AT8dOpxeR_riw_bx2VGsDc4dWTLsqIsCxZdyllJE9CFz3UFz60ZPByW0Ai1fuhpc1RFYuxA4Z422Z5xNvev4wwEXNX52Sofnuqebl1y2DdYuLCogxUAOMT4e3fJ-KRFSFZaSwx9lDe5npqxeKNC5TDzv0_4ZZkCOwHSO7IqmugDzEtSDIfsw\"\n" +
        "        }\n" +
        "    ]\n" +
        "}";
    String es256JwksJson = "{\n" +
        "    \"keys\": [\n" +
        "        {\n" +
        "            \"kty\": \"EC\",\n" +
        "            \"d\": \"G0bXBYlLfzC2fIjR2Rh8YUP1BeyS37oSOTgoFNp6hDU\",\n" +
        "            \"use\": \"sig\",\n" +
        "            \"crv\": \"P-256\",\n" +
        "            \"kid\": \"jap-jwk-keyid\",\n" +
        "            \"x\": \"gaNg_BBeCHwcbvmI94F9qDwh-EloVd0GOZMDJ03hlnk\",\n" +
        "            \"y\": \"65NEHBW8cwIEQ1IK8x8uskaRakujNoNoC_T2KjOSVfY\",\n" +
        "            \"alg\": \"ES256\"\n" +
        "        }\n" +
        "    ]\n" +
        "}";
    String es384JwksJson = "{\n" +
        "    \"keys\": [\n" +
        "        {\n" +
        "            \"kty\": \"EC\",\n" +
        "            \"d\": \"N_5ZUo-CE-H_zkvTV4w3hrS5QLBf2i9gyGLVUQCTY-8jsY0nJoyy3VQ2Pz2DRXW1\",\n" +
        "            \"use\": \"sig\",\n" +
        "            \"crv\": \"P-384\",\n" +
        "            \"kid\": \"jap-jwk-keyid\",\n" +
        "            \"x\": \"9u6MR_5wSQIqi84EeZcO5BuHfnpDUgnz-GvFRrU3fe66Z_n6hbEERClb1UhcdVZf\",\n" +
        "            \"y\": \"aRrJIGToPMeDgboLGUgYa0kvx7KyjlxM8WskEg_eteipXWR-natHIjFwFFSqDb9A\",\n" +
        "            \"alg\": \"ES384\"\n" +
        "        }\n" +
        "    ]\n" +
        "}";
    String es512JwksJson = "{\n" +
        "    \"keys\": [\n" +
        "        {\n" +
        "            \"kty\": \"EC\",\n" +
        "            \"d\": \"AHWjfpnoFxayAdKwLse6dsOXDU0mUP5ZnnpyCTWFiqR8QbvCtosRv01hds4yjnLLoqQQ2EU3vsBo9GLc2Z94q5l3\",\n" +
        "            \"use\": \"sig\",\n" +
        "            \"crv\": \"P-521\",\n" +
        "            \"kid\": \"jap-jwk-keyid\",\n" +
        "            \"x\": \"AF20L_FMTzA6Rl9IEYk7Ww-V06PiiQ7-cZ6p8Hgs8xha2TSC46BoZ3kZH1-aoVJUTlJCuhS5zfWcJVBA7Szx3izd\",\n" +
        "            \"y\": \"ARTnAphEcl9OtHJd6kDasfjNbPZrzWjt8SJFEHoxMqlwgl4dBAkO82itFfOxpvFYpei02rGSOQtambGA2u1ZwkYc\",\n" +
        "            \"alg\": \"ES512\"\n" +
        "        }\n" +
        "    ]\n" +
        "}";

    public JwtUtilTest() {
        userInfo.setId("1111");
        userInfo.setUsername("rd");
    }

    @Test
    public void createJwtTokenFromRs256() {
        String jwtToken = JwtUtil.createJwtToken(clientId, userInfo, tokenExpireIn, nonce, new IdsConfig()
            .setIssuer(issuer)
            .setJwtConfig(new JwtConfig().setJwksJson(rs256JwksJson)));
        System.out.println(jwtToken);
    }

    @Test
    public void parseJwtTokenFromRs256() {
        String jwt = "eyJraWQiOiJqYXAtandrLWtleWlkIiwiYWxnIjoiUlMyNTYifQ.eyJpc3MiOiJodHRwOi8vd3d3LmJhaWR1LmNvbSIsInN1YiI6IjExMTEiLCJhdWQiOiJ4eHh4eHh4IiwiZXhwIjoxNjUwMDMxMDE4LCJpYXQiOjE2MTg0OTUwMTgsIm5vbmNlIjoiYXNkYXNkIiwidXNlcm5hbWUiOiJyZCJ9.YgqeBmlrGeauzEAwPOi_WIjG7SyLieU8sbAq-2Ptqq8bDOg0CZdKnzaU9mr-3iEoOeAzTTXh02jHzEz8hhorxi2PFnjZy4H1HSgNqGZckAvwGnN5aC_tMPhx1I_8XMZ0_ZpRiCAlV1NSedveQbCm1jJVKSCoBSLUA4hCIWAQqAR__M-de08oQ-r3HfhFZkSghbzMOI8fXMLvVLtexQAxjek6hn769x-hi-AW-DVDPB_ifUojV8TUNZWZHNj2kG89rBwLgK5LsXEBFpBFvwtfkBYPJVxiSf3cGLcUPTpipQ8buvaLXojAYwE_MXIRklUm2FMAuodQKDJunExe3rzYjw";
        Map<String, Object> jwtInfo = JwtUtil.parseJwtToken(jwt, new IdsConfig()
            .setIssuer(issuer)
            .setJwtConfig(new JwtConfig()
                .setJwksJson(rs256JwksJson)
            ));
        System.out.println(jwtInfo);
    }

    @Test
    public void createJwtTokenFromRs384() {
        String jwtToken = JwtUtil.createJwtToken(clientId, userInfo, tokenExpireIn, nonce, new IdsConfig()
            .setIssuer(issuer)
            .setJwtConfig(new JwtConfig()
                .setJwksJson(rs384JwksJson)
                .setTokenSigningAlg(TokenSigningAlg.RS384)
            ));
        System.out.println(jwtToken);
    }

    @Test
    public void parseJwtTokenFromRs384() {
        String jwt = "eyJraWQiOiJqYXAtandrLWtleWlkIiwiYWxnIjoiUlMzODQifQ.eyJpc3MiOiJodHRwOi8vd3d3LmJhaWR1LmNvbSIsInN1YiI6IjExMTEiLCJhdWQiOiJ4eHh4eHh4IiwiZXhwIjoxNjUwMDMxMDYwLCJpYXQiOjE2MTg0OTUwNjAsIm5vbmNlIjoiYXNkYXNkIiwidXNlcm5hbWUiOiJyZCJ9.fqVFn5Hawa6_sDMP8qN1GDq5TTWPJ8WobxPa_Haw6eapEx36Mgqc2Nc5OaV-ZfzqKgEGRqKuWPQl4_KgxcrnkWD2qOmThGsgLlKE8U3MPNeiyCkGTVJ6fMLwXC1lCZhjP8bSJCMDN1E-RVT1oAPGuHptqVmcaVxYDjakzfy_ofFAItFah9O1L875l172LWSeVbt7tjPkShfnjE0XVHPhs1-mG5FcZL5ScpdhZ3doDK0H1Vf5LJAeapuhL1q_zNeBX2P88kCeTy1zc7bEEWaHlA_WM_qStvrXgcFoAlpuVTOAbipyJwX26vxQTi2fKvAhZHINtL03S1un1AQGuIy3FQ";
        Map<String, Object> jwtInfo = JwtUtil.parseJwtToken(jwt, new IdsConfig()
            .setIssuer(issuer)
            .setJwtConfig(new JwtConfig()
                .setJwksJson(rs384JwksJson)
                .setTokenSigningAlg(TokenSigningAlg.RS384)
            ));
        System.out.println(jwtInfo);
    }

    @Test
    public void createJwtTokenFromRs512() {
        String jwtToken = JwtUtil.createJwtToken(clientId, userInfo, tokenExpireIn, nonce, new IdsConfig()
            .setIssuer(issuer)
            .setJwtConfig(new JwtConfig()
                .setJwksJson(rs512JwksJson)
                .setTokenSigningAlg(TokenSigningAlg.RS512)
            ));
        System.out.println(jwtToken);
    }

    @Test
    public void parseJwtTokenFromRs512() {
        String jwt = "eyJraWQiOiJqYXAtandrLWtleWlkIiwiYWxnIjoiUlM1MTIifQ.eyJpc3MiOiJodHRwOi8vd3d3LmJhaWR1LmNvbSIsInN1YiI6IjExMTEiLCJhdWQiOiJ4eHh4eHh4IiwiZXhwIjoxNjUwMDMxMDk3LCJpYXQiOjE2MTg0OTUwOTcsIm5vbmNlIjoiYXNkYXNkIiwidXNlcm5hbWUiOiJyZCJ9.ibVDk8CxM6jbNcDGqRc-emjqmRvx4ghhSlzvv6rScC8JDmQGgEotdcmu0N5OaxQt-YZex9daAYBF3Cx09jIvjjot0Iy-0mLf1sT4tjG3K5LQ862LRao3L9MyE3Pzqhe2am9AMaGlGimCdrrAFmufSprTADw7jgLxoVIwY5ou1ei5SAO__RjZo-Nmxp8kTW2ea9zKFuhWEkjmLWdZAi6Iet8ntcSFsa1RFc05Urytu8fbOKlqQwaVLOfiaEQw6QDmd28Q7h_BFP_rbclaHaJHOjv05WMKeH5iOEN0wCz4HlZYKOLfLPzH5pZuutMdueiDdCN5WemGLeekpOcJ0BKDfw";
        Map<String, Object> jwtInfo = JwtUtil.parseJwtToken(jwt, new IdsConfig()
            .setIssuer(issuer)
            .setJwtConfig(new JwtConfig()
                .setJwksJson(rs512JwksJson)
                .setTokenSigningAlg(TokenSigningAlg.RS512)
            ));
        System.out.println(jwtInfo);
    }

    @Test
    public void createJwtTokenFromEs256() {
        String jwtToken = JwtUtil.createJwtToken(clientId, userInfo, tokenExpireIn, nonce, new IdsConfig()
            .setIssuer(issuer)
            .setJwtConfig(new JwtConfig()
                .setJwksJson(es256JwksJson)
                .setTokenSigningAlg(TokenSigningAlg.ES256)
            ));
        System.out.println(jwtToken);
    }

    @Test
    public void parseJwtTokenFromEs256() {
        String jwt = "eyJraWQiOiJqYXAtandrLWtleWlkIiwiYWxnIjoiRVMyNTYifQ.eyJpc3MiOm51bGwsInN1YiI6IjExMTEiLCJhdWQiOiJ4eHh4eHh4IiwiZXhwIjoxNjUwMDMxMTIzLCJpYXQiOjE2MTg0OTUxMjMsIm5vbmNlIjoiYXNkYXNkIiwidXNlcm5hbWUiOiJyZCJ9.k1b3DEceONkjvOfyE3oBLoVnMNBafx8cMQPU8971D5Xmksn4nnrJl9v2DinvbCrCTc2MVqznClvuhiQ0c76aXg";
        Map<String, Object> jwtInfo = JwtUtil.parseJwtToken(jwt, new IdsConfig()
            .setIssuer(issuer)
            .setJwtConfig(new JwtConfig()
                .setJwksJson(es256JwksJson)
                .setTokenSigningAlg(TokenSigningAlg.ES256)
            ));
        System.out.println(jwtInfo);
    }

    @Test
    public void createJwtTokenFromEs384() {
        String jwtToken = JwtUtil.createJwtToken(clientId, userInfo, tokenExpireIn, nonce, new IdsConfig()
            .setIssuer(issuer)
            .setJwtConfig(new JwtConfig()
                .setJwksJson(es384JwksJson)
                .setTokenSigningAlg(TokenSigningAlg.ES384)
            ));
        System.out.println(jwtToken);
    }

    @Test
    public void parseJwtTokenFromEs384() {
        String jwt = "eyJraWQiOiJqYXAtandrLWtleWlkIiwiYWxnIjoiRVMzODQifQ.eyJpc3MiOm51bGwsInN1YiI6IjExMTEiLCJhdWQiOiJ4eHh4eHh4IiwiZXhwIjoxNjE4NTkyMzQxLCJpYXQiOjE2MTg0OTIzNDEsIm5vbmNlIjoiYXNkYXNkIiwidXNlcm5hbWUiOm51bGx9.BhVF8JagDRjpnW8wbsNi-Fik9nVnIq8X5mpnrkpp1f6K7b1vqCuBuzmHYpy2Wwz1eVYUTa3haRay30jFC2OwzwXNMNXIdJ6X5w-KKefKtiFl_YITFQU-WDwAsPGT3ZfI";
        Map<String, Object> jwtInfo = JwtUtil.parseJwtToken(jwt, new IdsConfig()
            .setIssuer(issuer)
            .setJwtConfig(new JwtConfig()
                .setJwksJson(es384JwksJson)
                .setTokenSigningAlg(TokenSigningAlg.ES384)
            ));
        System.out.println(jwtInfo);
    }

    @Test
    public void createJwtTokenFromEs512() {
        String jwtToken = JwtUtil.createJwtToken(clientId, userInfo, tokenExpireIn, nonce, new IdsConfig()
            .setIssuer(issuer)
            .setJwtConfig(new JwtConfig()
                .setJwksJson(es512JwksJson)
                .setTokenSigningAlg(TokenSigningAlg.ES512)
            ));
        System.out.println(jwtToken);
    }

    @Test
    public void parseJwtTokenFromEs512() {
        String jwt = "eyJraWQiOiJqYXAtandrLWtleWlkIiwiYWxnIjoiRVM1MTIifQ.eyJpc3MiOm51bGwsInN1YiI6IjExMTEiLCJhdWQiOiJ4eHh4eHh4IiwiZXhwIjoxNjE4NTkyNDQwLCJpYXQiOjE2MTg0OTI0NDAsIm5vbmNlIjoiYXNkYXNkIiwidXNlcm5hbWUiOiJyZCJ9.ARZRKO52FZe7MCKy_Kv02IodCIli6y1ZbSlWpe4Qn0sCBNLGzmcK5A0302TM9dZ8yu8TgLK9j7MbW9i6VaeDZmfwAA0_z1J1SBiIwJ38IdhISJItFBQL28KVf02zfFXfKHl2AZ9DU_liyuNsdkuV92Np-sEAoFMpkC_4cSlJqpM6T-oi";
        Map<String, Object> jwtInfo = JwtUtil.parseJwtToken(jwt, new IdsConfig()
            .setIssuer(issuer)
            .setJwtConfig(new JwtConfig()
                .setJwksJson(es512JwksJson)
                .setTokenSigningAlg(TokenSigningAlg.ES512)
            ));
        System.out.println(jwtInfo);
    }

    @Test
    public void validateJwtToken() {
        String jwt = "eyJraWQiOiJqYXAtandrLWtleWlkIiwiYWxnIjoiUlMzODQifQ.eyJpc3MiOiJodHRwOi8vd3d3LmJhaWR1LmNvbSIsInN1YiI6IjExMTEiLCJhdWQiOiJ4eHh4eHh4IiwiZXhwIjoxNjUwMDMxMDYwLCJpYXQiOjE2MTg0OTUwNjAsIm5vbmNlIjoiYXNkYXNkIiwidXNlcm5hbWUiOiJyZCJ9.fqVFn5Hawa6_sDMP8qN1GDq5TTWPJ8WobxPa_Haw6eapEx36Mgqc2Nc5OaV-ZfzqKgEGRqKuWPQl4_KgxcrnkWD2qOmThGsgLlKE8U3MPNeiyCkGTVJ6fMLwXC1lCZhjP8bSJCMDN1E-RVT1oAPGuHptqVmcaVxYDjakzfy_ofFAItFah9O1L875l172LWSeVbt7tjPkShfnjE0XVHPhs1-mG5FcZL5ScpdhZ3doDK0H1Vf5LJAeapuhL1q_zNeBX2P88kCeTy1zc7bEEWaHlA_WM_qStvrXgcFoAlpuVTOAbipyJwX26vxQTi2fKvAhZHINtL03S1un1AQGuIy3FQ";
        Map<String, Object> jwtInfo = JwtUtil.validateJwtToken(clientId, userInfo.getId(), jwt, new IdsConfig()
            .setIssuer(issuer)
            .setJwtConfig(new JwtConfig()
                .setJwksJson(es512JwksJson)
                .setTokenSigningAlg(TokenSigningAlg.ES512)
                .setJwtVerificationType(JwtVerificationType.JWKS)
            ));
        System.out.println(jwtInfo);
    }
}
