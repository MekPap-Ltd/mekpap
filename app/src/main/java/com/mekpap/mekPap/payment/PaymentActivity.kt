package com.mekpap.mekPap.payment

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.mekpap.mekPap.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_payment.*

class PaymentActivity : AppCompatActivity() {

    val Serve by lazy {
        PaymentInterface.create()
    }

    var disposable:Disposable? = null
    private var TAG = "Payment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        paymentButton.setOnClickListener {
            val amount = paymentAmount.text.toString()
            val phone = paymentPhone.text.toString()
            val customerId = FirebaseAuth.getInstance().currentUser?.uid
            val customerName = ""
            if(customerId != null){
                val payment = Payment(amount,phone, customerName, customerId)
                if(amount.isNotBlank() && phone.isNotBlank()){
                    requestPayment(payment)
                }else{
                    Toast.makeText(this,"Fill in all the fields",Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this,"Not Authenticated",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun requestPayment(payment: Payment){
        disposable = Serve.payment(payment)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {result -> handleResponse(result)},
                        {error -> handleError(error.message)}
                )
    }

    @SuppressLint("LogNotTimber")
    private fun handleError(error:String?){
        Toast.makeText(this,"$error",Toast.LENGTH_SHORT).show()
        Log.d(TAG,"$error")
    }

    private fun handleResponse(response: Any){
        Log.d(TAG,"$response")
    }
}
