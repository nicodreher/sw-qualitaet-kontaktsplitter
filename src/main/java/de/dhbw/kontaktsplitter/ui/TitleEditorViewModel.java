package de.dhbw.kontaktsplitter.ui;

import de.dhbw.kontaktsplitter.models.Title;
import de.dhbw.kontaktsplitter.persistence.Configuration;
import de.dhbw.kontaktsplitter.ui.components.ElementEditor;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * @author Daniel Bornbaum
 */
public class TitleEditorViewModel implements Initializable
{
    @FXML
    private VBox topBox;

    @FXML
    private TextField newEntryField;

    @FXML
    private Button newEntryButton;

    private ElementEditor editor = new ElementEditor();

    /**
     * Overwrites the initialize method from Initializable, sets ui handlers, extends ui from fxml
     * @param url see package javafx.fxml.Initializable
     * @param resourceBundle see package javafx.fxml.Initializable
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        topBox.getChildren().setAll(editor.getElementsView());
        editor.updateElements(Configuration.getTitles().stream().map(Title::getTitle).collect(Collectors.toList()));

        newEntryButton.setOnAction(event -> {
            editor.addListElement(newEntryField.getText());
            newEntryField.clear();
        });
        newEntryButton.setDisable(true);
        newEntryField.setOnKeyReleased(keyEvent -> {
            if ("".equals(newEntryField.getText()))
            {
                newEntryButton.setDisable(true);
            }
            else
            {
                newEntryButton.setDisable(false);
            }
        });
    }

    /**
     * @return potentially modified titles in this element
     */
    public List<Title> getTitles()
    {
        return editor.getElements().stream().map(Title::new).collect(Collectors.toList());
    }
}
