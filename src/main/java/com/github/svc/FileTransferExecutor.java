package com.github.svc;

import com.github.global.helper.FileHelper;
import com.github.conf.FilesTransferConfig;
import com.github.interfaces.qry.ChunkUploadQry;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

/**
 * @author EalenXie Created on 2019/12/31 14:04.
 */
@Service
public class FileTransferExecutor implements FileTransfer {

    @Resource
    private FilesTransferConfig filesTransferConfig;

    /**
     * 简单的HTTP 文件上传
     * 1. 获取上传的文件名
     * 2. 定位要上传的路径,构建上传对象
     * 3. 文件上传
     *
     * @param file 上传文件对象
     */
    @Override
    public void simpleUpload(MultipartFile file) throws IOException {
        String fileName = StringUtils.isNotEmpty(file.getOriginalFilename()) ? file.getOriginalFilename() : file.getName();
        File res = new File(filesTransferConfig.getUploadUrl(), fileName);
        file.transferTo(res);
    }

    /**
     * 简单的Http文件 下载
     * 1. 定位要下载的文件对象
     * 2. 进行文件下载
     *
     * @param fileName 要下载的文件的文件名
     */
    @Override
    public void simpleDownload(String fileName) throws IOException {
        FileHelper.simpleDownload(new File(filesTransferConfig.getUploadUrl(), fileName));
    }


    /**
     * 分片上传
     */
    @Override
    public File chunkUpload(ChunkUploadQry qry) throws IOException {
        //1. 定位上传父级目录
        File folder = new File(filesTransferConfig.getChunkTempUrl(), qry.getMd5());
        if (!folder.exists()) FileUtils.forceMkdir(folder);
        if (!folder.isDirectory()) throw new IOException("Upload destination is a file, should be a folder");
        //2. 定位分片 分片命名规则 : 文件md5(分片序号).tmp
        File chunkFile = new File(folder, qry.getMd5() + "(" + qry.getChunkSeq() + ").tmp");
        if (!chunkFile.exists()) {
            qry.getFile().transferTo(chunkFile);
        } else {
            MultipartFile uploadFile = qry.getFile();
            //分片续传
            if (chunkFile.length() != uploadFile.getSize() && uploadFile.getSize() > chunkFile.length()) {
                try (RandomAccessFile chunkAccessFile = new RandomAccessFile(chunkFile, "rw"); InputStream input = uploadFile.getInputStream()) {
                    chunkAccessFile.seek(chunkFile.length());
                    long length = input.skip(chunkFile.length());
                    if (length > 0) {
                        byte[] bytes = new byte[1024];
                        int nRead;
                        // 从输入流中读入字节流，然后写到文件中
                        while ((nRead = input.read(bytes)) != -1) {
                            chunkAccessFile.write(bytes, 0, nRead);
                        }
                    }
                }
            } else if (chunkFile.length() < uploadFile.getSize()) {
                FileUtils.deleteQuietly(chunkFile);
                qry.getFile().transferTo(chunkFile);
            }
        }
        return chunkFile;
    }


}
