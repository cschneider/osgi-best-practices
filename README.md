# osgi-best-practices

OSGi application with an angular UI, REST Service and OSGi service.
The design follows current best practices for OSGi R7 applications. 

# Modules

* parent - Defines common dependencies and the build of OSGi bundles
* backend - Tasklist api, OSGi service impl and REST service. In a full blown application you might want several bundles here
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

   http://localhost:8080/tasklist/index.html
   
   http://localhost:8080/tasks

# Resources

* [Sling bundle parent](https://github.com/apache/sling-parent/tree/master/sling-bundle-parent) - Contains many of the best practices
* [Some hints to boost your productivity with declarative services](https://liquid-reality.de/2016/09/26/hints-ds.html)
* [Bnd maven plugins documentation](https://github.com/bndtools/bnd/tree/master/maven)
* [OSGi R7 Highlights JAX-RS Whiteboard](https://blog.osgi.org/2018/03/osgi-r7-highlights-jax-rs-whiteboard.html)
* [Best practices for Restul apis](https://blog.mwaysolutions.com/2014/06/05/10-best-practices-for-better-restful-api/)