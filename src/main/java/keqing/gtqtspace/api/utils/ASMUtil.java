package keqing.gtqtspace.api.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Optional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.launchwrapper.Launch;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.BiomeProperties;

import net.minecraftforge.fml.relauncher.FMLLaunchHandler;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;

import org.apache.commons.lang3.StringUtils;

public class ASMUtil {

    private static HashMap<String, Field> sFieldCache = new HashMap<>();



    @SuppressWarnings("unchecked")
    public static <T> Optional<T> getStaticObject(Class<?> clazz, String fieldName) {
        Optional<T> object = Optional.empty();
        try {
            Field result = clazz.getDeclaredField(fieldName);
            result.setAccessible(true);
            object = Optional.of((T) result.get(null));
        } catch (Exception e) {
        }
        return object;
    }

    /**
     * Finds a method with the specified name and parameters in the given class and makes it accessible. <p> Throws an exception if the method is not found.
     *
     * @param  clazz          The class to find the method on.
     * @param  methodName     The name of the method to find (used in developer environments, i.e. "getWorldTime").
     * @param  parameterTypes The parameter types of the method to find.
     *
     * @return                The method with the specified name and parameters in the given class.
     */
    @Nonnull
    public static Method findMethod(@Nonnull Class<?> clazz, @Nonnull String methodName, Class<?>... parameterTypes)
    {
        Preconditions.checkNotNull(clazz);
        Preconditions.checkArgument(StringUtils.isNotEmpty(methodName), "Method name cannot be empty");
        try
        {
            Method m = clazz.getDeclaredMethod(methodName, parameterTypes);
            m.setAccessible(true);
            return m;
        } catch (Exception e)
        {
            throw new ReflectionThrows.UnableToFindMethodException(e);
        }
    }

    /**
     * Finds a method with the specified name and parameters in the given class and makes it accessible. Note: for performance, store the returned value and avoid calling this repeatedly. <p> Throws an exception if the method is not found.
     *
     * @param  clazz          The class to find the method on.
     * @param  methodName     The name of the method to find (used in developer environments, i.e. "getWorldTime").
     * @param  methodObfName  The obfuscated name of the method to find (used in obfuscated environments, i.e. "getWorldTime"). If the name you are looking for is on a class that is never obfuscated, this should be null.
     * @param  parameterTypes The parameter types of the method to find.
     *
     * @return                The method with the specified name and parameters in the given class.
     */
    @Nonnull
    public static Method findMethod(@Nonnull Class<?> clazz, @Nonnull String methodName, @Nullable String methodObfName, Class<?>... parameterTypes)
    {
        Preconditions.checkNotNull(clazz);
        Preconditions.checkArgument(StringUtils.isNotEmpty(methodName), "Method name cannot be empty");

        String nameToFind = FMLLaunchHandler.isDeobfuscatedEnvironment() ? methodName : MoreObjects.firstNonNull(methodObfName, methodName);

        try
        {
            Method m = clazz.getDeclaredMethod(nameToFind, parameterTypes);
            m.setAccessible(true);
            return m;
        } catch (Exception e)
        {
            throw new ReflectionThrows.UnableToFindMethodException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T, E> T getPrivateValue(Class<? super E> classToAccess, @Nullable E instance, String fieldName) {
        try {
            return (T) findField(classToAccess, fieldName, null).get(instance);
        } catch (Exception e) {
            e.getStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static <T, E> T getPrivateValue(Class<? super E> classToAccess, @Nullable E instance, String fieldName, @Nullable String fieldObfName) {
        try {
            return (T) findField(classToAccess, fieldName, fieldObfName).get(instance);
        } catch (Exception e) {
            e.getStackTrace();
        }
        return null;
    }

    public static <T, E> void setPrivateValue(Class<? super T> classToAccess, @Nullable T instance, @Nullable E value, String fieldName) {
        try {
            findField(classToAccess, fieldName, null).set(instance, value);
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public static <T, E> void setPrivateValue(Class<? super T> classToAccess, @Nullable T instance, @Nullable E value, String fieldName, @Nullable String fieldObfName) {
        try {
            findField(classToAccess, fieldName, fieldObfName).set(instance, value);
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public static Field findField(@Nonnull Class<?> clazz, @Nonnull String fieldName, @Nullable String fieldObfName) {
        Preconditions.checkNotNull(clazz);
        Preconditions.checkArgument(StringUtils.isNotEmpty(fieldName), "Field name cannot be empty");

        String nameToFind = (Boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment") ? fieldName : MoreObjects.firstNonNull(fieldObfName, fieldName);
        try {
            Field f = clazz.getDeclaredField(nameToFind);
            f.setAccessible(true);
            return f;
        } catch (Exception e) {
            e.getStackTrace();
        }
        return null;
    }


    public static <E> String getBiomeName(BiomeProperties instance) {
        return ASMUtil.getPrivateValue(Biome.BiomeProperties.class, instance, "biomeName", "field_185412_a");
    }

    static class ReflectionThrows
    {

        public static class UnableToFindMethodException extends RuntimeException
        {

            private static final long serialVersionUID = 1L;

            public UnableToFindMethodException(String[] methodNames, Exception failed)
            {
                super(failed);
            }

            public UnableToFindMethodException(Throwable failed)
            {
                super(failed);
            }
        }

        public static class UnableToFindClassException extends RuntimeException
        {

            private static final long serialVersionUID = 1L;

            public UnableToFindClassException(String[] classNames, @Nullable Exception err)
            {
                super(err);
            }
        }

        public static class UnableToAccessFieldException extends RuntimeException
        {

            private static final long serialVersionUID = 1L;

            public UnableToAccessFieldException(String[] fieldNames, Exception e)
            {
                super(e);
            }

            public UnableToAccessFieldException(Exception e)
            {
                super(e);
            }
        }

        public static class UnableToFindFieldException extends RuntimeException
        {

            private static final long serialVersionUID = 1L;

            public UnableToFindFieldException(String[] fieldNameList, Exception e)
            {
                super(e);
            }

            public UnableToFindFieldException(Exception e)
            {
                super(e);
            }
        }

        public static class UnknownConstructorException extends RuntimeException
        {

            private static final long serialVersionUID = 1L;

            public UnknownConstructorException(final String message)
            {
                super(message);
            }
        }
    }
}