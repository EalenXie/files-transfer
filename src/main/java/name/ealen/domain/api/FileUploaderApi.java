package name.ealen.domain.api;

import name.ealen.domain.svc.FileTransfer;
import name.ealen.global.cons.ApiUrlConst;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @author EalenXie Created on 2019/12/31 13:35.
 */
@RestController
public class FileUploaderApi {

    @Resource
    private FileTransfer fileTransfer;

    @PostMapping(ApiUrlConst.SIMPLE_UPLOAD)
    public ResponseEntity simpleUpload(@RequestParam("file") MultipartFile file) throws IOException {
        fileTransfer.simpleUpload(file);
        return ResponseEntity.ok("上传成功");
    }

    /**
     * 本例子以文件名 来定位要下载的文件对象
     */
    @PostMapping(ApiUrlConst.SIMPLE_DOWNLOAD)
    public void simpleDownload(@RequestParam("fileName") String fileName) throws IOException {
        fileTransfer.simpleDownload(fileName);
    }

}
