package name.ealen.domain.api;

import name.ealen.domain.cons.ApiConst;
import name.ealen.domain.service.FileTransfer;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping(ApiConst.SIMPLE_UPLOAD)
    public ResponseEntity simpleUpload(@RequestParam("file") MultipartFile file) throws IOException {
        if (StringUtils.isEmpty(file.getOriginalFilename())) return ResponseEntity.badRequest().body("必须包含文件名");
        fileTransfer.simpleUpload(file);
        return ResponseEntity.ok("上传成功");
    }


    /**
     * 本例子以文件名 来定位要下载的文件对象
     */
    @PostMapping(value = ApiConst.SIMPLE_DOWNLOAD)
    public void simpleDownload(@RequestParam("fileName") String fileName) throws IOException {
        fileTransfer.simpleDownload(fileName);
    }

}
