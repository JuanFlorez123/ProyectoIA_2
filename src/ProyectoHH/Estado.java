package ProyectoHH;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Camilo
 */
public class Estado {

    private int[][] tablero;
    private Point posBlanco; //ubicacion (xy)
    private Point posNegro; //ubicacion (xy)
    private Double puntosBlanco;
    private Double puntosNegro;
    private Double utilidad;
    private int turno;
    private int profundidad;

    public Estado(int turno, int[][] tablero, Point posBlanco, Point posNegro, Double puntosBlanco, Double puntosNegro) {
        this.turno = turno;
        this.tablero = tablero;
        this.posBlanco = posBlanco;
        this.posNegro = posNegro;
        this.puntosBlanco = puntosBlanco;
        this.puntosNegro = puntosNegro;
    }

    public Estado() {
        this.puntosNegro = 0.0;
        this.puntosBlanco = 0.0;
    }

    public int getProfundidad() {
        return profundidad;
    }

    public void setProfundidad(int profundidad) {
        this.profundidad = profundidad;
    }

    public int getTurno() {
        return turno;
    }

    public void setTurno(int turno) {
        this.turno = turno;
    }

    public int[][] getTablero() {
        return tablero;
    }

    public void setTablero(int[][] tablero) {
        this.tablero = tablero;
    }

    public Point getPosBlanco() {
        return posBlanco;
    }

    public void setPosBlanco(Point posBlanco) {
        this.posBlanco = posBlanco;
    }

    public Point getPosNegro() {
        return posNegro;
    }

    public void setPosNegro(Point posNegro) {
        this.posNegro = posNegro;
    }

    public Double getPuntosB() {
        return puntosBlanco;
    }

    public void setPuntosB(Double puntosBlanco) {
        this.puntosBlanco += puntosBlanco;
    }

    public Double getPuntosNegro() {
        return puntosNegro;
    }

    public void setPuntosNegro(Double puntosNegro) {
        this.puntosNegro += puntosNegro;
    }

    public void setUtilidad(Double utilidad) {
        this.utilidad = utilidad;
    }

    public Estado resultado(Point accion) {
        Estado proximo = new Estado();
        int tamanio = this.tablero.length;
        Double puntos = 0.0;
        int[][] tablero = new int[tamanio][tamanio];
        for (int i = 0; i < tamanio; i++) {
            for (int j = 0; j < tamanio; j++) {
                tablero[i][j] = this.tablero[i][j];
            }
        }
        if (tablero[accion.x][accion.y] == 3) {
            puntos = 1.0;
        }
        if (tablero[accion.x][accion.y] == 4) {
            puntos = 3.0;
        }
        if (tablero[accion.x][accion.y] == 5) {
            puntos = 5.0;
        }
        if (this.turno == 1) {
            tablero[posBlanco.x][posBlanco.y] = 0;
            tablero[accion.x][accion.y] = 1;
            proximo.setPosNegro(this.posNegro);
            proximo.setPosBlanco(accion);
            proximo.setPuntosNegro(this.puntosNegro);
            proximo.setPuntosB(puntos);
            proximo.setTurno(2);
        }
        if (this.turno == 2) {
            tablero[posNegro.x][posNegro.y] = 0;
            tablero[accion.x][accion.y] = 2;
            proximo.setPosBlanco(this.posBlanco);
            proximo.setPosNegro(accion);
            proximo.setPuntosB(this.puntosBlanco);
            proximo.setPuntosNegro(puntos);
            proximo.setTurno(1);
        }

        proximo.setProfundidad(this.profundidad + 1);
        proximo.setTablero(tablero);
        return proximo;
    }

    public List movidasValidas() {

        List movidas = new ArrayList<>();
        Point posicion = new Point();
        Point posicionOponente = new Point();
        if (this.turno == 1) {//blanco
            posicion.setLocation(this.posBlanco.x, this.posBlanco.y);
            posicionOponente.setLocation(this.posNegro.x, this.posNegro.y);
        }
        if (this.turno == 2) {//negro
            posicion.setLocation(this.posNegro.x, this.posNegro.y);
            posicionOponente.setLocation(this.posBlanco.x, this.posBlanco.y);
        }
        int x = posicion.x - 2;
        int y = posicion.y - 1;
        Point movida = verificarMovimiento(x, y, posicionOponente);
        if (movida != null) {
            movidas.add(movida);
        }

        y = posicion.y + 1;
        movida = null;
        movida = verificarMovimiento(x, y, posicionOponente);
        if (movida != null) {
            movidas.add(movida);
        }

        x = posicion.x + 2;
        y = posicion.y - 1;
        movida = null;
        movida = verificarMovimiento(x, y, posicionOponente);
        if (movida != null) {
            movidas.add(movida);
        }

        y = posicion.y + 1;
        movida = null;
        movida = verificarMovimiento(x, y, posicionOponente);
        if (movida != null) {
            movidas.add(movida);
        }

        x = posicion.x - 1;
        y = posicion.y - 2;
        movida = null;
        movida = verificarMovimiento(x, y, posicionOponente);
        if (movida != null) {
            movidas.add(movida);
        }

        x = posicion.x + 1;
        movida = null;
        movida = verificarMovimiento(x, y, posicionOponente);
        if (movida != null) {
            movidas.add(movida);
        }

        x = posicion.x - 1;
        y = posicion.y + 2;
        movida = null;
        movida = verificarMovimiento(x, y, posicionOponente);
        if (movida != null) {
            movidas.add(movida);
        }

        x = posicion.x + 1;
        movida = null;
        movida = verificarMovimiento(x, y, posicionOponente);
        if (movida != null) {
            movidas.add(movida);
        }

        return movidas;
    }

    private Point verificarMovimiento(int x, int y, Point posicionOponente) {
        Point movimiento = null;
        if ((x >= 0) && (x <= 7) && (y >= 0) && (y <= 7)) {
            boolean ocupada = ((posicionOponente.distance(new Point(x, y))) == 0);
            if (!ocupada) {
                movimiento = new Point(x, y);
            }
        }
        return movimiento;
    }

    public static Estado crearEstadoInicial(int tamanio) {
        List casillas = new ArrayList<Point>();
        Point posNegro = new Point();//caballo negro
        Point posBlanco = new Point();//caballo blanco
        Double puntosNegro = 0.0;
        Double puntosBlanco = 0.0;
        Double utilidad = Double.NEGATIVE_INFINITY;
        int turno = 1;
        int profundidad = 0;
        int[][] tablero = new int[tamanio][tamanio];

        for (int fila = 0; fila < tamanio; fila++) {
            for (int col = 0; col < tamanio; col++) {
                casillas.add(new Point(fila, col));
                tablero[fila][col] = 0;
            }
        }
        for (int i = 0; i < 23; i++) {
            int max = casillas.size();
            Random rand = new Random(System.currentTimeMillis());
            int idx = rand.nextInt(max);
            Point figura = (Point) casillas.get(idx);

            if (i == 0) {
                tablero[figura.x][figura.y] = 1;//caballo blanco
                posBlanco.setLocation(figura.x, figura.y);
            }
            if (i == 1) {
                tablero[figura.x][figura.y] = 2;//caballo negro
                posNegro.setLocation(figura.x, figura.y);
            }
            if (i == 2) {
                tablero[figura.x][figura.y] = 5;//manzana
            }
            if (i == 3) {
                tablero[figura.x][figura.y] = 5;//manzana
            }
            if (i > 3 && i < 18) {//cesped
                tablero[figura.x][figura.y] = 3;
            }
            if (i >= 18 && i < 23) {//flores
                tablero[figura.x][figura.y] = 4;
            }

            casillas.remove(idx);
        }

        Estado inicial = new Estado(turno, tablero, posBlanco, posNegro, puntosBlanco, puntosNegro);
        inicial.setProfundidad(profundidad);
        inicial.setUtilidad(utilidad);
        return inicial;
    }

    public static void imprimirTablero(Estado estado) {
        int[][] tablero = estado.getTablero();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print(tablero[i][j] + " ");
            }
            System.out.println();
        }

        ProyectoHH.HungryHorses.jtJugadas.append("posBlanco: " + estado.getPosBlanco());
        ProyectoHH.HungryHorses.jtJugadas.append("posNegro: " + estado.getPosNegro());
        ProyectoHH.HungryHorses.jtJugadas.append("ptsBlanco: " + estado.getPuntosB());
        ProyectoHH.HungryHorses.jtJugadas.append("ptsNegro: " + estado.getPuntosNegro());
        ProyectoHH.HungryHorses.jtJugadas.append("utilidad: " + estado.calcularUtilidad());
        ProyectoHH.HungryHorses.jtJugadas.append("profundidad: " + estado.getProfundidad());
        ProyectoHH.HungryHorses.jtJugadas.append("turno (1) blanco (2) negro: " + estado.getTurno());
    }

    public Double calcularUtilidad() {
        Double utilidad = (Double) this.puntosBlanco - this.puntosNegro;//blanco - negro
        this.utilidad = utilidad;
        return utilidad;
    }

    public boolean terminal(int limite) {
        boolean seAcaba = false;

        if (puntosNegro >= 20) {
            seAcaba = true;
            return seAcaba;
        }
        if (puntosBlanco >= 20) {
            seAcaba = true;
            return seAcaba;
        }

        if ((profundidad >= limite)) {
            seAcaba = true;
            return seAcaba;
        } else {
            if ((39 - puntosNegro - puntosBlanco) == 0) {
                seAcaba = true;
                return seAcaba;
            } else {
                return seAcaba;
            }
        }
    }
}
