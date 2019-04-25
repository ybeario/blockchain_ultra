package com.cqupt.bear.blockchain.evidence.service;

import com.cqupt.bear.blockchain.evidence.model.Evidence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @author ：Y.Bear
 * @date ：Created in 2019/3/13 9:57
 * @description：证据相关逻辑
 * @modified By：
 * @version: $
 */
@Service
public class EvidenceService {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    WalletService walletService;
    @Autowired
    ContractService contractService;
    private Web3j web3j = Web3j.build(new HttpService());
    @Autowired
    @Qualifier("gasProviderImpl")
    GasProviderImpl gasProvider;

    private Credentials owner;
    private Credentials defaultAdmin;
    private Credentials researcher;


    public Evidence deployContract(String password, String evidenceName, String hash, String secretKey) throws Exception {
        owner = loadWallet(password);
        defaultAdmin = loadWallet(password);
        researcher = loadWallet(password);
        return contractService.deployContract(password, evidenceName, hash, secretKey, owner, defaultAdmin);
    }


    public String queryEvidenceInfo(String contractAddress) throws Exception {
        Credentials user = loadWallet("123456");
        Evidence evidence = loadContract(contractAddress, user);
        String info = null;
        if (evidence.isValid()) {
            info = evidence.queryEvidenceInfo().sendAsync().get();
            logger.info("证据编号: {} 证据指纹: {}", evidence.getContractAddress(), info);
        }
        return info;
    }

    public String giveRightToResearcher(String contractAddress) {
        Evidence evidence = Evidence.load(contractAddress, web3j, defaultAdmin, gasProvider);
        try {
            evidence.giveRightToResearcher(researcher.getAddress()).sendAsync().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            logger.warn("Only admin can call this.");
        }
        logger.info("Admin: {}  授权证据编号 {}  -->  Researcher: {}", defaultAdmin.getAddress(), contractAddress,
                researcher.getAddress());
        return defaultAdmin.getAddress() + "Authorize To" + researcher.getAddress();
    }

    public List<Evidence.AnalyzingEventResponse> acquireSecretKey(String contractAddress) {

        Evidence evidence = Evidence.load(contractAddress, web3j, researcher, gasProvider);
        List<Evidence.AnalyzingEventResponse> analyzingEvents = null;
        try {
            TransactionReceipt receipt = evidence.acquireKeyForResearch().sendAsync().get();
            evidence.getAnalyzingEvents(receipt).forEach((eventResponse) -> {
                logger.info("调用者: {}, 证据名称: {},库密钥: {}, 证据编号: {},", eventResponse.user, eventResponse.name,
                        eventResponse.key, eventResponse.block);
            });
            analyzingEvents = evidence.getAnalyzingEvents(receipt);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            logger.warn(researcher.getAddress() + ": You do not have sufficient permissions to call this.");
        }
        return analyzingEvents;
    }

    public void uploadAnalysisResult(String contractAddress, String result, String secretKey) {
        Evidence evidence = Evidence.load(contractAddress, web3j, researcher, gasProvider);
        try {
            evidence.uploadAnalysisResult(result, contractAddress, secretKey).sendAsync().get();
            logger.info("结论上传成功！  证据编号: {} 证据结论: {}", contractAddress, result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            logger.warn("Only researcher can call this.");
        }
    }


    public List<Evidence.AcquireResultEventResponse> acquireAnalysisResult(String contractAddress) {
        Evidence evidence = Evidence.load(contractAddress, web3j, owner, gasProvider);
        List<Evidence.AcquireResultEventResponse> acquireResultEvents = null;
        try {
            TransactionReceipt receipt = evidence.acquireAnalysisResult().sendAsync().get();
            evidence.getAcquireResultEvents(receipt).forEach((resultEventResponse) -> {
                logger.info("证据结论: {}, Owner是否赞同: {}, 证据名称: {}, 证据编号: {}, 调用者: {}, 调用者角色: " +
                                "{} ",
                        resultEventResponse.analysisResult,
                        resultEventResponse.approved,
                        resultEventResponse.name, resultEventResponse.block, resultEventResponse.user,
                        resultEventResponse.role);
            });
            acquireResultEvents = evidence.getAcquireResultEvents(receipt);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            logger.warn("You do not have sufficient permissions to call this.");
        }
        return acquireResultEvents;

    }


    public List<Evidence.OwnerApprovedEventResponse> approved(String contractAddress) {
        Evidence evidence = Evidence.load(contractAddress, web3j, owner, gasProvider);
        List<Evidence.OwnerApprovedEventResponse> ownerApprovedEvents = null;
        try {
            TransactionReceipt receipt = evidence.approve().sendAsync().get();
            evidence.getOwnerApprovedEvents(receipt).forEach((ownerApprovedEventResponse) -> {
                logger.info("调用者: {}, 是否赞同: {}, 证据编号: {}", ownerApprovedEventResponse.user,
                        ownerApprovedEventResponse.approve,
                        contractAddress);
            });
            ownerApprovedEvents = evidence.getOwnerApprovedEvents(receipt);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            logger.warn(" Only owner can call this.");
        }
        return ownerApprovedEvents;
    }

    public void resetKey(String contractAddress, String key) {
        Evidence evidence = Evidence.load(contractAddress, web3j, owner, gasProvider);
        try {
            evidence.resetKey(key).sendAsync().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            logger.warn("Only owner can call this.");
        }
        logger.info("{}: SecretKey has been reset !", owner.getAddress());
    }


    public Evidence loadContract(String contractAddress, Credentials credentials) throws IOException {
        Evidence evidence = Evidence.load(contractAddress, web3j, credentials, gasProvider);
        logger.info("证据编号 {} 加载成功: {}", evidence.getContractAddress(), evidence.isValid());
        return evidence;
    }

    private Credentials loadWallet(String password) throws Exception {
        String wallet = walletService.generateWallet(password);
        Credentials credentials = walletService.getCredentials(password, wallet);
        walletService.transferCoins(credentials);
        //  logger.info("Credentials loaded: wallet={}, address={}", wallet, credentials.getAddress());
        return credentials;
    }


}
