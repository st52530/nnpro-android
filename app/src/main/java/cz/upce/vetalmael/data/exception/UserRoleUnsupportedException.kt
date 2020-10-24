package cz.upce.vetalmael.data.exception

import cz.upce.vetalmael.data.model.UserRole

class UserRoleUnsupportedException(
    role: UserRole
) : Exception("Uživatelská role '$role' není v aplikaci podporovaná.")