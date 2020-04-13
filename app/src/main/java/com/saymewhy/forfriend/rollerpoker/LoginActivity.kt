package com.saymewhy.forfriend.rollerpoker

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.login.*


class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private var mAuthListener: FirebaseAuth.AuthStateListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        mAuthListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val user = firebaseAuth.currentUser
            if (user != null) {
                // User is signed in
                loginDone(user)//todo user
            }
        }

        btn_login_ok.setOnClickListener(this)
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        mAuthListener?.let { mAuth.addAuthStateListener(it) }
    }

    private fun loginDone(user: FirebaseUser?) {

        if (user != null) {
            val intent = Intent(this, MainActivity::class.java)
            // start your next activity
            startActivity(intent)
        }

    }

    private fun signIn(email: String, password: String) {

        if (!validateForm()) {
            return
        }

        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                // If sign in fails, display a message to the user. If sign in succeeds
                // the auth state listener will be notified and logic to handle the
                // signed in user can be handled in the listener.
                if (!task.isSuccessful) {
                    Toast.makeText(
                        this, "о новенький! welcome:) ",
                        Toast.LENGTH_SHORT
                    ).show()
                    signUp(email, password)
                } else {
                    Toast.makeText(
                        this, "давно не виделись! welcome:) ",
                        Toast.LENGTH_SHORT
                    ).show()
                    task.result?.user?.let { loginDone(it) }
                }

            }
    }

    private fun validateForm(): Boolean {
        if (et_login.text.toString() == "" || et_pass.text.toString() == "") {
            tv_login_exception.text = "введи что ли чет"
            tv_login_exception.visibility = View.VISIBLE
            return false
        }


        return true
    }

    private fun signUp(email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(
                this
            ) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("loginActivity ---------", "createUserWithEmail:success")
                    loginDone(mAuth.currentUser)

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(
                        "loginActivity ---------",
                        "createUserWithEmail:failure",
                        task.exception

                    )
                    tv_login_exception.text = task.exception?.message.toString()
                    tv_login_exception.visibility = View.VISIBLE
                    Toast.makeText(
                        this, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()

                }

            }
    }


    override fun onClick(v: View?) {
        signIn(et_login.text.toString(), et_pass.text.toString())
    }

}