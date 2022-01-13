package com.example.myhomework2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var emailText: EditText
    private lateinit var password: EditText
    private lateinit var passwordRepeat: EditText
    private lateinit var signupButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()

        //BEWARE! : BLOATED LISTENER AHEAD

        signupButton.setOnClickListener{

            val mail = emailText.text.toString()
            val pass = password.text.toString()
            val passR = passwordRepeat.text.toString()
            mailCheck(mail)



            when {
                mail.isEmpty() || pass.isEmpty() || passR.isEmpty() -> {
                    Toast.makeText(this,"Please fill all values", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                pass != passR -> {
                    Toast.makeText(this,"Passwords Do not match !",Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                pass.length < 8 ->{
                    Toast.makeText(this,"Password Should Be At Least 8 Symbols",Toast.LENGTH_LONG).show()
                }

                /* new to kotlin, idk how to simplify the boolean expression
                maybe if i just leave mailCheck(mail) code will run only if its true. xD idk i will try later.
                 */

                pass.length >= 8 && pass==passR && mailCheck(mail) == true -> {

                    FirebaseAuth.getInstance()
                        .createUserWithEmailAndPassword(mail,pass)
                        .addOnCompleteListener{ task ->
                            if(task.isSuccessful){
                                Toast.makeText(this,"Registration Done !",Toast.LENGTH_LONG).show()
                            }else{
                                Toast.makeText(this,"Problem Registering User",Toast.LENGTH_LONG).show()
                            }

                        }
                }

            }


        }
    }

    private fun init(){
        emailText = findViewById(R.id.emailText)
        password = findViewById(R.id.password)
        passwordRepeat = findViewById(R.id.passwordRepeat)
        signupButton = findViewById(R.id.signupButton)

    }
    private fun mailCheck(mail: String): Boolean{
        //it says return should be lifted out of if. but why ?... i need it xD
        if(mail.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
            return true
        }else{
            Toast.makeText(this,"E-mail Invalid !", Toast.LENGTH_LONG).show()
            return false
        }
    }


}