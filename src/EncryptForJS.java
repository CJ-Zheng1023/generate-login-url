/**
 * 功能：加密和解密的功能实现，采用DES算法
 * 作者：twq
 * 日期：2008.7.30
 */

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class EncryptForJS {
	
	private String Algorithm = "DES"; //算法
	private String ContractKey = "CPEES&SSS";
	private SecretKey deskey;   //密钥
	private Cipher cipher;
	private byte[] cipherByte;  //DES之后的数组
	private byte[] base64Byte;  //Base64之后的数组

	/**
	 * 不带参数的构造函数
	 * @throws NoSuchPaddingException 
	 * @throws InvalidKeySpecException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeyException 
	 */
	public EncryptForJS() throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException{
		init();
	}
	
	/**
	 * 带参数的构造函数，参数是：双方约定的密钥
	 * @throws NoSuchPaddingException 
	 * @throws InvalidKeySpecException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeyException 
	 */
	public EncryptForJS(String con_key) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException {
		this.ContractKey = con_key;
		init();
	}

	/**
	 * function：初始化方法
	 * @throws InvalidKeySpecException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeyException 
	 * @throws NoSuchPaddingException 
	 */
	private void init() throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException {
		Security.addProvider(new com.sun.crypto.provider.SunJCE());

		//产生密钥
		deskey = createDesKey();

		//初始化DES算法
		cipher = Cipher.getInstance(Algorithm);
	}

	/**
	 * function: 产生密钥
	 * @throws InvalidKeyException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeySpecException 
	 */
	private SecretKey createDesKey() throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException 
	{
		// 密钥
		DESKeySpec desKeySpec = new DESKeySpec(ContractKey.getBytes());

		// 选择DES算法
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(Algorithm);

		// 生成密钥
		SecretKey deskey = keyFactory.generateSecret(desKeySpec);

		return deskey;
	}
	
	/**
	 * 对明文字符串进行加密
	 * @param mingwen 要加密的数据
	 * @return 返回加密后的字符串
	 */
	public String getEncString(String mingwen) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException
	{
		//对数据进行DES加密
		cipher.init(Cipher.ENCRYPT_MODE, deskey);
		cipherByte = cipher.doFinal(mingwen.getBytes());
		
		//对DES密文进行Base64加密
		return Base64.encodeBytes(cipherByte);
	}
	
	/**
	 * 对密文字符串进行解密
	 * @param miwen 要解密的数据
	 * @return 返回解密后的字符串
	 */
	public String getDesString(String miwen) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException
	{
		//将数据进行Base64解密
		base64Byte = Base64.decode(miwen);
		
		//将数据进行DES解密
		cipher.init(Cipher.DECRYPT_MODE, deskey);
		cipherByte = cipher.doFinal(base64Byte);

		return (new String(cipherByte));
	}
	
	/**
	 * 对明文字符串进行加密
	 * @param mingwen 要加密的数据
	 * @return 返回加密后的字符串
	 */
	public String getEncString(String mingwen, String encoding) throws InvalidKeyException, BadPaddingException,
			IllegalBlockSizeException {
		// 对数据进行DES加密
		cipher.init(Cipher.ENCRYPT_MODE, deskey);
		try {
			cipherByte = cipher.doFinal(mingwen.getBytes(encoding));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		// 对DES密文进行Base64加密
		return Base64.encodeBytes(cipherByte);
	}

	/**
	 * 对密文字符串进行解密
	 * @param miwen 要解密的数据
	 * @return 返回解密后的字符串
	 */
	public String getDesString(String miwen, String encoding) throws InvalidKeyException, BadPaddingException,
			IllegalBlockSizeException {
		// 将数据进行Base64解密
		base64Byte = Base64.decode(miwen);
		// 将数据进行DES解密
		cipher.init(Cipher.DECRYPT_MODE, deskey);
		cipherByte = cipher.doFinal(base64Byte);
		String result = null;
		try {
			result = new String(cipherByte, encoding);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 *  main测试
	 */
	public static void main(String[] args) {
		String mingwen = "01050112345";
		System.out.println("明文[" + mingwen + "]");

		//加密测试
		EncryptForJS encrypt;
		String miwen = "";
		try {
			encrypt = new EncryptForJS("CPEES&SSS");
			miwen = encrypt.getEncString(mingwen);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		}
		System.out.println("*****加密后的数据*****::[" + miwen + "]");

		//解密测试
		try {
			encrypt = new EncryptForJS("CPEES&SSS");
			mingwen = encrypt.getDesString(miwen);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		}
		System.out.println("*****解密后的数据*****::[" + mingwen + "]");
	}
}