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
package com.fujieid.jap.ids.spi;

import com.fujieid.jap.core.spi.JapServiceLoader;
import com.fujieid.jap.ids.pipeline.IdsFilterPipeline;
import com.fujieid.jap.ids.pipeline.IdsPipeline;
import com.fujieid.jap.ids.pipeline.IdsSignInPipeline;
import org.junit.Test;

import java.util.List;

/**
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.0
 */
public class ServiceLoaderTest {

    @Test
    public void loadIdsPipeline() {
        List<IdsPipeline> list = JapServiceLoader.load(IdsPipeline.class);
        list.forEach(idsPipeline -> {
            System.out.println(idsPipeline);
            System.out.println(idsPipeline.getClass());
            idsPipeline.postHandle(null, null);
            System.out.println();
            System.out.println();
        });
    }

    @Test
    public void loadIdsFilterPipeline() {
        IdsFilterPipeline idsFilterPipeline = JapServiceLoader.loadFirst(IdsFilterPipeline.class);
        System.out.println(idsFilterPipeline);
        if (null != idsFilterPipeline) {
            System.out.println(idsFilterPipeline.getClass());
            idsFilterPipeline.postHandle(null, null);
            System.out.println();
            System.out.println();
        }
    }

    @Test
    public void loadIdsSignInPipeline() {
        IdsSignInPipeline idsSignInPipeline = JapServiceLoader.loadFirst(IdsSignInPipeline.class);
        System.out.println(idsSignInPipeline);
        if (null != idsSignInPipeline) {
            System.out.println(idsSignInPipeline.getClass());
            idsSignInPipeline.postHandle(null, null);
            System.out.println();
            System.out.println();
        }
    }
}
