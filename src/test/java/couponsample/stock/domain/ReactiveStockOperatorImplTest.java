package couponsample.stock.domain;

import com.google.common.collect.Lists;
import couponsample.counter.domain.Counter;
import couponsample.counter.domain.CounterRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

class ReactiveStockOperatorImplTest {

    private static ReactiveStockOperator operator;
    private static CounterRepository repository;

    @BeforeAll
    static void beforeAll() {
        repository = mock(CounterRepository.class);
        operator = new ReactiveStockOperatorImpl(repository);
    }

    @Test
    void set() {
        doReturn(Mono.just(Boolean.TRUE)).when(repository).save(any(Counter.class));

        Mono<Boolean> set = operator.set("set", 1L);
        StepVerifier.create(set)
                .expectNext(Boolean.TRUE)
                .verifyComplete();
    }

    @Test
    void remove() {
        doReturn(Mono.just(Boolean.TRUE)).when(repository).delete("remove");

        Mono<Boolean> remove = operator.remove("remove");
        StepVerifier.create(remove)
                .expectNext(Boolean.TRUE)
                .verifyComplete();
    }

    @Test
    void increase_key() {
        doReturn(Mono.just(1L)).when(repository).increase("increase", 1L);

        Mono<Void> increase = operator.increase("increase");
        StepVerifier.create(increase)
                .expectSubscription()
                .verifyComplete();
    }

    @Test
    void increase_key_value() {
        doReturn(Mono.just(1L)).when(repository).increase("increase", 1L);

        Mono<Void> increase = operator.increase("increase", 1L);
        StepVerifier.create(increase)
                .expectSubscription()
                .verifyComplete();
    }

    @Test
    void increaseAndGet_key() {
        doReturn(Mono.just(1L)).when(repository).increase("increase", 1L);

        Mono<Long> increase = operator.increaseAndGet("increase");
        StepVerifier.create(increase)
                .expectNext(1L)
                .verifyComplete();
    }

    @Test
    void increaseAndGet_key_value() {
        doReturn(Mono.just(1L)).when(repository).increase("increase", 1L);

        Mono<Long> increase = operator.increaseAndGet("increase", 1L);
        StepVerifier.create(increase)
                .expectNext(1L)
                .verifyComplete();
    }

    @Test
    void decrease_key() {
        doReturn(Mono.just(1L)).when(repository).decrease("decrease", 1L);

        Mono<Void> decrease = operator.decrease("decrease");
        StepVerifier.create(decrease)
                .expectSubscription()
                .verifyComplete();
    }

    @Test
    void decrease_key_value() {
        doReturn(Mono.just(1L)).when(repository).decrease("decrease", 1L);

        Mono<Void> decrease = operator.decrease("decrease", 1L);
        StepVerifier.create(decrease)
                .expectSubscription()
                .verifyComplete();
    }

    @Test
    void decreaseAndGet_key() {
        doReturn(Mono.just(1L)).when(repository).decrease("decrease", 1L);

        Mono<Long> decrease = operator.decreaseAndGet("decrease");
        StepVerifier.create(decrease)
                .expectNext(1L)
                .verifyComplete();
    }

    @Test
    void decreaseAndGet_key_value() {
        doReturn(Mono.just(1L)).when(repository).decrease("decrease", 1L);

        Mono<Long> decrease = operator.decreaseAndGet("decrease", 1L);
        StepVerifier.create(decrease)
                .expectNext(1L)
                .verifyComplete();
    }

    @Test
    void get_key() {
        Counter counter = Counter.of("get", 1L);
        doReturn(Mono.just(counter)).when(repository).findById("get");

        Mono<Counter> get = operator.get("get");
        StepVerifier.create(get)
                .expectNext(counter)
                .verifyComplete();
    }

    @Test
    void get_keys() {
        Counter counter1 = Counter.of("get1", 1L);
        Counter counter2 = Counter.of("get2", 2L);
        Counter counter3 = Counter.of("get3", 3L);
        Flux<Counter> counterFlux = Flux.just(counter1, counter2, counter3);

        doReturn(counterFlux).when(repository).findAllById(Lists.newArrayList("get1", "get2", "get3"));

        Flux<Counter> get = operator.get(Lists.newArrayList("get1", "get2", "get3"));
        StepVerifier.create(get)
                .expectNext(counter1)
                .expectNext(counter2)
                .expectNext(counter3)
                .verifyComplete();
    }
}