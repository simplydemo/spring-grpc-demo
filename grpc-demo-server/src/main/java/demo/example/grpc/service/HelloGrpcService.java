package demo.example.grpc.service;

import demo.example.grpc.binary.codec.HelloRequest;
import demo.example.grpc.binary.codec.HelloResponse;
import demo.example.grpc.binary.codec.HelloServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

@GrpcService
public class HelloGrpcService extends HelloServiceGrpc.HelloServiceImplBase {

    private final Logger logger = LoggerFactory.getLogger(HelloGrpcService.class);

    public void sayHello(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {
        var delayTime = System.currentTimeMillis();
        // let abc = "AAA";
        // val apple = "BBB";

        HelloResponse response = HelloResponse.newBuilder()
                .setResult("Hello ==> " + request.getMessage())
                .build();

        logger.info("response: {}", response);
        responseObserver.onNext(response);
        responseObserver.onCompleted();

    }

    public StreamObserver<HelloRequest> batchHello(StreamObserver<HelloResponse> responseObserver) {
        return new StreamObserver<>() {
            int pointCount = 0;

            HelloRequest previous;

            public void onNext(HelloRequest request) {
                pointCount++;
                previous = request;
                logger.info("index: {}, message: {}", pointCount, previous.getMessage());


                // process request message

                if (pointCount % 100 == 0) {
                    // responseObserver.onError(new RuntimeException("overflow data stream..."));
                    // 요청중 애러발생하면 재처리 루틴을 타거나, drop 처리를 하거나, 지연 처리를 한다.
                    try {
                        logger.info("서버 부하가 많이 발생 중이여서 처리를 못 함 --- 3 초간 지연");
                        TimeUnit.SECONDS.sleep(3);

                        // MyStreamObserver.setBufferSize(10)
                    } catch (InterruptedException ie) {
                        // Thread.currentThread().interrupt();
                    }
                }
            }

            public void onError(Throwable throwable) {
                logger.error("Encountered error in recordRoute", throwable);
            }

            public void onCompleted() {
                logger.info("Request onCompleted");

                final HelloResponse response = HelloResponse.newBuilder().setResult(
                        String.format("range: %s, result: %s", pointCount, previous.getMessage())
                ).build();
                responseObserver.onNext(response);
                responseObserver.onCompleted();
            }
        };
    }
}