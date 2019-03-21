package com.cqupt.bear.blockchain.evidence.dto;

/**
 * @author ：Y.Bear
 * @date ：Created in 2019/3/13 10:51
 * @description：证据结构
 * @modified By：
 * @version: $
 */
public class EvidenceStruct {
    private String evidenceName;
    private String hash;
    private String secretKey;
    private String adminAddress;
    private String blockId;

    public String getEvidenceName() {
        return evidenceName;
    }

    public void setEvidenceName(String evidenceName) {
        this.evidenceName = evidenceName;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getAdminAddress() {
        return adminAddress;
    }

    public void setAdminAddress(String adminAddress) {
        this.adminAddress = adminAddress;
    }

    public String getBlockId() {
        return blockId;
    }

    public void setBlockId(String blockId) {
        this.blockId = blockId;
    }

    @Override
    public String toString() {
        return "EvidenceStruct{" +
                "evidenceName='" + evidenceName + '\'' +
                ", hash='" + hash + '\'' +
                ", secretKey='" + secretKey + '\'' +
                ", adminAddress='" + adminAddress + '\'' +
                ", blockId='" + blockId + '\'' +
                '}';
    }
}
