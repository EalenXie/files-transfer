package name.ealen.domain.svc;

import name.ealen.global.utils.FileConvert;
import name.ealen.infra.conf.FilesTransferConfig;
import name.ealen.interfaces.qry.ChunkUploadQry;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;

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
     * @return 返回上传成功的文件对象
     */
    @Override
    public File simpleUpload(MultipartFile file) throws IOException {
        String fileName = StringUtils.isNotEmpty(file.getOriginalFilename()) ? file.getOriginalFilename() : file.getName();
        File res = new File(filesTransferConfig.getUploadUrl(), fileName);
        return FileConvert.simpleUpload(file, res);
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
        FileConvert.simpleDownload(new File(filesTransferConfig.getUploadUrl(), fileName));
    }

    @Override
    public void chunkUpload(ChunkUploadQry qry) throws IOException {
        //1. 根据文件分片信息 找到 文件信息
//        File folder=




    }


}
