package com.cqupt.bear.blockchain.evidence.service;

import com.cqupt.bear.blockchain.evidence.model.Evidence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;

import java.util.Optional;

/**
 * @author ：Y.Bear
 * @date ：Created in 2019/3/13 9:56
 * @description：合约部署
 * @modified By：
 * @version: $
 */
@Service
public class ContractService {
    private Logger logger = LoggerFactory.getLogger(getClass());
    Web3j web3j = Web3j.build(new HttpService());
    GasProviderImpl gasProvider = new GasProviderImpl();

    private Credentials credentials;

    public Evidence deployContract(String password, String evidenceName, String hash, String secretKey,
                                  Credentials owner, Credentials admin
    ) throws Exception {
        Evidence evidence = Evidence.deploy(web3j, owner, gasProvider, evidenceName, hash, secretKey,
                admin.getAddress()).sendAsync().get();
        Optional<TransactionReceipt> tr = evidence.getTransactionReceipt();
        LogReceipt(tr);
        logger.info("EvidenceAddress: {} , valid :  {}, EvidenceName: {} , EvidenceHash: {}",
                evidence.getContractAddress(),
                evidence.isValid(), evidenceName, hash);
        return evidence;
    }


    private void LogReceipt(Optional<TransactionReceipt> tr) {
        if (tr.isPresent()) {
            logger.info("Transaction receipt: BlockNumber: {},transactionHash: {}, blockHash {}, CumulativeGasUsed: " +
                            "{}, GasUsed: {}",
                    tr.get().getBlockNumber(), tr.get().getTransactionHash(), tr.get().getBlockHash(),
                    tr.get().getCumulativeGasUsed(),
                    tr.get().getGasUsed());
        }
    }
}
