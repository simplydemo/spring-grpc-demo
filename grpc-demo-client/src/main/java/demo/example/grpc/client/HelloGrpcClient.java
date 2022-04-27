package demo.example.grpc.client;

import demo.example.grpc.binary.codec.HelloRequest;
import demo.example.grpc.binary.codec.HelloResponse;
import demo.example.grpc.binary.codec.HelloServiceGrpc;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class HelloGrpcClient extends HelloServiceGrpc.HelloServiceImplBase {

    private final Logger logger = LoggerFactory.getLogger(HelloGrpcClient.class);

    @GrpcClient("hello")
    private HelloServiceGrpc.HelloServiceBlockingStub helloStub;

    public String sendMessage(final String name) {
        try {
            HelloResponse response = this.helloStub.sayHello(HelloRequest.newBuilder().setMessage(name).build());
            return response.getResult();
        } catch (StatusRuntimeException e) {
            var errmsg = "FAILED with " + e.getStatus().getCode().name();
            logger.error("ERR-MSG: {}", errmsg);
            return errmsg;
        }
    }

}
