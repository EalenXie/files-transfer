package name.ealen.infra.utils;

import name.ealen.global.utils.FileConvert;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class FileConvertTest {
    @Test
    void getFormatFileSize() {
        System.out.println(FileUtils.byteCountToDisplaySize(1024 * 1024 + 10 * 1024));
        System.out.println(FileConvert.getFormatFileSize(1024 * 1024 + 10 * 1024));
        assertEquals("1.01MB", FileConvert.getFormatFileSize(1024 * 1024 + 10 * 1024));
    }

    @Test
    void getLastOfSymbolSuffix() {
        assertEquals(".123",FileConvert.getLastOfSymbolSuffix("abc.123",'.'));
        assertEquals("#.123",FileConvert.getLastOfSymbolSuffix("张三+1#AABB#.123",'#'));
    }

    @Test
    void getLastOfSymbolPrefix() {
        assertEquals("abc",FileConvert.getLastOfSymbolPrefix("abc.123",'.'));
        assertEquals("张三+1#AABB",FileConvert.getLastOfSymbolPrefix("张三+1#AABB#.123",'#'));
    }
}