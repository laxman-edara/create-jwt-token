package com.ebsco.dmp.gateway;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.tomcat.util.codec.binary.Base64;

import com.google.gson.Gson;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class Sample {

    // Below is a dummy private key
    private static String privatekey = "-----BEGIN PRIVATE KEY-----\n"
            + "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAM7t8Ub1DP+B91NJ\n"
            + "nC45zqIvd1QXkQ5Ac1EJl8mUglWFzUyFbhjSuF4mEjrcecwERfRummASbLoyeMXl\n"
            + "eiPg7jvSaz2szpuV+afoUo9c1T+ORNUzq31NvM7IW6+4KhtttwbMq4wbbPpBfVXA\n"
            + "IAhvnLnCp/VyY/npkkjAid4c7RoVAgMBAAECgYBcCuy6kj+g20+G5YQp756g95oN\n"
            + "dpoYC8T/c9PnXz6GCgkik2tAcWJ+xlJviihG/lObgSL7vtZMEC02YXdtxBxTBNmd\n"
            + "upkruOkL0ElIu4S8CUwD6It8oNnHFGcIhwXUbdpSCr1cx62A0jDcMVgneQ8vv6vB\n"
            + "/YKlj2dD2SBq3aaCYQJBAOvc5NDyfrdMYYTY+jJBaj82JLtQ/6K1vFIwdxM0siRF\n"
            + "UYqSRA7G8A4ga+GobTewgeN6URFwWKvWY8EGb3HTwFkCQQDgmKtjjJlX3BotgnGD\n"
            + "gdxVgvfYG39BL2GnotSwUbjjce/yZBtrbcClfqrrOWWw7lPcX1d0v8o3hJfLF5dT\n"
            + "6NAdAkA8qAQYUCSSUwxJM9u0DOqb8vqjSYNUftQ9dsVIpSai+UitEEx8WGDn4SKd\n"
            + "V8kupy/gJlau22uSVYI148fJSCGRAkBz+GEHFiJX657YwPI8JWHQBcBUJl6fGggi\n"
            + "t0F7ibceOkbbsjU2U4WV7sHyk8Cei3Fh6RkPf7i60gxPIe9RtHVBAkAnPQD+BmND\n"
            + "By8q5f0Kwtxgo2+YkxGDP5bxDV6P1vd2C7U5/XxaN53Kc0G8zu9UlcwhZcQ5BljH\n" + "N24cUWZOo+60\n"
            + "-----END PRIVATE KEY-----";

    public static void main(String[] args) throws Exception {
        privatekey = privatekey.replace("-----BEGIN PRIVATE KEY-----", "");
        privatekey = privatekey.replace("-----END PRIVATE KEY-----", "");
        privatekey = privatekey.replaceAll("\\s+", "");

        // Base64 decode the result

        byte[] pkcs8EncodedBytes = Base64.decodeBase64(privatekey.getBytes());

        // extract the private key

        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(pkcs8EncodedBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PrivateKey privKey = kf.generatePrivate(keySpec);
        Map<String, Object> header = new HashMap<String, Object>();
        header.put("alg", "RS256");
        header.put("typ", "JWT");
        Payload payload = new Payload();
        payload.setFirstName("laxman");
        payload.setLastName("laxman");
        payload.setCreated(new Date());
        payload.setExpired(new Date());
        String profileJwt = Jwts.builder().setPayload(new Gson().toJson(payload)).signWith(SignatureAlgorithm.RS256, privKey).setHeader(header).compact();
        System.out.println(profileJwt);
    }

}

class Payload {
    private String firstName;
    private String lastName;
    private Date created;
    private Date expired;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getExpired() {
        return expired;
    }

    public void setExpired(Date expired) {
        this.expired = expired;
    }

}
