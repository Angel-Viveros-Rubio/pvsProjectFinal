package mx.poo.pvzproject.ui.utils;// ============================================================

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CooldownsTest {

    private Cooldowns cd;

    @BeforeEach
    void setUp() {
        cd = new Cooldowns();
    }

    // --- valores iniciales ---

    @Test
    void allCooldownsShouldStartAtZero() {
        assertEquals(0f, cd.getCorn(),       0.01f);
        assertEquals(0f, cd.getPapa(),       0.01f);
        assertEquals(0f, cd.getWater(),      0.01f);
        assertEquals(0f, cd.getRedBom(),     0.01f);
        assertEquals(0f, cd.getLilyPad(),    0.01f);
        assertEquals(0f, cd.getMaceta(),     0.01f);
        assertEquals(0f, cd.getCampanilla(), 0.01f);
        assertEquals(0f, cd.getChampi(),     0.01f);
    }

    // --- setters/getters ---

    @Test
    void setCornShouldUpdateValue() {
        cd.setCorn(5f);
        assertEquals(5f, cd.getCorn(), 0.01f);
    }

    @Test
    void setPapaShouldUpdateValue() {
        cd.setPapa(12f);
        assertEquals(12f, cd.getPapa(), 0.01f);
    }

    @Test
    void setWaterShouldUpdateValue() {
        cd.setWater(3f);
        assertEquals(3f, cd.getWater(), 0.01f);
    }

    @Test
    void setRedBomShouldUpdateValue() {
        cd.setRedBom(15f);
        assertEquals(15f, cd.getRedBom(), 0.01f);
    }

    // --- reset ---

    @Test
    void resetShouldSetAllToZero() {
        cd.setCorn(5f);
        cd.setPapa(10f);
        cd.setWater(3f);
        cd.setRedBom(15f);
        cd.setLilyPad(3f);
        cd.setMaceta(3f);
        cd.setCampanilla(7f);
        cd.setChampi(10f);

        cd.reset();

        assertEquals(0f, cd.getCorn(),       0.01f);
        assertEquals(0f, cd.getPapa(),       0.01f);
        assertEquals(0f, cd.getWater(),      0.01f);
        assertEquals(0f, cd.getRedBom(),     0.01f);
        assertEquals(0f, cd.getLilyPad(),    0.01f);
        assertEquals(0f, cd.getMaceta(),     0.01f);
        assertEquals(0f, cd.getCampanilla(), 0.01f);
        assertEquals(0f, cd.getChampi(),     0.01f);
    }

    @Test
    void resetOnFreshInstanceShouldNotCrash() {
        assertDoesNotThrow(() -> cd.reset());
    }
}
