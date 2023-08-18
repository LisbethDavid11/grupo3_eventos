package Ventas;

import Objetos.Venta;

public interface VentaListener {
    void onVentaRegistrada(Venta venta);

    void onVentaRegistrada();
}
