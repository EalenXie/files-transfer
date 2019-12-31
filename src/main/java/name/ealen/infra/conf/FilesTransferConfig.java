package name.ealen.infra.conf;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author EalenXie Created on 2019/12/31 14:28.
 * 文件传输 服务器配置
 */
@Data
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
}
