#### logging into instance

ssh -i ssh-key-2024-10-21.key opc@140.238.251.35


#### Docker pull from registry
sudo docker pull bom.ocir.io/bmafawqhp6dm/solidtext/solidtext:v6

## Docker run 
#### Make sure that you dont pass -d. So that its not detached and you can see logs.
sudo docker run  -p 8080:8080 e42535b1a520 