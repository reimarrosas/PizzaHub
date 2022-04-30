package me.reimarrosas.pizzahub.fragments.base;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.stripe.android.PaymentConfiguration;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResult;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import me.reimarrosas.pizzahub.R;
import me.reimarrosas.pizzahub.contracts.Notifiable;
import me.reimarrosas.pizzahub.databinding.FragmentOrderSummaryBinding;
import me.reimarrosas.pizzahub.helper.PaymentService;
import me.reimarrosas.pizzahub.models.MenuItem;
import me.reimarrosas.pizzahub.models.Order;
import me.reimarrosas.pizzahub.models.PaymentIntention;
import me.reimarrosas.pizzahub.models.Side;
import me.reimarrosas.pizzahub.recycleradapters.adapterdata.OrderSummaryData;
import me.reimarrosas.pizzahub.recycleradapters.adapterdata.OrderSummaryData.DataType;
import me.reimarrosas.pizzahub.recycleradapters.summaryadapters.SummaryAdapter;
import me.reimarrosas.pizzahub.services.OrderService;
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
public class OrderSummaryFragment extends Fragment implements Notifiable {

    private static final String TAG = "OrderSummaryFragment";

    private FragmentOrderSummaryBinding binding;

    private SummaryAdapter adapter;

    private PaymentSheet paymentSheet;
    private String paymentIntentClientSecret;
    private PaymentSheet.CustomerConfiguration customerConfig;
    private Retrofit retrofit;

    private Order order;

    private OrderService service;

    private boolean isOkToProceed = false;

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

        service = new OrderService(this);

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
                isOkToProceed = true;
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
        setupPrices();
        binding.buttonOrderSummaryOrder.setOnClickListener(_view -> {
            if (isOkToProceed) {
                PaymentSheet.Configuration configuration =
                        new PaymentSheet.Configuration
                                .Builder("Pizza Hub")
                                .customer(customerConfig)
                                .allowsDelayedPaymentMethods(true)
                                .build();
                paymentSheet.presentWithPaymentIntent(paymentIntentClientSecret, configuration);
            } else {
                Toast.makeText(getContext(), "Please wait...", Toast.LENGTH_SHORT).show();
            }
        });
        binding.buttonOrderSummaryCancel.setOnClickListener(_view -> {
            NavDirections action = OrderSummaryFragmentDirections
                    .actionOrderSummaryFragmentToDeliveryLocationFragment(order);
            Navigation.findNavController(_view).navigate(action);
        });
    }

    public void onPaymentSheetResult(final PaymentSheetResult paymentSheetResult) {
        if (paymentSheetResult instanceof PaymentSheetResult.Canceled) {
            Toast.makeText(getContext(), "Payment Cancelled!", Toast.LENGTH_SHORT).show();
        } else if (paymentSheetResult instanceof PaymentSheetResult.Failed) {
            PaymentSheetResult.Failed paymentFailed = (PaymentSheetResult.Failed) paymentSheetResult;
            Log.e(TAG, "onPaymentSheetResult: ", paymentFailed.getError());
            Toast.makeText(getContext(), "Payment Failed!", Toast.LENGTH_SHORT).show();
        } else if (paymentSheetResult instanceof PaymentSheetResult.Completed) {
            service.insertData(order, "orders");
        }
    }

    private void setupArgs() {
        order = OrderSummaryFragmentArgs.fromBundle(getArguments()).getOrder();
    }

    private void setupRecyclerView() {
        List<OrderSummaryData> dataList = transformOrderToData(order);
        adapter = new SummaryAdapter(getContext(), dataList);
        binding.recyclerViewOrderDetails.setAdapter(adapter);

        int spanCount = 2;

        int onePerRow = spanCount / 1;
        int twoPerRow = spanCount / 2;

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), spanCount);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int type = adapter.getItemViewType(position);
                return type == DataType.SECTION_HEADER.ordinal() ? onePerRow : twoPerRow;
            }
        });
        binding.recyclerViewOrderDetails.setLayoutManager(layoutManager);
    }

    private void setupPrices() {
        binding.textViewPrice.setText(
                binding.textViewPrice.getText().toString() +
                        decimalRounder(order.calculateTaxablePrice(), RoundingMode.HALF_UP));
        binding.textViewGSTPrice.setText(
                binding.textViewGSTPrice.getText().toString() +
                        decimalRounder(order.calculateFederalTax(), RoundingMode.HALF_UP));
        binding.textViewQSTPrice.setText(
                binding.textViewQSTPrice.getText().toString() +
                        decimalRounder(order.calculateQuebecTax(), RoundingMode.HALF_UP));
        binding.textViewTotalPrice.setText(
                binding.textViewTotalPrice.getText().toString() +
                        decimalRounder(order.calculateNetPrice(), RoundingMode.CEILING));
    }

    private void orderOrder() {
        NavDirections action =
                OrderSummaryFragmentDirections.actionOrderSummaryFragmentToOrderSuccessFragment(
                        order.getId(),
                        String.valueOf(order.getPrice()));
        Navigation.findNavController(getView()).navigate(action);
    }

    private double decimalRounder(BigDecimal b, RoundingMode r) {
        BigDecimal oneHundred = BigDecimal.valueOf(100);

        return b.multiply(oneHundred)
                .setScale(0, r)
                .divide(oneHundred)
                .doubleValue();
    }

    private List<OrderSummaryData> transformOrderToData(Order order) {
        List<OrderSummaryData> res = new ArrayList<>();
        res.add(new OrderSummaryData(null, "Size", DataType.SECTION_HEADER));
        res.add(new OrderSummaryData(order.getSize(), null, DataType.MENU_ITEM));
        res.add(new OrderSummaryData(null, "Toppings", DataType.SECTION_HEADER));
        addToSummaryDataList(res, order.getToppings());
        res.add(new OrderSummaryData(null, "Sides", DataType.SECTION_HEADER));
        addToSummaryDataList(res, order.getSides());
        res.add(new OrderSummaryData(null, "Drinks", DataType.SECTION_HEADER));
        addToSummaryDataList(res, order.getDrinks());
        res.add(new OrderSummaryData(null, "Price", DataType.SECTION_HEADER));

        return res;
    }

    private <T extends MenuItem> void addToSummaryDataList(List<OrderSummaryData> dataList, List<T> sourceList) {
        for (MenuItem m : sourceList) {
            dataList.add(new OrderSummaryData(m, null, DataType.MENU_ITEM));
        }
    }

    @Override
    public void notifyOperationSuccess(Throwable t) {
        if (t == null) {
            Log.d(TAG, "notifyOperationSuccess: Success!");
            orderOrder();
        } else {
            Log.e(TAG, "notifyOperationSuccess: ", t);
        }
    }
}