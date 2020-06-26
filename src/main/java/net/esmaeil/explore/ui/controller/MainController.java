package net.esmaeil.explore.ui.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import net.esmaeil.explore.CoreConstants;
import net.esmaeil.explore.CoreEvents;
import net.esmaeil.explore.event.AggregatorEvent;
import net.esmaeil.explore.event.EventManager;
import net.esmaeil.explore.graphic.GraphicManager;
import net.esmaeil.explore.language.LanguageManager;
import net.esmaeil.explore.ui.component.FileTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

@Component
public class MainController implements Initializable {
    private final EventManager eventManager;
    private final GraphicManager graphicManager;
    private final LanguageManager languageManager;
    @FXML
    private BorderPane rootPane;
    @FXML
    private MenuBar menuBar;
    @FXML
    private ToolBar toolBar;
    @FXML
    private TreeView<File> fileTree;
    @FXML
    private Label searchPathLabel;
    @FXML
    private Label statusLabel;
    @FXML
    private Button searchButton;
    @FXML
    private Button clearButton;
    @FXML
    private TextField queryField;

    @Autowired
    public MainController(EventManager eventManager, GraphicManager graphicManager, LanguageManager languageManager) {
        this.eventManager = eventManager;
        this.graphicManager = graphicManager;
        this.languageManager = languageManager;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        rootPane.setNodeOrientation(languageManager.getCurrentLanguage().getOrientation());

        AggregatorEvent<String, Menu> menuBarEvent = new AggregatorEvent<>("menuBar");
        eventManager.event(menuBarEvent, CoreEvents.BEFORE_MENU_BAR_CREATION);
        menuBar.getMenus().addAll(menuBarEvent.getResult());

        AggregatorEvent<String, Button> toolBoxEvent = new AggregatorEvent<>("toolBar");
        eventManager.event(toolBoxEvent, CoreEvents.BEFORE_TOOL_BOX_CREATION);
        toolBar.getItems().addAll(toolBoxEvent.getResult());

        FileTree myFileTree = new FileTree(fileTree, graphicManager.getGraphics(CoreConstants.CORE_PLUGIN_ID, FileTree.FILE_TREE_ICON_SIZE), false, File.listRoots());
        myFileTree.init();
        fileTree.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> searchPathLabel.setText(newValue.getValue().getAbsolutePath())
        );
        searchPathLabel.setText("");
        searchPathLabel.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);

        statusLabel.setText(languageManager.getValue(CoreConstants.CORE_PLUGIN_ID, CoreConstants.UI.WELCOME_MESSAGE_KEY));

        searchButton.setText(languageManager.getValue(CoreConstants.CORE_PLUGIN_ID, CoreConstants.UI.SEARCH_BUTTON_TEXT_KEY));

        clearButton.setText(languageManager.getValue(CoreConstants.CORE_PLUGIN_ID, CoreConstants.UI.CLEAR_BUTTON_TEXT_KEY));

        queryField.setPromptText(languageManager.getValue(CoreConstants.CORE_PLUGIN_ID, CoreConstants.UI.QUERY_PROMPT));
    }
}
