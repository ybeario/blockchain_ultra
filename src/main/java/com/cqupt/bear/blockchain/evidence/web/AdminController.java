package com.cqupt.bear.blockchain.evidence.web;

import com.cqupt.bear.blockchain.evidence.dto.GivenRightResult;
import com.cqupt.bear.blockchain.evidence.service.EvidenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Y.bear
 * @version 创建时间：2018年11月30日 下午7:22:08
 * 类说明
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    EvidenceService evidenceService;
    private Logger logger = LoggerFactory.getLogger(getClass());

    // @ResponseBody
    @PostMapping("/giveRightTo")
    public ModelAndView giveRightTo(String contractAddress, String researcherAccount, ModelAndView modelAndView) {
        if (!researcherAccount.equals("researcher")) {
            modelAndView.addObject("error", "被授权账户异常");
            modelAndView.setViewName("admin/rightsManagement");
        } else {
            GivenRightResult result = evidenceService.giveRightToResearcher(contractAddress);
            modelAndView.addObject("admin", result.getAdmin());
            modelAndView.addObject("researcher", result.getResearcher());
            modelAndView.addObject("contractAddress", result.getContractAddress());
            modelAndView.setViewName("admin/authorizationSuccess");
        }
        return modelAndView;
    }
}
