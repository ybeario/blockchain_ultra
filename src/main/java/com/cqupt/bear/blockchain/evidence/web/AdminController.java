package com.cqupt.bear.blockchain.evidence.web;

import com.cqupt.bear.blockchain.evidence.service.EvidenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Y.bear
 * @version 创建时间：2018年11月30日 下午7:22:08
 * 类说明
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    EvidenceService evidenceService;

    @ResponseBody
    @GetMapping("/giveRightTo")
    public String giveRightTo(@RequestParam String contractAddress) {
        String message = null;

            message = evidenceService.giveRightToResearcher(contractAddress);

        return message;
    }
}
