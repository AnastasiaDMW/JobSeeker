package com.example.jobseeker.di

import android.content.Context
import com.example.data.di.DataModule
import com.example.jobseeker.view.fragments.search_fragment.SearchFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DataModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(searchFragment: SearchFragment)

}