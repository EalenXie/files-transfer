package com.github.interfaces.qry;


import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author EalenXie Created on 2020/1/17 11:56.
 * 分块上传请求
 */
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

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getChunkMd5() {
        return chunkMd5;
    }

    public void setChunkMd5(String chunkMd5) {
        this.chunkMd5 = chunkMd5;
    }

    public Integer getChunkSeq() {
        return chunkSeq;
    }

    public void setChunkSeq(Integer chunkSeq) {
        this.chunkSeq = chunkSeq;
    }

    public Long getChunkSize() {
        return chunkSize;
    }

    public void setChunkSize(Long chunkSize) {
        this.chunkSize = chunkSize;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
