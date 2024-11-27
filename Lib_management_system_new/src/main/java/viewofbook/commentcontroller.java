package viewofbook;

import dao.commentDAO;
import dao.likeDAO;
import dao.studentDAO;
import database.ImageStorage;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import model.comment;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import model.likes;
import model.student;
import org.controlsfx.control.Rating;

import java.io.IOException;
import java.nio.Buffer;

public class commentcontroller {

    @FXML
    private Label commentField;

    @FXML
    private ImageView studentImage;

    @FXML
    private Label studentName;

    @FXML
    private Label date;

    @FXML
    private Button like;

    @FXML
    ImageView heartimgae;

    @FXML
    private Label countoflike;

    @FXML Rating rating;

    public void setData(comment comment) {
        commentField.setText(comment.getComment());
        student studentSearch = new student();
        studentSearch.setStudentID(String.valueOf(comment.getStudentID()));
        student student = studentDAO.getInstance().getById(studentSearch);
        ImageStorage.loadStudentImage(String.valueOf(comment.getStudentID()), studentImage);
        studentName.setText(student.getName() + " #" + studentSearch.getStudentID());
        date.setText(comment.getCreatedAt().toString());
        countoflike.setText(String.valueOf(comment.getCountoflike()));
        rating.setRating((double) comment.getRating());
        System.out.println((double)comment.getRating());
        int studentID = Integer.parseInt(model.student.getInstance().getStudentID());
        boolean checkliked = likeDAO.getInstance().hasLiked(studentID, comment.getId());
        if (checkliked) {
            heartimgae.setImage(new Image(getClass().getResource("/viewofbook/heart.png").toExternalForm()));
        } else {
            heartimgae.setImage(new Image(getClass().getResource("/viewofbook/heartEmpty.png").toExternalForm()));
        }
        like.setOnAction(event -> {
            try {
                likeaction(comment);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void likeaction(comment comment) throws IOException {
        int studentID = Integer.parseInt(model.student.getInstance().getStudentID());
        boolean checkliked = likeDAO.getInstance().hasLiked(studentID, comment.getId());
        if (checkliked == true) {
            comment.setCountoflike(comment.getCountoflike() - 1);
            heartimgae.setImage(new Image(getClass().getResource("/viewofbook/heartEmpty.png").toExternalForm()));
            countoflike.setText(String.valueOf(comment.getCountoflike()));
            likes likes = new likes(studentID, comment.getId());
            int result = likeDAO.getInstance().delete(likes);
            if(result != 0)  commentDAO.getInstance().update(comment);
        } else {
            comment.setCountoflike(comment.getCountoflike() + 1);
            heartimgae.setImage(new Image(getClass().getResource("/viewofbook/heart.png").toExternalForm()));
            countoflike.setText(String.valueOf(comment.getCountoflike()));
            likes likes = new likes(studentID, comment.getId());
           int result = likeDAO.getInstance().insert(likes);
            if(result != 0) commentDAO.getInstance().update(comment);
        }
    }

}


