package com.github.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author EalenXie Created on 2019/12/31 14:28.
 * 文件传输 服务器配置
 */
@Configuration
@ConfigurationProperties(prefix = "files.transfer")
public class FilesTransferConfig {

    /**
     * 地址前缀
     */
    private String urlPrefix;
    /**
     * 上传地址
     */
    private String uploadUrl;

    /**
     * 文件分块临时存放地址
     */
    private String chunkTempUrl;

    public String getUrlPrefix() {
        return urlPrefix;
    }

    public void setUrlPrefix(String urlPrefix) {
        this.urlPrefix = urlPrefix;
    }

    public String getUploadUrl() {
        return uploadUrl;
    }

    public void setUploadUrl(String uploadUrl) {
        this.uploadUrl = uploadUrl;
    }

    public String getChunkTempUrl() {
        return chunkTempUrl;
    }

    public void setChunkTempUrl(String chunkTempUrl) {
        this.chunkTempUrl = chunkTempUrl;
    }
}
