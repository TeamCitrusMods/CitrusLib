package dev.teamcitrus.citruslib.reload;

import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * Taken from Placebo with consent of Shadows
 * @author Shadows of Fire
 * @link <a href="https://github.com/Shadows-of-Fire/Placebo/tree/1.20.4">...</a>
 */
public class DynamicHolder<T> implements Supplier<T> {
    public static final ResourceLocation EMPTY = new ResourceLocation("empty", "empty");
    protected final DynamicRegistry<? super T> registry;
    protected final ResourceLocation id;
    @Nullable protected T value;

    DynamicHolder(DynamicRegistry<? super T> registry, ResourceLocation id) {
        this.id = id;
        this.registry = registry;
    }

    public boolean isBound() {
        this.bind();
        return this.value != null;
    }

    @Override
    public T get() {
        this.bind();
        Objects.requireNonNull(this.value, "Trying to access unbound value: " + this.id);
        return this.value;
    }

    public Optional<T> getOptional() {
        return this.isBound() ? Optional.of(this.value()) : Optional.empty();
    }

    public ResourceLocation getId() {
        return this.id;
    }

    public boolean is(ResourceLocation id) {
        return this.id.equals(id);
    }

    @Override
    @SuppressWarnings("rawtypes")
    public boolean equals(Object obj) {
        return this == obj || obj instanceof DynamicHolder dh && dh.registry == this.registry && dh.id.equals(this.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.registry);
    }

    @SuppressWarnings("unchecked")
    void bind() {
        if (this.value != null) return;
        this.value = (T) this.registry.getValue(this.id);
    }

    void unbind() {
        this.value = null;
    }

    @Deprecated(forRemoval = true)
    public T value() {
        return this.get();
    }

}
