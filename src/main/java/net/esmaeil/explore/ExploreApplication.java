package net.esmaeil.explore;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ExploreApplication extends Application {
    private static String[] arguments;
    private ConfigurableApplicationContext applicationContext;
    private Parent root;

    public static void main(String[] args) {
        arguments = args;
        launch(arguments);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Explore");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    @Override
    public void init() throws Exception {
        super.init();
        applicationContext = SpringApplication.run(ExploreApplication.class, arguments);

        FXMLLoader fxmlLoader = new FXMLLoader(applicationContext.getClassLoader().getResource("fxml/main.fxml"));
        fxmlLoader.setControllerFactory(applicationContext::getBean);
        root = fxmlLoader.load();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        applicationContext.close();
    }
}
