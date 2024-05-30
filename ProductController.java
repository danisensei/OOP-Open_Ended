import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import java.net.URL;
import java.util.ResourceBundle;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class ProductController implements Initializable {
    @FXML
    private TilePane Tile;
    public VBox createCustomVBox(String name, String price, String imagepath) {
        Label nameLabel = new Label(name);
        nameLabel.setAlignment(javafx.geometry.Pos.TOP_CENTER);
        nameLabel.setPrefSize(222, 17);

        ImageView imageView = new ImageView(new Image(imagepath));
        imageView.setFitHeight(118);
        imageView.setFitWidth(153);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);

        Label priceLabel = new Label(price);
        priceLabel.setAlignment(javafx.geometry.Pos.BOTTOM_CENTER);
        priceLabel.setPrefSize(206, 17);
        priceLabel.setFont(new Font("System Bold", 12));

        Label dollarLabel = new Label("$");
        dollarLabel.setAlignment(javafx.geometry.Pos.BOTTOM_CENTER);
        dollarLabel.setPrefSize(206, 17);
        dollarLabel.setFont(new Font("System Bold", 12));

        VBox vbox = new VBox();
        vbox.setAlignment(javafx.geometry.Pos.CENTER);
        vbox.setPrefSize(200, 200);
        vbox.getChildren().addAll(nameLabel, imageView, priceLabel, dollarLabel);

        return vbox;
    }

    private DatabaseConnection databaseConnection;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        databaseConnection = new DatabaseConnection();
        try {
            connection = databaseConnection.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM products");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
               String name, price, imagepath;
               name = resultSet.getString("name");
                price = resultSet.getString("price");
                imagepath = resultSet.getString("image");
                VBox box = createCustomVBox(name, price, imagepath);
                Tile.getChildren().add(box);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
