package com.upc.backendnutrimiski.services;

import com.upc.backendnutrimiski.models.Child;
import com.upc.backendnutrimiski.models.User;
import com.upc.backendnutrimiski.util.Encryption;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.tomcat.jni.Local;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import javax.crypto.spec.SecretKeySpec;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;


public class UtilService {

    private static final ZoneId oldZone = ZoneId.of(TimeZone.getDefault().getID());

    public static LocalDate getNowDate(){
        LocalDateTime oldDateTime = LocalDateTime.now();

        ZoneId newZone = ZoneId.of("America/Lima");

        LocalDateTime newDateTime = oldDateTime.atZone(oldZone)
                .withZoneSameInstant(newZone)
                .toLocalDateTime();

        System.out.println(newDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE));
        System.out.println(newDateTime.toLocalDate());

        return newDateTime.toLocalDate();
    }

    public static LocalDate getNowDateMealsWhitAddDays(Integer days){
        return getNowDate().plusDays(days);
    }

    public static Double getCaloriesForChild(Child child){
        double calorias = 0;
        double factorEjercicio = 1.2;

        if (child.getSex().equals("M"))
            calorias = (655 + (9.6 * child.getWeight())) + ((1.8 * child.getHeight()) - (4.7 * child.getAge())) * factorEjercicio;
        else
            calorias = (66 + (13.7 * child.getWeight())) + ((5 * child.getHeight()) - (6.8 * child.getAge())) * factorEjercicio;
        return calorias;
    }

    public static Integer getActualAge(LocalDate date){
        Period age = Period.between(date, getNowDate());
        return  age.getYears();
    }

    public static String randomString() {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        int length = 5;
        for(int i = 0; i < length; i++) {
            int index = random.nextInt(alphabet.length());
            char randomChar = alphabet.charAt(index);
            sb.append(randomChar);
        }
        String randomString = sb.toString();
        return randomString;
    }

    public static String randomPassword() {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxz1234567890";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        int length = 8;
        for(int i = 0; i < length; i++) {
            int index = random.nextInt(alphabet.length());
            char randomChar = alphabet.charAt(index);
            sb.append(randomChar);
        }
        String randomString = sb.toString();
        return randomString;
    }

    public static String  encriptarContrasena (String contrasena){

        byte[] salt = new String("12345678").getBytes();
        int iterationCount = 40000;
        int keyLength = 128;
        SecretKeySpec key = null;
        try {
            key = Encryption.createSecretKey("contrasena".toCharArray(), salt, iterationCount, keyLength);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        String nueva = contrasena;
        try {
            nueva = Encryption.encrypt(contrasena, key);
        } catch (GeneralSecurityException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return nueva;
    }


    public static String  desencriptarContrasena (String contrasena){

        byte[] salt = new String("12345678").getBytes();
        int iterationCount = 40000;
        int keyLength = 128;
        SecretKeySpec key = null;
        try {
            key = Encryption.createSecretKey("contrasena".toCharArray(), salt, iterationCount, keyLength);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        String nueva = contrasena;
        try {
            nueva = Encryption.decrypt(contrasena, key);
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }

        return nueva;
    }


    public static String sendForgetPassword(User user, String newPassword){
        Properties properties = new Properties();

        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.starttls.required", "true");
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        String myAccountEmail = "nutrimiskyapp@gmail.com";
        String password = "bryanxd123";

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccountEmail,password);
            }
        });

        Message message = prepareMessage(session, myAccountEmail, user, newPassword);

        try {
            Transport.send(message);
            System.out.println("Mensaje enviado correctamente");
            return "Se ha enviado el email";
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static Message prepareMessage(Session session, String myAccountEmail, User user, String newPassword) {
        Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));
            message.setSubject("Recuperaci??n de contrase??a - FullFeedApp");
            StringBuffer content = new StringBuffer();
            content.append("Estimado ").append(user.getFirstName()).append(" ").append(user.getLastName()).append(",\n\n")
                    .append("Su contrase??a se ha reiniciado a : ").append(newPassword).append("\n")
                    .append("No se olvide de actualizarla antes de seguir usando la aplicaci??n.").append("\n\n")
                    .append("Atentamente la familida de FullFeedApp.");
            message.setText(content.toString());
            return message;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static String getJWTToken(String username) {
        String secretKey = "mySecretKey";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");

        String token = Jwts
                .builder()
                .setId("softtekJWT")
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 60000000))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();

        return token;
    }
}
