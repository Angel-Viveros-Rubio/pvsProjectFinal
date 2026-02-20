package mx.poo.pvzproject.ui.utils;
/**
 * Clase que contiene todas las constantes globales del juego.
 *
 * Aquí se definen valores fijos como:
 * - Configuración del grid (filas, columnas, tamaños y posiciones).
 * - Control de niveles.
 * - Dimensiones del mundo.
 * - Posiciones del HUD y cartas.
 * - Límites de proyectiles.
 *
 * También incluye algunos métodos utilitarios para:
 * - Calcular la posición exacta donde se debe dibujar una planta.
 * - Detectar si un toque está dentro de una celda.
 * - Determinar si una línea es de agua dependiendo del nivel.
 *
 * Centralizar estos valores aquí permite que el juego sea más
 * fácil de ajustar y mantener sin modificar múltiples clases.
 */
public class Constants {

    // ===== GRID =====
    public static final int LANES = 5;
    public static final int COLUMNS = 10;
    public static final float GRID_START_X = 220f;
    public static final float GRID_START_Y = 73f;
    public static final float CELL_WIDTH = 68f;
    public static final float CELL_HEIGHT = 110f;
    public static Language CURRENT_LANGUAGE = Language.ES;

    // ===== NIVELES =====
    public static int CURRENT_LEVEL = 1;
    public static final int MAX_LEVEL = 10;

    // Líneas con agua (solo jungle - niveles 4-6)
    public static final int[] WATER_LANES_JUNGLE = {0, 4};

    // ===== POSICIONES =====
    public static float getPlantX(int column) {
        return GRID_START_X + column * CELL_WIDTH + CELL_WIDTH / 2f;
    }

    public static float getPlantY(int lane) {
        return GRID_START_Y + lane * CELL_HEIGHT + CELL_HEIGHT / 2f;
    }

    // ===== COLISIONES / INPUT =====
    public static boolean isInCell(float touchX, float touchY, int column, int lane) {
        float cellX = GRID_START_X + column * CELL_WIDTH;
        float cellY = GRID_START_Y + lane * CELL_HEIGHT;
        return touchX >= cellX && touchX < cellX + CELL_WIDTH &&
            touchY >= cellY && touchY < cellY + CELL_HEIGHT;
    }

    public static boolean isWaterLane(int lane) {
        // Solo hay agua en niveles jungle (4-6)
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
    public static final float WORLD_WIDTH  = 1280f;
    public static final float WORLD_HEIGHT = 720f;

    // ===== HUD / CARTAS =====
    public static final float BAR_HEIGHT      = 90f;
    public static final float BAR_WIDTH       = WORLD_WIDTH * 0.7f;
    public static final float BAR_X           = WORLD_WIDTH * 0.1f;
    public static final float BAR_Y           = WORLD_HEIGHT - BAR_HEIGHT;
    public static final float CARD_WIDTH      = 79f;
    public static final float CARD_HEIGHT     = 64f;
    public static final float CARD_PADDING    = 14f;
    public static final float CARDS_Y         = BAR_Y + 15f;
    private static final float CARD_MARGIN_LEFT = 140f;
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
    public static final float PROJECTILE_END_X =
        getPlantX(COLUMNS - 1) + CELL_WIDTH;
}
