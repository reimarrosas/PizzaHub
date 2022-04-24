package me.reimarrosas.pizzahub.helper;

import me.reimarrosas.pizzahub.models.Order;
import me.reimarrosas.pizzahub.models.PaymentIntention;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PaymentService {

    @POST("payment-sheet")
    Call<PaymentIntention> postPayment(@Body Order order);

}
