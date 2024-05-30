package com.example.proyecto1;

public class ProduccionRegistro {
    //-----------------------------------CONSTRUCTOR---------------------------------------

    String IdProducto,ladrillo,cantidad,tipo,fecha_creacion;

    public ProduccionRegistro() {
    }

    public ProduccionRegistro(String idProducto, String ladrillo, String cantidad, String tipo, String fecha_creacion) {
        IdProducto = idProducto;
        this.ladrillo = ladrillo;
        this.cantidad = cantidad;
        this.tipo = tipo;
        this.fecha_creacion = fecha_creacion;
    }

    public String getIdProducto() { return IdProducto; }

    public void setIdProducto(String idProducto) { IdProducto = idProducto; }

    public String getLadrillo() { return ladrillo;}

    public void setLadrillo(String ladrillo) { this.ladrillo = ladrillo; }

    public String getCantidad() { return cantidad; }

    public void setCantidad(String cantidad) { this.cantidad = cantidad; }

    public String getTipo() { return tipo; }

    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getFecha_creacion() { return fecha_creacion; }

    public void setFecha_creacion(String fecha_creacion) { this.fecha_creacion = fecha_creacion; }

}