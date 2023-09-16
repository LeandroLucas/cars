/**
 * Mar 11, 2020
 */
package com.company.carsapi.utils;

import com.company.carsapi.exceptions.EncryptPasswordException;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Objects;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;


public class CryptUtils {

	public static String encryptPassword(String login, String password) {
		Objects.requireNonNull(login, "login can't be null");
		Objects.requireNonNull(password, "password can't be null");
		try {
			byte[] encryptedPassword = CryptUtils.getEncryptedPassword(password, login.getBytes());
			StringBuilder hexString = new StringBuilder();
			for (byte b : encryptedPassword) {
				hexString.append(String.format("%02X", 0xFF & b));
			}
			return hexString.toString();
		} catch (Exception e) {
			throw new EncryptPasswordException();
		}
	}

	private static byte[] getEncryptedPassword(String password, byte[] salt)
			throws NoSuchAlgorithmException, InvalidKeySpecException {

		String algorithm = "PBKDF2WithHmacSHA1";

		int derivedKeyLength = 160; //160 bits (20 bytes)

		/*
		 * A quantidade de iterações é um fator crítico para a segurança das senhas.
		 * Um número maior de iterações torna mais difícil realizar
		 * ataques de força bruta ou de dicionário, pois cada tentativa de
		 * adivinhar a senha requer um tempo significativo para calcular a chave derivada.
		 */
		int iterations = 20000;

		KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, derivedKeyLength);

		SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(algorithm);

		return secretKeyFactory.generateSecret(spec).getEncoded();
	}
}
