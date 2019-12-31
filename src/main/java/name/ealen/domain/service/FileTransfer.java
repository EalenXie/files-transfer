package name.ealen.domain.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author EalenXie Created on 2019/12/31 13:58.
 * 文件传输服务
 */
public interface FileTransfer {

    /**
     * 简单(传统)文件上传
     *
     * @param file 上传文件对象
     * @return 上传成功后的文件对象
     * @throws IOException 可能会抛出IOException
     */
    public File simpleUpload(MultipartFile file) throws IOException;


    /**
     * 简单(传统) 文件下载
     *
     * @param fileName 要下载的文件的文件名
     * @throws IOException 可能抛出IOException
     */
    public void simpleDownload(String fileName) throws IOException;
}
