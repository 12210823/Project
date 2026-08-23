import java.net.URISyntaxException;
import java.nio.file.Path;

/**
 * Entry point used by the packaged Windows application.
 *
 * <p>The game loads its artwork and audio from the {@code resource/} directory
 * using relative paths. In a jpackage application image the JAR and that
 * directory are placed together under {@code app/}; this bootstrap makes that
 * location the Java working directory before starting the game.</p>
 */
public final class PackagingBootstrap {
    private PackagingBootstrap() {
    }

    public static void main(String[] args) {
        try {
            Path codeSource = Path.of(
                    PackagingBootstrap.class.getProtectionDomain().getCodeSource().getLocation().toURI()
            );
            Path applicationDirectory = codeSource.getParent();
            if (applicationDirectory != null) {
                System.setProperty("user.dir", applicationDirectory.toString());
            }
        } catch (URISyntaxException ignored) {
            // Fall back to the launcher's existing working directory.
        }

        Main.main(args);
    }
}
