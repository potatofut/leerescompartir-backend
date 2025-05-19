package com.compartir.libros.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.apache.commons.codec.binary.Base64;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;
import com.compartir.libros.dto.email.GmailCredential;
import com.compartir.libros.dto.email.GoogleTokenResponse;

import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.mail.Message.RecipientType;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.Session;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import jakarta.mail.util.ByteArrayDataSource;
import lombok.SneakyThrows;

import java.io.IOException;
import java.util.Properties;

@Service
public class GmailAPIService {

    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private HttpTransport httpTransport;
    private GmailCredential gmailCredential;

    @Value("${spring.google.client-id}")
    private String clientId;
    @Value("${spring.google.client-secret}")
    private String secretKey;
    @Value("${spring.google.refresh-token}")
    private String refreshToken;
    @Value("${spring.google.from-email}")
    private String fromEmail;

    @SneakyThrows
    public GmailAPIService() {

        this.httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        this.gmailCredential = new GmailCredential(
                clientId,
                secretKey,
                refreshToken,
                null,
                null,
                fromEmail);
    }

    public boolean sendMessage(
            String toEmail,
            String subject,
            String body,
            MultipartFile attachment) throws MessagingException, IOException {

        refreshAccessToken();

        Message message = createMessageWithEmail(
                createEmail(toEmail, gmailCredential.userEmail(), subject, body, attachment));

        return createGmail()
                .users()
                .messages()
                .send(gmailCredential.userEmail(), message)
                .execute()
                .getLabelIds()
                .contains("SENT");
    }

    private Gmail createGmail() {
        Credential credential = authorize();
        return new Gmail.Builder(httpTransport, JSON_FACTORY, credential)
                .build();
    }

    private MimeMessage createEmail(
            String to,
            String from,
            String subject,
            String bodyText,
            MultipartFile attachment) throws MessagingException {

        MimeMessage email = new MimeMessage(Session.getDefaultInstance(new Properties(), null));

        email.setFrom(new InternetAddress(from));
        email.addRecipient(RecipientType.TO, new InternetAddress(to));
        email.setSubject(subject);

        Multipart multipart = new MimeMultipart();
        MimeBodyPart bodyPart = new MimeBodyPart();
        bodyPart.setContent(bodyText, "text/html; charset=utf-8");
        multipart.addBodyPart(bodyPart);

        if (attachment != null && !attachment.isEmpty()) {
            bodyPart = new MimeBodyPart();

            try {
                DataSource ds = new ByteArrayDataSource(attachment.getBytes(), attachment.getContentType());
                bodyPart.setDataHandler(new DataHandler(ds));
                bodyPart.setFileName(attachment.getOriginalFilename());
                multipart.addBodyPart(bodyPart);
            } catch (Exception e) {
                e.printStackTrace();
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Not able to process request");
            }
        }

        email.setContent(multipart);
        
        return email;
    }

    private Message createMessageWithEmail(MimeMessage emailContent)
            throws MessagingException, IOException {

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        emailContent.writeTo(buffer);

        return new Message()
                .setRaw(Base64.encodeBase64URLSafeString(buffer.toByteArray()));
    }

    private Credential authorize() {

        try {
            TokenResponse tokenResponse = refreshAccessToken();
            return new Credential(BearerToken.authorizationHeaderAccessMethod()).setFromTokenResponse(
                    tokenResponse);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Not able to process request.");
        }
    }

    private TokenResponse refreshAccessToken() {

        RestTemplate restTemplate = new RestTemplate();
        GmailCredential gmailCredentialsDto = new GmailCredential(
                clientId,
                secretKey,
                refreshToken,
                "refresh_token",
                null,
                null);

        HttpEntity<GmailCredential> entity = new HttpEntity<>(gmailCredentialsDto);

        try {

            GoogleTokenResponse response = restTemplate.postForObject(
                    "https://www.googleapis.com/oauth2/v4/token",
                    entity,
                    GoogleTokenResponse.class);

            gmailCredential = new GmailCredential(
                    clientId,
                    secretKey,
                    refreshToken,
                    null,
                    response.getAccessToken(),
                    fromEmail);

            return response;

        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Not able to process request.");
        }
    }
}
