- local: `rsync -vr src pom.xml ubuntu@44.193.91.180:`
- remote: `mvn package && java -jar target/quarkus-app/quarkus-run.jar`
- local: `curl -v -X POST -H "Content-Type: application/json" --data-binary @test.json http://44.198.154.21:8080/hello`

