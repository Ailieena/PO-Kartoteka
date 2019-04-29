package kartoteka;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Card {
    String imgSrc;

    private double x;
    private double y;

    private Image image;
    private ImageView imageView;
    private EventHandler<MouseEvent> dragEvent;
    private EventHandler<MouseEvent> returnEvent;

    public Card(String imgSrc, Pane group) throws FileNotFoundException {
        this.imgSrc = imgSrc;
        image = new Image(new FileInputStream(imgSrc));
        imageView = new ImageView(image);
        imageView.setFitWidth(100);
        imageView.setFitHeight(150);
        group.getChildren().addAll(imageView);

        dragEvent = e->{
            set(e.getX(), e.getY());
        };

        returnEvent = e->{
            set(x, y);
            System.out.println("a");
        };
    }

    public Card setMovable(boolean b) {
        if(b) {
            imageView.addEventHandler(MouseEvent.MOUSE_DRAGGED, dragEvent);
        }
        else {
            imageView.removeEventHandler(MouseEvent.MOUSE_DRAGGED, dragEvent);
        }
        return this;
    }

    public Card setReturn(boolean b) {
        if(b) {
            imageView.addEventHandler(MouseEvent.MOUSE_RELEASED, returnEvent);
        }
        else {
            imageView.removeEventHandler(MouseEvent.MOUSE_RELEASED, returnEvent);
        }
        return this;
    }

    public void set(double x, double y) {
        imageView.setX(x);
        imageView.setY(y);
    }
}
