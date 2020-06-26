package net.esmaeil.explore.ui.component;

import com.google.common.io.Files;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import net.esmaeil.explore.ui.utils.Size;

import java.io.File;
import java.util.Arrays;
import java.util.Map;

public class FileTree {
    public static final String ROOT_FOLDER_GRAPHIC_KEY = "root_file_tree_folder";
    public static final String FOLDER_GRAPHIC_KEY = "file_tree_folder";
    public static final String FILE_GRAPHIC_KEY = "file_tree_extension_%s";
    public static final String DEFAULT_FILE_GRAPHIC_KEY = "file_tree_default";
    public static final Size FILE_TREE_ICON_SIZE = new Size(15, 15);
    private final TreeView<File> treeView;
    private final File[] roots;
    private final Map<String, Image> imageMap;
    private final boolean showFiles;

    public FileTree(TreeView<File> treeView, Map<String, Image> imageMap, boolean showFiles, File... rootFolders) {
        this.treeView = treeView;
        this.roots = rootFolders;
        this.imageMap = imageMap;
        this.showFiles = showFiles;
    }

    public void init() {
        TreeItem<File> rootItem = new TreeItem<>();
        for (File root : roots)
            if (root.exists())
                rootItem.getChildren().add(createTree(root));
        rootItem.setExpanded(true);
        rootItem.setValue(new File("/This PC"));
        rootItem.setGraphic(new ImageView(imageMap.get(ROOT_FOLDER_GRAPHIC_KEY)));
        treeView.setRoot(rootItem);
        treeView.setCellFactory((e) -> new TreeCell<File>() {
            @Override
            protected void updateItem(File item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    if (item.getName().isEmpty())
                        setText(item.getPath());
                    else
                        setText(item.getName());
                    setGraphic(getTreeItem().getGraphic());
                } else {
                    setText("");
                    setGraphic(null);
                }
            }
        });
    }

    private TreeItem<File> createTree(File file) {
        TreeItem<File> item = new TreeItem<>(file);
        File[] children = file.listFiles(child -> child.isDirectory() || showFiles);
        if (children != null)
            if (children.length > 0) {
                Arrays.sort(children, (file1, file2) -> {
                    if (file1.isDirectory() == file2.isDirectory())
                        return 0;
                    if (file1.isDirectory())
                        return -1;
                    return 1;
                });
                item.addEventHandler(TreeItem.branchExpandedEvent(), event -> {
                    if (item.getChildren().get(0).getValue() == null) {
                        item.getChildren().clear();
                        for (File child : children)
                            item.getChildren().add(createTree(child));
                    }
                });
                item.getChildren().add(new TreeItem<>());
            }
        if (item.getValue().isDirectory()) {
            item.setGraphic(new ImageView(imageMap.get(FOLDER_GRAPHIC_KEY)));
        } else {
            String extension = Files.getFileExtension(item.getValue().getName());
            Image fileGraphic = imageMap.getOrDefault(String.format(FILE_GRAPHIC_KEY, extension), imageMap.get(DEFAULT_FILE_GRAPHIC_KEY));
            item.setGraphic(new ImageView(fileGraphic));
        }
        return item;
    }

    public File[] getRoots() {
        return roots;
    }
}
