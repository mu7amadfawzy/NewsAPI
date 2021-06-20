package com.tempo.news.di.annotations

import androidx.lifecycle.ViewModel
import dagger.MapKey
import kotlin.reflect.KClass

/**
 * a ViewModelKey annotation which when used on provider methods,
 * basically says that the services returned by these methods must be put into special Map.
 * The keys in this Map will be of type KClass<out ViewModel>
 * and the values will be of type <? extends ViewModel> (subclass of ViewModel).
 * **/
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@MapKey
internal annotation class ViewModelKey(val value: KClass<out ViewModel>)