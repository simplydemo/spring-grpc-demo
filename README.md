# spring-grpc-demo
spring-boot 기반 gRPC 샘플 프로젝트를 구성 합니다.

## Git
```
git clone https://github.com/chiwoo-samples/spring-grpc-demo.git

git config --local user.name <your_name>
git config --local user.email <your_email>
```

## Install gRPC Interface Module
gRPC 메시지 및 서비스 객체는 client + server 가 서로 공유하는 객체이므로 grpc-demo-interface 모듈을 local maven 저장소에 install 합니다.

```
./gradlew clean build publishToMavenLocal --exclude-task test -p grpc-demo-interface
```

## Build Client Module
gRPC 클라이언트 모듈을 빌드 합니다.  
```
./gradlew clean build --exclude-task test -p grpc-demo-client
```


## Build Server Module
gRPC 서버 모듈을 빌드 합니다.
```
./gradlew clean build --exclude-task test -p grpc-demo-server
```

## Run Server Module

```
./gradlew build bootRun --exclude-task test -p grpc-demo-server
```


## Run Client Module

```
./gradlew build bootRun --exclude-task test -p grpc-demo-client
```

## cURL

- Sync call 
```
curl -v -L -X GET 'http://localhost:8081/hello' --header 'Content-Type: application/json' 
```

- Async call
```
curl -v -L -X GET 'http://localhost:8081/hello-async?size=1000' --header 'Content-Type: application/json' 
```


## Appendix
[Protocol Buffers](https://developers.google.com/protocol-buffers/docs/reference/overview)
[Mr-luo-hm/again-boot](https://github.com/Mr-luo-hm/again-boot)