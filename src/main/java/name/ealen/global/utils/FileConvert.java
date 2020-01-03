package name.ealen.global.utils;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author EalenXie Created on 2019/10/11 11:33.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class FileConvert {
    private static DecimalFormat df;

    static {
        // 设置数字格式，保留一位有效小数
        df = new DecimalFormat("#0.00");
        //四舍五入
        df.setRoundingMode(RoundingMode.HALF_UP);
        //设置数字的分数部分中允许的最小位数。
        df.setMinimumFractionDigits(2);
        //设置数字的分数部分中允许的最大位数。
        df.setMaximumFractionDigits(2);
    }

    /**
     * 简单的http文件上传
     *
     * @param file   上传的文件请求对象
     * @param target 上传后的文件对象
     * @return 上传成功后文件对象
     */
    public static File simpleUpload(MultipartFile file, File target) throws IOException {
        //1. 文件传输
        file.transferTo(target);
        //2. 返回传输成功后的文件对象
        return target;
    }

    /**
     * 简单的http文件下载
     *
     * @param file 下载的文件对象
     */
    public static void simpleDownload(File file) throws IOException {
        try (FileInputStream fis = FileUtils.openInputStream(file); ReadableByteChannel readChannel = Channels.newChannel(fis)) {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes != null && attributes.getResponse() != null) {
                HttpServletResponse response = attributes.getResponse();
                // 设置编码
                response.setCharacterEncoding("UTF-8");
                // 设置文件格式(二进制流)
                response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
                // 下载方式以附件形式, 并设置默认名
                response.addHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + URLEncoder.encode(file.getName(), "UTF-8"));
                // 下载文件大小
                response.setHeader("download-size", String.valueOf(file.length()));
                // 下载文件大小标准格式
                response.setHeader("download-size-format", FileConvert.getFormatFileSize(file.length()));
                // 获取响应的输出流
                OutputStream stream = response.getOutputStream();
                try (WritableByteChannel writeChannel = Channels.newChannel(stream)) {
                    //设置缓存区
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    while (true) {
                        buffer.clear();
                        int len = readChannel.read(buffer);//读入数据
                        if (len < 0) {
                            break;//传输结束
                        }
                        buffer.flip();
                        writeChannel.write(buffer);//写入数据
                    }
                }
                stream.close();
            }
        }


    }

    /**
     * 根据文件长度得到文件大小(比如 : 1.81GB,1.83MB)
     *
     * @param length 文件长度
     * @return 返回一个表示文件大小的字符串 比如 1.51G、1.82MB
     */
    public static String getFormatFileSize(long length) {
        double size = ((double) length) / (1 << 30);
        if (size >= 1) return df.format(size) + "GB";
        size = ((double) length) / (1 << 20);
        if (size >= 1) return df.format(size) + "MB";
        size = ((double) length) / (1 << 10);
        if (size >= 1) return df.format(size) + "KB";
        return length + "B";
    }


    /**
     * 获取文件后缀 如果是文件夹 则后缀为null
     *
     * @param file 文件对象
     * @return 返回文件后缀
     */
    public static String getSuffixByFile(File file) throws IOException {
        if (file.exists()) {
            if (file.isDirectory()) return null;
            return getLastOfSymbolSuffix(file.getPath(), '.');
        } else {
            throw new FileNotFoundException();
        }
    }

    /**
     * 获取文件前缀 如果是文件夹 则前缀为文件夹名字
     *
     * @param file 文件对象
     * @return 返回文件前缀
     */
    public static String getPrefixByFile(File file) throws IOException {
        if (file.exists()) {
            if (file.isDirectory()) return null;
            return getLastOfSymbolPrefix(file.getPath(), '.');
        } else {
            throw new FileNotFoundException();
        }
    }

    /**
     * 获取路径最后一个符号后的字符串内容 比如 方法传入参数(abc.123,'.')  则返回 .123
     *
     * @param path   字符串
     * @param symbol 符号
     * @return 最后一个符号后的字符串内容
     */
    public static String getLastOfSymbolSuffix(@NonNull String path, @NonNull Character symbol) {
        if (path.contains(symbol.toString())) return path.substring(path.lastIndexOf(symbol));
        else return "";
    }

    /**
     * 获取路径最后一个符号前的字符串内容 比如 方法传入参数(abc.123,'.')  则返回 abc
     *
     * @param path   字符串
     * @param symbol 符号
     * @return 最后一个符号前的字符串内容
     */
    public static String getLastOfSymbolPrefix(@NonNull String path, @NonNull Character symbol) {
        if (path.contains(symbol.toString())) return path.substring(0, path.lastIndexOf(symbol));
        else return path;
    }


    public static void toZip(String srcDir, OutputStream out) {
        toZip(Collections.singletonList(new File(srcDir)), out);
    }


    /**
     * 压缩成ZIP
     *
     * @param srcFiles 需要压缩的文件列表
     * @param out      压缩文件输出流
     */

    public static void toZip(List<File> srcFiles, OutputStream out) {
        long start = System.currentTimeMillis();
        try (ZipOutputStream zos = new ZipOutputStream(out)) {
            for (File srcFile : srcFiles) {
                compress(srcFile, zos, srcFile.getName());
            }
            log.info("压缩完成，耗时：{}ms", System.currentTimeMillis() - start);
        } catch (IOException e) {
            log.error("printStackTrace", e);
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


    public static void writeObjectToFile(Object target, File file) {
        try (FileOutputStream fos = new FileOutputStream(file); ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(target);
        } catch (IOException e) {
            log.warn("writeObjectToFile error : {}", e.getMessage());
        }
    }


}
