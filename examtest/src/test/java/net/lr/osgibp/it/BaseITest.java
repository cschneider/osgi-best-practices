package net.lr.osgibp.it;

import static org.ops4j.pax.exam.CoreOptions.*;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.configureConsole;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.debugConfiguration;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.features;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.karafDistributionConfiguration;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.keepRuntimeFolder;

import java.io.File;

import org.ops4j.pax.exam.CoreOptions;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.options.MavenArtifactProvisionOption;
import org.ops4j.pax.exam.options.MavenArtifactUrlReference;
import org.ops4j.pax.exam.options.MavenUrlReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseITest {
    protected static final String KARAF_VERSION = "4.2.8";

    private static final boolean DEBUG = false;

    protected final Logger log = LoggerFactory.getLogger(this.getClass());
    protected final MavenArtifactUrlReference karafUrl;
    protected final MavenUrlReference karafStandardRepo;
    protected final MavenUrlReference osgibpRepo;

    public BaseITest() {
        karafUrl = maven("org.apache.karaf", "apache-karaf-minimal", KARAF_VERSION).type("tar.gz");
        karafStandardRepo = maven("org.apache.karaf.features", "standard", KARAF_VERSION).classifier("features")
                .type("xml");

        osgibpRepo = maven("net.lr.osgibp", "features", "1.0.0-SNAPSHOT").classifier("features").type("xml");
    }

    public Option[] config() {
        return new Option[] //
                {
                        debug(),
                        karafDistributionConfiguration().frameworkUrl(karafUrl).unpackDirectory(new File("target", "exam"))
                                .useDeployFolder(false),
                        systemTimeout(30000),
                        systemProperty("karaf.log").value("log"), // Workaround for karaf 4.2.7 in exam
                        keepRuntimeFolder(), // Allows to easily introspect after run. Beware: Consumes lot of disk space over time
                        configureConsole().ignoreLocalConsole(),
                        localRepo(),
                        awaitility(),
                        features(osgibpRepo, "osgibp-backend")
                };
    }

    private Option debug() {
        return when(DEBUG).useOptions(debugConfiguration("5005", true));
    }

    private Option localRepo() {
        String localRepo = System.getProperty("maven.repo.local", "");
        return when(localRepo.length() > 0).useOptions(systemProperty("org.ops4j.pax.url.mvn.localRepository").value(localRepo));
    }

    private Option awaitility() {
        return CoreOptions.composite( //
                mvn("org.awaitility", "awaitility"),
                mvn("org.apache.servicemix.bundles", "org.apache.servicemix.bundles.hamcrest"));
    }

    private MavenArtifactProvisionOption mvn(String groupId, String artifactId) {
        return mavenBundle(groupId, artifactId).versionAsInProject();
    }

}
