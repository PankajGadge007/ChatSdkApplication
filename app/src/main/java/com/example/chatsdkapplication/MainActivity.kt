package com.example.chatsdkapplication

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import co.chatsdk.core.session.ChatSDK
import co.chatsdk.core.types.AccountDetails
import co.chatsdk.core.types.AccountDetails.*
import co.chatsdk.ui.utils.ToastHelper
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.iid.FirebaseInstanceId
import io.reactivex.android.schedulers.AndroidSchedulers


class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    var signInButton: Button? = null
    var txtEmail: EditText? = null
    var txtPassword: EditText? = null

    val RC_SIGN_IN = 900
//    protected var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        InterfaceManager.shared().a.startLoginActivity(this, true)
//        FirebasePushModule.activate()
        signInButton = findViewById(R.id.button)
        txtEmail = findViewById(R.id.edit_email)
        txtPassword = findViewById(R.id.edit_password)

        auth = FirebaseAuth.getInstance()
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
//        updateUI(currentUser)
    }

    //    fun startAuthenticationActivity() {
//
//        val idps = ArrayList<AuthUI.IdpConfig>()
//
//        idps.add(AuthUI.IdpConfig.EmailBuilder().build())
//
//        startActivityForResult(
//            AuthUI.getInstance()
//                .createSignInIntentBuilder()
//                .setAvailableProviders(idps)
//                .build(),
//            RC_SIGN_IN
//        )
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        // RC_SIGN_IN is the request code you passed into  startActivityForResult(...) when starting the sign in flow.
//        if (requestCode == RC_SIGN_IN) {
//            val response = IdpResponse.fromResultIntent(data)
//
//            // Successfully signed in
//            if (resultCode == Activity.RESULT_OK) {
//                // Success
//            } else {
//                // Handle Error
//            }
//        }
//    }
//
//    fun login_click(v: View) {
//        startAuthenticationActivity()
//    }
//
    override fun onResume() {
        super.onResume()
//        ChatSDK.ui().startSplashScreenActivity(this)
//        authenticateWithCachedToken()
        signInButton!!.setOnClickListener {
            val customToken: String? = ""
            customToken?.let {
                auth.signInWithCustomToken(it)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("", "signInWithCustomToken:success")
                            val user = auth.currentUser
//                            updateUI(user)
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("", "signInWithCustomToken:failure", task.exception)
                            Toast.makeText(
                                baseContext, "Authentication failed.",
                                Toast.LENGTH_SHORT
                            ).show()
//                            updateUI(null)
                        }
                    }
            }

            val detailsToken = google()//anonymous()
            authenticateAccount(detailsToken)

            if (txtEmail!!.text.isNotEmpty() || txtPassword!!.text.isNotEmpty()) {
//                val details = username(txtEmail!!.text.toString(), txtPassword!!.text.toString())
//                FirebaseInstanceId.getInstance().instanceId
//                    .addOnCompleteListener(OnCompleteListener { task ->
//                        if (!task.isSuccessful) {
//                            Log.w("", "getInstanceId failed", task.exception)
//                            return@OnCompleteListener
//                        }
//
//                        // Get new Instance ID token
//                        val token = task.result?.token
//                        val detailsToken = token(token)
//                        authenticateAccount(detailsToken)
//                    })
            } else {
//                ToastHelper.show(this, "Please enter valid Email/Password")
            }
        }
    }

    private fun authenticateAccount(detailsToken: AccountDetails?) {
        ChatSDK.auth()
            .authenticate(detailsToken)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                ToastHelper.show(this, "Success")
                //                        ChatSDK.ui().startMainActivity(this@MainActivity)
            }, { throwable ->
                ToastHelper.show(this, "Fail: " + throwable.message)
                // Setup failed
            })
    }

//    protected fun authenticateWithCachedToken() {
//        signInButton!!.setEnabled(false)
//
//        ChatSDK.auth().authenticate()
//            .observeOn(AndroidSchedulers.mainThread())
//            .doFinally { signInButton!!.setEnabled(true) }
//            .subscribe({ ChatSDK.ui().startMainActivity(this@MainActivity) }, { throwable ->
//                // Setup failed
//            })
//    }

}
