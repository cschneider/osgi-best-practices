package net.lr.osgibp.app;

import static org.junit.Assert.assertNotNull;

import java.io.Closeable;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

public class ServiceRule implements TestRule {
    private final Bundle bundle = FrameworkUtil.getBundle(this.getClass());
    private Set<Closeable> trackers = new HashSet<>();

    @Override
    public Statement apply(Statement base, Description description) {
        assertNotNull("OSGi Bundle tests must be run inside an OSGi framework", bundle);
        return new Statement() {
            
            @Override
            public void evaluate() throws Throwable {
                
                try {
                    base.evaluate();
                } finally {
                    trackers.stream().forEach(ServiceRule::silentClose);
                }
            }
        };
    }
    
    public <T> T require(Class<T> serviceClass) {
        ServiceTracker<T, T> tracker = new ServiceTracker<T, T>(bundle.getBundleContext(), serviceClass, null);
        tracker.open();
        trackers.add(()->tracker.close());
        try {
            return tracker.waitForService(10000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Timeout waiting for service " + serviceClass.getName());
        }
    }
    
    private static void silentClose(Closeable closeable) {
        try {
            closeable.close();
        } catch (IOException e) {
            // Ignore
        }
    }

}
