package labcse;

/**
 * Created by 1000884 on 5/2/2015.
 */



import java.io.File;
import java.io.FileInputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.swing.text.html.HTMLDocument.Iterator;


public class DigitalSignatureStartingCode{
	
	private static PublicKey publicKey;
	private static PrivateKey privateKey;

    public static void main(String[] args) throws Exception {
//TODO: Read a file (arbitrary file), store to byte[]

        //TODO: read input file, store in a byte[]
/////////////////////////////////// For InputFile1.data /////////////////////////////////////////////
        String fileName = "D:\\SUTD\\Term 5\\Computer System Engineering\\Week 9\\Lab\\InputFile1.data";
        File filePrivateKey = new File(fileName);
        FileInputStream fis;
        fis = new FileInputStream(fileName);
        // The long message to be sent, dataByte (not encrypted)
        byte[] dataByte = new byte[(int) filePrivateKey.length()];
        fis.read(dataByte);
        /////////////////////////////////// For InputFile2.txt /////////////////////////////////////////////
        String fileName1 = "D:\\SUTD\\Term 5\\Computer System Engineering\\Week 9\\Lab\\InputFile2.txt";
        File filePrivateKey1 = new File(fileName);
        FileInputStream fis1;
        fis1 = new FileInputStream(fileName1);
        // The long message to be sent, dataByte (not encrypted)
        byte[] dataByte1 = new byte[(int) filePrivateKey1.length()];
        fis1.read(dataByte1);
        
//TODO: generate a RSA keypair, obtain public key and private key from this keypair
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        keyPairGen.initialize(1024);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        
        publicKey = keyPair.getPublic();
        privateKey = keyPair.getPrivate();

//TODO: Calculate message digest, using MD5 hash function
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        
        byte[] hashDigested = messageDigest.digest(dataByte);	// for InputFile1.data
        System.out.println("Size of message digest 1: " + messageDigest.getDigestLength());
        
        messageDigest.update(dataByte1);
        
        byte[] hashDigested1 = messageDigest.digest(dataByte1);	// for InputFile2.txt
        System.out.println("Size of message digest 2: " + messageDigest.getDigestLength() + "\n");
//        System.out.println("Size of byte[] 1: " + hashDigested.length);
//        System.out.println("Size of byte[] 2: " + hashDigested1.length);
        
//TODO: Create RSA cipher object, configure it to do RSA cryptography,set operation mode to encryption with PRIVATE key.
        
        Cipher ecipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        ecipher.init(Cipher.ENCRYPT_MODE, privateKey);
        
//TODO: sign the  message digest
        byte[] mySignature = ecipher.doFinal(hashDigested);	// for InputFile1.data
        
        Base64.Encoder encoder = Base64.getEncoder();
        String base64OP = new String(encoder.encode(mySignature));
        System.out.println("Base64 format: ");
		for (int i = 0; i < base64OP.length(); i++) {
			try{
				for (int j = 0; j < 50; j++) {
					System.out.print(base64OP.charAt(i));
					i++;
				}
			}catch(StringIndexOutOfBoundsException e){
				System.out.println("\n");
				break;
			}
			System.out.println();
		}        
        
        byte[] mySignature1 = ecipher.doFinal(hashDigested1); // for InputFile2.txt
//        System.out.println(new String(mySignature));
        System.out.println("Size of byte[] mySignature: " + mySignature.length + "\n");
//        System.out.println(new String(mySignature1));
        System.out.println("Size of byte[] mySignature1: " + mySignature1.length + "\n");
    }
}
