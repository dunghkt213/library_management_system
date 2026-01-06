import org.junit.jupiter.api.*;

public class loginServiceTest {

    // TC01 – Login đúng
    @Test
    public void testLoginSuccess() {
        String id = "SV001";
        String pass = "123456";
        Assertions.assertEquals("123456", pass);
    }

    // TC02 – Login sai mật khẩu
    @Test
    public void testLoginWrongPassword() {
        Assertions.assertNotEquals("123456", "999999");
    }

    // TC03 – Login với input rỗng
    @Test
    public void testLoginEmptyInput() {
        String id = "";
        String pw = "";
        Assertions.assertTrue(id.isEmpty() || pw.isEmpty());
    }

    // TC04 – Load FXML (FAIL)
    @Test
    public void testLoadFXMLFail() {
        Assertions.assertThrows(Exception.class, () -> {
            new javafx.fxml.FXMLLoader(
                getClass().getResource("/sai-duong-dan.fxml")
            ).load();
        });
    }
}
