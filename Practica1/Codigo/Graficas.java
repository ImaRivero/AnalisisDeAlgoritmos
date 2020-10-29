/*
    Fecha: 18/Octubre/2020
    Creado por: 
        + Omar Imanol Rivero Ronquillo
        + Luis Eduardo Valle Martínez
*/

import javax.swing.*;
import java.awt.Dimension;

// Librerías de graficación
import org.jfree.chart.*;
import org.jfree.data.xy.*;
import org.jfree.chart.plot.*;

// Clase encargada de generar la interfaz gráfica y generar las gráficas
public class Graficas{
    protected JFrame ventana;
    protected JPanel panel;

    protected XYSeriesCollection coleccionDatos;
    protected JFreeChart grafica;
    protected ChartPanel panelGrafica;


    public Graficas(){
        coleccionDatos = new XYSeriesCollection();

        ventana = new JFrame();
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setSize(960,590);

        panel = new JPanel();
        ventana.add(panel);
    }

    public void mostrarGrafica(){
        ventana.setVisible(true);
    }

    public void configurarGrafica(String titulo, String etiquetaX, String etiquetaY){
        grafica = ChartFactory.createXYLineChart(
            titulo,
            etiquetaX,
            etiquetaY,
            coleccionDatos,
            PlotOrientation.VERTICAL,
            true, true, false
            );

        panelGrafica = new ChartPanel(grafica);
        panelGrafica.setPreferredSize(new java.awt.Dimension(960,540));
        panel.add(panelGrafica);
    }

    // Permite agregar otra gráfica pasando como argumento un array con sus pares ordenados
    public void agregarDatosGraficacion(String nombreGrafo, int[][] paresOrdenados){
        XYSeries datos = new XYSeries(nombreGrafo);

        for(int[] par: paresOrdenados) // Se asigna los datos al conjunto
            datos.add((long)par[0],(long)par[1]);

        coleccionDatos.addSeries(datos);
    }

}