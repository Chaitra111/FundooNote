package com.bridgelabz.ToDo_1.utility;

import com.bridgelabz.ToDo_1.userservice.model.User;
import com.bridgelabz.ToDo_1.utility.exceptionService.ToDoException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;


/**
 * @author Chaitra Ankolekar
 * Purpose :Utility class for validation
 */
public class Utility {
	static final String KEY = "chaitra";

	public Utility() {

	}
	/**
	 * method validate the fields when registering
	 * @param register
	 * @return
	 * @throws UserExceptionHandling
	 */

	/*public boolean isValidateAllFields(RegistrationModel register) throws UserExceptionHandler {
		if (!validateEmailAddress(register.getEmailId())) {
			throw new UserExceptionHandler("emailid not valid  Exception");
		} else if (!isValidUserName(register.getUserName())) {
			throw new UserExceptionHandler("UserName Not valid  Exception");
		} else if (!validatePassword(register.getPassword())) {
			throw new UserExceptionHandler("password not valid Exception");
		} else if (!isValidMobileNumber(register.getPhoneNumber())) {
			throw new UserExceptionHandler("mobilenumber not valid  Exception");
		}
		return false;
	}

	public static boolean validateEmailAddress(String emailId) {
		Pattern emailNamePtrn = Pattern
				.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
		Matcher mtch = emailNamePtrn.matcher(emailId);
		return mtch.matches();

	}

	public static boolean validatePassword(String password) {
		Pattern pattern = Pattern.compile("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}");
		Matcher matcher = pattern.matcher(password);
		return matcher.matches();

	}

	public static boolean isValidUserName(String userName) {
		Pattern userNamePattern = Pattern.compile("^[a-z0-9_-]{6,14}$");
		Matcher matcher = userNamePattern.matcher(userName);
		return matcher.matches();

	}

	public static boolean isValidMobileNumber(String mobileNumber) {
		Pattern mobileNumberPattern = Pattern.compile("\\d{10}");
		Matcher matcher = mobileNumberPattern.matcher(mobileNumber);
		return matcher.matches();
	}*/

	/**
	 * to create a jwt token
	 * 
	 * @param id
	 * @return
	 */
	public String createToken(String id) {

		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

		Date date = new Date();

		JwtBuilder builder = Jwts.builder().setId(id).setIssuedAt(date).signWith(signatureAlgorithm, KEY);
		return builder.compact();

	}

	/**
	 * to decode the jwt token
	 * 
	 * @param jwt
	 * @return
	 * @throws ToDoException 
	 */
	public Claims parseJwt(String jwt) throws ToDoException {
		System.out.println(jwt);
		 return Jwts.parser().setSigningKey(KEY).parseClaimsJws(jwt).getBody();
		/*try {
			Claims claims = Jwts.parser().setSigningKey("KEY").parseClaimsJws(jwt).getBody();
			return claims;
		} catch (UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
			throw new ToDoException("Error in JWT Token");
		} catch (ExpiredJwtException e) {
			throw new ToDoException("Token Expired");
		}*/
	}

	/**
	 * @param user
	 * @return token
	 */
	public String createTokens(User user) {
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		//String id=user.getUserId();
		String subject = user.getEmailId();
		//String issuer = user.getUserName();
		String issuer = user.getUserId();
		Date date = new Date();
		JwtBuilder builder = Jwts.builder().setSubject(subject).setIssuedAt(date).setIssuer(issuer)
				.signWith(signatureAlgorithm, KEY);
		return builder.compact();
	}
}
