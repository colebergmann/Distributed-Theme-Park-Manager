cd core
mvn compile install
cd ../rollercoaster
mvn compile install
cd ../parkmanager
mvn compile install
docker-compose build
docker-compose up --remove-orphans