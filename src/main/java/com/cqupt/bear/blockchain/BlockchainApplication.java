package com.cqupt.bear.blockchain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class BlockchainApplication {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    Web3j web3j = Web3j.build(new HttpService());

    public static void main(String[] args) {
        SpringApplication.run(BlockchainApplication.class, args);
    }

    @PostConstruct
    public void listen() {
        web3j.transactionFlowable().subscribe(tx -> {
            logger.info("BlockHash: {}, BlockNumber:{},Input: {}, Creates: {}, Gas: {}, Hash: {}", tx.getBlockHash(), tx.getBlockNumber(), tx.getInput(), tx.getCreates(), tx.getGas(), tx.getHash());
        });
    }
}
