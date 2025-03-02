package tomson124.simplyjetpacks.config;

public class ConfigDefaults {

    // Mod Integration
    public static final boolean enableIntegrationVanilla = true;
    public static final boolean enableIntegrationImmersiveEngineering = true;
    public static final boolean enableIntegrationMekanism = true;
    public static final boolean enableIntegrationEnderIO = true;
    public static final boolean enableIntegrationThermalExpansion = true;
    public static final boolean enableIntegrationThermalDynamics = true;

    // Controls
    public static final boolean invertHoverSneakingBehavior = false;

    // Audio
    public static final boolean enableJetpackSounds = true;

    // GUI
    public static final boolean showThrottle = true;
    public static final boolean showExactEnergy = false;
    public static final boolean enableStateMessages = true;
    public static final boolean enableJetpackHud = true;
    public static final boolean showHoverState = true;
    public static final boolean showEHoverState = true;
    public static final boolean showChargerState = true;
    public static final boolean hudTextShadow = true;
    public static final int hudTextColor = 0xFFFFFF;
    public static final int hudXOffset = 0;
    public static final int hudYOffset = 0;
    public static final long hudScale = 1;
    public static final HUDPosition hudTextPosition = HUDPosition.TOP_LEFT;

    // Misc
    public static final boolean enableJetpackParticles = true;
    public static final boolean enableJoinAdvancements = true;

    public enum HUDPosition {
        TOP_LEFT,
        TOP_CENTER,
        TOP_RIGHT,
        LEFT,
        RIGHT,
        BOTTOM_LEFT,
        BOTTOM_RIGHT;
    }
}
