package API;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.Message;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.io.*;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Properties;

public class GMailer {
    private static final String APPLICATION_NAME = "Library Management System";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private Gmail service;

    public GMailer() throws Exception {
        service = getGmailService();
    }

    private Gmail getGmailService() throws Exception {
        Credential credential = getCredentials();
        return new Gmail.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY,
                credential
        ).setApplicationName(APPLICATION_NAME).build();
    }

    private Credential getCredentials() throws Exception {
        InputStream in = getClass().getClassLoader().getResourceAsStream("credentials.json");
        if (in == null) {
            System.out.println("Classpath: " + System.getProperty("java.class.path"));
            System.out.println("File: " + getClass().getClassLoader().getResource("credentials.json"));
            throw new FileNotFoundException("Resource file 'credentials.json' not found in classpath.");
        }

        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY,
                clientSecrets,
                Collections.singletonList(GmailScopes.GMAIL_SEND)
        ).setAccessType("offline").build();

        String authorizationUrl = flow.newAuthorizationUrl().setRedirectUri("urn:ietf:wg:oauth:2.0:oob").build();
        System.out.println("Please open the following URL in your browser to authorize:");
        System.out.println(authorizationUrl);
        System.out.print("Enter the authorization code: ");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String authorizationCode = br.readLine();
        return flow.createAndStoreCredential(flow.newTokenRequest(authorizationCode)
                .setRedirectUri("urn:ietf:wg:oauth:2.0:oob").execute(), "user");
    }


    public void sendEmail(String recipientEmail, String subject, String bodyText) throws MessagingException, IOException {
        MimeMessage email = createEmail(recipientEmail, "23021477@vnu.edu.vn", subject, bodyText);
        Message message = createMessageWithEmail(email);
        service.users().messages().send("me", message).execute();
    }

    private MimeMessage createEmail(String to, String from, String subject, String bodyText) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        MimeMessage email = new MimeMessage(javax.mail.Session.getDefaultInstance(props));
        email.setFrom(new InternetAddress(from));
        email.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(to));
        email.setSubject(subject);
        email.setText(bodyText);
        return email;
    }

    private Message createMessageWithEmail(MimeMessage email) throws IOException, MessagingException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        email.writeTo(buffer);
        byte[] rawEmail = buffer.toByteArray();
        return new Message().setRaw(java.util.Base64.getUrlEncoder().encodeToString(rawEmail));
    }
}
