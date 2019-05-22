package com.cqupt.bear.blockchain.evidence.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;

/**
 * @author Y.bear
 * @version 创建时间：2018年11月30日 下午7:22:27
 * 类说明
 */
@Controller
public class PublicController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 预览pdf文件
     */
    @GetMapping(value = "/preview")
    public void pdfStreamHandler(HttpServletResponse response, String fileName) {
        logger.info(fileName);
        String path = System.getProperty("user.dir") + System.getProperty("file.separator") + "solidity.pdf";
        File file = new File(path);
        if (file.exists()) {
            byte[] data = null;
            try {
                FileInputStream input = new FileInputStream(file);
                data = new byte[input.available()];
                input.read(data);
                response.getOutputStream().write(data);
                input.close();
            } catch (Exception e) {
                logger.error("pdf文件处理异常：" + e.getMessage());
            }
        } else {
            return;
        }
    }
}
