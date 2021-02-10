package com.gezierri.app_lojavirtual.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import com.google.android.material.navigation.NavigationView
import androidx.navigation.ui.AppBarConfiguration
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import com.gezierri.app_lojavirtual.R
import com.gezierri.app_lojavirtual.fragments.ProdutoCadastroActivity
import com.google.firebase.auth.FirebaseAuth
import com.gezierri.app_lojavirtual.fragments.Produtos

class TelaPrincipalActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_principal)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val produtosFragment = Produtos()
        val fragment = supportFragmentManager.beginTransaction()
        fragment.replace(R.id.frameContainer,produtosFragment)
        fragment.commit()

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)

        val toggle = ActionBarDrawerToggle(
            this,  drawerLayout,  toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.tela_principal, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.menu_sair ->{
                FirebaseAuth.getInstance().signOut()
                R.id.tela_login
                finish()
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.nav_home -> {

                val produtosFragment = Produtos()
                val fragment = supportFragmentManager.beginTransaction()
                fragment.replace(R.id.frameContainer, produtosFragment)
                fragment.commit()

            }

            R.id.nav_produtos -> {

                val intent = Intent(this, ProdutoCadastroActivity::class.java)
                startActivity(intent)
            }

            R.id.nav_contato -> {
                enviarEmail()
            }
        }
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    private fun enviarEmail(){

        val PACKGEM_GOOGLEEMAIL = "com.google.android.gm"
        val email = Intent(Intent.ACTION_SEND)
        email.putExtra(Intent.EXTRA_EMAIL, arrayOf(""))
        email.putExtra(Intent.EXTRA_SUBJECT, arrayOf(""))
        email.putExtra(Intent.EXTRA_TEXT , arrayOf(""))

        email.type = "message/rec822"
        email.setPackage(PACKGEM_GOOGLEEMAIL)
        startActivity(Intent.createChooser(email, "Escolha o app de e-mail"))
    }

}