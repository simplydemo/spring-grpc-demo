package demo.example.grpc.api;

import demo.example.grpc.client.HelloGrpcAsyncClient;
import demo.example.grpc.client.HelloGrpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

    private final HelloGrpcClient helloGrpcClient;
    private final HelloGrpcAsyncClient helloGrpcAsyncClient;

    public HelloController(HelloGrpcClient helloGrpcClient, HelloGrpcAsyncClient helloGrpcAsyncClient) {
        this.helloGrpcClient = helloGrpcClient;
        this.helloGrpcAsyncClient = helloGrpcAsyncClient;
    }

    @GetMapping("/hello")
    public String hello() {
        final String result = helloGrpcClient.sendMessage("hello, world");
        logger.info("result: {}", result);
        return result;
    }

    @GetMapping("/hello-async")
    public String helloAsync(@RequestParam(name = "size", defaultValue = "1") Integer size) {
        helloGrpcAsyncClient.sendMessage(size);
        return null;
    }
}
