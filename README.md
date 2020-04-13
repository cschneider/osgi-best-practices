# osgi-best-practices

![Java CI](https://github.com/cschneider/osgi-best-practices/workflows/Java%20CI/badge.svg)

OSGi application with an angular UI, REST Service and OSGi service.
The design follows current best practices for OSGi R7 applications. 

[Slides for eclipsecon 2109 talk "Testing OSGi applications"](https://de.slideshare.net/ChristianSchneider3/eclipsecon-2019-testing-osgi-applications)

# Modules

* parent - Defines common dependencies and the build of OSGi bundles
* backend - Tasklist api, OSGi service impl and REST service. In a full blown application you might want several bundles here
* halbrowser - Repackaged halbrowser for OSGi
* ui - Static Angular UI that uses the REST backend
* app - Defines the index and application assembly using bndrun

# Build

    mvn clean install
    
# Run

    cd app; java -jar target/app.jar
    
# Alternatively run in Apache Karaf

Download and run Apache Karaf 4.2.6

    feature:repo-add mvn:org.apache.aries.jax.rs/org.apache.aries.jax.rs.features/1.0.5/xml
    feature:install aries-jax-rs-whiteboard aries-jax-rs-whiteboard-jackson war
    install -s mvn:net.lr.osgibp/net.lr.osgibp.backend
    install -s mvn:net.lr.osgibp/net.lr.osgibp.ui/1.0.0-SNAPSHOT
    
# Test

* [Entry page with links to all services](http://localhost:8080/ui/index.html) 
* [Tasklist UI](http://localhost:8080/tasklist/index.html)
* [Raw REST endpoint](http://localhost:8080/tasks)

# Resources

* [OSGi enroute microservice example](https://github.com/osgi/osgi.enroute/tree/master/examples/microservice) - Great example for a rest service. It also shows how to do JPA in OSGi.
* [Sling bundle parent](https://github.com/apache/sling-parent/tree/master/sling-bundle-parent) - Contains many of the best practices
* [Some hints to boost your productivity with declarative services](https://liquid-reality.de/2016/09/26/hints-ds.html)
* [Bnd maven plugins documentation](https://github.com/bndtools/bnd/tree/master/maven)
* [OSGi R7 Highlights JAX-RS Whiteboard](https://blog.osgi.org/2018/03/osgi-r7-highlights-jax-rs-whiteboard.html)
* [Best practices for Restul apis](https://blog.mwaysolutions.com/2014/06/05/10-best-practices-for-better-restful-api/)
* [Pure Annotation-Driven Bundle Development](https://virtual.osgiusers.org/2018/10/pure-annotation-driven-dev)

## Testing

* [Kent Beck - Properties of valuable tests](https://medium.com/@kentbeck_7670/test-desiderata-94150638a4b3)
* [Pax exam](https://ops4j1.jira.com/wiki/spaces/PAXEXAM4/overview)
* [Awaitility](https://github.com/awaitility/awaitility/wiki)

* [Testcontainers](https://www.testcontainers.org/)
