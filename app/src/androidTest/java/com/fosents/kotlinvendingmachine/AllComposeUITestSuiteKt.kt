package com.fosents.kotlinvendingmachine

import com.fosents.kotlinvendingmachine.ui.screen.CoinsFragmentTest
import com.fosents.kotlinvendingmachine.ui.screen.MaintenanceScreenTest
import com.fosents.kotlinvendingmachine.ui.screen.ProductsScreenTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    CoinsFragmentTest::class,
    MaintenanceScreenTest::class,
    ProductsScreenTest::class,
)
class AllComposeUITestSuiteKt
