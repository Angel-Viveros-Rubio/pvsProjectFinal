package mx.poo.pvzproject.gameProcess.spawn;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class LevelFactoryTest {

    @Test
    void levelOneShouldHaveTwoWaves() {
        ArrayList<Wave> waves = LevelFactory.getLevel(1);

        assertNotNull(waves);
        assertEquals(2, waves.size());
    }

    @Test
    void levelTenShouldHaveFourWaves() {
        ArrayList<Wave> waves = LevelFactory.getLevel(10);

        assertNotNull(waves);
        assertEquals(4, waves.size());
    }

    @Test
    void invalidLevelShouldReturnEmptyList() {
        ArrayList<Wave> waves = LevelFactory.getLevel(999);

        assertNotNull(waves);
        assertTrue(waves.isEmpty());
    }

    @Test
    void levelSevenShouldHaveThreeWaves() {
        ArrayList<Wave> waves = LevelFactory.getLevel(7);

        assertEquals(3, waves.size());
    }
}
