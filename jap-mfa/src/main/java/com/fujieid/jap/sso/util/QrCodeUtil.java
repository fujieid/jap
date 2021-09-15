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
package com.fujieid.jap.sso.util;

import cn.hutool.core.img.Img;
import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.util.CharsetUtil;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.HashMap;

/**
 * qr code condensed version of tools, Quoted from hutool
 * <p>
 * 基于Zxing的二维码工具类
 *
 * @author looly
 * @see <a href="https://gitee.com/dromara/hutool/blob/v5-master/hutool-extra/src/main/java/cn/hutool/extra/qrcode/QrCodeUtil.java" target="_blank">hutool</a>
 * @since 4.0.2
 */
public class QrCodeUtil {

    /**
     * 生成二维码到文件，二维码图片格式取决于文件的扩展名
     *
     * @param content    文本内容
     * @param width      宽度
     * @param height     高度
     * @param targetFile 目标文件，扩展名决定输出格式
     * @return 目标文件
     */
    public static File generate(String content, int width, int height, File targetFile) {
        final BufferedImage image = generate(content, width, height);
        ImgUtil.write(image, targetFile);
        return targetFile;
    }

    /**
     * 生成二维码到输出流
     *
     * @param content   文本内容
     * @param width     宽度
     * @param height    高度
     * @param imageType 图片类型（图片扩展名），见{@link ImgUtil}
     * @param out       目标流
     */
    public static void generate(String content, int width, int height, String imageType, OutputStream out) {
        final BufferedImage image = generate(content, width, height);
        ImgUtil.write(image, imageType, out);
    }

    /**
     * 生成二维码图片
     *
     * @param content 文本内容
     * @param width   宽度
     * @param height  高度
     * @return 二维码图片（黑白）
     */
    public static BufferedImage generate(String content, int width, int height) {
        return generate(content, new QrConfig(width, height));
    }

    /**
     * 生成二维码图片
     *
     * @param content 文本内容
     * @param config  二维码配置，包括长、宽、边距、颜色等
     * @return 二维码图片（黑白）
     * @since 4.1.2
     */
    public static BufferedImage generate(String content, QrConfig config) {
        return generate(content, BarcodeFormat.QR_CODE, config);
    }

    /**
     * 生成二维码或条形码图片<br>
     * 只有二维码时QrConfig中的图片才有效
     *
     * @param content 文本内容
     * @param format  格式，可选二维码、条形码等
     * @param config  二维码配置，包括长、宽、边距、颜色等
     * @return 二维码图片（黑白）
     * @since 4.1.14
     */
    public static BufferedImage generate(String content, BarcodeFormat format, QrConfig config) {
        final BitMatrix bitMatrix = encode(content, format, config);
        final BufferedImage image = toImage(bitMatrix, config.foreColor, config.backColor);
        final Image logoImg = config.img;
        if (null != logoImg && BarcodeFormat.QR_CODE == format) {
            // 只有二维码可以贴图
            final int qrWidth = image.getWidth();
            final int qrHeight = image.getHeight();
            int width;
            int height;
            // 按照最短的边做比例缩放
            if (qrWidth < qrHeight) {
                width = qrWidth / config.ratio;
                height = logoImg.getHeight(null) * width / logoImg.getWidth(null);
            } else {
                height = qrHeight / config.ratio;
                width = logoImg.getWidth(null) * height / logoImg.getHeight(null);
            }

            Img.from(image).pressImage(
                // 圆角
                Img.from(logoImg).round(0.3).getImg(),
                new Rectangle(width, height),
                1
            );
        }
        return image;
    }

    /**
     * 将文本内容编码为条形码或二维码
     *
     * @param content 文本内容
     * @param format  格式枚举
     * @param config  二维码配置，包括长、宽、边距、颜色等
     * @return {@link BitMatrix}
     * @since 4.1.2
     */
    public static BitMatrix encode(String content, BarcodeFormat format, QrConfig config) {
        final MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        if (null == config) {
            // 默认配置
            config = new QrConfig();
        }
        BitMatrix bitMatrix;
        try {
            bitMatrix = multiFormatWriter.encode(content, format, config.width, config.height, config.toHints());
        } catch (WriterException e) {
            throw new IllegalStateException(e);
        }

        return bitMatrix;
    }

    /**
     * BitMatrix转BufferedImage
     *
     * @param matrix    BitMatrix
     * @param foreColor 前景色
     * @param backColor 背景色(null表示透明背景)
     * @return BufferedImage
     * @since 4.1.2
     */
    public static BufferedImage toImage(BitMatrix matrix, int foreColor, Integer backColor) {
        final int width = matrix.getWidth();
        final int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, null == backColor ? BufferedImage.TYPE_INT_ARGB : BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (matrix.get(x, y)) {
                    image.setRGB(x, y, foreColor);
                } else if (null != backColor) {
                    image.setRGB(x, y, backColor);
                }
            }
        }
        return image;
    }

    /**
     * 二维码设置
     *
     * @author looly
     * @since 4.1.2
     */
    public static class QrConfig {

        private static final int BLACK = 0xFF000000;
        private static final int WHITE = 0xFFFFFFFF;

        /**
         * 宽
         */
        protected int width;
        /**
         * 长
         */
        protected int height;
        /**
         * 前景色（二维码颜色）
         */
        protected int foreColor = BLACK;
        /**
         * 背景色，默认白色，null表示透明
         */
        protected Integer backColor = WHITE;
        /**
         * 边距1~4
         */
        protected Integer margin = 2;
        /**
         * 设置二维码中的信息量，可设置1-40的整数
         */
        protected Integer qrVersion;
        /**
         * 纠错级别
         */
        protected ErrorCorrectionLevel errorCorrection = ErrorCorrectionLevel.M;
        /**
         * 编码
         */
        protected Charset charset = CharsetUtil.CHARSET_UTF_8;
        /**
         * 二维码中的Logo
         */
        protected Image img;
        /**
         * 二维码中的Logo缩放的比例系数，如5表示长宽最小值的1/5
         */
        protected int ratio = 6;

        /**
         * 构造，默认长宽为300
         */
        public QrConfig() {
            this(300, 300);
        }

        /**
         * 构造
         *
         * @param width  宽
         * @param height 长
         */
        public QrConfig(int width, int height) {
            this.width = width;
            this.height = height;
        }

        /**
         * 转换为Zxing的二维码配置
         *
         * @return 配置
         */
        public HashMap<EncodeHintType, Object> toHints() {
            // 配置
            final HashMap<EncodeHintType, Object> hints = new HashMap<>(7);
            if (null != this.charset) {
                hints.put(EncodeHintType.CHARACTER_SET, charset.toString().toLowerCase());
            }
            if (null != this.errorCorrection) {
                hints.put(EncodeHintType.ERROR_CORRECTION, this.errorCorrection);
            }
            if (null != this.margin) {
                hints.put(EncodeHintType.MARGIN, this.margin);
            }
            if (null != this.qrVersion) {
                hints.put(EncodeHintType.QR_VERSION, this.qrVersion);
            }
            return hints;
        }
    }
}

