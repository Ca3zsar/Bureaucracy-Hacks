package com.example.navbar

interface Comunicator {

    fun passDataCom(editTextInput : String, editTextInput2 : String, editTextInput3 : String)
    fun passDataActe(idInstitutie : Int, arrayActe : ArrayList<String>)
    fun passDataProcess(process : String)

}