package demo.example.grpc;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CollectionShuffle {

    private final Logger log = LoggerFactory.getLogger(CollectionShuffle.class);

    private final List numbers = Arrays.asList(IntStream.rangeClosed(1, 45).toArray());

    @Test
    void testCollectionShuffle() {
        log.info("testCollectionShuffle");
        Collections.shuffle(numbers);
        List nums = (List) numbers.stream().limit(6).sorted().collect(Collectors.toList());
        log.info("result: {}", nums);
    }
}
