package me.reimarrosas.pizzahub.fragments.base;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.stripe.android.PaymentConfiguration;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResult;

import me.reimarrosas.pizzahub.R;
import me.reimarrosas.pizzahub.databinding.FragmentOrderSummaryBinding;
import me.reimarrosas.pizzahub.helper.PaymentService;
import me.reimarrosas.pizzahub.models.Order;
import me.reimarrosas.pizzahub.models.PaymentIntention;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrderSummaryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderSummaryFragment extends Fragment {

    private static final String TAG = "OrderSummaryFragment";

    private FragmentOrderSummaryBinding binding;

    private PaymentSheet paymentSheet;
    private String paymentIntentClientSecret;
    private PaymentSheet.CustomerConfiguration customerConfig;
    private Retrofit retrofit;

    private Order order;

    public OrderSummaryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment OrderSummaryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OrderSummaryFragment newInstance() {
        OrderSummaryFragment fragment = new OrderSummaryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupArgs();

        retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.payment_api_string))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PaymentService service = retrofit.create(PaymentService.class);

        paymentSheet = new PaymentSheet(this, this::onPaymentSheetResult);
        Call<PaymentIntention> intentionCall = service.postPayment(order);
        intentionCall.enqueue(new Callback<PaymentIntention>() {
            @Override
            public void onResponse(Call<PaymentIntention> call, Response<PaymentIntention> response) {
                Log.d(TAG, "onResponse: Responded");
                PaymentIntention intention = response.body();
                customerConfig = new PaymentSheet.CustomerConfiguration(
                        intention.getCustomer(),
                        intention.getEphemeralKey());
                paymentIntentClientSecret = intention.getPaymentIntent();
                PaymentConfiguration.init(getContext(), intention.getPublishableKey());
            }

            @Override
            public void onFailure(Call<PaymentIntention> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOrderSummaryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupRecyclerView();
        binding.buttonOrderSummaryOrder.setOnClickListener(_view -> {
            PaymentSheet.Configuration configuration =
                    new PaymentSheet.Configuration
                            .Builder("Pizza Hub")
                            .customer(customerConfig)
                            .allowsDelayedPaymentMethods(true)
                            .build();
            paymentSheet.presentWithPaymentIntent(paymentIntentClientSecret, configuration);
        });
    }

    public void onPaymentSheetResult(final PaymentSheetResult paymentSheetResult) {
        if (paymentSheetResult instanceof PaymentSheetResult.Canceled) {
            Toast.makeText(getContext(), "Payment Cancelled!", Toast.LENGTH_SHORT).show();
        } else if (paymentSheetResult instanceof PaymentSheetResult.Failed) {
            PaymentSheetResult.Failed paymentFailed = (PaymentSheetResult.Failed) paymentSheetResult;
            Log.e(TAG, "onPaymentSheetResult: ", paymentFailed.getError());
            Toast.makeText(getContext(),
                    "Payment Failed!: " + paymentFailed.getError().getMessage(),
                    Toast.LENGTH_SHORT).show();
        } else if (paymentSheetResult instanceof PaymentSheetResult.Completed) {
            orderOrder();
        }
    }

    private void setupArgs() {
        order = OrderSummaryFragmentArgs.fromBundle(getArguments()).getOrder();
    }

    private void setupRecyclerView() {
    }

    private void orderOrder() {
        NavDirections action =
                OrderSummaryFragmentDirections.actionOrderSummaryFragmentToOrderSuccessFragment(
                        "123456",
                        String.valueOf(order.getPrice()));
        Navigation.findNavController(getView()).navigate(action);
    }

}