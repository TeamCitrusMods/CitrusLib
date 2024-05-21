package dev.teamcitrus.citruslib.reload;

import dev.teamcitrus.citruslib.codec.CodecProvider;
import org.jetbrains.annotations.ApiStatus;

import java.util.function.Consumer;

/**
 * Taken from Placebo with consent of Shadows
 * @author Shadows of Fire
 * @link <a href="https://github.com/Shadows-of-Fire/Placebo/tree/1.20.4">...</a>
 */
public interface RegistryCallback<R extends CodecProvider<? super R>> {
    public void beginReload(DynamicRegistry<R> manager);

    public void onReload(DynamicRegistry<R> manager);

    public static <R extends CodecProvider<? super R>> RegistryCallback<R> create(Consumer<DynamicRegistry<R>> beginReload, Consumer<DynamicRegistry<R>> onReload) {
        return new Delegated<>(beginReload, onReload);
    }

    public static <R extends CodecProvider<? super R>> RegistryCallback<R> beginOnly(Consumer<DynamicRegistry<R>> beginReload) {
        return new Delegated<>(beginReload, v -> {
        });
    }

    public static <R extends CodecProvider<? super R>> RegistryCallback<R> reloadOnly(Consumer<DynamicRegistry<R>> onReload) {
        return new Delegated<>(v -> {
        }, onReload);
    }

    @ApiStatus.Internal
    class Delegated<R extends CodecProvider<? super R>> implements RegistryCallback<R> {

        private Consumer<DynamicRegistry<R>> beginReload, onReload;

        public Delegated(Consumer<DynamicRegistry<R>> beginReload, Consumer<DynamicRegistry<R>> onReload) {
            this.beginReload = beginReload;
            this.onReload = onReload;
        }

        @Override
        public void beginReload(DynamicRegistry<R> manager) {
            this.beginReload.accept(manager);
        }

        @Override
        public void onReload(DynamicRegistry<R> manager) {
            this.onReload.accept(manager);
        }

    }
}


