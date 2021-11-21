import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class reto3 {

    protected ArrayList<CuerpoDeAgua> cuerpos = new ArrayList<>();
    protected ArrayList<String> riesgoAlto = new ArrayList<String>();
    protected float maximo = 0;
    private final String clave = "OkOkOkOkOkOk";

    public void almacenaDato(String nombre, int ID, String municipio, String TipoCuerpoAgua, String tipoAgua,
            float irca) {

        cuerpos.add(new CuerpoDeAgua(nombre, ID, municipio, irca, TipoCuerpoAgua, tipoAgua));

    }

    public float contar() {
        float cont = 0;
        for (int i = 0; i < cuerpos.size(); i++) {
            float _irca = cuerpos.get(i).getIrca();
            if (_irca >= 35.1 && _irca <= 100) {
                cont++;
            }
        }
        return cont;
    }

    public String nivel(float irca) {

        if (irca >= 80.1 && irca <= 100) {
            return "INVIABLE SANITARIAMENTE";
        } else if (irca >= 35.1 && irca <= 80) {
            return "ALTO";
        } else if (irca >= 14.1 && irca <= 35) {
            return "MEDIO";
        } else if (irca >= 5.1 && irca <= 14) {
            return "BAJO";
        } else {
            return "SIN RIESGO";
        }

    }

    public float maximoIrca(){
        float max = 0;
        for (int i = 0; i < cuerpos.size(); i++) {
            if (cuerpos.get(i).getIrca() > max){
                max = cuerpos.get(i).getIrca();
            }
        }
        return max;
    }

    public boolean verificarCadena(String cadena) {
        boolean aceptada = false;
        Pattern p = Pattern.compile("[a-zA-Z]*");
        Matcher m = p.matcher(cadena);
        if (m.matches()) {
            aceptada = true;
        }
        return aceptada;
    }

    public ArrayList<CuerpoDeAgua> getCuerpos() {
        return this.cuerpos;
    }

    public ArrayList<String> getRiesgoAlto() {
        return this.riesgoAlto;
    }

    public float getMaximo() {
        return this.maximo;
    }

    public String getClave() {
        return this.clave;
    }

}
