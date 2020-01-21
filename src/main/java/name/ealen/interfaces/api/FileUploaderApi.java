package name.ealen.interfaces.api;

import name.ealen.domain.svc.FileTransfer;
import name.ealen.global.cons.ApiUrlConst;
import name.ealen.interfaces.qry.ChunkUploadQry;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
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

    @PostMapping(ApiUrlConst.CHUNK_UPLOAD)
    public ResponseEntity chunkUpload(@RequestParam("md5") String md5, @RequestParam("chunkMd5") String chunkMd5,
                                      @RequestParam("chunkSeq") Integer chunkSeq, @RequestParam("chunkSize") Long chunkSize,
                                      @RequestParam(value = "path", required = false) String path,
                                      @RequestParam("file") MultipartFile file) throws IOException {
        File upload = fileTransfer.chunkUpload(new ChunkUploadQry(md5, chunkMd5, chunkSeq, chunkSize, path, file));
        if (upload.length() == file.getSize()) return ResponseEntity.ok("上传成功");
        else return ResponseEntity.ok("上传失败");
    }


}
