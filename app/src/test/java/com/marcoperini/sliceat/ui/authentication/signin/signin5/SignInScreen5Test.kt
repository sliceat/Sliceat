package com.marcoperini.sliceat.ui.authentication.signin.signin5

import assertk.assertThat
import assertk.assertions.isNotEqualTo
import com.marcoperini.sliceat.utils.HashClass
import org.junit.Test
import org.koin.test.KoinTest

class SignInScreen5Test: KoinTest {

    @Test
    fun `test hasString`() {
        val password = "prova"
        val hashPassword = HashClass.transformStringToHash(password)
        assertThat(password).isNotEqualTo(hashPassword)
    }
}
