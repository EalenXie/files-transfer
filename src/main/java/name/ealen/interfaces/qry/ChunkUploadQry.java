package name.ealen.interfaces.qry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author EalenXie Created on 2020/1/17 11:56.
 * 分块上传请求
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChunkUploadQry {
    /**
     * 文件MD5
     */
    @NotEmpty
    private String md5;
    /**
     * 当前分块MD5
     */
    @NotEmpty
    private String chunkMd5;
    /**
     * 当前分块序号
     */
    @NotEmpty
    private Integer chunkSeq;
    /**
     * 当前分块大小
     */
    private Long chunkSize;
    /**
     * 上传的本地文件路径
     */
    @NotEmpty
    private String path;
    /**
     * 当前分块二进制文件上传对象
     */
    @NotNull
    private MultipartFile file;

}
