package name.ealen.interfaces.qry;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author EalenXie Created on 2020/1/17 11:56.
 * 分块上传请求
 */
@Data
public class ChunkUploadQry {
    /**
     * 文件MD5
     */
    private String md5;
    /**
     * 当前分块MD5
     */
    private String chunkMd5;
    /**
     * 当前分块序号
     */
    private Integer chunkSeq;
    /**
     * 当前分块大小
     */
    private Long chunkSize;

    /**
     * 当前分块二进制文件上传对象
     */
    private MultipartFile file;

}
