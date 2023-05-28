# bin/bash
docker run --network tgather-network -m 5g --env JAVA_OPTS='-Dspring.profiles.active=dev -Dfile.encoding=UTF-8 -Xmx2048m -XX:MaxMetaspaceSize=1024m' -d --name chat-service leesg107/chat-service || true