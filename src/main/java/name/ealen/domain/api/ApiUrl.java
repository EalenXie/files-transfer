package name.ealen.domain.api;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author EalenXie Created on 2019/12/31 13:56.
 * 接口 地址 常量池
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
class ApiUrl {


    /**
     * 简单的文件上传接口
     */
    static final String SIMPLE_UPLOAD = "/simple/upload";

    /**
     * 简单的文件下载接口
     */
    static final String SIMPLE_DOWNLOAD = "/simple/download";
}
