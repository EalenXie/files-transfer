package name.ealen.global.cons;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author EalenXie Created on 2019/12/31 13:56.
 * 接口 地址 常量池
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiUrlConst {


    /**
     * 简单的文件上传接口
     */
    public static final String SIMPLE_UPLOAD = "/simple/upload";

    /**
     * 简单的文件下载接口
     */
    public static final String SIMPLE_DOWNLOAD = "/simple/download";

    /**
     * 文件分块上传
     */
    public static final String CHUNK_UPLOAD = "/chunk/upload";

    /**
     * 分块合并 完成上传
     */
    public static final String CHUNK_MERGE = "/chunk/merge";
}
