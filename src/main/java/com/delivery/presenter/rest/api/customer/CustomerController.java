package com.delivery.presenter.rest.api.customer;

import com.delivery.core.usecases.UseCaseExecutor;
import com.delivery.core.usecases.customer.CreateCustomerUseCase;
import com.delivery.presenter.rest.api.entities.ApiResponse;
import com.delivery.presenter.rest.api.entities.AuthenticationResponse;
import com.delivery.presenter.rest.api.entities.SignInRequest;
import com.delivery.presenter.rest.api.entities.SignUpRequest;
import com.delivery.presenter.usecases.UseCaseExecutorImpl;
import com.delivery.presenter.usecases.security.AuthenticateCustomerUseCase;
import com.delivery.presenter.usecases.security.AuthenticateCustomerUseCaseInputMapper;
import com.delivery.presenter.usecases.security.AuthenticateCustomerUseCaseOutputMapper;
import com.delivery.presenter.usecases.security.CreateCustomerInputMapper;
import com.delivery.presenter.usecases.security.CreateCustomerUseCaseOutputMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
public class CustomerController implements CustomerResource {
    private final UseCaseExecutor useCaseExecutor;
    private final CreateCustomerUseCase createCustomerUseCase;
    private final CreateCustomerInputMapper createCustomerUseCaseInputMapper;
    private final AuthenticateCustomerUseCase authenticateCustomerUseCase;
    private final UseCaseExecutorImpl useCaseExecutorImpl;

    @Override
    public CompletableFuture<ResponseEntity<ApiResponse>> signUp(@Valid @RequestBody SignUpRequest signUpRequest,
                                                                 HttpServletRequest httpServletRequest) {
        int a =1;
        return useCaseExecutorImpl.execute(
                createCustomerUseCase,
                createCustomerUseCaseInputMapper.map(signUpRequest),
                (outputValues) -> CreateCustomerUseCaseOutputMapper.map(outputValues.getCustomer(), httpServletRequest));
    }

    @Override
    public CompletableFuture<ResponseEntity<AuthenticationResponse>> signIn(@Valid @RequestBody SignInRequest signInRequest) {
        return useCaseExecutor.execute(
                authenticateCustomerUseCase,
                AuthenticateCustomerUseCaseInputMapper.map(signInRequest),
                AuthenticateCustomerUseCaseOutputMapper::map);
    }
}
