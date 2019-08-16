package com.sungkunn.inam.example

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.sungkunn.inam.R


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class LoginDialogFragment : DialogFragment(), Toolbar.OnMenuItemClickListener, View.OnClickListener {

    var appBar: AppBarLayout? = null
    var toolbar: Toolbar? = null
    var etUsername: TextInputEditText? = null
    var etPassword: TextInputEditText? = null
    var btnLogin: MaterialButton? = null

    var inflater: LayoutInflater? = null

    private lateinit var auth: FirebaseAuth
    private var RC_SIGN_IN = 101


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppTheme_FullScreenDialog)
        auth = FirebaseAuth.getInstance()
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog.window.setLayout(width, height)
            dialog.window.setWindowAnimations(R.style.AppTheme_Slide)
        }
        val currentUser = auth.currentUser
//        updateUI(currentUser)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var rootView = inflater.inflate(R.layout.fragment_login_dialog, container, false)
        toolbar = rootView.findViewById(R.id.toolbar_dialog)
        etUsername = rootView.findViewById(R.id.et_username)
        etPassword = rootView.findViewById(R.id.et_password)
        btnLogin = rootView.findViewById(R.id.btn_login)
        this.inflater = inflater
        toolbar!!.setNavigationOnClickListener { v -> dismiss() }
//        toolbar!!.setTitle("Some Title")
//        toolbar!!.inflateMenu(R.menu.menu_dialog)
        toolbar!!.setOnMenuItemClickListener(this)

        btnLogin!!.setOnClickListener(this)

        return rootView
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        dismiss()
        return true
    }

    override fun onClick(v: View?) {
//        auth.signOut()
//        var txt = "${etUsername!!.text} - ${etPassword!!.text}"
        var username = etUsername!!.text.toString()
        var password = etPassword!!.text.toString()
//        Toast.makeText(activity, txt, Toast.LENGTH_SHORT).show()
//        auth.signInWithEmailAndPassword(username, password)
//            .addOnCompleteListener(activity!!) { task ->
//                if (task.isSuccessful) {
//                    // Sign in success, update UI with the signed-in user's information
////                    Log.d(TAG, "signInWithEmail:success")
//                    val user = auth.currentUser
////                    updateUI(user)
//                } else {
//                    // If sign in fails, display a message to the user.
////                    Log.w(TAG, "signInWithEmail:failure", task.exception)
//                    Toast.makeText(inflater!!.context, "Authentication failed.",
//                        Toast.LENGTH_SHORT).show()
////                    updateUI(null)
//                }
//
//                // ...
//            }
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build(),
            AuthUI.IdpConfig.FacebookBuilder().build())
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setLogo(R.drawable.inam_logo)
                .build(),
            RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                val user = FirebaseAuth.getInstance().currentUser

                // ...
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
            }
        }
    }


    companion object {
        @JvmStatic
        fun newInstance() =
            LoginDialogFragment().apply {
                arguments = Bundle().apply {
                }
            }

        fun display(fragmentManager: FragmentManager?): LoginDialogFragment {
            val loginDialog = LoginDialogFragment()
            if (fragmentManager != null) {
                loginDialog.show(fragmentManager, "loginDialog")
            }
            return loginDialog
        }

    }
}
