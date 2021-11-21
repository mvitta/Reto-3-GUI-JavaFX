import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;

public class reto3Controller extends reto3 implements Initializable {

    private int ID;
    private String nombre;
    private String municipio;
    private String tipoCuerpoAgua;
    private String tipoAgua;
    private float irca;
    private static int indice = 0;
    private float _cont;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField textNombre;

    @FXML
    private TextField textID;

    @FXML
    private TextField textMunicipio;

    @FXML
    private TextField textTipoCuerpoAgua;

    @FXML
    private TextField textTipoAgua;

    @FXML
    private TextField textIrca;

    @FXML
    private Button btnIngresar;

    @FXML
    private Button btnProcesar;

    @FXML
    private TextArea entradas;

    @FXML
    private TextArea salidas;

    @FXML
    private Button limpiarCampos;

    @FXML
    private Button borrarAreaEntradas;

    @FXML
    private Button borrarRegistro;

    @FXML
    private Label estadoRegistro;

    @FXML
    private Button LimpiarSalidas;

    @FXML
    void eventoLimpiarSalidas(ActionEvent event) {
        salidas.setText("");
    }

    @FXML
    void eventoBorrarAreaEntradas(ActionEvent event) {
        entradas.setText("");
        cuerpos.clear();
        indice = 0;
        _cont = 0;
    }

    @FXML
    void eventoBorrarRegistro(ActionEvent event) {
        String[] temporal = entradas.getText().split("\n");

        ArrayList<String> contenidoEntradas = new ArrayList<>();

        if ((temporal[0] == "" || temporal[0] != "") && indice > 0) {
            for (int i = 0; i < temporal.length; i++) {
                contenidoEntradas.add(temporal[i]);
            }
            String[] ultimo = contenidoEntradas.remove(contenidoEntradas.size() - 1).split(" ");
            cuerpos.remove(cuerpos.size() - 1);

            if (super.nivel(Float.parseFloat(ultimo[ultimo.length - 1])).equals("ALTO")) {
                riesgoAlto.remove(riesgoAlto.size() - 1);
            }
            indice--;
            entradas.setText("");
            for (String string : contenidoEntradas) {
                entradas.appendText(string + "\n");
            }
        }

    }

    @FXML
    void eventoIngresar(ActionEvent event) {
        String estado = "";
        String error = "";
        // valida nombre
        this.nombre = textNombre.getText();
        if (nombre.equals("") || !verificarCadena(this.nombre)) {
            this.nombre = null;
            error += textNombre.getId().substring(4, textNombre.getId().length()) + "\n";
        } else {
            estado += "Ok";
        }
        // valida municipio
        this.municipio = textMunicipio.getText();
        if (municipio.equals("") || !verificarCadena(this.municipio)) {
            this.municipio = null;
            error += textMunicipio.getId().substring(4, textMunicipio.getId().length()) + "\n";
        } else {
            estado += "Ok";
        }
        // valida cuerpo de agua
        this.tipoCuerpoAgua = textTipoCuerpoAgua.getText();
        if (this.tipoCuerpoAgua.equals("") || !verificarCadena(this.tipoCuerpoAgua)) {
            this.tipoCuerpoAgua = null;
            error += textTipoCuerpoAgua.getId().substring(4, textTipoCuerpoAgua.getId().length()) + "\n";
        } else {
            estado += "Ok";
        }
        // valida tipo de agua
        this.tipoAgua = textTipoAgua.getText();
        if (this.tipoAgua.equals("") || !verificarCadena(this.tipoAgua)) {
            this.tipoAgua = null;
            error += textTipoAgua.getId().substring(4, textTipoAgua.getId().length()) + "\n";
        } else {
            estado += "Ok";
        }

        try {
            // valida el ID
            this.ID = Integer.parseInt(textID.getText());
            if (this.ID >= 0) {
                estado += "Ok";

            }

        } catch (NumberFormatException e) {
            error += textID.getId().substring(4, textID.getId().length()) + "\n";
            this.ID = 0;
        }

        try {
            // valida el irca
            this.irca = Float.parseFloat(textIrca.getText());
            if (this.irca >= 0) {
                estado += "Ok";
            }

        } catch (NumberFormatException e) {
            error += textIrca.getId().substring(4, textIrca.getId().length()) + "\n";
            this.irca = 0;

        }
        // historial
        System.out.println("***********************************************");
        System.out.println(estado);
        // si todos los campos estan Ok entonces con el metodo almacenaDato() de la
        // super clase
        // crea y agrega un objeto de tipo cuerpoDeAgua en un ArrayList
        if (estado.equals(getClave())) {
            super.almacenaDato(nombre, ID, municipio, tipoCuerpoAgua, tipoAgua, irca);

            entradas.appendText(cuerpos.get(indice).getNombre() + " " + cuerpos.get(indice).getId() + " "
                    + cuerpos.get(indice).getMunicipio() + " " + cuerpos.get(indice).getTipoDeAgua() + " "
                    + cuerpos.get(indice).getTipoDeCuerpo() + " " + cuerpos.get(indice).getIrca() + "\n");

            estadoRegistro.setText(" Registro Exitoso ");
            estadoRegistro.setTextFill(Color.GREEN);
            float valorIrca = cuerpos.get(indice).getIrca();
            // cuenta los de nivel AlTO y INVIABLE SANITARIAMENTE
            this._cont = super.contar();
            // municipios con nivel alto
            if (super.nivel(valorIrca).equals("ALTO")) {
                riesgoAlto.add(cuerpos.get(indice).getMunicipio());
            }

            indice++;
        } else {
            estadoRegistro.setText(" Error en el Registro ");
            estadoRegistro.setTextFill(Color.RED);
            Alert a = new Alert(AlertType.ERROR);
            a.setHeaderText(" - Error en los siquientes campos - ");
            a.setContentText(error);
            a.showAndWait();
        }

    }

    @FXML
    void eventoProcesar(ActionEvent event) {

        if (cuerpos.size() == 0) {
            salidas.setText("");
        } else {
            // limpia el area del texto
            salidas.setText("");
            // salida 1
            for (int i = 0; i < cuerpos.size(); i++) {
                salidas.appendText(cuerpos.get(i).getNombre() + " " + cuerpos.get(i).getMunicipio() + "\n");
            }
            // salida 2
            this._cont = super.contar();
            salidas.appendText(new DecimalFormat("0.00").format((double) this._cont) + " \n");
            // salida 3
            if (riesgoAlto.size() == 0) {
                salidas.appendText("NA");
            } else {
                for (int i = 0; i < riesgoAlto.size(); i++) {
                    salidas.appendText(riesgoAlto.get(i) + " ");
                }
            }
            // salida 4
            salidas.appendText("\n" + super.nivel(maximoIrca()));
            estadoRegistro.setText(" Procesado con Exito ");
            estadoRegistro.setTextFill(Color.GREEN);
        }

    }

    @FXML
    void eventoLimpiarCampos(ActionEvent event) {
        textNombre.setText("");
        textID.setText("");
        textMunicipio.setText("");
        textTipoCuerpoAgua.setText("");
        textTipoAgua.setText("");
        textIrca.setText("");
        estadoRegistro.setText("");

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        assert textNombre != null : "fx:id=\"textNombre\" was not injected: check your FXML file 'reto3.fxml'.";
        assert textID != null : "fx:id=\"textID\" was not injected: check your FXML file 'reto3.fxml'.";
        assert textMunicipio != null : "fx:id=\"textMunicipio\" was not injected: check your FXML file 'reto3.fxml'.";
        assert textTipoCuerpoAgua != null
                : "fx:id=\"textTipoCuerpoAgua\" was not injected: check your FXML file 'reto3.fxml'.";
        assert textTipoAgua != null : "fx:id=\"textTipoAgua\" was not injected: check your FXML file 'reto3.fxml'.";
        assert textIrca != null : "fx:id=\"textIrca\" was not injected: check your FXML file 'reto3.fxml'.";
        assert btnIngresar != null : "fx:id=\"btnIngresar\" was not injected: check your FXML file 'reto3.fxml'.";
        assert btnProcesar != null : "fx:id=\"btnProcesar\" was not injected: check your FXML file 'reto3.fxml'.";
        assert entradas != null : "fx:id=\"entradas\" was not injected: check your FXML file 'reto3.fxml'.";
        assert salidas != null : "fx:id=\"salidas\" was not injected: check your FXML file 'reto3.fxml'.";

    }
}
