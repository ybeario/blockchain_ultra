package com.cqupt.bear.blockchain.evidence.dto;

/**
 * @author ：Y.Bear
 * @date ：Created in 2019/5/21 19:10
 * @description：包装授权后的返回信息
 * @modified By：
 * @version: $
 */
public class GivenRightResult {
    private String admin;
    private String researcher;
    private String contractAddress;

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String getResearcher() {
        return researcher;
    }

    public void setResearcher(String researcher) {
        this.researcher = researcher;
    }

    public String getContractAddress() {
        return contractAddress;
    }

    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }
}
