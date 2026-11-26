package com.mycompany.cotizacion;
public interface dbMetodos {
 boolean abrir(); boolean cerrar(); int insertar(Object obj); int actualizar(Object obj);
 int habilitar(int id); int deshabilitar(int id); Object buscar(int id); Object buscar(String num);
}
