package com.gezierri.app_lojavirtual.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Dados(

        val id: String = "",
        val nome: String = "",
        val preco: String = "",
        val url: String = "",

):Parcelable