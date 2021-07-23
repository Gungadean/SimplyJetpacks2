package stormedpanda.simplyjetpacks.datagen;

import net.minecraft.data.CustomRecipeBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.util.ResourceLocation;
import stormedpanda.simplyjetpacks.SimplyJetpacks;
import stormedpanda.simplyjetpacks.init.RegistryHandler;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider {

    public ModRecipeProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void buildShapelessRecipes(@Nonnull Consumer<IFinishedRecipe> consumer) {
        CustomRecipeBuilder.special(RegistryHandler.JETPACK_SPECIAL_RECIPE.get()).save(consumer, new ResourceLocation(SimplyJetpacks.MODID, "jetpack_special_recipe").toString());
    }

    private static ResourceLocation modId(String path) {
        return new ResourceLocation(SimplyJetpacks.MODID, path);
    }
}
