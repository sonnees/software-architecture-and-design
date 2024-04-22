package com.sonnees.evendriven;

import com.sonnees.OrderRequest;
import com.sonnees.OrderResponse;
import com.sonnees.SendOrderServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@AllArgsConstructor
public class SendOrderGrpcService extends SendOrderServiceGrpc.SendOrderServiceImplBase {
    private SendOrderServices sendOrderServices;
    @Override
    public void sendOrder(OrderRequest request, StreamObserver<OrderResponse> responseObserver) {
        System.out.println("1");
        String status = sendOrderServices.sendOrder(request.toString());
        responseObserver.onNext(

                OrderResponse
                        .newBuilder()
                        .setStatus(status)
                        .build()
        );

        responseObserver.onCompleted();
    }

}
