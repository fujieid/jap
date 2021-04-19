package com.fujieid.jap.ids.endpoint;

import com.fujieid.jap.ids.BaseIdsTest;
import com.fujieid.jap.ids.exception.InvalidRequestException;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class ApprovalEndpointTest extends BaseIdsTest {

    @Test
    public void showConfirmPage() throws IOException {
        Assert.assertThrows(InvalidRequestException.class, () -> new ApprovalEndpoint().showConfirmPage(httpServletRequestMock, httpServletResponseMock));
    }

    @Test
    public void getAuthClientInfo() {
        Assert.assertThrows(InvalidRequestException.class, () -> new ApprovalEndpoint().getAuthClientInfo(httpServletRequestMock));
    }
}
