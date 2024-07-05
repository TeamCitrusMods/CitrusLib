package dev.teamcitrus.citruslib.team;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.arguments.ResourceOrTagKeyArgument;
import net.minecraft.resources.ResourceLocation;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

//TODO: Very much WIP
public class PermissionArgument implements ArgumentType<PermissionArgument.Result> {
    private static final Collection<String> EXAMPLES = Arrays.asList(
            "citruslib:can_invite", "citruslib:can_change_name", "citruslib:change_team_color"
    );
    private final List<ResourceLocation> PERMISSIONS;

    public PermissionArgument() {
        this.PERMISSIONS = PermissionManager.ALL_PERMISSIONS;
    }

    public static PermissionArgument permissions(CommandBuildContext context) {
        return new PermissionArgument();
    }

    @Override
    public Result parse(StringReader reader) throws CommandSyntaxException {
        int i = reader.peek();
        try {
            ResourceLocation resourceLocation = ResourceLocation.read(reader);
            return new Result(resourceLocation);
        } catch (CommandSyntaxException e) {
            reader.setCursor(i);
            throw e;
        }
    }

    @Override
    public Collection<String> getExamples() {
        return EXAMPLES;
    }

    public static class Result implements Predicate<ResourceLocation> {
        private final ResourceLocation permission;

        public Result(ResourceLocation id) {
            this.permission = id;
        }

        @Override
        public boolean test(ResourceLocation resourceLocation) {
            return permission == resourceLocation;
        }
    }
}
