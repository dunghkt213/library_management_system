package API;

public class Main {
    public static void main(String[] args) {
        try {
            GMailer gMailer = new GMailer();

            String recipientEmail = "dung1990hk@gmail.com";
            String subject = "Test Email from GMailer";
            String bodyText = "This is a test email sent using Gmail API.";

            // Gửi email
            gMailer.sendEmail(recipientEmail, subject, bodyText);
            System.out.println("Email sent successfully!");
        } catch (Exception e) {
            System.out.println("An error occurred while sending the email.");
            e.printStackTrace();
        }
    }
}

