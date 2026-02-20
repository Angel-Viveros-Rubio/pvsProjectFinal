package mx.poo.pvzproject.ui.render;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import mx.poo.pvzproject.ui.screens.IGameContext;
import mx.poo.pvzproject.ui.utils.Assets;
import mx.poo.pvzproject.ui.utils.Constants;

/**
 * Renderiza las tarjetas de plantas.
 * Depende de IGameContext en lugar de GameScreenManager directamente.
 */
public class CardRenderer {

    private final IGameContext ctx;

    public CardRenderer(IGameContext ctx) {
        this.ctx = ctx;
    }

    public void render(SpriteBatch batch) {
        renderCard(batch, Assets.cardCornShooter,   Constants.CARD_X1, ctx.getSelectedPlant() == 0);
        renderCard(batch, Assets.cardPapa,           Constants.CARD_X2, ctx.getSelectedPlant() == 1);
        renderCard(batch, Assets.cardWaterPlant,     Constants.CARD_X3, ctx.getSelectedPlant() == 2);
        renderCard(batch, Assets.cardRedBom,         Constants.CARD_X4, ctx.getSelectedPlant() == 3);
        renderCard(batch, Assets.cardLilyPad,        Constants.CARD_X5, ctx.getSelectedPlant() == 4);
        renderCard(batch, Assets.cardMaceta,         Constants.CARD_X6, ctx.getSelectedPlant() == 5);
        renderCard(batch, Assets.cardCampanilla,     Constants.CARD_X7, ctx.getSelectedPlant() == 6);
        renderCard(batch, Assets.cardChampi,         Constants.CARD_X8, ctx.getSelectedPlant() == 7);
        renderCard(batch, Assets.cardShovel,         Constants.CARD_X9, ctx.getSelectedPlant() == 8);

        // Cooldown overlays
        renderCooldown(batch, Assets.cardCornShooter,   Constants.CARD_X1, ctx.getCooldowns().corn);
        renderCooldown(batch, Assets.cardPapa,           Constants.CARD_X2, ctx.getCooldowns().papa);
        renderCooldown(batch, Assets.cardWaterPlant,     Constants.CARD_X3, ctx.getCooldowns().water);
        renderCooldown(batch, Assets.cardRedBom,         Constants.CARD_X4, ctx.getCooldowns().redBom);
        renderCooldown(batch, Assets.cardLilyPad,        Constants.CARD_X5, ctx.getCooldowns().lilyPad);
        renderCooldown(batch, Assets.cardMaceta,         Constants.CARD_X6, ctx.getCooldowns().maceta);
        renderCooldown(batch, Assets.cardCampanilla,     Constants.CARD_X7, ctx.getCooldowns().campanilla);
        renderCooldown(batch, Assets.cardChampi,         Constants.CARD_X8, ctx.getCooldowns().champi);
    }

    private void renderCard(SpriteBatch batch, com.badlogic.gdx.graphics.Texture tex, float cardX, boolean selected) {
        float scale  = selected ? 0.9f : 1.0f;
        float width  = Constants.CARD_WIDTH  * scale;
        float height = Constants.CARD_HEIGHT * scale;
        float x = cardX + (Constants.CARD_WIDTH  - width)  / 2f;
        float y = Constants.CARDS_Y + (Constants.CARD_HEIGHT - height) / 2f;
        batch.draw(tex, x, y, width, height);
    }

    private void renderCooldown(SpriteBatch batch, com.badlogic.gdx.graphics.Texture tex, float cardX, float cooldown) {
        if (cooldown <= 0f) return;

        float width  = Constants.CARD_WIDTH;
        float height = Constants.CARD_HEIGHT;
        float x = cardX;
        float y = Constants.CARDS_Y;

        batch.setColor(0f, 0f, 0f, 0.5f);
        batch.draw(tex, x, y, width, height);
        batch.setColor(Color.WHITE);

        String text = String.format("%.0f", cooldown);
        ctx.getRenderSystem().layout.setText(ctx.getRenderSystem().font, text);
        ctx.getRenderSystem().font.draw(batch, text,
            x + width  / 2f - ctx.getRenderSystem().layout.width  / 2f,
            y + height / 2f + ctx.getRenderSystem().layout.height / 2f);
    }
}
