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

import com.fujieid.jap.ids.BaseIdsTest;
import com.fujieid.jap.ids.JapIds;
import com.fujieid.jap.ids.model.UserInfo;
import com.fujieid.jap.ids.model.enums.JwtVerificationType;
import com.fujieid.jap.ids.model.enums.TokenSigningAlg;
import org.junit.Test;

import java.util.Map;

/**
 * https://mkjwk.org/
 */
public class JwtUtilTest extends BaseIdsTest {

    String clientId = "xxxxxxx";
    UserInfo userInfo = new UserInfo();
    String nonce = "asdasd";

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
        JapIds.getIdsConfig().getJwtConfig().setJwksJson(rs256JwksJson);
        String jwtToken = JwtUtil.createJwtToken(clientId, userInfo, tokenExpireIn, nonce, issuer);
        System.out.println(jwtToken);
    }

    @Test
    public void parseJwtTokenFromRs256() {
        String jwt = "eyJraWQiOiJqYXAtandrLWtleWlkIiwiYWxnIjoiUlMyNTYifQ.eyJpc3MiOiJodHRwOi8vd3d3LmJhaWR1LmNvbSIsInN1YiI6IjExMTEiLCJhdWQiOiJ4eHh4eHh4IiwiZXhwIjoxNjUwNDU4MzczLCJpYXQiOjE2MTg5MjIzNzMsIm5vbmNlIjoiYXNkYXNkIiwidXNlcm5hbWUiOiJyZCJ9.EgqR5HJT3lhfF7bWtxOu0YLlCZ3Fze5dZ3ueUoxKPbzIGUd36592O-DZO_3xWL6jpW5GrZSeLV-mYpHB9T0vCcWWrZOvc1TB7_BDlOdsRHitZoxieTxK3Gvzcd-a4gK88ymB8FUL7_97xJ3ReCiJlf5g6haDIftLJ1kH8bbi8gdas0HpQg2Ey9OPGEbd4hx-djzxlqXrn1zQyZfUCdNOJbFE-UzXfzT7KNktp7UrkA48KiQ0Av9dyDU1qolNYjnljHvSKmJuruWqT0vC20jaLqzc7J2-zWyfAUI-SD5a7lrDJWtvYdj4H8FJKP1A5TO8-5hu_hXy3YAOiBVM3Px9jQ";
        JapIds.getIdsConfig().getJwtConfig().setJwksJson(rs256JwksJson);
        Map<String, Object> jwtInfo = JwtUtil.parseJwtToken(jwt);
        System.out.println(jwtInfo);
    }

    @Test
    public void createJwtTokenFromRs384() {
        JapIds.getIdsConfig()
            .getJwtConfig()
            .setJwksJson(rs384JwksJson)
            .setTokenSigningAlg(TokenSigningAlg.RS384);
        String jwtToken = JwtUtil.createJwtToken(clientId, userInfo, tokenExpireIn, nonce, issuer);
        System.out.println(jwtToken);
    }

    @Test
    public void parseJwtTokenFromRs384() {
        String jwt = "eyJraWQiOiJqYXAtandrLWtleWlkIiwiYWxnIjoiUlMzODQifQ.eyJpc3MiOiJodHRwOi8vd3d3LmJhaWR1LmNvbSIsInN1YiI6IjExMTEiLCJhdWQiOiJ4eHh4eHh4IiwiZXhwIjoxNjUwMDMxMDYwLCJpYXQiOjE2MTg0OTUwNjAsIm5vbmNlIjoiYXNkYXNkIiwidXNlcm5hbWUiOiJyZCJ9.fqVFn5Hawa6_sDMP8qN1GDq5TTWPJ8WobxPa_Haw6eapEx36Mgqc2Nc5OaV-ZfzqKgEGRqKuWPQl4_KgxcrnkWD2qOmThGsgLlKE8U3MPNeiyCkGTVJ6fMLwXC1lCZhjP8bSJCMDN1E-RVT1oAPGuHptqVmcaVxYDjakzfy_ofFAItFah9O1L875l172LWSeVbt7tjPkShfnjE0XVHPhs1-mG5FcZL5ScpdhZ3doDK0H1Vf5LJAeapuhL1q_zNeBX2P88kCeTy1zc7bEEWaHlA_WM_qStvrXgcFoAlpuVTOAbipyJwX26vxQTi2fKvAhZHINtL03S1un1AQGuIy3FQ";

        JapIds.getIdsConfig()
            .getJwtConfig()
            .setJwksJson(rs384JwksJson)
            .setTokenSigningAlg(TokenSigningAlg.RS384);
        Map<String, Object> jwtInfo = JwtUtil.parseJwtToken(jwt);
        System.out.println(jwtInfo);
    }

    @Test
    public void createJwtTokenFromRs512() {
        JapIds.getIdsConfig()
            .getJwtConfig()
            .setJwksJson(rs512JwksJson)
            .setTokenSigningAlg(TokenSigningAlg.RS512);
        String jwtToken = JwtUtil.createJwtToken(clientId, userInfo, tokenExpireIn, nonce, issuer);
        System.out.println(jwtToken);
    }

    @Test
    public void parseJwtTokenFromRs512() {
        String jwt = "eyJraWQiOiJqYXAtandrLWtleWlkIiwiYWxnIjoiUlM1MTIifQ.eyJpc3MiOiJodHRwOi8vd3d3LmJhaWR1LmNvbSIsInN1YiI6IjExMTEiLCJhdWQiOiJ4eHh4eHh4IiwiZXhwIjoxNjUwMDMxMDk3LCJpYXQiOjE2MTg0OTUwOTcsIm5vbmNlIjoiYXNkYXNkIiwidXNlcm5hbWUiOiJyZCJ9.ibVDk8CxM6jbNcDGqRc-emjqmRvx4ghhSlzvv6rScC8JDmQGgEotdcmu0N5OaxQt-YZex9daAYBF3Cx09jIvjjot0Iy-0mLf1sT4tjG3K5LQ862LRao3L9MyE3Pzqhe2am9AMaGlGimCdrrAFmufSprTADw7jgLxoVIwY5ou1ei5SAO__RjZo-Nmxp8kTW2ea9zKFuhWEkjmLWdZAi6Iet8ntcSFsa1RFc05Urytu8fbOKlqQwaVLOfiaEQw6QDmd28Q7h_BFP_rbclaHaJHOjv05WMKeH5iOEN0wCz4HlZYKOLfLPzH5pZuutMdueiDdCN5WemGLeekpOcJ0BKDfw";
        JapIds.getIdsConfig()
            .getJwtConfig()
            .setJwksJson(rs512JwksJson)
            .setTokenSigningAlg(TokenSigningAlg.RS512);
        Map<String, Object> jwtInfo = JwtUtil.parseJwtToken(jwt);
        System.out.println(jwtInfo);
    }

    @Test
    public void createJwtTokenFromEs256() {
        JapIds.getIdsConfig()
            .getJwtConfig()
            .setJwksJson(es256JwksJson)
            .setTokenSigningAlg(TokenSigningAlg.ES256);
        String jwtToken = JwtUtil.createJwtToken(clientId, userInfo, tokenExpireIn, nonce, issuer);
        System.out.println(jwtToken);
    }

    @Test
    public void parseJwtTokenFromEs256() {
        String jwt = "eyJraWQiOiJqYXAtandrLWtleWlkIiwiYWxnIjoiRVMyNTYifQ.eyJpc3MiOm51bGwsInN1YiI6IjExMTEiLCJhdWQiOiJ4eHh4eHh4IiwiZXhwIjoxNjUwMDMxMTIzLCJpYXQiOjE2MTg0OTUxMjMsIm5vbmNlIjoiYXNkYXNkIiwidXNlcm5hbWUiOiJyZCJ9.k1b3DEceONkjvOfyE3oBLoVnMNBafx8cMQPU8971D5Xmksn4nnrJl9v2DinvbCrCTc2MVqznClvuhiQ0c76aXg";

        JapIds.getIdsConfig()
            .getJwtConfig()
            .setJwksJson(es256JwksJson)
            .setTokenSigningAlg(TokenSigningAlg.ES256);
        Map<String, Object> jwtInfo = JwtUtil.parseJwtToken(jwt);
        System.out.println(jwtInfo);
    }

    @Test
    public void createJwtTokenFromEs384() {
        JapIds.getIdsConfig()
            .getJwtConfig()
            .setJwksJson(es384JwksJson)
            .setTokenSigningAlg(TokenSigningAlg.ES384);
        String jwtToken = JwtUtil.createJwtToken(clientId, userInfo, tokenExpireIn, nonce, issuer);
        System.out.println(jwtToken);
    }

    @Test
    public void parseJwtTokenFromEs384() {
        String jwt = "eyJraWQiOiJqYXAtandrLWtleWlkIiwiYWxnIjoiRVMzODQifQ.eyJpc3MiOiJodHRwOi8vd3d3LmJhaWR1LmNvbSIsInN1YiI6IjExMTEiLCJhdWQiOiJ4eHh4eHh4IiwiZXhwIjoxNjUwMzU5OTI0LCJpYXQiOjE2MTg4MjM5MjQsIm5vbmNlIjoiYXNkYXNkIiwidXNlcm5hbWUiOiJyZCJ9.s833h8GubRExoKfr3XkYrU8qzkaqxme_ATXNJAeSDgC8HJWKTLh1SlXhQaSBjKF5DQ0nJoodJFnpr1dBcC_pZ-GooADXdLOOQtu0Y-p0OXCkh4zHEWzKyu-hPs2Sbis0";

        JapIds.getIdsConfig()
            .getJwtConfig()
            .setJwksJson(es384JwksJson)
            .setTokenSigningAlg(TokenSigningAlg.ES384);
        Map<String, Object> jwtInfo = JwtUtil.parseJwtToken(jwt);
        System.out.println(jwtInfo);
    }

    @Test
    public void createJwtTokenFromEs512() {
        JapIds.getIdsConfig()
            .getJwtConfig()
            .setJwksJson(es512JwksJson)
            .setTokenSigningAlg(TokenSigningAlg.ES512);
        String jwtToken = JwtUtil.createJwtToken(clientId, userInfo, tokenExpireIn, nonce, issuer);
        System.out.println(jwtToken);
    }

    @Test
    public void parseJwtTokenFromEs512() {
        String jwt = "eyJraWQiOiJqYXAtandrLWtleWlkIiwiYWxnIjoiRVM1MTIifQ.eyJpc3MiOiJodHRwOi8vd3d3LmJhaWR1LmNvbSIsInN1YiI6IjExMTEiLCJhdWQiOiJ4eHh4eHh4IiwiZXhwIjoxNjUwMzU5OTQ3LCJpYXQiOjE2MTg4MjM5NDcsIm5vbmNlIjoiYXNkYXNkIiwidXNlcm5hbWUiOiJyZCJ9.AAsIflGEuCTJJxZ8CV6aEVkXM8TXjvs9RfsXRuhTyfzXjqtMMaEUZ9aaH1vXlNrQWPvcJDw4HgcQvzRitN1_NLNFASTAzr7l0ef-J_4Raj-DQTpQq5Wdb4icGyunPy-LeLfvNYygbJNNa9AbQ4kMiizI4NIy6gKNc5nXHziTmyXA2eN2";

        JapIds.getIdsConfig()
            .getJwtConfig()
            .setJwksJson(es512JwksJson)
            .setTokenSigningAlg(TokenSigningAlg.ES512);
        Map<String, Object> jwtInfo = JwtUtil.parseJwtToken(jwt);
        System.out.println(jwtInfo);
    }

    @Test
    public void validateJwtToken() {
        String jwt = "eyJraWQiOiJqYXAtandrLWtleWlkIiwiYWxnIjoiRVM1MTIifQ.eyJpc3MiOiJodHRwOi8vd3d3LmJhaWR1LmNvbSIsInN1YiI6IjExMTEiLCJhdWQiOiJ4eHh4eHh4IiwiZXhwIjoxNjUwMzU5OTQ3LCJpYXQiOjE2MTg4MjM5NDcsIm5vbmNlIjoiYXNkYXNkIiwidXNlcm5hbWUiOiJyZCJ9.AAsIflGEuCTJJxZ8CV6aEVkXM8TXjvs9RfsXRuhTyfzXjqtMMaEUZ9aaH1vXlNrQWPvcJDw4HgcQvzRitN1_NLNFASTAzr7l0ef-J_4Raj-DQTpQq5Wdb4icGyunPy-LeLfvNYygbJNNa9AbQ4kMiizI4NIy6gKNc5nXHziTmyXA2eN2";
        JapIds.getIdsConfig()
            .getJwtConfig()
            .setJwksJson(es512JwksJson)
            .setTokenSigningAlg(TokenSigningAlg.ES512)
            .setJwtVerificationType(JwtVerificationType.JWKS);
        Map<String, Object> jwtInfo = JwtUtil.validateJwtToken(clientId, userInfo.getId(), jwt, EndpointUtil.getJwksUrl(null));
        System.out.println(jwtInfo);
    }
}
