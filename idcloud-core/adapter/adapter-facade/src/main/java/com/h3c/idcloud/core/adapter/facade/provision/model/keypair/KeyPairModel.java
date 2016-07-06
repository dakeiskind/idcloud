package com.h3c.idcloud.core.adapter.facade.provision.model.keypair;

import org.codehaus.jackson.annotate.JsonProperty;

public class KeyPairModel {
    private String name;
    @JsonProperty("public_key")
    private String publicKey;
    @JsonProperty("private_key")
    private String privateKey;
    private String fingerprint;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getFingerprint() {
        return fingerprint;
    }

    public void setFingerprint(String fingerprint) {
        this.fingerprint = fingerprint;
    }

}
