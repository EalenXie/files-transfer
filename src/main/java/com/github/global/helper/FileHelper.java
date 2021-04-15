package com.github.global.helper;


import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 文件处理工具类
 */
public class FileHelper {

    private FileHelper() {

    }

    private static final Logger log = LoggerFactory.getLogger(FileHelper.class);
    private static final DecimalFormat DF;

    /**
     * 1 KB = 1024 B
     */
    private static final double KB = 1024;
    /**
     * 1 MB = 1024 KB
     */
    private static final double MB = KB * KB;
    /**
     * 1 GB = 1024 MB
     */
    private static final double GB = KB * MB;
    /**
     * 1 TB = 1024 GB
     */
    private static final double TB = KB * GB;
    /**
     * 正则 图片文件名后缀
     */
    public static final String PICTURE_REG = ".+(.JPEG|.JPG|.GIF|.BMP|.PNG|.TIF)$";
    /**
     * 正则 音乐文件名后缀
     */
    public static final String MUSIC_REG = ".+(.MP3|.MIDI|.WMA|.FLAC|.CDA)$";
    /**
     * 正则 视频文件名后缀
     */
    public static final String VEDIO_REG = ".+(.MPG|.MPEG|.MOV|.MOD|.MKV|.WMV|.VOB|.3GP|.MP4|.AVI|.ASF|.FLV|.RM|.RA|.RAM|.RMVB)$";
    /**
     * 正则 常见Office文档格式文件名后缀(包括文档，文本)
     */
    public static final String DOC_REG = ".+(.DOC|.DOCX|.DOCM|.DOTM|.DOTX|.XLS|.XLSX|.XLSM|.XLTX|.XLTM|.XLSB|.XLAM|.PPT|.PPTX|.PPTM|.PPSX|.PPSM|.POTX|.POTM|.PPAM|.PDF|.WPS|.DOT|.RTF)$";
    /**
     * 常见文本及数据格式
     */
    public static final String DATA_REG = ".+(.TXT|.CSV|.JSON|.XML|.YML|.YAML)$";
    /**
     * 正则 常见压缩格式文件名后缀
     */
    public static final String ZIP_REG = ".+(.RAR|.GZ|.ARJ|.ZIP|.7Z|.ACE|.GZIP|.CAB|.TGZ|.BZ2|.UUE|.LZH|.XZ|.TZ|.TAR)$";

    static {
        // 设置数字格式，保留一位有效小数
        DF = new DecimalFormat("#0.00");
        //四舍五入
        DF.setRoundingMode(RoundingMode.HALF_UP);
        //设置数字的分数部分中允许的最小位数。
        DF.setMinimumFractionDigits(2);
        //设置数字的分数部分中允许的最大位数。
        DF.setMaximumFractionDigits(2);
    }



    /**
     * 简单的http文件下载
     *
     * @param file 下载的文件对象
     */
    public static void simpleDownload(File file) throws IOException {
        try (FileInputStream fis = FileUtils.openInputStream(file); ReadableByteChannel readChannel = Channels.newChannel(fis)) {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                HttpServletResponse response = attributes.getResponse();
                if (response != null) {
                    // 设置编码
                    response.setCharacterEncoding(StandardCharsets.UTF_8.name());
                    // 设置文件格式(二进制流)
                    response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
                    // 下载方式以附件形式, 并设置默认名
                    response.addHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + URLEncoder.encode(file.getName(), StandardCharsets.UTF_8.name()));
                    // 下载文件大小
                    response.setHeader("download-size", String.valueOf(file.length()));
                    // 下载文件大小标准格式
                    response.setHeader("download-size-format", FileHelper.getFormatSize(file.length()));
                    // 获取响应的输出流
                    OutputStream stream = response.getOutputStream();
                    try (WritableByteChannel writeChannel = Channels.newChannel(stream)) {
                        //设置缓存区
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        while (true) {
                            buffer.clear();
                            //读入数据
                            int len = readChannel.read(buffer);
                            if (len < 0) {
                                //传输结束
                                break;
                            }
                            buffer.flip();
                            //写入数据
                            writeChannel.write(buffer);
                        }
                    }
                    stream.close();
                }
            }
        }
    }

    /**
     * 根据长度得到格式大小(比如 : 1.81GB,1.83MB)
     *
     * @param length 文件长度
     * @return 返回一个表示文件大小的字符串 比如 1.51G、1.82MB
     */
    public static String getFormatSize(long length) {
        if (length < KB) return length + "B";
        if (length < MB) return DF.format(length / KB) + "KB";
        if (length < GB) return DF.format(length / MB) + "MB";
        if (length < TB) return DF.format(length / GB) + "GB";
        return DF.format(length / TB) + "TB";
    }

    /**
     * 计算文件的MD5
     */
    public static String getFileMD5(File file) throws IOException {
        return DigestUtils.md5Hex(new FileInputStream(file)).toUpperCase();
    }

    /**
     * 获取文件后缀 如果是文件夹 则后缀为null
     *
     * @return 文件后缀
     */
    public static String getFileSuffix(File file) throws FileNotFoundException {
        if (!file.exists()) throw new FileNotFoundException();
        if (file.isDirectory()) return null;
        return file.getPath().substring(file.getPath().lastIndexOf("."));
    }

    /**
     * 获取文件前缀 如果是文件夹 则前缀为文件名
     *
     * @return 文件后缀
     */
    public static String getFilePrefix(File file) throws FileNotFoundException {
        if (!file.exists()) throw new FileNotFoundException();
        if (file.isDirectory()) return file.getName();
        return file.getPath().substring(0, file.getPath().lastIndexOf("."));
    }


    /**
     * 压缩成 Zip
     *
     * @param src 需要压缩的文件
     * @param out 压缩文件输出流
     */
    public static void toZip(File src, OutputStream out) throws IOException {
        toZip(Collections.singletonList(src), out);
    }

    /**
     * 压缩成ZIP
     *
     * @param srcFiles 需要压缩的文件列表
     * @param out      压缩文件输出流
     */
    public static void toZip(List<File> srcFiles, OutputStream out) throws IOException {
        long start = System.currentTimeMillis();
        try (ZipOutputStream zos = new ZipOutputStream(out)) {
            for (File srcFile : srcFiles) {
                compress(srcFile, zos, srcFile.getName());
            }
            log.info("压缩完成，耗时：{}ms", System.currentTimeMillis() - start);
        }
    }

    /**
     * 递归压缩方法
     *
     * @param sourceFile 源文件
     * @param zos        zip输出流
     * @param name       压缩后的名称
     */
    private static void compress(File sourceFile, ZipOutputStream zos, String name) throws IOException {
        byte[] buf = new byte[5 * 1024 * 1024];
        if (sourceFile.isFile()) {
            zos.putNextEntry(new ZipEntry(name));
            int len;
            try (FileInputStream in = new FileInputStream(sourceFile)) {
                while ((len = in.read(buf)) != -1) {
                    zos.write(buf, 0, len);
                }
                zos.closeEntry();
            }
        } else {
            File[] listFiles = sourceFile.listFiles();
            if (listFiles == null || listFiles.length == 0) {
                zos.putNextEntry(new ZipEntry(name + "/"));
                zos.closeEntry();
            } else {
                for (File file : listFiles) {
                    compress(file, zos, name + "/" + file.getName());
                }
            }
        }
    }

}
