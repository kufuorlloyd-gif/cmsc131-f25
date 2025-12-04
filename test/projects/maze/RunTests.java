package projects.maze;


import static org.junit.platform.engine.discovery.ClassNameFilter.includeClassNamePatterns;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectPackage;

import java.io.PrintWriter;

import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.TestPlan;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;
import org.junit.platform.launcher.listeners.TestExecutionSummary.Failure;

public class RunTests {

    public static void main(String[] args) {
        runAccountTests();
    }

    static void runAccountTests() {
        // builder method
        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder
        .request()
        .selectors(selectPackage("projects.maze"))
        .filters(includeClassNamePatterns(".*Test"))
        .build();

        // static factory method
        Launcher launcher = LauncherFactory.create();
        
        SummaryGeneratingListener listener = new SummaryGeneratingListener();
        launcher.registerTestExecutionListeners(listener);

        // disocver and execute unit tests
        TestPlan testPlan = launcher.discover(request);
        launcher.execute(testPlan);

        // Report results
        TestExecutionSummary summary = listener.getSummary();
        System.out.println("Maze Project Unit Test Summary");
        summary.printTo(new PrintWriter(System.out));
        
        // Report test name and message for each failure
        System.out.println(
            "Failures" + System.lineSeparator() + "--------"
        );
        for (int i = 0; i < summary.getFailures().size(); i++) {
            Failure failure = summary.getFailures().get(i);
            System.out.println(
                String.format(
                    "%s%s    %s",
                    failure.getTestIdentifier().getDisplayName(),
                    System.lineSeparator(),

                    failure.getException().getMessage()
                )
            );
        }
    }

}