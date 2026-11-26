package com.mycompany.unidad003;

/** Clase Terreno con los datos y metodos proporcionados. */
public class Terreno {
    private String numTerreno;
    private float ancho;
    private float largo;

    // Constructor por omision
    public Terreno() {
        this.numTerreno = "Sin numero";
        this.ancho = 0.0f;
        this.largo = 0.0f;
    }

    // Constructor por argumento
    public Terreno(String numTerreno, float ancho, float largo) {
        this.numTerreno = numTerreno;
        this.ancho = ancho;
        this.largo = largo;
    }

    // Constructor por copia
    public Terreno(Terreno x) {
        this.numTerreno = x.numTerreno;
        this.ancho = x.ancho;
        this.largo = x.largo;
    }

    // Metodos set/get
    public void setNumTerreno(String numTerreno) {
        this.numTerreno = numTerreno;
    }

    public String getNumTerreno() {
        return this.numTerreno;
    }

    public void setAncho(float ancho) {
        this.ancho = ancho;
    }

    public float getAncho() {
        return this.ancho;
    }

    public void setLargo(float largo) {
        this.largo = largo;
    }

    public float getLargo() {
        return this.largo;
    }

    // Metodos de comportamiento
    public float calcularPerimetro() {
        float perimetro = 0.0f;
        perimetro = this.ancho * 2 + this.largo * 2;
        return perimetro;
    }

    public float calcularArea() {
        float area = 0.0f;
        area = this.ancho * this.largo;
        return area;
    }

    public void imprimir() {
        // FORMATO corregido para evitar DecimalFormat(null).
        String FORMATO = "#,##0.00";
        java.text.DecimalFormat df = new java.text.DecimalFormat(FORMATO);
        System.out.println("Num. Lote " + this.numTerreno);
        System.out.println("Ancho " + df.format(this.ancho));
        System.out.println("Largo " + df.format(this.largo));
        System.out.println("Area " + df.format(this.calcularArea()));
        System.out.println("Perimetro " + df.format(this.calcularPerimetro()));
    }
}
