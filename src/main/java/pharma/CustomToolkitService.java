package pharma;


import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.testfx.toolkit.ToolkitService;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Custom implementation of TestFX's ToolkitService that bypasses the
 * reflective cleanup routines causing the InaccessibleObjectException.
 */
public class CustomToolkitService implements ToolkitService {


    @Override
    public Future<Stage> setupPrimaryStage(CompletableFuture<Stage> primaryStageFuture, Class<? extends Application> applicationClass, String... applicationArgs) {
        return null;
    }

    @Override
    public Future<Void> setupFixture(Runnable runnable) {
        return null;
    }

    @Override
    public <T> Future<T> setupFixture(Callable<T> callable) {
        return null;
    }

    @Override
    public Future<Stage> setupStage(Stage stage, Consumer<Stage> stageConsumer) {
        return null;
    }

    @Override
    public Future<Scene> setupScene(Stage stage, Supplier<? extends Scene> sceneSupplier) {
        return null;
    }

    @Override
    public Future<Parent> setupSceneRoot(Stage stage, Supplier<? extends Parent> sceneRootSupplier) {
        return null;
    }

    @Override
    public Future<Application> setupApplication(Supplier<Stage> stageSupplier, Class<? extends Application> applicationClass, String... applicationArgs) {
        return null;
    }

    @Override
    public Future<Application> setupApplication(Supplier<Stage> stageSupplier, Supplier<Application> applicationSupplier, String... applicationArgs) {
        return null;
    }

    @Override
    public Future<Void> cleanupApplication(Application application) {
        return null;
    }

    // Add additional overrides if necessary...
}