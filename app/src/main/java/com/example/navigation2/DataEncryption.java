package com.example.navigation2;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;


public class DataEncryption {
    String h="Hello Ho are you";

    private byte key[]=h.getBytes();
    private Cipher cipher,decipher;
    private SecretKeySpec secretKeySpec;
    public DataEncryption(){

    }


    public String Dencrypt(String k) throws UnsupportedEncodingException {
        try {
            cipher=Cipher.getInstance("AES");
            decipher=Cipher.getInstance("AES");
        } catch (
                NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (
                NoSuchPaddingException e) {
            e.printStackTrace();
        }
        secretKeySpec= new SecretKeySpec(key,"AES");
        byte[] Encryptionbyte=k.getBytes("ISO-8859-1");
        String decryptedtxt=k;
        byte[] decryption;
        try {
            decipher.init(Cipher.DECRYPT_MODE,secretKeySpec);
            decryption=decipher.doFinal(Encryptionbyte);
            decryptedtxt=new String(decryption);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return  decryptedtxt;
    }

    public String Encrypt(String k) throws InvalidKeyException {
        try {
            cipher=Cipher.getInstance("AES");
            decipher=Cipher.getInstance("AES");
        } catch (
                NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (
                NoSuchPaddingException e) {
            e.printStackTrace();
        }
        secretKeySpec= new SecretKeySpec(key,"AES");

        byte[] stringbyte=k.getBytes();
        byte[] encrptionbyte=new byte[stringbyte.length];
        cipher.init(Cipher.ENCRYPT_MODE,secretKeySpec);
        try {
            encrptionbyte=cipher.doFinal(stringbyte);
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        String result;
        try {
            result=new String(encrptionbyte,"ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            result=null;
            e.printStackTrace();
        }

        return result;
    }

}

