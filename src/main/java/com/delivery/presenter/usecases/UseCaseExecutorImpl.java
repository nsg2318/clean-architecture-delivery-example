package com.delivery.presenter.usecases;

import com.delivery.core.usecases.UseCase;
import com.delivery.core.usecases.UseCaseExecutor;
import com.delivery.core.usecases.customer.CreateCustomerUseCase;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

@Service
public class UseCaseExecutorImpl implements UseCaseExecutor {
    @Override
    public <RX, I extends UseCase.InputValues, O extends UseCase.OutputValues> CompletableFuture<RX> execute(
        UseCase<I, O> useCase, I input, Function<O, RX> outputMapper) {
        int a = 1;


        if (input instanceof CreateCustomerUseCase.InputValues) {
            System.out.println("yes");
        }
        CompletableFuture<I> iCompletableFuture = CompletableFuture.supplyAsync(() -> input);
        CompletableFuture<O> oCompletableFuture = iCompletableFuture.thenApplyAsync((apply) -> useCase.execute(apply));
        CompletableFuture<RX> rxCompletableFuture = oCompletableFuture.thenApplyAsync(outputMapper);
        return rxCompletableFuture;
    }
}
