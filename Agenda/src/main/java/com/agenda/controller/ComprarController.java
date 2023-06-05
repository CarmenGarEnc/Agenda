package com.agenda.controller;

import com.agenda.AgendaApplication;
import com.agenda.clases.ConexionBBDD;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class ComprarController implements Initializable {
    @FXML
    private ImageView regaloImagen;
    @FXML
    private Button btnBuscar;

    @FXML
    private Button btnCerrar;

    @FXML
    private Button btnVolver;
    @FXML
    public ChoiceBox<String> cboxRegalos;
    ArrayList<String> regalos= ConexionBBDD.listarRegalos(BuscarController.getIdBuscar());

    /**
     * Metodo initialize da los valores a cboxRegalos
     * @param url URL
     * @param resourceBundle ResourceBundle
     */
    public void  initialize(URL url, ResourceBundle resourceBundle){
        //Esto sirve para rellenar el comboBox inicialmente con los regalos
        cboxRegalos.getItems().addAll(regalos);
        cboxRegalos.setValue(regalos.get(0));
    }

    /**
     * Metodo buscar abre el navegador con la ruta generada del url mas el regalo almacenado
     * @param event ActionEvent
     */
    @FXML
    void buscar(ActionEvent event) {
        String regalo=cboxRegalos.getValue().trim().replaceAll("\\s","+").replaceAll("á","a").replaceAll("é","e").replaceAll("í","i").replaceAll("ó","o").replaceAll("ú","u").replaceAll("\\.","+");
        if(java.awt.Desktop.isDesktopSupported()){
            Desktop desktop=java.awt.Desktop.getDesktop();
            try{
                URI url=new URI("https://www.amazon.es/s?k="+regalo);
                desktop.browse(url);
            }catch (URISyntaxException | IOException ex){
                ex.printStackTrace();
            }
        }
    }
    /**
     * Metodo volver, una vez se pulsa el boton btnVolver, vuelve a la escena informacion
     * @param event ActionEvent
     */
    @FXML
    void volver(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(AgendaApplication.class.getResource("informacion.fxml"));
        try {
            Parent rt = fxmlLoader.load();
            Scene scene = new Scene(rt);
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stagePrincipal=(Stage) btnVolver.getScene().getWindow();
        stagePrincipal.close();
    }
    /**
     * Metodo cerrar, una vez se pulsa el botón btnCerrar, el programa cierra su ejecución
     * @param event ActionEvent
     */
    @FXML
    void cerrar(ActionEvent event) {
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cerrando...");
        alert.setContentText("Quieres Salir?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get()== ButtonType.OK){
            Stage stagePrincipal=(Stage) btnCerrar.getScene().getWindow();
            stagePrincipal.close();
            ConexionBBDD.desconectar();
        }
    }

}
