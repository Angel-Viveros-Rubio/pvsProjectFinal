package mx.poo.pvzproject.gameProcess.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import mx.poo.pvzproject.gameProcess.entities.plants.*;
import mx.poo.pvzproject.ui.screens.IGameContext;
import mx.poo.pvzproject.gameProcess.entities.plants.properties.StonePlaceable;
import mx.poo.pvzproject.ui.utils.Constants;

import java.util.ArrayList;

/**
 * Maneja el input de colocación de plantas.
 * Depende de IGameContext en lugar de GameScreenManager directamente.
 */
public class PlantPlacer {

    private final IGameContext ctx;

    public PlantPlacer(IGameContext ctx) {
        this.ctx = ctx;
    }

    public void handleInput() {
        if (!Gdx.input.justTouched()) return;

        Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        ctx.getRenderSystem().viewport.unproject(touchPos);

        int lane = -1, column = -1;
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

        ArrayList<Plant> plantasEnCell = new ArrayList<>();
        for (Plant p : ctx.getPlants()) {
            if (Math.abs(p.getX() - plantX) < 50 && Math.abs(p.getY() - plantY) < 50) {
                plantasEnCell.add(p);
            }
        }

        boolean isWaterLane  = (Constants.CURRENT_LEVEL >= 4 && Constants.CURRENT_LEVEL <= 6) && (lane == 0 || lane == 4);
        boolean isStoneTerrain = (Constants.CURRENT_LEVEL >= 7);
        Plant   basePlant    = plantasEnCell.isEmpty() ? null : plantasEnCell.get(0);

        // Pala
        if (ctx.getSelectedPlant() == 8) {
            if (!plantasEnCell.isEmpty()) ctx.getPlants().remove(plantasEnCell.get(plantasEnCell.size() - 1));
            return;
        }

        // Nenúfar
        if (ctx.getSelectedPlant() == 4) {
            if (isWaterLane && plantasEnCell.isEmpty()
                && ctx.getManagers().waterManager.canAfford(2)
                && ctx.getCooldowns().lilyPad <= 0f) {
                ctx.getManagers().waterManager.spendWater(2);
                ctx.getPlants().add(new LilyPad(plantX, plantY));
                ctx.getCooldowns().lilyPad = 3f;
            }
            return;
        }

        // Maceta
        if (ctx.getSelectedPlant() == 5) {
            if (isStoneTerrain && plantasEnCell.isEmpty()) {
                Maceta tempMaceta = new Maceta(0, 0);
                if (ctx.getManagers().waterManager.canAfford(tempMaceta.getCost()) && ctx.getCooldowns().maceta <= 0f) {
                    ctx.getManagers().waterManager.spendWater(tempMaceta.getCost());
                    ctx.getPlants().add(new Maceta(plantX, plantY));
                    ctx.getCooldowns().maceta = tempMaceta.getCooldownTime();
                }
            }
            return;
        }

        boolean canPlace;
        if      (isWaterLane)   canPlace = (basePlant instanceof LilyPad)        && plantasEnCell.size() == 1;
        else if (isStoneTerrain) canPlace = (basePlant instanceof StonePlaceable) && plantasEnCell.size() == 1;
        else                     canPlace = plantasEnCell.isEmpty();

        if (!canPlace) return;

        Plant tempPlant      = null;
        float currentCooldown = 0f;
        float cooldownTime    = 0f;
        int   cost            = 0;

        switch (ctx.getSelectedPlant()) {
            case 0: tempPlant = new CornShooter(0, 0); currentCooldown = ctx.getCooldowns().corn;       break;
            case 1: tempPlant = new Papa(0, 0);         currentCooldown = ctx.getCooldowns().papa;       break;
            case 2: tempPlant = new WaterPlant(0, 0);   currentCooldown = ctx.getCooldowns().water;      break;
            case 3: tempPlant = new RedBom(0, 0);       currentCooldown = ctx.getCooldowns().redBom;     break;
            case 6: tempPlant = new Campanilla(0, 0);   currentCooldown = ctx.getCooldowns().campanilla; break;
            case 7: tempPlant = new Champi(0, 0);       currentCooldown = ctx.getCooldowns().champi;     break;
            default: return;
        }

        cooldownTime = tempPlant.getCooldownTime();
        cost         = tempPlant.getCost();

        if (!ctx.getManagers().waterManager.canAfford(cost) || currentCooldown > 0f) return;

        ctx.getManagers().waterManager.spendWater(cost);
        if (plantasEnCell.size() > 1) ctx.getPlants().remove(plantasEnCell.get(plantasEnCell.size() - 1));

        switch (ctx.getSelectedPlant()) {
            case 0: ctx.getPlants().add(new CornShooter(plantX, plantY)); ctx.getCooldowns().corn       = cooldownTime; break;
            case 1: ctx.getPlants().add(new Papa(plantX, plantY));         ctx.getCooldowns().papa       = cooldownTime; break;
            case 2: ctx.getPlants().add(new WaterPlant(plantX, plantY));   ctx.getCooldowns().water      = cooldownTime; break;
            case 3: ctx.getPlants().add(new RedBom(plantX, plantY));       ctx.getCooldowns().redBom     = cooldownTime; break;
            case 6: ctx.getPlants().add(new Campanilla(plantX, plantY));   ctx.getCooldowns().campanilla = cooldownTime; break;
            case 7: ctx.getPlants().add(new Champi(plantX, plantY));       ctx.getCooldowns().champi     = cooldownTime; break;
        }
    }
}
