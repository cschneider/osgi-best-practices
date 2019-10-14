package net.lr.osgibp.it;

import static org.ops4j.pax.exam.CoreOptions.maven;
import static org.ops4j.pax.exam.CoreOptions.mavenBundle;
import static org.ops4j.pax.exam.CoreOptions.systemProperty;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.configureConsole;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.features;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.karafDistributionConfiguration;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.keepRuntimeFolder;

import java.io.File;

import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.options.MavenArtifactProvisionOption;
import org.ops4j.pax.exam.options.MavenArtifactUrlReference;
import org.ops4j.pax.exam.options.MavenUrlReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseITest {
    protected static final String KARAF_VERSION = "4.2.6";

    protected final Logger log = LoggerFactory.getLogger(this.getClass());
    protected final MavenArtifactUrlReference karafUrl;
    protected final MavenUrlReference karafStandardRepo;
    protected final MavenUrlReference ariesJaxRsRepo;

    public BaseITest() {
        karafUrl = maven("org.apache.karaf", "apache-karaf-minimal", KARAF_VERSION).type("tar.gz");
        karafStandardRepo = maven("org.apache.karaf.features", "standard", KARAF_VERSION).classifier("features")
                .type("xml");
        ariesJaxRsRepo = maven("org.apache.aries.jax.rs", "org.apache.aries.jax.rs.features", "1.0.5").type("xml");
    }

    public Option[] config() {
        return new Option[] //
        {
                // KarafDistributionOption.debugConfiguration("5005", true),
                karafDistributionConfiguration().frameworkUrl(karafUrl).unpackDirectory(new File("target", "exam"))
                        .useDeployFolder(false),
                systemProperty("karaf.log").value("log"), // Workaround for karaf 4.2.7 in exam
                keepRuntimeFolder(), // Allows to easily introspect after run
                configureConsole().ignoreLocalConsole(),

                // Awaitility support
                mvn("org.awaitility", "awaitility"),
                mvn("org.apache.servicemix.bundles", "org.apache.servicemix.bundles.hamcrest"),

                // This works even better if you define a feature for your own project
                features(ariesJaxRsRepo, "aries-jax-rs-whiteboard", "aries-jax-rs-whiteboard-jackson"), //
                mvn("net.lr.osgibp", "net.lr.osgibp.backend")//
        };
    }

    private MavenArtifactProvisionOption mvn(String groupId, String artifactId) {
        return mavenBundle(groupId, artifactId).versionAsInProject();
    }

}
