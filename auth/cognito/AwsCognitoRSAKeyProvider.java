// 
// Decompiled by Procyon v0.6.0
// 

package com.yojito.minima.auth.cognito;

import java.security.PublicKey;
import java.security.PrivateKey;
import java.security.interfaces.RSAPrivateKey;
import com.auth0.jwk.JwkProviderBuilder;
import java.security.interfaces.RSAPublicKey;
import java.net.MalformedURLException;
import java.net.URL;
import com.auth0.jwt.interfaces.RSAKeyProvider;

public class AwsCognitoRSAKeyProvider implements RSAKeyProvider
{
    private final URL a;
    
    public AwsCognitoRSAKeyProvider(final String url) {
        try {
            this.a = new URL(url);
        }
        catch (final MalformedURLException ex) {
            throw new RuntimeException(String.format("Invalid URL provided, URL=%s", url));
        }
    }
    
    public RSAPublicKey getPublicKeyById(final String keyId) {
        try {
            return (RSAPublicKey)new JwkProviderBuilder(this.a).build().get(keyId).getPublicKey();
        }
        catch (final Exception ex) {
            throw new RuntimeException(String.format("Failed to get JWT kid=%s from aws_kid_store_url=%s", keyId, this.a));
        }
    }
    
    public RSAPrivateKey getPrivateKey() {
        return null;
    }
    
    public String getPrivateKeyId() {
        return null;
    }
}
