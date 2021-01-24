package com.stealthfueldelivery.app.delivery.others;


// new Code
import android.app.Activity;
import android.util.Log;


import javax.net.ssl.HostnameVerifier;
import com.stealthfueldelivery.app.delivery.R;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;


public class HttpsTrustManager {

    public static SSLSocketFactory allowAllSSL(Activity activity) {




        CertificateFactory cf = null;
        try {
            try {
                cf = CertificateFactory.getInstance("X.509");
            } catch (CertificateException e) {
                e.printStackTrace();
            }
            InputStream caInput = activity.getResources().openRawResource(R.raw.biddrip_a2hosted_com);
//            InputStream caInput = new BufferedInputStream(new FileInputStream("biddrip_a2hosted_com.cer"));
            Certificate ca;
            try {
                ca = cf.generateCertificate(caInput);
//                 Log.e("CERT", "ca=" + ((X509Certificate) ca).getSubjectDN());
            } finally {
                try {
                    caInput.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            String keyStoreType = KeyStore.getDefaultType();
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            try {
                keyStore.load(null, null);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            try {
                keyStore.setCertificateEntry("ca", ca);
            } catch (KeyStoreException e) {
                e.printStackTrace();
            }

            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
            tmf.init(keyStore);


            HostnameVerifier hostnameVerifier = new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {

                    Log.e("CipherUsed", session.getCipherSuite());
//                    return hostname.compareTo("fuelwebservice.biddrip.mochahosted.com")==0; //The Hostname of your server.
                    return true;//hostname.compareTo("fuelwebservice.biddrip.mochahosted.com")==0 ||
                    //hostname.compareTo("clients4.google.com") == 0; //The Hostname of your server.
                }
            };



            HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifier);
            SSLContext context = null;
            context = SSLContext.getInstance("TLS");

            try {
                context.init(null, tmf.getTrustManagers(), null);

            } catch (KeyManagementException e) {
                e.printStackTrace();
            }

//            HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());

//            SSLSocketFactory sf = context.getSocketFactory();


//            return sf;

        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  null;
    }


}
