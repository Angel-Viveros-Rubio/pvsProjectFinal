package mx.poo.pvzproject.ui.utils;

/**
 * Clase que centraliza todas las constantes globales del juego.
 *
 * <p>
 * Define valores fijos relacionados con la configuración del grid,
 * control de niveles, dimensiones del mundo, posiciones del HUD,
 * cartas y límites de proyectiles.
 * </p>
 *
 * <p>
 * También proporciona métodos utilitarios para el cálculo de posiciones
 * dentro del grid, detección de colisiones por entrada táctil y validación
 * de líneas especiales (como agua) según el nivel actual.
 * </p>
 *
 * <p>
 * Centralizar estos valores mejora la mantenibilidad y facilita
 * futuras modificaciones sin afectar múltiples clases del sistema.
 * </p>
 *
 * @author SmallJunior
 * @version 1.0
 */
public class Constants {

    // ===== GRID =====

    /** Número total de líneas (filas) del grid. */
    public static final int LANES = 5;

    /** Número total de columnas del grid. */
    public static final int COLUMNS = 10;

    /** Coordenada X inicial del grid. */
    public static final float GRID_START_X = 220f;

    /** Coordenada Y inicial del grid. */
    public static final float GRID_START_Y = 73f;

    /** Ancho de cada celda del grid. */
    public static final float CELL_WIDTH = 68f;

    /** Alto de cada celda del grid. */
    public static final float CELL_HEIGHT = 110f;

    /** Idioma actual del juego. */
    public static Language CURRENT_LANGUAGE = Language.ES;

    // ===== NIVELES =====

    /** Nivel actual del juego. */
    public static int CURRENT_LEVEL = 1;

    /** Nivel máximo disponible. */
    public static final int MAX_LEVEL = 10;

    /**
     * Líneas que contienen agua en el escenario tipo jungle
     * (aplica únicamente para niveles 4 a 6).
     */
    public static final int[] WATER_LANES_JUNGLE = {0, 4};

    // ===== POSICIONES =====

    /**
     * Calcula la coordenada X centrada para dibujar una planta
     * en una columna específica.
     *
     * @param column índice de la columna
     * @return posición X centrada de la celda
     */
    public static float getPlantX(int column) {
        return GRID_START_X + column * CELL_WIDTH + CELL_WIDTH / 2f;
    }

    /**
     * Calcula la coordenada Y centrada para dibujar una planta
     * en una línea específica.
     *
     * @param lane índice de la línea
     * @return posición Y centrada de la celda
     */
    public static float getPlantY(int lane) {
        return GRID_START_Y + lane * CELL_HEIGHT + CELL_HEIGHT / 2f;
    }

    // ===== COLISIONES / INPUT =====

    /**
     * Verifica si una coordenada táctil se encuentra dentro
     * de una celda específica del grid.
     *
     * @param touchX coordenada X del toque
     * @param touchY coordenada Y del toque
     * @param column columna evaluada
     * @param lane línea evaluada
     * @return true si el toque está dentro de la celda; false en caso contrario
     */
    public static boolean isInCell(float touchX, float touchY, int column, int lane) {
        float cellX = GRID_START_X + column * CELL_WIDTH;
        float cellY = GRID_START_Y + lane * CELL_HEIGHT;
        return touchX >= cellX && touchX < cellX + CELL_WIDTH &&
            touchY >= cellY && touchY < cellY + CELL_HEIGHT;
    }

    /**
     * Determina si una línea corresponde a una zona de agua
     * según el nivel actual.
     *
     * <p>
     * Solo aplica para niveles 4 a 6 (escenario jungle).
     * </p>
     *
     * @param lane índice de la línea
     * @return true si la línea es de agua; false en caso contrario
     */
    public static boolean isWaterLane(int lane) {
        if (CURRENT_LEVEL < 4 || CURRENT_LEVEL > 6) {
            return false;
        }

        for (int waterLane : WATER_LANES_JUNGLE) {
            if (lane == waterLane) {
                return true;
            }
        }
        return false;
    }

    // ===== WORLD =====

    /** Ancho total del mundo del juego. */
    public static final float WORLD_WIDTH  = 1280f;

    /** Alto total del mundo del juego. */
    public static final float WORLD_HEIGHT = 720f;

    // ===== HUD / CARTAS =====

    /** Altura de la barra superior del HUD. */
    public static final float BAR_HEIGHT = 90f;

    /** Ancho de la barra superior del HUD. */
    public static final float BAR_WIDTH = WORLD_WIDTH * 0.7f;

    /** Posición X de la barra del HUD. */
    public static final float BAR_X = WORLD_WIDTH * 0.1f;

    /** Posición Y de la barra del HUD. */
    public static final float BAR_Y = WORLD_HEIGHT - BAR_HEIGHT;

    /** Ancho de cada carta del HUD. */
    public static final float CARD_WIDTH = 79f;

    /** Alto de cada carta del HUD. */
    public static final float CARD_HEIGHT = 64f;

    /** Espaciado horizontal entre cartas. */
    public static final float CARD_PADDING = 14f;

    /** Posición Y donde se renderizan las cartas. */
    public static final float CARDS_Y = BAR_Y + 15f;

    /** Margen izquierdo interno para el inicio de las cartas. */
    private static final float CARD_MARGIN_LEFT = 140f;

    /** Posiciones horizontales individuales de cada carta. */
    public static final float CARD_X1 = BAR_X + CARD_MARGIN_LEFT;
    public static final float CARD_X2 = CARD_X1 + CARD_WIDTH + CARD_PADDING;
    public static final float CARD_X3 = CARD_X2 + CARD_WIDTH + CARD_PADDING;
    public static final float CARD_X4 = CARD_X3 + CARD_WIDTH + CARD_PADDING;
    public static final float CARD_X5 = CARD_X4 + CARD_WIDTH + CARD_PADDING;
    public static final float CARD_X6 = CARD_X5 + CARD_WIDTH + CARD_PADDING;
    public static final float CARD_X7 = CARD_X6 + CARD_WIDTH + CARD_PADDING;
    public static final float CARD_X8 = CARD_X7 + CARD_WIDTH + CARD_PADDING;
    public static final float CARD_X9 = CARD_X8 + CARD_WIDTH + 20f + CARD_PADDING;

    // ===== PROYECTILES =====

    /**
     * Límite horizontal máximo que pueden alcanzar los proyectiles.
     * Se calcula en función de la última columna del grid.
     */
    public static final float PROJECTILE_END_X =
        getPlantX(COLUMNS - 1) + CELL_WIDTH;
}
