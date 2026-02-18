package com.fosents.kotlinvendingmachine

import com.fosents.kotlinvendingmachine.domain.usecase.CheckOutOfOrderUseCaseTest
import com.fosents.kotlinvendingmachine.domain.usecase.ExecutePurchaseOrderUseCaseTest
import com.fosents.kotlinvendingmachine.domain.usecase.GetAvailableProductsUseCaseTest
import com.fosents.kotlinvendingmachine.domain.usecase.GetProductAndCoinsUseCaseTest
import com.fosents.kotlinvendingmachine.domain.usecase.InsertUserCoinsUseCaseTest
import com.fosents.kotlinvendingmachine.domain.usecase.ManageVendingIdUseCaseTest
import com.fosents.kotlinvendingmachine.domain.usecase.ResetCoinsUseCaseTest
import com.fosents.kotlinvendingmachine.domain.usecase.ResetProductsUseCaseTest
import com.fosents.kotlinvendingmachine.domain.usecase.ReturnChangeUseCaseTest
import com.fosents.kotlinvendingmachine.domain.usecase.SyncRemoteDataUseCaseTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    CheckOutOfOrderUseCaseTest::class,
    ExecutePurchaseOrderUseCaseTest::class,
    GetAvailableProductsUseCaseTest::class,
    GetProductAndCoinsUseCaseTest::class,
    InsertUserCoinsUseCaseTest::class,
    ManageVendingIdUseCaseTest::class,
    ResetCoinsUseCaseTest::class,
    ResetProductsUseCaseTest::class,
    ReturnChangeUseCaseTest::class,
    SyncRemoteDataUseCaseTest::class,
)
class AllUnitTestSuiteKt
