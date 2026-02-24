package mx.poo.pvzproject.ui.utils;

/**
 * Clase que gestiona los tiempos de recarga (cooldowns)
 * de las diferentes plantas del juego.
 * <p>
 * Cada atributo representa el tiempo restante antes de que
 * una planta pueda volver a ser utilizada.
 * </p>
 *
 * <p>
 * Los valores se manejan en segundos y se actualizan
 * dinámicamente durante la ejecución del juego.
 * </p>
 *
 * @author SmallJunior
 * @version 1.0
 */
public class Cooldowns {

    /** Tiempo de recarga de la planta tipo corn. */
    private float corn      = 0f;

    /** Tiempo de recarga de la planta tipo papa. */
    private float papa      = 0f;

    /** Tiempo de recarga de la planta tipo water. */
    private float water     = 0f;

    /** Tiempo de recarga de la planta tipo redBom. */
    private float redBom    = 0f;

    /** Tiempo de recarga de la planta tipo lilyPad. */
    private float lilyPad   = 0f;

    /** Tiempo de recarga de la planta tipo maceta. */
    private float maceta    = 0f;

    /** Tiempo de recarga de la planta tipo campanilla. */
    private float campanilla = 0f;

    /** Tiempo de recarga de la planta tipo champi. */
    private float champi    = 0f;

    /**
     * Reinicia todos los tiempos de recarga a cero.
     * <p>
     * Se utiliza normalmente al comenzar una nueva partida
     * o al reiniciar el estado del juego.
     * </p>
     */
    public void reset() {
        corn = papa = water = redBom = lilyPad = maceta = campanilla = champi = 0f;
    }

    public float getCorn() {
        return corn;
    }

    public void setCorn(float corn) {
        this.corn = corn;
    }

    public float getPapa() {
        return papa;
    }

    public void setPapa(float papa) {
        this.papa = papa;
    }

    public float getWater() {
        return water;
    }

    public void setWater(float water) {
        this.water = water;
    }

    public float getRedBom() {
        return redBom;
    }

    public void setRedBom(float redBom) {
        this.redBom = redBom;
    }

    public float getLilyPad() {
        return lilyPad;
    }

    public void setLilyPad(float lilyPad) {
        this.lilyPad = lilyPad;
    }

    public float getMaceta() {
        return maceta;
    }

    public void setMaceta(float maceta) {
        this.maceta = maceta;
    }

    public float getCampanilla() {
        return campanilla;
    }

    public void setCampanilla(float campanilla) {
        this.campanilla = campanilla;
    }

    public float getChampi() {
        return champi;
    }

    public void setChampi(float champi) {
        this.champi = champi;
    }
}
