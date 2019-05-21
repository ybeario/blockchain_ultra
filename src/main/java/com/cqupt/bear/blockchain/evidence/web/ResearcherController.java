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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
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

    @ResponseBody
    @PostMapping("/acquireKey")
    public List<Evidence.AnalyzingEventResponse> acquireSecretKey(@RequestParam String contractAddress) {
        List<Evidence.AnalyzingEventResponse> analyzingEventResponses =
                null;

        analyzingEventResponses = evidenceService.acquireSecretKey(contractAddress);

        return analyzingEventResponses;
    }

    @ResponseBody
    @PostMapping("/uploadResult")
    public String uploadResult(String result, String secretKey, String contractAddress) {

        evidenceService.uploadAnalysisResult(contractAddress, result, secretKey);


        return "结果上传成功";
    }

    @PostMapping("/acquireEvidence")
    public ResponseEntity<byte[]> acquireEvidence(String contractAddress) throws Exception {
        String folder =
                System.getProperty("user.dir") + System.getProperty("file.separator") + "evidences" + System.getProperty("file.separator") + contractAddress;
        File file = new File(folder);
        File[] files = file.listFiles();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment",
                new String(files[0].getName().getBytes("UTF-8"), "iso-8859-1"));
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(files[0]),
                headers, HttpStatus.CREATED);
    }
}
