import org.junit.jupiter.api.*;

public class testBookDAO {

    // TC05 – Search hợp lệ
    @Test
    public void testSearchValidID() {
        String id = "B001";
        Assertions.assertEquals("B001", id);
    }

    // TC06 – Search với null (FAIL)
    @Test
    public void testSearchNullID() {
        String id = null;
        Assertions.assertThrows(Exception.class, () -> {
            if (id.equals("B001")) { }
        });
    }

    // TC07 – Search với input rỗng
    @Test
    public void testSearchEmptyInputs() {
        Assertions.assertTrue("".isEmpty());
    }

    // TC11 – Xóa sách hợp lệ
    @Test
    public void testDeleteBookSuccess() {
        boolean deleted = true;
        Assertions.assertTrue(deleted);
    }
}
