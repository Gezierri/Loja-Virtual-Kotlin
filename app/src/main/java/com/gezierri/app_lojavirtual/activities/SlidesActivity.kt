 package com.gezierri.app_lojavirtual.activities

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import com.gezierri.app_lojavirtual.R
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.heinrichreimersoftware.materialintro.app.IntroActivity
import com.heinrichreimersoftware.materialintro.slide.FragmentSlide
import kotlinx.android.synthetic.main.tela_login.*
import kotlinx.android.synthetic.main.tela_login.editEmail
import kotlinx.android.synthetic.main.tela_login.editSenha

 class SlidesActivity : IntroActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        verificarUsuario()

        isButtonBackVisible = false
        isButtonNextVisible = false

        /* addSlide(
             FragmentSlide.Builder()
                 .background(android.R.color.transparent)
                 .backgroundDark(android.R.color.white)
                 .canGoBackward(false)
                 .fragment(R.layout.tela_1)
                 .build()
         )

         addSlide(
             FragmentSlide.Builder()
                 .background(android.R.color.transparent)
                 .backgroundDark(android.R.color.white)
                 .fragment(R.layout.tela_2)
                 .build()
         )*/

        addSlide(
            FragmentSlide.Builder()
                .background(android.R.color.transparent)
                .backgroundDark(android.R.color.white)
                .fragment(R.layout.tela_login)
                .canGoForward(false)
                .build()
        )

    }

    fun telaCadastro(view: View) {
        var intent = Intent(this, FormCadatroActivity::class.java)
        startActivity(intent)
    }

     private fun telaPrincipal(){
         var intent = Intent(this, TelaPrincipalActivity::class.java)
         startActivity(intent)
     }

    fun btnEntrar(view: View) {
        var email = editEmail.text.toString()
        var senha = editSenha.text.toString()
        if (validadorDeCampos()){
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, senha).addOnCompleteListener {
                if (it.isSuccessful){
                    telaPrincipal()
                }
            }.addOnFailureListener {
                when(it){
                    is FirebaseAuthInvalidCredentialsException -> snackbar("Email ou senha incorretos")
                    is FirebaseNetworkException -> snackbar("Sem conexão com internet")
                    else -> snackbar("Erro ao logar")
                }
            }
        }
    }

    private fun validadorDeCampos(): Boolean {
        var email = editEmail.text.toString()
        var senha = editSenha.text.toString()

        if (email.isNotEmpty()) {
            if (senha.isNotEmpty()) {
                return true
            } else {
                snackbar("Preencha o campo senha")
            }
        } else {
            snackbar("Preencha o campo email")
        }
        return false
    }

     private fun snackbar(msg: String) {
         var snackbar = Snackbar.make(tela_login, msg, Snackbar.LENGTH_INDEFINITE)
             .setBackgroundTint(Color.WHITE).setTextColor(Color.BLACK).setAction("OK", View.OnClickListener {

             })
         snackbar.show()
     }

     private fun verificarUsuario(){

         val usuarioAtual = FirebaseAuth.getInstance().currentUser

         if (usuarioAtual != null){
             telaPrincipal()
         }
     }
}