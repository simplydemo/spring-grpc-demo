package demo.example.grpc.client;

import demo.example.grpc.binary.codec.HelloRequest;
import demo.example.grpc.binary.codec.HelloResponse;
import demo.example.grpc.binary.codec.HelloServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class HelloGrpcAsyncClient extends HelloServiceGrpc.HelloServiceImplBase {

    private final Logger logger = LoggerFactory.getLogger(HelloGrpcAsyncClient.class);

    @GrpcClient("helloBatch")
    private HelloServiceGrpc.HelloServiceStub helloBatchStub;

    public void sendMessage(final Integer size) {

        final StreamObserver responseStreamObserver = new StreamObserver<HelloResponse>() {

            public void onNext(HelloResponse value) {
                logger.info("HelloResponse: {}", value);
            }

            public void onError(Throwable e) {
                logger.error("ERR-MSG-ASYNC: {}", "FAILED with " + e.getMessage(), e);
            }

            public void onCompleted() {
                logger.info("Completed tasks");
            }
        };

        final StreamObserver<HelloRequest> requestObserver = helloBatchStub.batchHello(responseStreamObserver);
        logger.info("init requestObserver");
        try {
            // size === Queue.SIZE
            // i (index) retrieve message from Queue
            for (int i = 0; i < size; i++) {
                HelloRequest request = HelloRequest.newBuilder().setMessage("Hello idx: " + (i + 1)).build();
                requestObserver.onNext(request);
            }
            logger.info("sending completed requestObserver");
        } catch (RuntimeException e) {
            requestObserver.onError(e);
            throw e;
        }

        requestObserver.onCompleted();
        logger.info("send onCompleted requestObserver");
    }
}
