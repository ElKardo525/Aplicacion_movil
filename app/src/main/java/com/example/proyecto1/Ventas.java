package com.example.proyecto1;

public class Ventas {
    //-----------------------------------CONSTRUCTOR---------------------------------------
    // Variables miembro para almacenar la información de una venta
    String IdVenta,ladrillo,cantidad,tipo,precio,fecha_creacion;
    // Constructor por defecto sin parámetros
    public Ventas() {
    }
    //Constructor que inicializa todas las variables miembro con los valores proporcionados
    public Ventas(String idVenta, String ladrillo, String cantidad, String tipo, String precio, String fecha_creacion) {
        IdVenta = idVenta;
        this.ladrillo = ladrillo;
        this.cantidad = cantidad;
        this.tipo = tipo;
        this.precio = precio;
        this.fecha_creacion = fecha_creacion;
    }
    // Métodos getter y setter para acceder y modificar las variables miembro
    public String getIdVenta() {
        return IdVenta;
    }
    public void setIdVenta(String idVenta) {
        IdVenta = idVenta;
    }

    public String getLadrillo() {
        return ladrillo;
    }

    public void setLadrillo(String ladrillo) {
        this.ladrillo = ladrillo;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(String fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }
}
