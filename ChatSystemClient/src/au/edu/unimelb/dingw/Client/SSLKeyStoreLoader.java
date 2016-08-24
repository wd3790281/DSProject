package au.edu.unimelb.dingw.Client;

import javax.net.ssl.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;

/**
 * Created by dingwang on 15/10/20.
 */
public class SSLKeyStoreLoader {
    public SSLSocketFactory load(){
        InputStream inputStream = ClassLoader.getSystemResourceAsStream("myKey");
        SSLSocketFactory factory = null;
        try {
            KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
            ks.load(inputStream,"123456".toCharArray());
            KeyManagerFactory kmf =
                    KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            kmf.init(ks,"123456".toCharArray());
            TrustManagerFactory tmf =
                    TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(ks);
            SSLContext ctx = SSLContext.getInstance("TLSv1");
            ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
            factory = ctx.getSocketFactory();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        return factory;
    }
}
