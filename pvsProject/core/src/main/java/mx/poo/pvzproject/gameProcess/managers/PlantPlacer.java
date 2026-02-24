package mx.poo.pvzproject.gameProcess.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import mx.poo.pvzproject.gameProcess.entities.plants.*;
import mx.poo.pvzproject.ui.screens.IGameContext;
import mx.poo.pvzproject.gameProcess.entities.plants.properties.StonePlaceable;
import mx.poo.pvzproject.ui.utils.Constants;

import java.util.ArrayList;

/**
 * Gestiona la colocación de plantas en el tablero.
 *
 * <p>
 * Esta clase procesa la entrada táctil del jugador para determinar
 * en qué celda del tablero se desea colocar una planta y valida
 * si la acción es permitida según las reglas del juego.
 * </p>
 *
 * <p>
 * Responsabilidades principales:
 * </p>
 * <ul>
 *     <li>Detectar la celda seleccionada mediante coordenadas táctiles.</li>
 *     <li>Validar restricciones de terreno (agua, piedra o normal).</li>
 *     <li>Verificar costos de agua y tiempos de cooldown.</li>
 *     <li>Instanciar y colocar la planta correspondiente.</li>
 *     <li>Permitir eliminar plantas mediante la pala.</li>
 * </ul>
 *
 * <p>
 * Depende únicamente de {@link IGameContext}, evitando acoplamiento
 * directo con la clase principal que administra el estado del juego.
 * </p>
 *
 * @author SmallJunior
 * @version 1.0
 */
public class PlantPlacer {

    /** Contexto del juego. */
    private final IGameContext ctx;

    /**
     * Constructor del gestor de colocación.
     *
     * @param ctx contexto del juego
     */
    public PlantPlacer(IGameContext ctx) {
        this.ctx = ctx;
    }

    /**
     * Procesa la entrada del jugador para colocar o eliminar plantas.
     *
     * <p>
     * Flujo general:
     * </p>
     * <ol>
     *     <li>Detectar celda seleccionada.</li>
     *     <li>Verificar restricciones del terreno.</li>
     *     <li>Validar recursos y cooldown.</li>
     *     <li>Instanciar la planta y actualizar estado.</li>
     * </ol>
     */
    public void handleInput() {

        if (!Gdx.input.justTouched()) return;

        Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        ctx.getRenderSystem().viewport.unproject(touchPos);

        int lane = -1, column = -1;

        // =============================
        // Detección de celda seleccionada
        // =============================

        outer:
        for (int l = 0; l < Constants.LANES; l++) {
            for (int c = 0; c < Constants.COLUMNS; c++) {
                if (Constants.isInCell(touchPos.x, touchPos.y, c, l)) {
                    lane   = l;
                    column = c;
                    break outer;
                }
            }
        }

        if (lane == -1 || column == -1) return;

        float plantX = Constants.getPlantX(column);
        float plantY = Constants.getPlantY(lane);

        // =============================
        // Plantas existentes en celda
        // =============================

        ArrayList<Plant> plantasEnCell = new ArrayList<>();
        for (Plant p : ctx.getPlants()) {
            if (Math.abs(p.getX() - plantX) < 50 &&
                Math.abs(p.getY() - plantY) < 50) {
                plantasEnCell.add(p);
            }
        }

        boolean isWaterLane   = (Constants.CURRENT_LEVEL >= 4 && Constants.CURRENT_LEVEL <= 6)
            && (lane == 0 || lane == 4);

        boolean isStoneTerrain = (Constants.CURRENT_LEVEL >= 7);

        Plant basePlant = plantasEnCell.isEmpty() ? null : plantasEnCell.get(0);

        // =============================
        // Pala (eliminación)
        // =============================

        if (ctx.getSelectedPlant() == 8) {
            if (!plantasEnCell.isEmpty()) {
                ctx.getPlants().remove(plantasEnCell.get(plantasEnCell.size() - 1));
            }
            return;
        }

        // =============================
        // Nenúfar (LilyPad)
        // =============================

        if (ctx.getSelectedPlant() == 4) {

            if (isWaterLane &&
                plantasEnCell.isEmpty() &&
                ctx.getManagers().waterManager.canAfford(2) &&
                ctx.getCooldowns().getLilyPad() <= 0f) {

                ctx.getManagers().waterManager.spendWater(2);
                ctx.getPlants().add(new LilyPad(plantX, plantY));
                ctx.getCooldowns().setLilyPad(3f);
            }
            return;
        }

        // =============================
        // Maceta (terreno piedra)
        // =============================

        if (ctx.getSelectedPlant() == 5) {

            if (isStoneTerrain && plantasEnCell.isEmpty()) {

                Maceta tempMaceta = new Maceta(0, 0);

                if (ctx.getManagers().waterManager.canAfford(tempMaceta.getCost()) &&
                    ctx.getCooldowns().getMaceta() <= 0f) {

                    ctx.getManagers().waterManager.spendWater(tempMaceta.getCost());
                    ctx.getPlants().add(new Maceta(plantX, plantY));
                    ctx.getCooldowns().setMaceta(tempMaceta.getCooldownTime());
                }
            }
            return;
        }

        // =============================
        // Validación de terreno
        // =============================

        boolean canPlace;

        if (isWaterLane)
            canPlace = (basePlant instanceof LilyPad) && plantasEnCell.size() == 1;
        else if (isStoneTerrain)
            canPlace = (basePlant instanceof StonePlaceable) && plantasEnCell.size() == 1;
        else
            canPlace = plantasEnCell.isEmpty();

        if (!canPlace) return;

        // =============================
        // Creación temporal para validación
        // =============================

        Plant tempPlant = null;
        float currentCooldown = 0f;

        switch (ctx.getSelectedPlant()) {
            case 0: tempPlant = new CornShooter(0, 0); currentCooldown = ctx.getCooldowns().getCorn();       break;
            case 1: tempPlant = new Papa(0, 0);         currentCooldown = ctx.getCooldowns().getPapa();       break;
            case 2: tempPlant = new WaterPlant(0, 0);   currentCooldown = ctx.getCooldowns().getWater();      break;
            case 3: tempPlant = new RedBom(0, 0);       currentCooldown = ctx.getCooldowns().getRedBom();     break;
            case 6: tempPlant = new Campanilla(0, 0);   currentCooldown = ctx.getCooldowns().getCampanilla(); break;
            case 7: tempPlant = new Champi(0, 0);       currentCooldown = ctx.getCooldowns().getChampi();     break;
            default: return;
        }

        float cooldownTime = tempPlant.getCooldownTime();
        int   cost         = tempPlant.getCost();

        if (!ctx.getManagers().waterManager.canAfford(cost) ||
            currentCooldown > 0f) return;

        // =============================
        // Colocación definitiva
        // =============================

        ctx.getManagers().waterManager.spendWater(cost);

        if (plantasEnCell.size() > 1) {
            ctx.getPlants().remove(plantasEnCell.get(plantasEnCell.size() - 1));
        }

        switch (ctx.getSelectedPlant()) {
            case 0:
                ctx.getPlants().add(new CornShooter(plantX, plantY));
                ctx.getCooldowns().setCorn(cooldownTime);
                break;
            case 1:
                ctx.getPlants().add(new Papa(plantX, plantY));
                ctx.getCooldowns().setPapa(cooldownTime);
                break;
            case 2:
                ctx.getPlants().add(new WaterPlant(plantX, plantY));
                ctx.getCooldowns().setWater(cooldownTime);
                break;
            case 3:
                ctx.getPlants().add(new RedBom(plantX, plantY));
                ctx.getCooldowns().setRedBom(cooldownTime);
                break;
            case 6:
                ctx.getPlants().add(new Campanilla(plantX, plantY));
                ctx.getCooldowns().setCampanilla(cooldownTime);
                break;
            case 7:
                ctx.getPlants().add(new Champi(plantX, plantY));
                ctx.getCooldowns().setChampi(cooldownTime);
                break;
        }
    }
}
