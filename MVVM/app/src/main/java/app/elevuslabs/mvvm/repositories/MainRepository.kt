package app.elevuslabs.mvvm.repositories

import app.elevuslabs.mvvm.rest.RetrofitService

class MainRepository constructor(private val retrofitService: RetrofitService) {

    fun getAllLives() = retrofitService.getAllLives()

}