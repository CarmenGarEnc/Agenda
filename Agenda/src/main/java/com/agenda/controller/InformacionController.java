package com.agenda.controller;

import com.agenda.AgendaApplication;
import com.agenda.clases.ConexionBBDD;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.w3c.dom.Document;

//import java.awt.Desktop;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class InformacionController implements Initializable {

    @FXML
    private Button btnDescargar;

    @FXML
    private Button btnSalir;
    @FXML
    private Button btnVolver;

    @FXML
    private TextArea cajaTexto;
    @FXML
    private Hyperlink linkCompra;

    private String texto="";
    private String infoUsuarios=ConexionBBDD.informacionUsuario(BuscarController.getIdBuscar());
    private String inforegalos=ConexionBBDD.informacionRegalos(BuscarController.getIdBuscar());

    /**
     * Metodo initialize envia el texto a la caja de texto nada mas que se ejecute la escena
     * @param url URL
     * @param resourceBundle ResourceBundle
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        texto=infoUsuarios.concat(inforegalos);
        cajaTexto.setText(texto);
    }

    /**
     * Metodo comprar deriva a un navegador en caso de no haber regalos registrados o a otra escena en caso de que si tenga
     * @param event Actionevent
     * @throws URISyntaxException Excepcion para url no existentes, url no bien formada
     * @throws IOException Excepcion entrada/salida
     */
    @FXML
    void comprar(ActionEvent event)throws URISyntaxException, IOException {
        if (inforegalos.equals("" + BuscarController.getIdBuscar() + " no ha guardado sugerencia de regalos.")) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Comprar");
            alert.setContentText("El usuario no ha indicado que le gustaría de regalo. Desea ir a comprar de todos modos?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                if (java.awt.Desktop.isDesktopSupported()) {
                    Desktop desktop = java.awt.Desktop.getDesktop();
                    try {
                        URI url = new URI("https://www.amazon.es");
                        desktop.browse(url);
                    } catch (URISyntaxException | IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        } else {
                FXMLLoader fxmlLoader = new FXMLLoader(AgendaApplication.class.getResource("comprar.fxml"));
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
                Stage stagePrincipal=(Stage) linkCompra.getScene().getWindow();
                stagePrincipal.close();
        }
    }

    /**
     * Método descargar Fichero, al ejecutar el boton btnDescargar se genera un fichero con la información del usuario que se ha mostrado
     * @param event ActionEvent
     */
    @FXML
    void descargarFichero(ActionEvent event) {
        File fichero=new File(BuscarController.getIdBuscar()+".txt");
        try {
            FileWriter escribir=new FileWriter(fichero);
            escribir.write(texto);
            Alert alertok=new Alert(Alert.AlertType.INFORMATION);
            alertok.setTitle("Archivo descargado");
            alertok.setContentText("Podrá encontrar el archivo en: "+fichero.getAbsolutePath());
            alertok.showAndWait();
            escribir.close();
        } catch (IOException ex) {//Error relacionado con la entrada y salida del programa
            Alert alerterror=new Alert(Alert.AlertType.ERROR);
            alerterror.setTitle("Error archivo no descargado");
            alerterror.setContentText("No se ha podido descargar el archivo");
            alerterror.showAndWait();
        }

    }

    /**
     * Metodo salir, una vez se pulsa el botón btnSalir, el programa cierra su ejecución
     * @param event ActionEvent
     */
    @FXML
    void salir(ActionEvent event) {
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cerrando...");
        alert.setContentText("Quieres Salir?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get()== ButtonType.OK){
            Stage stagePrincipal=(Stage) btnSalir.getScene().getWindow();
            stagePrincipal.close();
            ConexionBBDD.desconectar();
        }
    }

    /**
     * Metodo volver, una vez se pulsa el boton btnVolver, vuelve a la escena buscar
     * @param event ActionEvent
     */
    @FXML
    void volver(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(AgendaApplication.class.getResource("buscar.fxml"));
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

}
