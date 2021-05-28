package com.example.navbar.ui

class ComunicatorStaticClass {
    companion object {
        lateinit var idInstitutie : String
        lateinit var acteNecesare : ArrayList<String>
        lateinit var nume : String
        lateinit var prenume : String
        lateinit var email : String
        lateinit var proces : String
    }
    init {
        idInstitutie = this.toString()
        nume = this.toString()
        prenume = this.toString()
        email = this.toString()
        proces = this.toString()
    }
}