package com.sentigo.bangkit.sentigoapp.di

import com.sentigo.bangkit.sentigoapp.model.City

object DataKota {
    const val CITY_NAME: String = "city_name"

    fun getCity(): ArrayList<City> {
        val listCity = ArrayList<City>()

        listCity.add(
            City(
                "https://img.okezone.com/content/2022/12/08/337/2722874/ini-5-alasan-kenapa-kota-surabaya-disebut-kota-pahlawan-nWb0plbz3Y.jpg",
                "Surabaya"
            )
        )

        listCity.add(
            City(
                "https://awsimages.detik.net.id/community/media/visual/2022/04/05/balai-kota-malang-yang-menjadi-ikon-kota-malang_169.jpeg?w=650&q=80",
                "Malang"
            )
        )

        listCity.add(
            City(
                "https://www.goodnewsfromindonesia.id/uploads/post/large-goodnewsfromindonesia-gnfi-kantorpemkab-blitar-849549a459ea0e1fe3b1677c1c7c92c4.jpg",
                "Blitar"
            )
        )

        listCity.add(
            City(
                "https://lenteratoday.com/wp-content/uploads/2021/06/IMG-20210601-WA0083.jpg",
                "Sidoarjo"
            )
        )

        return listCity
    }
}