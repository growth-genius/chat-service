# bin/bash
docker stop chat-service || true
docker rm chat-service || true
docker rmi leesg107/chat-service || true
./gradlew clean bootBuildImage --imageName=leesg107/chat-service
