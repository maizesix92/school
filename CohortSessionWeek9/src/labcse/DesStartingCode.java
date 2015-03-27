package labcse;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;


public class DesStartingCode {

	private static Cipher ecipher;
	private static Cipher dcipher;
	
	private static SecretKey key;

	public static void main(String[] args) throws IOException, BadPaddingException, IllegalBlockSizeException {
		//TODO: read input file, store in a byte[]
		String fileName = "D:\\SUTD\\Term 5\\Computer System Engineering\\Week 9\\Lab\\InputFile1.data";
		File filePrivateKey = new File(fileName);
		FileInputStream fis;
		fis = new FileInputStream(fileName);
		byte[] dataByte = new byte[(int) filePrivateKey.length()];
		fis.read(dataByte);

		//TODO: generate secret key using DES algorithm
		KeyGenerator keyGen;
		try {
			keyGen = KeyGenerator.getInstance("DES");
			key = keyGen.generateKey();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		//TODO: Create cipher object, configure it to do DES cryptography, set operation mode to encryption
		try {
			ecipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
			ecipher.init(Cipher.ENCRYPT_MODE, key);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		//TODO: Do the DES encryption here, by calling method Cipher.doFinal(). Convert encrypted byte[] to Base64 format
		System.out.println("Ciphertext:\n" + new String(dataByte) + "\n");
		byte[] result = ecipher.doFinal(dataByte);	// result contains the encrypted byte[] of dataByte
		System.out.println("Encrypted ciphertext:\n" + new String(result));
		System.out.println();
		Base64.Encoder encoder = Base64.getEncoder();
		String stringResult = encoder.encodeToString(result);
		System.out.println("Base64 format:\n");
		for (int i = 0; i < stringResult.length(); i++) {
			try{
				for (int j = 0; j < 50; j++) {
					System.out.print(stringResult.charAt(i));
					i++;
				}
			}catch(StringIndexOutOfBoundsException e){
				System.out.println("\n");
				break;
			}
			System.out.println();
		}

		
		//TODO: set the cipher object to decryption mode

		try {
			dcipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
			dcipher.init(Cipher.DECRYPT_MODE, key);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		}
		//TODO:  Do the DES decryption
		byte[] decryptionResult = dcipher.doFinal(result);	// Decryption of encrypted byte[] to yield dataByte
		System.out.println("Decrypted ciphertext(aka original ciphertext): " + new String(decryptionResult));
		System.out.println();
		Base64.Encoder encoder1 = Base64.getEncoder();
		String stringResult1 = encoder.encodeToString(decryptionResult);
		System.out.println("Base64 format: ");
		for (int i = 0; i < stringResult1.length(); i++) {
			try{
				for (int j = 0; j < 50; j++) {
					System.out.print(stringResult1.charAt(i));
					i++;
				}
			}catch(StringIndexOutOfBoundsException e){
				break;
			}
			System.out.println();
		}
	}
}