# Readme 

#### Running through command line
`./gradlew bootRun` Before this make sure that the java version on the command line
is pointing to the right java version. 

`./gradlew bootJar` Builds an executable JAR. Avoid No Main manifest error


#### Connect using command line
./psql -d "solidtext" Where the "solidtext" is the database name
then in the console type \c solidtext again to connect.
Run this "SELECT * FROM st_user;"

#### Exporting sql to remote
Dump sql first using datagrip.
Then run sql script using datagrip