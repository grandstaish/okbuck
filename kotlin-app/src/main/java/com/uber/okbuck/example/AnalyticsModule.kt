package com.uber.okbuck.example

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AnalyticsModule {

    @Provides
    @Singleton
    internal fun provideAnalytics(): Analytics = AnalyticsImpl()
}
