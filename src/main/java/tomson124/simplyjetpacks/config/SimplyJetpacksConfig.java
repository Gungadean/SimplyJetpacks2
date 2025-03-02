package tomson124.simplyjetpacks.config;

import net.minecraft.client.Minecraft;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.server.ServerLifecycleHooks;
import tomson124.simplyjetpacks.SimplyJetpacks;
import tomson124.simplyjetpacks.item.JetpackType;
import tomson124.simplyjetpacks.network.NetworkHandler;
import tomson124.simplyjetpacks.network.packets.PacketJetpackConfigSync;

import java.util.List;

@Mod.EventBusSubscriber(modid = SimplyJetpacks.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SimplyJetpacksConfig {

    public static final Builder CLIENT_BUILDER = new Builder();
    public static final Builder COMMON_BUILDER = new Builder();
    public static final Builder SERVER_BUILDER = new Builder();

    public static ForgeConfigSpec CLIENT_CONFIG;
    public static ForgeConfigSpec COMMON_CONFIG;
    public static ForgeConfigSpec SERVER_CONFIG;

    public static void register() {
        setupClientConfig();
        setupCommonConfig();
        setupServerConfig();

        CLIENT_CONFIG = CLIENT_BUILDER.build();
        COMMON_CONFIG = COMMON_BUILDER.build();
        SERVER_CONFIG = SERVER_BUILDER.build();

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, CLIENT_CONFIG);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, COMMON_CONFIG);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, SERVER_CONFIG);
    }

    private static void setupClientConfig() {
        CLIENT_BUILDER.comment("Simply Jetpacks 2 - Client Configurations").push("simplyjetpacks-client");

        CLIENT_BUILDER.comment("Controls Configurations").push("controls");

        invertHoverSneakingBehavior = CLIENT_BUILDER
                .comment("This sets whether you must hold sneak to hover.")
                .translation("config.simplyjetpacks.invertHoverSneakingBehavior")
                .define("invertHoverSneakingBehavior", ConfigDefaults.invertHoverSneakingBehavior);

        CLIENT_BUILDER.pop();

        CLIENT_BUILDER.comment("Audio Configurations").push("audio");

        enableJetpackSounds = CLIENT_BUILDER
                .comment("This sets whether Jetpack sounds will play.")
                .translation("config.simplyjetpacks.enableJetpackSounds")
                .define("enableJetpackSounds", ConfigDefaults.enableJetpackSounds);

        CLIENT_BUILDER.pop();

        CLIENT_BUILDER.comment("Visual Configurations").push("visual");

        enableJetpackParticles = CLIENT_BUILDER
                .comment("This sets whether Jetpack particles will be displayed.")
                .translation("config.simplyjetpacks.enableJetpackParticles")
                .define("enableJetpackParticles", ConfigDefaults.enableJetpackParticles);

        CLIENT_BUILDER.pop();

        CLIENT_BUILDER.comment("GUI Configurations").push("gui");

        showThrottle = CLIENT_BUILDER
                .comment("Show the Throttle value in the Jetpack HUD.")
                .translation("config.simplyjetpacks.showThrottle")
                .define("showThrottle", ConfigDefaults.showThrottle);

        showExactEnergy = CLIENT_BUILDER
                .comment("Show the exact energy of the Jetpack in the HUD.")
                .translation("config.simplyjetpacks.showExactEnergy")
                .define("showExactEnergy", ConfigDefaults.showExactEnergy);

        enableStateMessages = CLIENT_BUILDER
                .comment("This sets whether or not Jetpack state messages will show.")
                .translation("config.simplyjetpacks.enableStateMessages")
                .define("enableStateMessages", ConfigDefaults.enableStateMessages);

        enableJetpackHud = CLIENT_BUILDER
                .comment("This sets whether or not the Jetpack HUD will be visible.")
                .translation("config.simplyjetpacks.enableJetpackHud")
                .define("enableJetpackHud", ConfigDefaults.enableJetpackHud);

        showHoverState = CLIENT_BUILDER
                .comment("Show the Hover State in the HUD.")
                .translation("config.simplyjetpacks.showHoverState")
                .define("showHoverState", ConfigDefaults.showHoverState);

        showEHoverState = CLIENT_BUILDER
                .comment("Show the Emergency Hover State in the HUD.")
                .translation("config.simplyjetpacks.showEHoverState")
                .define("showEHoverState", ConfigDefaults.showEHoverState);

        showChargerState = CLIENT_BUILDER
                .comment("Show the Charger State in the HUD.")
                .translation("config.simplyjetpacks.showChargerState")
                .define("showChargerState", ConfigDefaults.showChargerState);

        hudTextColor = CLIENT_BUILDER
                .comment("This sets the color of the Jetpack HUD.")
                .translation("config.simplyjetpacks.hudTextColor")
                .defineInRange("hudTextColor", ConfigDefaults.hudTextColor, Integer.MIN_VALUE, Integer.MAX_VALUE);

        hudTextPosition = CLIENT_BUILDER
                .comment("Set the position of the Jetpack HUD on the screen.")
                .translation("config.simplyjetpacks.hudTextPosition")
                .defineEnum("hudTextPosition", ConfigDefaults.hudTextPosition);

        hudXOffset = CLIENT_BUILDER
                .comment("Set the X offset of the Jetpack HUD on the screen.")
                .translation("config.simplyjetpacks.hudXOffset")
                .defineInRange("hudXOffset", ConfigDefaults.hudXOffset, Integer.MIN_VALUE, Integer.MAX_VALUE);

        hudYOffset = CLIENT_BUILDER
                .comment("Set the Y offset of the Jetpack HUD on the screen.")
                .translation("config.simplyjetpacks.hudYOffset")
                .defineInRange("hudYOffset", ConfigDefaults.hudYOffset, Integer.MIN_VALUE, Integer.MAX_VALUE);

        hudScale = CLIENT_BUILDER
                .comment("Set the scale of the Jetpack HUD on the screen.")
                .translation("config.simplyjetpacks.hudScale")
                .defineInRange("hudScale", ConfigDefaults.hudScale, 1, 100);

        hudTextShadow = CLIENT_BUILDER
                .comment("Set if the Jetpack HUD values have text shadows.")
                .translation("config.simplyjetpacks.hudTextShadow")
                .define("hudTextShadow", ConfigDefaults.hudTextShadow);

        CLIENT_BUILDER.pop();
        CLIENT_BUILDER.pop();
    }

    private static void setupCommonConfig() {
        COMMON_BUILDER.comment("Simply Jetpacks 2 - Common Configurations").push("simplyjetpacks-common");

        COMMON_BUILDER.comment("Integration Configurations").push("integration");

        enableIntegrationVanilla = COMMON_BUILDER
                .comment("Enable Vanilla Jetpacks Integration.")
                .translation("config.simplyjetpacks.enableIntegrationVanilla")
                .worldRestart()
                .define("enableIntegrationVanilla", ConfigDefaults.enableIntegrationVanilla);

        enableIntegrationImmersiveEngineering = COMMON_BUILDER
                .comment("Enable Immersive Engineering Jetpacks Integration.")
                .translation("config.simplyjetpacks.enableIntegrationImmersiveEngineering")
                .worldRestart()
                .define("enableIntegrationImmersiveEngineering", ConfigDefaults.enableIntegrationImmersiveEngineering);

        enableIntegrationMekanism = COMMON_BUILDER
                .comment("Enable Mekanism Jetpacks Integration.")
                .translation("config.simplyjetpacks.enableIntegrationMekanism")
                .worldRestart()
                .define("enableIntegrationMekanism", ConfigDefaults.enableIntegrationMekanism);

        enableIntegrationEnderIO = COMMON_BUILDER
                .comment("Enable EnderIO Jetpacks Integration.")
                .translation("config.simplyjetpacks.enableIntegrationEnderIO")
                .worldRestart()
                .define("enableIntegrationEnderIO", ConfigDefaults.enableIntegrationEnderIO);

        enableIntegrationThermalExpansion = COMMON_BUILDER
                .comment("Enable Thermal Expansion Jetpacks Integration.")
                .translation("config.simplyjetpacks.enableIntegrationThermalExpansion")
                .worldRestart()
                .define("enableIntegrationThermalExpansion", ConfigDefaults.enableIntegrationThermalExpansion);

        enableIntegrationThermalDynamics = COMMON_BUILDER
                .comment("Enable Thermal Dynamics Jetpacks Integration.")
                .translation("config.simplyjetpacks.enableIntegrationThermalDynamics")
                .worldRestart()
                .define("enableIntegrationThermalDynamics", ConfigDefaults.enableIntegrationThermalDynamics);

        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Jetpack Tuning Configurations").push("tuning");
        JetpackConfig.createJetpackConfig(COMMON_BUILDER);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Misc Configurations").push("misc");

        enableJoinAdvancements = COMMON_BUILDER
                .comment("Enable Advancements on Player join.")
                .translation("config.simplyjetpacks.enableJoinAdvancements")
                .define("enableJoinAdvancements", ConfigDefaults.enableJoinAdvancements);

        COMMON_BUILDER.pop();
        COMMON_BUILDER.pop();
    }

    private static void setupServerConfig() {
        SERVER_BUILDER.comment("Simply Jetpacks 2 - Server Configurations").push("simplyjetpacks-server");
        //JetpackConfig.createJetpackConfig(SERVER_BUILDER);
        SERVER_BUILDER.pop();
    }

    public static void sendServerConfigFiles(Player player) {
        JetpackType.loadAllConfigs();
        for(JetpackType jetpack : JetpackType.JETPACK_ALL) {
            NetworkHandler.sendToClient(new PacketJetpackConfigSync(jetpack), (ServerPlayer) player);
        }
    }

    @SubscribeEvent
    public static void onLoad(final ModConfigEvent.Loading configEvent) {
        SimplyJetpacks.LOGGER.info("Config Loaded: {}", configEvent.getConfig().getFileName());

        // Prevent loading of jetpack configs before common config has been loaded by system.
        if (configEvent.getConfig().getFileName().equals("simplyjetpacks-common.toml")) {
            JetpackType.loadAllConfigs();
        }
    }

    @SubscribeEvent
    public static void onFileChange(final ModConfigEvent.Reloading configEvent) {
        SimplyJetpacks.LOGGER.info("Config Re-Loaded: {}", configEvent.getConfig().getFileName());

        if (configEvent.getConfig().getFileName().equals("simplyjetpacks-common.toml")) {
            // Ensure config is only reloaded for the server side
            if (FMLEnvironment.dist.isDedicatedServer() || Minecraft.getInstance().isLocalServer()) {

                List<ServerPlayer> playerList = ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayers();
                SimplyJetpacks.LOGGER.info("Server jetpack config updated. Syncing {} player(s) configs.", playerList.size());
                for (Player player : playerList) {
                    sendServerConfigFiles(player);
                }
                SimplyJetpacks.LOGGER.info("Finished syncing server jetpack configs.");
            }
        }
    }

    // Client
    public static ForgeConfigSpec.BooleanValue invertHoverSneakingBehavior;
    public static ForgeConfigSpec.BooleanValue enableJetpackSounds;
    public static ForgeConfigSpec.BooleanValue enableJetpackParticles;
    public static ForgeConfigSpec.BooleanValue showThrottle;
    public static ForgeConfigSpec.BooleanValue showExactEnergy;
    public static ForgeConfigSpec.BooleanValue enableStateMessages;
    public static ForgeConfigSpec.BooleanValue enableJetpackHud;
    public static ForgeConfigSpec.BooleanValue showHoverState;
    public static ForgeConfigSpec.BooleanValue showEHoverState;
    public static ForgeConfigSpec.BooleanValue showChargerState;
    public static ForgeConfigSpec.BooleanValue hudTextShadow;
    public static ForgeConfigSpec.IntValue hudTextColor;
    public static ForgeConfigSpec.IntValue hudXOffset;
    public static ForgeConfigSpec.IntValue hudYOffset;
    public static ForgeConfigSpec.LongValue hudScale;
    public static ForgeConfigSpec.EnumValue<ConfigDefaults.HUDPosition> hudTextPosition;

    // Common
    public static ForgeConfigSpec.BooleanValue enableIntegrationVanilla;
    public static ForgeConfigSpec.BooleanValue enableIntegrationImmersiveEngineering;
    public static ForgeConfigSpec.BooleanValue enableIntegrationMekanism;
    public static ForgeConfigSpec.BooleanValue enableIntegrationEnderIO;
    public static ForgeConfigSpec.BooleanValue enableIntegrationThermalExpansion;
    public static ForgeConfigSpec.BooleanValue enableIntegrationThermalDynamics;
    public static ForgeConfigSpec.BooleanValue enableJoinAdvancements;

    // Server
}
