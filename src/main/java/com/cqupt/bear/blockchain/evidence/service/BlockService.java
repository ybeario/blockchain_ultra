package com.cqupt.bear.blockchain.evidence.service;

import com.cqupt.bear.blockchain.evidence.dto.BlockchainTransaction;
import org.web3j.protocol.core.methods.response.EthTransaction;

/**
 * @author Y.bear
 * @version 创建时间：2018年12月17日 上午9:48:54 类说明 面向接口编程，完成基于以太坊和布比区块链的智能合约调用
 */
public interface BlockService {
    void depoly();

    BlockchainTransaction upload(BlockchainTransaction transaction) throws Exception;

    EthTransaction query(String blockHash) throws Exception;
}
