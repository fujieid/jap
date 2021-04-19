package com.fujieid.jap.ids.provider;

import com.fujieid.jap.ids.model.IdsScope;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

public class IdsScopeProviderTest {

    @Test
    public void initScopes() {
        IdsScopeProvider.initScopes(Collections.singletonList(new IdsScope().setCode("code").setDescription("aasdasd")));
        Assert.assertEquals(1, IdsScopeProvider.getScopes().size());
        Assert.assertEquals("code", IdsScopeProvider.getScopes().get(0).getCode());
    }

    @Test
    public void addScope() {
        IdsScopeProvider.addScope(new IdsScope().setCode("code").setDescription("aasdasd"));
        List<IdsScope> scopeList = IdsScopeProvider.getScopeByCodes(Collections.singletonList("code"));
        Assert.assertNotNull(scopeList);
        Assert.assertEquals(1, scopeList.size());
    }

    @Test
    public void getScopes() {
        List<IdsScope> scopeList = IdsScopeProvider.getScopes();
        Assert.assertNotNull(scopeList);
    }

    @Test
    public void getScopeByCodes() {
        List<IdsScope> scopeList = IdsScopeProvider.getScopeByCodes(Collections.singletonList("codaae"));
        Assert.assertEquals(0, scopeList.size());
    }

    @Test
    public void getScopeByCodesContainScope() {
        List<IdsScope> scopeList = IdsScopeProvider.getScopeByCodes(Collections.singletonList("openid"));
        Assert.assertNotNull(scopeList);
    }

    @Test
    public void getScopeCodes() {
        List<String> scopeList = IdsScopeProvider.getScopeCodes();
        Assert.assertNotNull(scopeList);
    }
}
