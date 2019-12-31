package name.ealen.domain.service;

import name.ealen.infra.conf.FilesTransferConfig;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

/**
 * @author EalenXie Created on 2019/12/31 14:04.
 */
@Service
public class FileTransferExecutor implements FileTransfer {

    @Resource
    private FilesTransferConfig filesTransferConfig;

    @Override
    public File simpleUpload(MultipartFile file) throws IOException {
        //1. 定位要上传的路径 构建上传对象
        String fileName = file.getName();
        if (StringUtils.isNotEmpty(file.getOriginalFilename())) {
            fileName = file.getOriginalFilename();
        }
        File res = new File(filesTransferConfig.getUploadUrl(), fileName);
        //2. 文件传输
        file.transferTo(res);
        //3. 返回传输成功后的文件对象
        return res;
    }

    @Override
    public void simpleDownload(String fileName) throws IOException {
        //1. 定位要下载的文件路径 构建下载文件对象
        File downFile = new File(filesTransferConfig.getUploadUrl(), fileName);
        if (!downFile.exists()) {
            throw new FileNotFoundException("文件对象不存在 , The file target does not exist");
        }
        if (downFile.isDirectory()) {
            throw new IOException("下载的是文件夹,本例子不支持文件夹下载");
        }
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null && attributes.getResponse() != null) {
            HttpServletResponse response = attributes.getResponse();
            // 设置编码
            response.setCharacterEncoding("UTF-8");
            // 设置文件格式(二进制流)
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            // 下载方式以附件形式, 并设置默认名
            response.addHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            // 获取响应的输出流
            OutputStream stream = response.getOutputStream();
            try (FileInputStream fis = new FileInputStream(downFile);
                 //获取读通道
                 ReadableByteChannel readChannel = Channels.newChannel(fis);
                 //获取写通道
                 WritableByteChannel writeChannel = Channels.newChannel(stream)) {
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
