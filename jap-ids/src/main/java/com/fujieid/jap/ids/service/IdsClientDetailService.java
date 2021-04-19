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
package com.fujieid.jap.ids.service;

import com.fujieid.jap.ids.exception.IdsException;
import com.fujieid.jap.ids.model.ClientDetail;

import java.util.List;

/**
 * Client application related interfaces
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.0
 */
public interface IdsClientDetailService {

    /**
     * Query client information through client id
     *
     * @param clientId Client application id
     * @return ClientDetail
     */
    default ClientDetail getByClientId(String clientId) {
        throw new IdsException("Not implemented `IdsClientDetailService.getByClientId(String)`");
    }

    /**
     * Add client
     *
     * @param clientDetail Client application details
     * @return ClientDetail
     */
    default ClientDetail add(ClientDetail clientDetail) {
        throw new IdsException("Not implemented `IdsClientDetailService.add(ClientDetail)`");
    }

    /**
     * Modify the client
     *
     * @param clientDetail Client application details
     * @return ClientDetail
     */
    default ClientDetail update(ClientDetail clientDetail) {
        throw new IdsException("Not implemented `IdsClientDetailService.update(ClientDetail)`");
    }

    /**
     * Delete client by primary key
     *
     * @param id Primary key of the client application
     * @return boolean
     */
    default boolean removeById(String id) {
        throw new IdsException("Not implemented `IdsClientDetailService.removeById(String)`");
    }

    /**
     * Delete client by client id
     *
     * @param clientId Client application id
     * @return ClientDetail
     */
    default boolean removeByClientId(String clientId) {
        throw new IdsException("Not implemented `IdsClientDetailService.removeByClientId(String)`");
    }

    /**
     * Get all client detail
     *
     * @return List
     */
    default List<ClientDetail> getAllClientDetail() {
        throw new IdsException("Not implemented `IdsClientDetailService.getAllClientDetail()`");
    }


}
