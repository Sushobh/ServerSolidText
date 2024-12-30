./gradlew bootJar
docker build -t "solidtext-v9" .
docker tag  da58cb479013  ap-mumbai-1.ocir.io/bmafawqhp6dm/solidtext2:v9
docker push ap-mumbai-1.ocir.io/bmafawqhp6dm/solidtext2:v9



