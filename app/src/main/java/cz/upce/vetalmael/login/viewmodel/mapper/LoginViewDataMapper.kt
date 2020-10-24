package cz.upce.vetalmael.login.viewmodel.mapper

import cz.upce.vetalmael.login.viewmodel.data.LoginViewData
import cz.upce.vetalmael.login.viewmodel.data.LoginViewModelData

interface LoginViewDataMapper {

    fun toViewData(data: LoginViewModelData): LoginViewData
}