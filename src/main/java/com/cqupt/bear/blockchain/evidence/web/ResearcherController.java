package com.cqupt.bear.blockchain.evidence.web;

import com.cqupt.bear.blockchain.evidence.model.Evidence;
import com.cqupt.bear.blockchain.evidence.service.EvidenceService;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author Y.bear
 * @version 创建时间：2018年11月30日 下午7:25:09
 * 类说明
 */
@Controller
@RequestMapping("/researcher")
public class ResearcherController {
    @Autowired
    EvidenceService evidenceService;
    private Logger logger = LoggerFactory.getLogger(getClass());
    private String key;
    private String name;
    private String researcher;

    @PostMapping("/acquireKey")
    public ModelAndView acquireSecretKey(String contractAddress, ModelAndView modelAndView) {
        acquireObjects(contractAddress);
        modelAndView.addObject("key", key);
        modelAndView.addObject("name", name);
        modelAndView.addObject("contractAddress", contractAddress);
        modelAndView.addObject("researcher", researcher);
        modelAndView.setViewName("researcher/returnKey");
        return modelAndView;
    }

    private void acquireObjects(String contractAddress) {
        List<Evidence.AnalyzingEventResponse> analyzingEventResponses =
                null;
        analyzingEventResponses = evidenceService.acquireSecretKey(contractAddress);
        analyzingEventResponses.forEach((response) -> {
            key = response.key;
            name = response.name;
            researcher = response.user;
        });
    }

    @PostMapping("/uploadResult")
    public ModelAndView uploadResult(String result, String secretKey, String contractAddress, ModelAndView modelAndView) {
        evidenceService.uploadAnalysisResult(contractAddress, result, secretKey);
        modelAndView.setViewName("researcher/uploadSuccess");
        return modelAndView;
    }


    @PostMapping("/acquireEvidence")
    public ResponseEntity<byte[]> acquireEvidence(String contractAddress, String password,
                                                  HttpServletResponse response) throws Exception {
        if (!password.equals(key)) {
            response.sendRedirect("/researcher/acquireEvidencePage?error=true");
        }
        String folder =
                System.getProperty("user.dir") + System.getProperty("file.separator") + "evidences" + System.getProperty("file.separator") + contractAddress;
        File file = new File(folder);
        File[] files = file.listFiles();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment",
                new String(files[0].getName().getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(files[0]),
                headers, HttpStatus.CREATED);
    }
}
