package mx.poo.pvzproject.ui.utils;
// ============================================================

import mx.poo.pvzproject.ui.utils.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConstantsTest {

    @BeforeEach
    void resetLevel() {
        Constants.CURRENT_LEVEL = 1; // estado limpio antes de cada test
    }

    // --- getPlantX ---

    @Test
    void getPlantXColumnZeroShouldReturnGridStartPlusHalfCell() {
        float expected = Constants.GRID_START_X + Constants.CELL_WIDTH / 2f;
        assertEquals(expected, Constants.getPlantX(0), 0.01f);
    }

    @Test
    void getPlantXColumnOneShouldOffsetByOneCell() {
        float expected = Constants.GRID_START_X + Constants.CELL_WIDTH + Constants.CELL_WIDTH / 2f;
        assertEquals(expected, Constants.getPlantX(1), 0.01f);
    }

    // --- getPlantY ---

    @Test
    void getPlantYLaneZeroShouldReturnGridStartPlusHalfCell() {
        float expected = Constants.GRID_START_Y + Constants.CELL_HEIGHT / 2f;
        assertEquals(expected, Constants.getPlantY(0), 0.01f);
    }

    @Test
    void getPlantYLaneOneShouldOffsetByOneCell() {
        float expected = Constants.GRID_START_Y + Constants.CELL_HEIGHT + Constants.CELL_HEIGHT / 2f;
        assertEquals(expected, Constants.getPlantY(1), 0.01f);
    }

    // --- isInCell ---

    @Test
    void isInCellShouldReturnTrueForTopLeftCornerOfCell() {
        float cellX = Constants.GRID_START_X;
        float cellY = Constants.GRID_START_Y;
        assertTrue(Constants.isInCell(cellX, cellY, 0, 0));
    }

    @Test
    void isInCellShouldReturnFalseOutsideCell() {
        assertFalse(Constants.isInCell(0f, 0f, 0, 0));
    }

    @Test
    void isInCellShouldReturnFalseAtRightBoundary() {
        float cellX = Constants.GRID_START_X + Constants.CELL_WIDTH; // límite derecho exacto
        float cellY = Constants.GRID_START_Y;
        assertFalse(Constants.isInCell(cellX, cellY, 0, 0));
    }

    // --- isWaterLane ---

    @Test
    void isWaterLaneShouldReturnFalseOnLevel1() {
        Constants.CURRENT_LEVEL = 1;
        assertFalse(Constants.isWaterLane(0));
    }

    @Test
    void isWaterLaneShouldReturnTrueForLane0OnLevel4() {
        Constants.CURRENT_LEVEL = 4;
        assertTrue(Constants.isWaterLane(0));
    }

    @Test
    void isWaterLaneShouldReturnTrueForLane4OnLevel5() {
        Constants.CURRENT_LEVEL = 5;
        assertTrue(Constants.isWaterLane(4));
    }

    @Test
    void isWaterLaneShouldReturnFalseForLane1OnLevel4() {
        Constants.CURRENT_LEVEL = 4;
        assertFalse(Constants.isWaterLane(1));
    }

    @Test
    void isWaterLaneShouldReturnFalseAfterLevel6() {
        Constants.CURRENT_LEVEL = 7;
        assertFalse(Constants.isWaterLane(0));
    }
}
