package com.evanslai.sslsocket;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.SocketException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.HandshakeCompletedEvent;
import javax.net.ssl.HandshakeCompletedListener;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private byte[] authEncryptBytes;
    private byte[] buf;
    private byte[] keepAliveEncyrptByte;
    private byte[] keepBuf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        byte[] key = {0x0e, 0x0b, (byte) 0xea, 0x12, 0x0e, (byte) 0xba, (byte) 0xcd, (byte) 0xf0, 0x08, (byte) 0xea, (byte) 0xa1,
                (byte) 0xb7, (byte) 0xf0, 0x2a, 0x61, (byte) 0xef, 0x67, 0x69, (byte) 0xfe, (byte) 0xe2, (byte) 0xf1, 0x29,
                (byte) 0xb4, (byte) 0xed, 0x51, 0x03, (byte) 0x9a, 0x03, (byte) 0xf1, (byte) 0x83, 0x1f, (byte) 0xe1};
//        Log.d(TAG, "onCreate: " + Arrays.toString(key));

        // 创建AES秘钥
        SecretKeySpec keySpec = new SecretKeySpec(key, "AES/CBC/PKCS5PADDING");
        byte[] ivKey = {(byte) 0x9a, (byte) 0xf6,0x2f, (byte) 0xc1,0x2c, (byte) 0xbe, (byte) 0xd8, (byte) 0xec,0x3e, (byte) 0xa4, (byte) 0xd6, (byte) 0x9b,0x41, (byte) 0xe5, (byte) 0xd6, (byte) 0xe0};
        IvParameterSpec iv = new IvParameterSpec(ivKey);//使用CBC模式，需要一个向量iv，可增加加密算法的强度
        // 创建密码器
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            // 初始化加密器
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv);
            Date date = new Date();
            SimpleDateFormat myFormatObj = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault());
            String formattedDate = myFormatObj.format(date);
            // "2019-10-26T17:46:21+0800"
            String auth = "{\"cid\":\"0\",\"timestamp\":\"" + formattedDate + "\",\"mac\":\"a1:5a:a1:20:f0:6b\"}";
//            Log.d(TAG, "onCreate: " + auth);
            byte[] authByte = auth.getBytes("UTF-8");
//            Log.d(TAG, "onCreate: authByte" + Arrays.toString(authByte));
            authEncryptBytes = cipher.doFinal(authByte);
            int authHexSize = (authEncryptBytes.length >> 8) & 0xFF; //
            int authHexSize1 = authEncryptBytes.length & 0xFF;
            byte[] authHeader = {0x00,0x00, (byte) authHexSize, (byte) authHexSize1};
            buf = new byte[authHeader.length + authEncryptBytes.length];
            System.arraycopy(authHeader, 0, buf, 0, authHeader.length);
            System.arraycopy(authEncryptBytes, 0, buf, authHeader.length, authEncryptBytes.length);

            byte checksum = 0x0;
            for (int i = 1; i < buf.length; i++) {
                checksum = (byte) (checksum + buf[i]);
            }
            buf[0] = (byte) (checksum % 256);

            String keepAlive = "{\"keepalive\":\"true\"}";
            byte[] keepAliveByte = keepAlive.getBytes("UTF-8");
//            Log.d(TAG, "onCreate: " + keepAliveByte.length);
//            keepAliveEncyrptByte = cipher.doFinal(keepAliveByte);
            int keepHexSize = (keepAliveByte.length >> 8) & 0xFF; //
            int keepHexSize1 = keepAliveByte.length & 0xFF;
            byte[] keepHeader = {0x00,0x00, (byte) keepHexSize, (byte) keepHexSize1};
            keepBuf = new byte[keepHeader.length + keepAliveByte.length];
            System.arraycopy(keepHeader, 0, keepBuf, 0, keepHeader.length);
            System.arraycopy(keepAliveByte, 0, keepBuf, keepHeader.length, keepAliveByte.length);

            byte[] xorKey = {(byte) 0xa1, 0x5a, (byte) 0xa1, 0x20, (byte) 0xf0, 0x6b};

            for (int i = 0 , j = 0; i < keepAliveByte.length; i++) {
                byte int_ptr = keepAliveByte[i];
                int_ptr ^= xorKey[j];
                keepBuf[i+4] = int_ptr;
//                Log.d(TAG, "onCreate----: " + (keepBuf[i+4] & 0xFF));
                if (6 == ++j) {
                    j = 0;
                }
            }

            byte keepChecksum = 0x0;
            for (int i = 1; i < keepBuf.length; i++) {
                keepChecksum = (byte) (keepChecksum + keepBuf[i]);
            }
            keepBuf[0] = (byte) (keepChecksum % 256);

//            for (byte b : keepBuf) {
//                Log.d(TAG, "onCreate: " + (b & 0xFF));
//            }

//            cipher.init(Cipher.DECRYPT_MODE, keySpec, iv);
//            byte[] bytes1 = cipher.doFinal(authEncryptBytes);
//            Log.d(TAG, "onCreate: " + Arrays.toString(bytes1));
        } catch (NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException
                | UnsupportedEncodingException | BadPaddingException | IllegalBlockSizeException | InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }

//        TrustManagerFactory trustManagerFactory = null;
//        try {
//            trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
//            trustManagerFactory.init((KeyStore) null);
//            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
//            if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
//                throw new IllegalStateException("Unexpected default trust managers:" + Arrays.toString(trustManagers));
//            }
//            X509TrustManager trustManager = (X509TrustManager) trustManagers[0];
//            SSLContext sslContext = SSLContext.getInstance("SSL");
//            sslContext.init(null, new TrustManager[] { trustManager }, null);

            new Thread(new Runnable() {
                @Override
                public void run() {

                    try {
                        SSLContext sslCtx = SSLContext.getInstance("SSL");
                        sslCtx.init(null, null, null);
//                        SSLSocketFactory sslsocketfactory = sslCtx.getSocketFactory();
                        SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
                        SSLSocket sslsocket = (SSLSocket) sslsocketfactory.createSocket("rss.simpnic.com", 9529);
                        sslsocket.setKeepAlive(true);
                        sslsocket.setReuseAddress(true);
                        sslsocket.setTcpNoDelay(true);
//                        sslsocket.setSoLinger(true, 5);
                        // enable TLSv1.1 only
                        sslsocket.setEnabledProtocols(new String[] {"TLSv1.1"});
                        sslsocket.setEnabledCipherSuites(new String[] {"TLS_RSA_WITH_AES_128_CBC_SHA"});
                        sslsocket.startHandshake();
                        Log.d(TAG, "run: " + sslsocket.isConnected());
                        Log.d(TAG, "run: " + sslsocket.getInetAddress());

//                        DataOutputStream dataOutputStream = new DataOutputStream(sslsocket.getOutputStream());
                        OutputStream dataOutputStream = sslsocket.getOutputStream();
//                        DataInputStream dataInputStream = new DataInputStream(sslsocket.getInputStream());
                        InputStream dataInputStream = sslsocket.getInputStream();

                        dataOutputStream.write(buf);
                        dataOutputStream.flush();
                        dataOutputStream.close();
//                        int read = dataInputStream.read();
//                        Log.d(TAG, "run: " + read);
                        while (true) {
                            Log.d(TAG, "run: while");
                            Thread.sleep(5000);
//                            dataOutputStream = new DataOutputStream(sslsocket.getOutputStream());
//                            Log.d(TAG, "run: " + dataOutputStream.size());
                            dataOutputStream = sslsocket.getOutputStream();
                            dataOutputStream.write(keepBuf, 0, keepBuf.length);
                            dataOutputStream.flush();
                            dataOutputStream.close();
                            dataInputStream = sslsocket.getInputStream();
                           int read = dataInputStream.read();
                            Log.d(TAG, "run: " + read);
                            dataInputStream.close();
//                            Log.d(TAG, "run: " + sslsocket.getInputStream().read());
                        }
                    } catch (IOException | InterruptedException | NoSuchAlgorithmException | KeyManagementException e) {
                        e.printStackTrace();
                    }
                }
            }).start();


//            BufferedReader reader = new BufferedReader(new InputStreamReader(
//                    sslsocket.getInputStream()));



//            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
//            OkHttpClient client = new OkHttpClient.Builder().sslSocketFactory(sslSocketFactory, trustManager).build();
//
//            MediaType mediaType = MediaType.parse("application/json");
//            RequestBody requestBody = RequestBody.create(mediaType, authEncryptBytes);
//            Request request = new Request.Builder()
//                    .url("https://rss.simpnic.com")
////                    .post(requestBody)
//                    .build();
//
//            client.newCall(request).enqueue(new Callback() {
//                @Override
//                public void onFailure(Call call, IOException e) {
//                    e.printStackTrace();
//                }
//
//                @Override
//                public void onResponse(Call call, final Response response) throws IOException {
//                    if (!response.isSuccessful()) {
//                        throw new IOException("Unexpected code " + response);
//                    } else {
//                        // do something wih the result
//                        Log.d(TAG, "onResponse: " + response.code());
//                    }
//                }
//            });
//        } catch (NoSuchAlgorithmException | KeyStoreException | KeyManagementException | IOException e) {
//            e.printStackTrace();
//        }

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//
//                // Open SSLSocket directly to rss.simpnic.com
////                SocketFactory sf = SSLSocketFactory.getDefault();
////                SSLSocket socket = null;
//                try {
//                    URL url = new URL("https://wikipedia.org");
//                    URLConnection urlConnection = url.openConnection();
//                    OutputStream socket = urlConnection.getOutputStream();
//
////                    socket = (SSLSocket) sf.createSocket("rss.simpnic.com", 9529);
////                    HostnameVerifier hv = HttpsURLConnection.getDefaultHostnameVerifier();
////                    SSLSession s = socket.getSession();
////
////                    // Verify that the certicate hostname is for mail.google.com
////                    // This is due to lack of SNI support in the current SSLSocket.
////                    if (!hv.verify("rss.simpnic.com", s)) {
////                        throw new SSLHandshakeException("Expected rss.simpnic.com, " +
////                                "found " + s.getPeerPrincipal());
////                    }
//
////                    socket.startHandshake();
////                    System.out.println(socket);
////                    System.out.println("ok");
////                    OutputStream output = socket.getOutputStream();
////                    InputStream input = socket.getInputStream();
//                    PrintWriter out = new PrintWriter(
//                            new BufferedWriter(
//                                    new OutputStreamWriter(
//                                            urlConnection.getOutputStream())));
//
//                    out.println("POST / HTTP/1.0");
//                    out.println();
//                    out.println("{\"cid\":\"0\",\"timestamp\":\"2019-09-28T17:50:00Z\",\"mac\":\"01:02:03:04:05:06\"}");
//                    out.flush();
//
//                    /* read response */
//                    BufferedReader in = new BufferedReader(
//                            new InputStreamReader(
//                                    urlConnection.getInputStream()));
//
//                    String inputLine;
//                    while ((inputLine = in.readLine()) != null)
//                        System.out.println(inputLine);
//
//                    in.close();
//                    out.close();
//                    socket.close();
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();


    }

}


