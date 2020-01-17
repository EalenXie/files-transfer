package name.ealen.global.cons;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author EalenXie Created on 2020/1/2 9:41.
 * 文件 常量池
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileConst {


    /**
     * 正则 图片文件名后缀
     */
    public static final String PICTURE_REG = ".+(.JPEG|.JPG|.GIF|.BMP|.PNG|.TIF)$";

    /**
     * 正则 音乐文件名后缀
     */
    public static final String MUSIC_REG = ".+(.MP3|.MIDI|.WMA|.FLAC|.CDA)$";

    /**
     * 正则 视频文件名后缀
     */
    public static final String VEDIO_REG = ".+(.MPG|.MPEG|.MOV|.MOD|.MKV|.WMV|.VOB|.3GP|.MP4|.AVI|.ASF|.FLV|.RM|.RA|.RAM|.RMVB)$";

    /**
     * 正则 常见Office文档格式文件名后缀(包括文档，文本)
     */
    public static final String DOC_REG = ".+(.DOC|.DOCX|.DOCM|.DOTM|.DOTX|.XLS|.XLSX|.XLSM|.XLTX|.XLTM|.XLSB|.XLAM|.PPT|.PPTX|.PPTM|.PPSX|.PPSM|.POTX|.POTM|.PPAM|.PDF|.WPS|.DOT|.RTF)$";

    /**
     * 常见文本及数据格式
     */
    public static final String DATA_REG = ".+(.TXT|.CSV|.JSON|.XML|.YML|.YAML)$";

    /**
     * 正则 常见压缩格式文件名后缀
     */
    public static final String ZIP_REG = ".+(.RAR|.GZ|.ARJ|.ZIP|.7Z|.ACE|.GZIP|.CAB|.TGZ|.BZ2|.UUE|.LZH|.XZ|.TZ|.TAR)$";


}
