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
/**
 * Supports SPI features. When implementing an interface, developers can pass parameters through the <code>setXx</code> method,
 * or through the SPI specification. for example:
 * <p>
 * For the <code>JapUserService</code> interface provided by jap, developers can set the interface implementation class through <code>xx.setUserService</code>,
 * You can also configure the implementation class of the interface in a file named after the interface name in the <code>resources/META-INF/services</code> folder, such as:
 * <code>resources/META-INF/services/com.fujieid.jap.core.JapUserService</code>, the content of the file is <code>xx.xxx.xx.JapUserServiceImpl</code>
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.0
 */
package com.fujieid.jap.core.spi;
