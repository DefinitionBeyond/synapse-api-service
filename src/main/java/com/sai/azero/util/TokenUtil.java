package com.sai.azero.util;

import com.github.nitram509.jmacaroons.Macaroon;
import com.github.nitram509.jmacaroons.MacaroonsBuilder;

/**
 * @Description
 * @Author liutao
 * @CreateTime 2020/5/22 18:25
 */
public class TokenUtil {

    private String userName;
    private String location;
    private String identifier;
    private String secretKey;
    private String deviceId;
    private String azeroUserId;
    private String userId;
    private long validTime;
    private static final long DEFAULT_VALID_TIME = 100 * 365 * 24 * 60 * 1000L;

    public TokenUtil(String userName, String location, String secretKey, String deviceId, String identifier, String azeroUserId, long validTime) {
        this.userId = "@" + userName + ":" + location + "";
        this.location = location;
        this.secretKey = secretKey;
        this.identifier = identifier;
        this.deviceId = deviceId;
        this.validTime = validTime;
    }

    public TokenUtil(String userName, String location, String secretKey, String deviceId, String identifier, String azeroUserId) {
        this(userName, location, secretKey, deviceId, identifier, azeroUserId, DEFAULT_VALID_TIME);
    }

    public String getToken() throws RuntimeException {
        long curTime = System.currentTimeMillis();
        long expiry = curTime + this.validTime;
        Macaroon macaroon = new MacaroonsBuilder(this.location, this.secretKey, this.identifier)
                .add_first_party_caveat("gen = 1")
                .add_first_party_caveat("userId = " + this.userId)
                .add_first_party_caveat("userId = " + this.azeroUserId)
                .add_first_party_caveat("type = login")
                .add_first_party_caveat("time < " + expiry)
                .getMacaroon();
        String serialized = macaroon.serialize();
        return serialized;
    }


}
