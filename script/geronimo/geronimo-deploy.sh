
pushd p6spy-geronimo-h2-wrapper && \
mvn clean package && \
popd && \
pushd target/geronimo-*/bin && \
./deploy --user system --password manager install-library --groupId org.codehaus.cargo.classpath ~/.m2/repository/org/ow2/spec/osgi/ow2-jdbc-service-1.0-spec/1.0.13/ow2-jdbc-service-1.0-spec-1.0.13.jar && \
./deploy --user system --password manager install-library --groupId org.codehaus.cargo.classpath ~/.m2/repository/com/h2database/h2/1.3.173/h2-1.3.173.jar && \
./deploy --user system --password manager install-library --groupId org.codehaus.cargo.classpath ~/.m2/repository/p6spy/p6spy/2.0.0-SNAPSHOT/p6spy-2.0.0-SNAPSHOT.jar && \
./deploy --user system --password manager install-plugin $PWD/../../../p6spy-geronimo-h2-wrapper/target/p6spy-1.0.car && \
./deploy --user system --password manager deploy $PWD/../../../target/p6spy-it.war && \
popd
