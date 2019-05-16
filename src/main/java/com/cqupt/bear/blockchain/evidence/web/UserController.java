package com.cqupt.bear.blockchain.evidence.web;

import com.cqupt.bear.blockchain.common.utils.JsonUtils;
import com.cqupt.bear.blockchain.evidence.dto.BlockchainTransaction;
import com.cqupt.bear.blockchain.evidence.dto.EvidenceInfo;
import com.cqupt.bear.blockchain.evidence.model.Evidence;
import com.cqupt.bear.blockchain.evidence.service.BlockService;
import com.cqupt.bear.blockchain.evidence.service.EvidenceService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author Y.bear
 * @version 创建时间：2018年11月30日 下午7:21:57 类说明
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    EvidenceService evidenceService;
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    @Qualifier("web3jBlockServiceImpl")
    private BlockService service;

    //  @PostMapping("/file")
    public ModelAndView upload(MultipartFile evidence, ModelAndView modelAndView, String identityCard, String telephone,
                               String email) throws IllegalStateException, IOException {
        String originalFilename = evidence.getOriginalFilename();
        String md5 = DigestUtils.md5DigestAsHex(evidence.getBytes());
        String folder = System.getProperty("user.dir") + "\\evidences";
        File localFile = new File(folder, originalFilename);
        evidence.transferTo(localFile);
        BlockchainTransaction transaction = new BlockchainTransaction();
        transaction.setData(md5);
        transaction.setFromId(0);
        transaction.setToId(0);
        try {
            transaction = executeData(transaction);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        logger.info(transaction.getId());
        EvidenceInfo evidenceInfo = new EvidenceInfo(md5, "0001", transaction.getId(), "0005", transaction);
        logger.info("文件：\"" + originalFilename + "\"成功写入，" + "相关信息为：" + evidenceInfo.toString());
        String json = JsonUtils.toJson(evidenceInfo);
        logger.info(json);
        modelAndView.addAllObjects(JsonUtils.toMap(json));
        modelAndView.setViewName("user/uploadSuccess");
        return modelAndView;
    }

    public BlockchainTransaction executeData(@RequestBody BlockchainTransaction transaction) throws Exception {
        return service.upload(transaction);
    }

    @GetMapping("/query")
    public ModelAndView queryBlock(String txHash, ModelAndView modelAndView) throws JsonProcessingException {
        EvidenceInfo info = null;
        if (txHash == null) {
            modelAndView.setViewName("/user/queryPage");
            return modelAndView;
        }
        if (txHash.startsWith("\"")) {
            txHash = txHash.substring(1, txHash.length() - 1);
        }
        logger.info("查询txHash:" + txHash);
        try {
            info = service.query(txHash);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (info != null) {
            String evidenceInfo = JsonUtils.toJson(info);
            modelAndView.addAllObjects(JsonUtils.toMap(evidenceInfo));
            modelAndView.setViewName("user/queryResult");
        }
        return modelAndView;
    }

    @PostMapping("/query")
    public ModelAndView query(String txHash, ModelAndView modelAndView) throws JsonProcessingException {
        if (txHash == null) {
            modelAndView.setViewName("/user/queryPage");
            return modelAndView;
        }
        ModelAndView result = queryBlock(txHash, modelAndView);
        return result;
    }


    @PostMapping("/file")
    public ModelAndView deployEvidenceContract(MultipartFile evidence, String secretKey, ModelAndView modelAndView) throws Exception {
        String hash = DigestUtils.md5DigestAsHex(evidence.getBytes()).toUpperCase();
        Evidence result = evidenceService.deployContract("123456", evidence.getOriginalFilename(), hash, secretKey);
        System.out.println(result.getContractAddress());
        modelAndView.addObject("md5", hash);
        modelAndView.addObject("ContractAddress", result.getContractAddress());
        modelAndView.addObject("evidenceName", "证据'"+evidence.getOriginalFilename()+"'上传成功！");
        modelAndView.setViewName("user/uploadSuccess");
        return modelAndView;
    }

    @ResponseBody
    @PostMapping("/evidenceInfo")
    public String getEvidenceInfo(String contractAddress) {
        String info = null;
        try {
            info = evidenceService.queryEvidenceInfo(contractAddress);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "当前区块保存的数据指纹信息为：" + info;
    }

    @ResponseBody
    @GetMapping("/getResult")
    public List<Evidence.AcquireResultEventResponse> getEvidenceResult(@RequestParam String contractAddress) {
        List<Evidence.AcquireResultEventResponse> acquireResultEventResponses =
                null;

        acquireResultEventResponses = evidenceService.acquireAnalysisResult(contractAddress);

        return acquireResultEventResponses;
    }

    @ResponseBody
    @GetMapping("/approve")
    public List<Evidence.OwnerApprovedEventResponse> approved(@RequestParam String contractAddress) {
        List<Evidence.OwnerApprovedEventResponse> approved = null;

        approved = evidenceService.approved(contractAddress);

        return approved;
    }

    @ResponseBody
    @GetMapping("/reset")
    public String reset(@RequestParam String contractAddress, @RequestParam String key) {

        evidenceService.resetKey(contractAddress, key);

        return "重置成功";
    }
}
