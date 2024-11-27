package viewofbook;
import dao.studentDAO;
import database.ImageStorage;
import model.comment;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import model.student;

public class commentcontroller
{

    @FXML
    private Label commentField;

    @FXML
    private ImageView studentImage;

    @FXML
    private Label studentName;

    @FXML
    private Label date;

    public void setData(comment comment){
        commentField.setText( comment.getComment() );
        student studentSearch = new student();
        studentSearch.setStudentID(String.valueOf(comment.getStudentID()));
        student student = studentDAO.getInstance().getById(studentSearch);
        ImageStorage.loadStudentImage(String.valueOf(comment.getStudentID()), studentImage);
        studentName.setText(student.getName() + " #" + studentSearch.getStudentID());
        date.setText(comment.getCreatedAt().toString());
    }

}
