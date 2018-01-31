echo "STARTING COPYING LOG FILES TO CENTRAL LOG LOCATION  "
IMAGE_NAMES=`docker ps | grep -Po "[/][t][c][p][ ]*[a-z0-9_-]*"| grep -Po "[a-z0-9-_]*" | grep --invert-match 'tcp'  | tr '\n' ','`
IFS=',' read -r -a array <<< "$IMAGE_NAMES"
for i in "${!array[@]}"
do
  docker cp ${array[i]}:/maven/logs /home/cognitaopr/log/acumosLog
  echo "Copied log of image: ${array[i]}"
done
