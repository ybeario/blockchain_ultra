package com.cqupt.bear.blockchain.evidence.web;

import com.cqupt.bear.blockchain.evidence.model.Evidence;
import com.cqupt.bear.blockchain.evidence.service.EvidenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author Y.bear
 * @version 创建时间：2018年11月30日 下午7:25:09
 * 类说明
 */
@Controller
@RequestMapping("/researcher")
public class ResearcherController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    EvidenceService evidenceService;

    @ResponseBody
    @GetMapping("/acquireKey")
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
}
