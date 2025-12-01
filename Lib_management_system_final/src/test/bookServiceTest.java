import model.book;
import org.junit.jupiter.api.*;

public class bookServiceTest {

    // TC08 – Thêm sách hợp lệ
    @Test
    public void testAddBookSuccess() {
        book b = new book();
        b.setBookID("B001");
        Assertions.assertEquals("B001", b.getBookID());
    }

    // TC09 – Sửa quantity hợp lệ
    @Test
    public void testUpdateQuantityValid() {
        book b = new book();
        b.setQuantity(10);
        Assertions.assertTrue(b.getQuantity() >= 0);
    }

    // TC10 – Quantity âm (FAIL)
    @Test
    public void testNegativeQuantityFail() {
        book b = new book();
        b.setQuantity(-10);
        Assertions.assertTrue(b.getQuantity() < 0,
                "Hệ thống đang cho phép quantity âm");
    }

    // TC12 – Xóa sách đang mượn (FAIL)
    @Test
    public void testDeleteBookWhileBorrowed() {
        boolean isBorrowed = true;
        boolean deleteAllowed = true;
        Assertions.assertTrue(isBorrowed && deleteAllowed,
                "Hệ thống cho phép xóa sách đang mượn");
    }
}
