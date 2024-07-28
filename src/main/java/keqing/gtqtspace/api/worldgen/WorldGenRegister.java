package keqing.gtqtspace.api.worldgen;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import gregtech.api.GTValues;
import gregtech.api.util.FileUtility;
import gregtech.api.util.GTLog;
import gregtech.api.worldgen.config.IWorldgenDefinition;
import gregtech.api.worldgen.config.WorldGenRegistry;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraftforge.fml.common.Loader;
import org.apache.commons.io.IOUtils;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WorldGenRegister {
    public static final WorldGenRegister INSTANCE = new WorldGenRegister();
    private static final int VIRTUAL_ORE_VEIN_VERSION = 2;
    private final Int2ObjectMap<String> namedDimensions = new Int2ObjectOpenHashMap<>();
    private final List<VirtualOreDepositDefinition> registeredVirutalOreVeinsDefinitions = new ArrayList<>();
    private final List<VirtualOreDepositDefinition> removedVirtualOreVeinDefinitions = new ArrayList<>();
    private final List<VirtualOreDepositDefinition> addonRegisteredVirtualOreVeinDefinitions = new ArrayList<>();

    public static void init() throws IOException {

        /*
        try {
            WorldGenRegister.removeGTConfigs();
        } catch (IOException e) {
            GTLog.logger.fatal("Failed to replace GT worldgen configs", e);
        }
*/
        try {
            WorldGenRegister.copyCustomConfigs();
        } catch (IOException exception) {
            GTLog.logger.fatal("Failed to add GA worldgen", exception);
        }

        WorldGenRegistry.INSTANCE.reinitializeRegisteredVeins();

        try {
            INSTANCE.reinitializeRegisteredVirtualOreVeins();
        } catch (IOException | RuntimeException exception) {
            GTLog.logger.fatal("Failed to initialize worldgen registry.", exception);
        }
    }


    public void reinitializeRegisteredVirtualOreVeins() throws IOException {
        GTLog.logger.info("Reloading ore generation files from config...");
        registeredVirutalOreVeinsDefinitions.clear();
        Path configPath = Loader.instance().getConfigDir().toPath().resolve(GTValues.MODID);
        // The Path for the file used to name dimensions for the JEI ore gen page
        Path dimensionsFile = configPath.resolve("dimensions.json");
        // The folder where worldgen definitions are stored
        Path worldgenRootPath = configPath.resolve("worldgen");
        // Lock file used to determine if the worldgen files need to be regenerated
        // This is used to ensure modifications to ore gen in modpacks are not overwritten
        Path jarFileExtractLock = configPath.resolve("worldgen_extracted_gcyl.json");
        if (!Files.exists(worldgenRootPath))
            Files.createDirectories(worldgenRootPath);

        // Remove the old extract lock file if it exists to remove clutter
        Path jarFileExtractLockOLD = configPath.resolve("worldgen_extracted.txt");
        Files.deleteIfExists(jarFileExtractLockOLD);

        // The folder where all physical veins are stored
        Path virtualPath = worldgenRootPath.resolve("virtual");
        if (!Files.exists(virtualPath))
            Files.createDirectories(virtualPath);

        // Checks if the dimension file exists. If not, creates the file and extracts the defaults from the mod jar
        if (!Files.exists(dimensionsFile)) {
            Files.createFile(dimensionsFile);
            extractJarVeinDefinitions(configPath, dimensionsFile);
        }

        if (Files.exists(jarFileExtractLock)) {
            JsonObject extractLock = FileUtility.tryExtractFromFile(jarFileExtractLock);
            if (extractLock != null) {
                boolean needsUpdate = false;
                if (extractLock.get("virtualOreVersion").getAsInt() != VIRTUAL_ORE_VEIN_VERSION) {
                    extractJarVeinDefinitions(configPath, virtualPath);
                    needsUpdate = true;
                }
                // bump the version(s) on the lock file if needed
                if (needsUpdate) {
                    extractJarVeinDefinitions(configPath, jarFileExtractLock);
                }
            }
        } else {
            // force an override here as needed for updating legacy config blocks
            if (VIRTUAL_ORE_VEIN_VERSION > 1) {
                extractJarVeinDefinitions(configPath, virtualPath);
            }
            // create extraction lock since it doesn't exist
            Files.createFile(jarFileExtractLock);
            extractJarVeinDefinitions(configPath, jarFileExtractLock);
        }

        // attempt extraction if worldgen root directory is empty
        boolean shouldExtract;
        try (Stream<Path> stream = Files.list(worldgenRootPath.resolve(virtualPath))) {
            shouldExtract = !stream.findFirst().isPresent();
        }
        if (shouldExtract) {
            extractJarVeinDefinitions(configPath, virtualPath);
        }

        // Read the dimensions name from the dimensions file
        gatherNamedDimensions(dimensionsFile);

        // Will always fail when called from initializeRegistry
        // Placed here to delete the file before being gathered and having its definition initialized
        if (!removedVirtualOreVeinDefinitions.isEmpty()) {
            removeExistingFiles(virtualPath, removedVirtualOreVeinDefinitions);
        }

        // Gather the worldgen vein files from the various folders in the config
        List<Path> virtualOreVeinFiles;
        try (Stream<Path> stream = Files.walk(virtualPath)) {
            virtualOreVeinFiles = stream.filter(path -> path.toString().endsWith(".json"))
                    .filter(Files::isRegularFile)
                    .collect(Collectors.toList());
        }

        for (Path worldgenDefinition : virtualOreVeinFiles) {

            // Tries to extract the json worldgen definition from the file
            JsonObject element = FileUtility.tryExtractFromFile(worldgenDefinition);
            if (element == null) {
                break;
            }

            // Finds the file name to create the Definition with, using a consistent separator character
            String depositName = FileUtility
                    .nativeSepToSlash(virtualPath.relativize(worldgenDefinition).toString());

            try {
                // Creates the deposit definition and initializes various components based on the json entries in the
                // file
                VirtualOreDepositDefinition deposit = new VirtualOreDepositDefinition(depositName);
                // Adds the registered definition to the list of all registered definitions
                if (deposit.initializeFromConfig(element)) {
                    registeredVirutalOreVeinsDefinitions.add(deposit);
                }
            } catch (RuntimeException exception) {
                GTLog.logger.error("Failed to parse worldgen definition {} on path {}", depositName, worldgenDefinition,
                        exception);
            }
        }

        addAddonFiles(worldgenRootPath, addonRegisteredVirtualOreVeinDefinitions, registeredVirutalOreVeinsDefinitions);

        GTLog.logger.info("Loaded {} virtual ore worldgen definitions", registeredVirutalOreVeinsDefinitions.size());
        GTLog.logger.info("Loaded {} virtual ore worldgen definitions from addon mods",
                addonRegisteredVirtualOreVeinDefinitions.size());
        GTLog.logger.info("Loaded {} total worldgen definitions",
                registeredVirutalOreVeinsDefinitions.size() + registeredVirutalOreVeinsDefinitions.size());
    }

    private static <T extends IWorldgenDefinition> void addAddonFiles(Path root,  List<T> definitions,
                                                                       List<T> registeredDefinitions) {
        Iterator<T> it = definitions.iterator();
        while (it.hasNext()) {
            T definition = it.next();

            JsonObject element = FileUtility
                    .tryExtractFromFile(root.resolve(FileUtility.slashToNativeSep(definition.getDepositName())));

            if (element == null) {
                GTLog.logger.error("Addon mod tried to register bad ore definition at {}", definition.getDepositName());
                it.remove();
                continue;
            }

            try {
                definition.initializeFromConfig(element);
                registeredDefinitions.add(definition);
            } catch (RuntimeException exception) {
                GTLog.logger.error("Failed to parse addon worldgen definition {}", definition.getDepositName(),
                        exception);
            }
        }
    }

    private static void removeExistingFiles(Path root,  List<? extends IWorldgenDefinition> definitions) {
        for (IWorldgenDefinition definition : definitions) {
            Path filePath = root.resolve(Paths.get(FileUtility.slashToNativeSep(definition.getDepositName())));

            try {
                if (Files.exists(filePath)) {
                    Files.delete(filePath);
                    GTLog.logger.info("Removed oregen file at {}", definition.getDepositName());
                }
            } catch (IOException exception) {
                GTLog.logger.error("Failed to remove oregen file at {}", definition.getDepositName());
            }
        }
    }

    private static void copyCustomConfigs() throws IOException {
        Path configPath = Loader.instance().getConfigDir().toPath().resolve(GTValues.MODID);
        Path worldgenVirtualRootPath = configPath.resolve("worldgen/virtual");
        Path jarFileExtractLock = configPath.resolve("worldgen_extracted_gcyl.json");

        if (!Files.exists(worldgenVirtualRootPath)) {
            Files.createDirectories(worldgenVirtualRootPath);
        }

        if (!Files.exists(jarFileExtractLock) || !Files.list(worldgenVirtualRootPath).peek(path -> GTLog.logger.info(path)).findFirst().isPresent()) {
            if (!Files.exists(jarFileExtractLock)) {
                Files.createFile(jarFileExtractLock);
                extractJarVeinDefinitions(configPath, jarFileExtractLock);
            }

            WorldGenRegister.extractJarVeinDefinitions(configPath, worldgenVirtualRootPath);
        }

    }

    private void gatherNamedDimensions(Path dimensionsFile) {
        JsonObject element = FileUtility.tryExtractFromFile(dimensionsFile);
        if (element == null) {
            return;
        }

        try {
            JsonArray dims = element.getAsJsonArray("dims");
            for (JsonElement dim : dims) {
                namedDimensions.put(dim.getAsJsonObject().get("dimID").getAsInt(),
                        dim.getAsJsonObject().get("dimName").getAsString());
            }
        } catch (RuntimeException exception) {
            GTLog.logger.error("Failed to parse named dimensions", exception);
        }
    }


    private static void extractJarVeinDefinitions(Path configPath, Path targetPath) throws IOException {
        // The path of the worldgen folder in the config folder
        Path worldgenRootPath = configPath.resolve("worldgen");
        // The path of the physical vein folder in the config folder
        Path oreVeinRootPath = worldgenRootPath.resolve("vein");
        // The path of the bedrock fluid vein folder in the config folder
        Path bedrockFluidVeinRootPath = worldgenRootPath.resolve("fluid");
        // The path of the virtual ore vein folder in the config folder
        Path virtualOreVeinRootPath = worldgenRootPath.resolve("virtual");
        // The path of the named dimensions file in the config folder
        Path dimensionsRootPath = configPath.resolve("dimensions.json");
        // THe path of the lock file in the config folder
        Path extractLockPath = configPath.resolve("worldgen_extracted_gcyl.json");
        FileSystem zipFileSystem = null;
        try {
            URL sampleUrl = WorldGenRegistry.class.getResource("/assets/gcyl/");
            if (sampleUrl == null) throw new FileNotFoundException("Could not find .gtassetsroot");
            URI sampleUri = sampleUrl.toURI();
            // The Path for representing the worldgen folder in the assets folder in the Gregtech resources folder in
            // the jar
            Path worldgenJarRootPath;
            // The Path for representing the vein folder in the vein folder in the assets folder in the Gcyl
            // resources folder in the jar
            Path oreVeinJarRootPath;
            // The Path for representing the fluid folder in the vein folder in the assets folder in the Gcyl
            // resources folder in the jar
            Path bedrockFluidJarRootPath;
            // The Path for representing the virtual folder in the vein folder in the assets folder in the Gcyl
            // resources folder in the jar
            Path virtualOreJarRootPath;
            if (sampleUri.getScheme().equals("jar") || sampleUri.getScheme().equals("zip")) {
                zipFileSystem = FileSystems.newFileSystem(sampleUri, Collections.emptyMap());
                worldgenJarRootPath = zipFileSystem.getPath("/assets/gcyl/worldgen");

                virtualOreJarRootPath = zipFileSystem.getPath("/assets/gcyl/worldgen/virtual");
            } else if (sampleUri.getScheme().equals("file")) {
                URL url = WorldGenRegistry.class.getResource("/assets/gcyl/worldgen");
                if (url == null) throw new FileNotFoundException("Could not find /assets/gcyl/worldgen");
                worldgenJarRootPath = Paths.get(url.toURI());

                url = WorldGenRegistry.class.getResource("/assets/gcyl/worldgen/vein");
                if (url == null) throw new FileNotFoundException("Could not find /assets/gcyl/worldgen/vein");
                oreVeinJarRootPath = Paths.get(url.toURI());

                url = WorldGenRegistry.class.getResource("/assets/gcyl/worldgen/fluid");
                if (url == null) throw new FileNotFoundException("Could not find /assets/gcyl/worldgen/fluid");
                bedrockFluidJarRootPath = Paths.get(url.toURI());

                url = WorldGenRegistry.class.getResource("/assets/gcyl/worldgen/virtual");
                if (url == null) throw new FileNotFoundException("Could not find /assets/gcyl/worldgen/virtual");
                virtualOreJarRootPath = Paths.get(url.toURI());
            } else {
                throw new IllegalStateException(
                        "Unable to locate absolute path to worldgen root directory: " + sampleUri);
            }

            // Attempts to extract the worldgen definition jsons
            if (targetPath.compareTo(virtualOreVeinRootPath) == 0) {
                GTLog.logger.info("Attempting extraction of standard worldgen definitions from {} to {}",
                        virtualOreJarRootPath, virtualOreVeinRootPath);
                // Find all the default worldgen files in the assets folder
                List<Path> jarFiles;
                try (Stream<Path> stream = Files.walk(virtualOreJarRootPath)) {
                    jarFiles = stream.filter(Files::isRegularFile).collect(Collectors.toList());
                }

                // Replaces or creates the default worldgen files
                for (Path jarFile : jarFiles) {
                    Path worldgenPath = virtualOreVeinRootPath
                            .resolve(virtualOreJarRootPath.relativize(jarFile).toString());
                    Files.createDirectories(worldgenPath.getParent());
                    Files.copy(jarFile, worldgenPath, StandardCopyOption.REPLACE_EXISTING);
                }
                GTLog.logger.info("Extracted {} builtin worldgen virtual ore definitions into virtual folder",
                        jarFiles.size());
            }
            // Attempts to extract the named dimensions json folder
            else if (targetPath.compareTo(dimensionsRootPath) == 0) {
                GTLog.logger.info("Attempting extraction of standard dimension definitions from {} to {}",
                        worldgenJarRootPath, dimensionsRootPath);

                Path dimensionFile = worldgenJarRootPath.resolve("dimensions.json");

                Path worldgenPath = dimensionsRootPath
                        .resolve(worldgenJarRootPath.relativize(worldgenJarRootPath).toString());
                Files.copy(dimensionFile, worldgenPath, StandardCopyOption.REPLACE_EXISTING);

                GTLog.logger.info("Extracted builtin dimension definitions into worldgen folder");
            }
            // Attempts to extract lock txt file
            else if (targetPath.compareTo(extractLockPath) == 0) {
                Path extractLockFile = worldgenJarRootPath.resolve("worldgen_extracted_gcyl.json");

                Path worldgenPath = extractLockPath
                        .resolve(worldgenJarRootPath.relativize(worldgenJarRootPath).toString());
                Files.copy(extractLockFile, worldgenPath, StandardCopyOption.REPLACE_EXISTING);

                GTLog.logger.info("Extracted jar lock file into worldgen folder");
            }

        } catch (URISyntaxException impossible) {
            // this is impossible, since getResource always returns valid URI
            throw new RuntimeException(impossible);
        } finally {
            if (zipFileSystem != null) {
                // close zip file system to avoid issues
                IOUtils.closeQuietly(zipFileSystem);
            }
        }
    }

    public static List<VirtualOreDepositDefinition> getVirtualOreDepositDefinitions() {
        return Collections.unmodifiableList(INSTANCE.registeredVirutalOreVeinsDefinitions);
    }
}
