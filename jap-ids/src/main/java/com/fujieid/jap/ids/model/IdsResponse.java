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
package com.fujieid.jap.ids.model;

import cn.hutool.core.util.ObjectUtil;
import com.fujieid.jap.ids.model.enums.ErrorResponse;
import com.xkcoding.json.util.StringUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.0
 */
public class IdsResponse<K, V> extends HashMap<String, Object> {
    private final String error = "error";
    private final String error_description = "error_description";
    private final String error_uri = "error_uri";
    private final String state = "state";
    private final String data = "data";

    public IdsResponse<K, V> error(ErrorResponse errorCode) {
        return this.error(errorCode.getError())
            .errorDescription(errorCode.getErrorDescription());
    }

    public IdsResponse<K, V> error(String errorCode) {
        this.put(this.error, errorCode);
        return this;
    }

    public IdsResponse<K, V> errorDescription(String errorDescription) {
        this.put(this.error_description, errorDescription);
        return this;
    }

    public IdsResponse<K, V> errorUri(String errorUri) {
        this.put(this.error_uri, errorUri);
        return this;
    }

    public IdsResponse<K, V> state(String state) {
        this.put(this.state, state);
        return this;
    }

    public IdsResponse<K, V> data(Object data) {
        this.put(this.error, "");
        this.put(this.error_description, "");
        this.put(this.data, data);
        return this;
    }

    public boolean isSuccess() {
        return StringUtil.isEmpty(this.getError());
    }

    public IdsResponse<K, V> add(String key, Object value) {
        this.put(key, value);
        return this;
    }

    public IdsResponse<K, V> addAll(Map<String, Object> map) {
        this.putAll(map);
        return this;
    }

    public String getError() {
        return ObjectUtil.isEmpty(this.get(error)) ? null : String.valueOf(this.get(error));
    }

    public String getErrorDescription() {
        return ObjectUtil.isEmpty(this.get(error_description)) ? null : String.valueOf(this.get(error_description));
    }

    public String getErrorUri() {
        return ObjectUtil.isEmpty(this.get(error_uri)) ? null : String.valueOf(this.get(error_uri));
    }

    public String getState() {
        return ObjectUtil.isEmpty(this.get(state)) ? null : String.valueOf(this.get(state));
    }

    public Object getData() {
        return this.get(data);
    }
}
