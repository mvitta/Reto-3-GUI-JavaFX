public class CuerpoDeAgua extends ObjetoGeografico {

    private String nombre;
    private int Id;
    private String municipio;
    private float irca;

    public CuerpoDeAgua(String nombre, int Id, String municipio, float irca, String tipoDeAgua, String tipoDeCuerpo) {
        super(tipoDeAgua, tipoDeCuerpo);
        this.nombre = nombre;
        this.Id = Id;
        this.municipio = municipio;
        this.irca = irca;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return this.Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getMunicipio() {
        return this.municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public float getIrca() {
        return this.irca;
    }

    public void setIrca(float irca) {
        this.irca = irca;
    }

}