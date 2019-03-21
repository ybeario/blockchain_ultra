package com.cqupt.bear.blockchain.evidence.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthCoinbase;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;
import java.math.BigInteger;

/**
 * @author ：Y.Bear
 * @date ：Created in 2019/3/13 9:56
 * @description：钱包生成、钱包管理
 * @modified By：
 * @version: $
 */
@Service
public class WalletService {
    private Logger logger = LoggerFactory.getLogger(getClass());
    Web3j web3j = Web3j.build(new HttpService());

    public String generateWallet(String password) throws Exception {
        //创建或读取账户
        //String adminPath = System.getProperty("user.dir") + System.getProperty("file.separator") + "admin";
        String wallet = WalletUtils.generateLightNewWalletFile(password, null);
        logger.info("Wallet 创建成功:{}", wallet);
        return wallet;
    }

    public Credentials getCredentials(String password, String wallet) throws IOException, CipherException {
        Credentials credentials = WalletUtils.loadCredentials(password, wallet);
        logger.info("Credentials 创建成功: wallet={}, address={}", wallet, credentials.getAddress());
        return credentials;
    }

    public void transferCoins(Credentials credentials) throws IOException {
        EthCoinbase coinbase = web3j.ethCoinbase().send();
        EthGetTransactionCount transactionCount = web3j.ethGetTransactionCount(coinbase.getAddress(),
                DefaultBlockParameterName.LATEST).send();
        Transaction transaction = Transaction.createEtherTransaction(coinbase.getAddress(),
                transactionCount.getTransactionCount(), BigInteger.valueOf(20_000_000_000_000L),
                BigInteger.valueOf(21_000), credentials.getAddress(), BigInteger.valueOf(999_000_000_000_000_000L));
        web3j.ethSendTransaction(transaction).send();
    }
}
