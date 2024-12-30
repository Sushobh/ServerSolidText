#### logging into instance

ssh -i ssh-key-2024-10-21.key ubuntu@141.148.197.19


#### Docker pull from registry
sudo docker pull bom.ocir.io/bmafawqhp6dm/solidtext/solidtext:v6

## Docker run 
#### To view the logs omit -d
sudo docker run  -p 8080:8080 e42535b1a520 
#### To run in background use -d, -t is supposed to keep it running
sudo docker run -t -d -p 8080:8080 e42535b1a520 