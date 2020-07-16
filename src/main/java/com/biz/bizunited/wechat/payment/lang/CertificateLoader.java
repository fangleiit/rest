package com.biz.bizunited.wechat.payment.lang;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import java.io.InputStream;
import java.security.KeyStore;
import java.util.MissingResourceException;

/**
 * 配置安全证书
 * Created by defei on 7/7/15.
 */
public class CertificateLoader {

    public static final String ALGORITHM = "TLSv1";

    /**
     * To store our own certificates
     */
    public KeyStore identification;

    public String passPhrase;

    public CertificateLoader(String identificationPath, String pws) {
        passPhrase = pws;
        this.loadIdentification(identificationPath, pws);
    }

    public SSLContext getSSLContext() {
        try {
            KeyManagerFactory kmFactory =
                KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            kmFactory.init(this.identification, this.passPhrase.toCharArray());
            KeyManager[] km = kmFactory.getKeyManagers();

            SSLContext sslCtx = SSLContext.getInstance(ALGORITHM);
            sslCtx.init(km, null, null);
            return sslCtx;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Load identification from .p12 file.
     * Existing <code>identification</code> will be dropped.
     *
     * @param pwd Pwd to decrypt the cert, mch_id if certificate is acquired from pay.weixin.qq.com .
     */
    private void loadIdentification(String resourcePath, String pwd) {
        try {
            InputStream stream = this.getClass().getResourceAsStream(resourcePath);

            if (stream == null)
                throw (new MissingResourceException("Load identification failed.",
                    CertificateLoader.class.getName(), resourcePath));

            this.identification = KeyStore.getInstance("PKCS12");
            this.identification.load(stream, pwd.toCharArray());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

}
