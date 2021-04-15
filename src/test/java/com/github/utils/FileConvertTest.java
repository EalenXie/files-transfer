package com.github.utils;

import com.github.global.helper.FileHelper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class FileConvertTest {
    @Test
    void getFormatFileSize() {
        System.out.println(FileHelper.getFormatSize(1024 * 1024 + 10 * 1024));
        System.out.println(FileHelper.getFormatSize(1024 * 1024));
        assertEquals("1.01MB", FileHelper.getFormatSize(1024 * 1024 + 10 * 1024));
    }
}