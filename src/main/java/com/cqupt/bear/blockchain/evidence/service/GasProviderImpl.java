package com.cqupt.bear.blockchain.evidence.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

import java.math.BigInteger;

/**
 * @author ：Y.Bear
 * @date ：Created in 2019/1/13 19:42
 * @description：实现GasProvider
 * @modified By：
 * @version: 1.0$
 */
@Component
public class GasProviderImpl implements ContractGasProvider {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private BigInteger GAS_PRICE = DefaultGasProvider.GAS_PRICE;
    private BigInteger GAS_LIMIT = DefaultGasProvider.GAS_LIMIT;

    public void setGAS_PRICE(BigInteger GAS_PRICE) {
        this.GAS_PRICE = GAS_PRICE;
    }

    public void setGAS_LIMIT(BigInteger GAS_LIMIT) {
        this.GAS_LIMIT = GAS_LIMIT;
    }

    @Override
    public BigInteger getGasPrice(String s) {
        return GAS_PRICE;
    }

    @Override
    public BigInteger getGasPrice() {
        return null;
    }

    @Override
    public BigInteger getGasLimit(String s) {

        return GAS_LIMIT;
    }

    @Override
    public BigInteger getGasLimit() {
        return null;
    }
}
