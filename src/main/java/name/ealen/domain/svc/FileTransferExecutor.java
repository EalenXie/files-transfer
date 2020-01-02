package name.ealen.domain.svc;

import name.ealen.infra.conf.FilesTransferConfig;
import name.ealen.global.utils.FileConvert;
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

    @Override
    public File simpleUpload(MultipartFile file) throws IOException {
        //1. 定位要上传的路径 构建上传对象
        String fileName = file.getName();
        if (StringUtils.isNotEmpty(file.getOriginalFilename())) {
            fileName = file.getOriginalFilename();
        }
        File res = new File(filesTransferConfig.getUploadUrl(), fileName);
        //2. 文件上传
        return FileConvert.simpleUpload(file, res);
    }

    @Override
    public void simpleDownload(String fileName) throws IOException {
        //1. 定位要下载的文件对象
        File file = new File(filesTransferConfig.getUploadUrl(), fileName);
        //2. 文件下载
        FileConvert.simpleDownload(file);
    }


}
