## Docker container registry for oracle
https://ap-mumbai-1.ocir.io
Username = tenancy_namespace/yourmailid => tenancy_object_namespace/something@gmail.com
password = auth_token_created

#### Login 
docker login ap-mumbai-1.ocir.io

## Pushing an image to oracle container registry
First tag the image you want to push
```
docker tag <image-identifier> <target-tag>
```
The tag should be of format
```
<registry-domain>/<tenancy-namespace>/<repo-name>:<version>
```
registry-domain = ap-mumbai-1.ocir.io
Example target tag ap-mumbai-1.ocir.io/bmafawqhp6dm/solidtext/solidtext2:v1
#### Push using  
   ```
   docker push ap-mumbai-1.ocir.io/bmafawqhp6dm/solidtext/solidtext2:v1
   ```