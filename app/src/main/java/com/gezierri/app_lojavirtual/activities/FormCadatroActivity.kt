package com.gezierri.app_lojavirtual.activities

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.gezierri.app_lojavirtual.R
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import kotlinx.android.synthetic.main.activity_form_cadastro.*

class FormCadatroActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_cadastro)
        supportActionBar!!.hide()
        btnCadastro.setOnClickListener {
            cadastrar()
        }
    }

    private fun cadastrar() {
        var email = editEmail.text.toString()
        var senha = editSenha.text.toString()
        if (validadorDeCampos()) {
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        var snackbar = Snackbar.make(form_cadastro, "Cadastro realizado com sucesso.", Snackbar.LENGTH_INDEFINITE)
                            .setBackgroundTint(Color.WHITE).setTextColor(Color.BLACK).setAction("OK", View.OnClickListener {
                                finish()
                            })
                        snackbar.show()
                    }
                }.addOnFailureListener {

                    var erro = it
                    when(erro){
                        is FirebaseAuthWeakPasswordException -> snackbar("Digite uma senha mais forte")
                        is FirebaseAuthUserCollisionException -> snackbar("Usuário já cadastrado")
                        is FirebaseAuthInvalidCredentialsException -> snackbar("Email ou senha incorretos")
                        is FirebaseNetworkException -> snackbar("Sem conexão com internet")
                        else -> snackbar("Erro ao cadastrar usuário")
                    }
                    snackbar("Erro ao cadastrar")
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
        var snackbar = Snackbar.make(form_cadastro, msg, Snackbar.LENGTH_INDEFINITE)
            .setBackgroundTint(Color.WHITE).setTextColor(Color.BLACK).setAction("OK", View.OnClickListener {

            })
        snackbar.show()
    }
}