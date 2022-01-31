### Build docker images and push

`./docker-push.sh`

### Run tests locally

You need docker and terraform cli installed on your machine. 

To provision the infrastructure

`terraform apply`

Then run

`./gradlew clean build`
