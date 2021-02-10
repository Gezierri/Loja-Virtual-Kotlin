package com.gezierri.app_lojavirtual.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.gezierri.app_lojavirtual.R
import com.gezierri.app_lojavirtual.model.Dados
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_produto_cadastro.*
import java.util.*

class ProdutoCadastroActivity : AppCompatActivity() {

    private var selecionarUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_produto_cadastro)

        bt_selecionar_foto.setOnClickListener {
            selecionarFotoGaleria()
        }

        bt_cadastrar_produto.setOnClickListener {
            salvarDadosFirebase()
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0) {
            selecionarUri = data?.data

            try {

                foto_produto.setImageURI(selecionarUri)
                bt_selecionar_foto.alpha = 0f

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun selecionarFotoGaleria() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.type = "image/*"
        startActivityForResult(intent, 0)
    }

    private fun salvarDadosFirebase() {

        val nomeProduto = UUID.randomUUID().toString()
        val referencia = FirebaseStorage.getInstance().getReference(
            "/imagens/${nomeProduto}"
        )

        selecionarUri?.let {
            referencia.putFile(it)
                .addOnSuccessListener {
                    referencia.downloadUrl.addOnSuccessListener {

                        val url = it.toString()
                        val nome = edit_nome.text.toString()
                        val preco = edit_preco.text.toString()
                        val id = FirebaseAuth.getInstance().uid

                        val produtos = Dados(url, nome, preco)
                        FirebaseFirestore.getInstance().collection("Produtos")
                            .add(produtos)
                            .addOnSuccessListener {
                                Toast.makeText(
                                    this,
                                    "Produto cadastrado com sucesso",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }.addOnFailureListener {

                            }
                    }
                }
        }
    }
}
