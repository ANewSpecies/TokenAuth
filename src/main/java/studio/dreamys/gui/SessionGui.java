package studio.dreamys.gui;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.exceptions.AuthenticationUnavailableException;
import com.mojang.authlib.exceptions.InvalidCredentialsException;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.Session;
import org.lwjgl.input.Keyboard;
import studio.dreamys.TokenAuth;

import java.awt.*;
import java.io.IOException;
import java.util.UUID;

public class SessionGui extends GuiScreen {
    private GuiScreen previousScreen;

    private String status = "Session:";
    private GuiTextField sessionField;
    private ScaledResolution sr;

    public SessionGui(GuiScreen previousScreen) {
        this.previousScreen = previousScreen;
    }

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        sr = new ScaledResolution(mc);

        sessionField = new GuiTextField(1, mc.fontRendererObj,sr.getScaledWidth() / 2 - 100, sr.getScaledHeight() / 2, 200, 20);
        sessionField.setMaxStringLength(32767);
        sessionField.setFocused(true);

        buttonList.add(new GuiButton(998, sr.getScaledWidth() / 2 - 100, sr.getScaledHeight() / 2 + 30, 200, 20, "Login"));
        buttonList.add(new GuiButton(999, sr.getScaledWidth() / 2 - 100, sr.getScaledHeight() / 2 + 60, 200, 20, "Restore"));

        super.initGui();
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);

        super.onGuiClosed();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();

        mc.fontRendererObj.drawString(status, sr.getScaledWidth() / 2 - mc.fontRendererObj.getStringWidth(status) / 2, sr.getScaledHeight() / 2 - 30, Color.WHITE.getRGB());
        sessionField.drawTextBox();

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        //login button
        if (button.id == 998) {
            try {
                String[] args = sessionField.getText().split(":");

                String name = args[0];
                //convert stripped string to uuid
                UUID uuid = UUID.fromString(args[1].replaceFirst("(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)", "$1-$2-$3-$4-$5"));
                String token = args[2];

                //check if token is valid
                try {
                    mc.getSessionService().joinServer(new GameProfile(uuid, name), token, uuid.toString());
                } catch (AuthenticationUnavailableException var7) {
                    status = "§cError: Authentication unavailable";
                    return;
                } catch (InvalidCredentialsException var8) {
                    status = "§cError: Invalid credentials";
                    return;
                } catch (AuthenticationException authenticationexception) {
                    status = "§cError: Authentication failed";
                    return;
                }

                //set session and return to previous screen
                mc.session = new Session(args[0], args[1], args[2], "mojang");
                mc.displayGuiScreen(previousScreen);
                return;

            //in case we couldn't set session for some reason
            } catch(Exception e) {
                status = "§cError: Couldn't set session     " + e.getMessage();
                e.printStackTrace();
            }
        }

        //restore button
        if (button.id == 999) {
            try {
                mc.session = TokenAuth.originalSession;
                mc.displayGuiScreen(previousScreen);
                return;
            } catch (Exception e) {
                status = "§cError: Couldn't restore session";
            }
        }

        super.actionPerformed(button);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        sessionField.textboxKeyTyped(typedChar, keyCode);

        if (Keyboard.KEY_ESCAPE == keyCode) mc.displayGuiScreen(previousScreen);
        else super.keyTyped(typedChar, keyCode);
    }
}
