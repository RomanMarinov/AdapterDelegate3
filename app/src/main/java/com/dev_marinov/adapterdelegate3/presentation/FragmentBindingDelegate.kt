package com.dev_marinov.adapterdelegate3.presentation

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

// для FragmentBindingDelegate
// https://github.com/sockeqwe/AdapterDelegates/blob/master/kotlin-dsl-viewbinding/src/main/java/com/hannesdorfmann/adapterdelegates4/dsl/ViewBindingListAdapterDelegateDsl.kt


class FragmentBindingDelegate<T : ViewBinding>(
    val fragment: Fragment,
    private val bindingFactory: (View) -> T
) : ReadOnlyProperty<Fragment, T>{

    private var binding: T? = null

    init {
        // LifecycleEventObserver - слушатель lifecycle
        fragment.lifecycle.addObserver(object : LifecycleEventObserver {

            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                if (event == Lifecycle.Event.ON_CREATE) {

                    fragment.viewLifecycleOwnerLiveData.observe(fragment, Observer { lifecycleOwner ->
                        lifecycleOwner.lifecycle.addObserver(object : LifecycleEventObserver {
                            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                                if (event == Lifecycle.Event.ON_DESTROY) {
                                    binding = null
                                }
                            }
                        })
                    })
                }
            }
        })
    }



    // метод для который научит делегат возвращать какое-то значение
    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        // возращает текущий биндинг
        val binding = binding
        if (binding != null) {
            return binding
        }

            // условие проверки для состояния фрагмента, можем ли мы работать с ui
        val lifecycle = fragment.viewLifecycleOwner.lifecycle
        // если фрагмент не инициализован, то бросаем эксепшн
        if (!lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)) {
            throw IllegalStateException("Should not initialize binding when fragment views are destroyed ")
        }

            // берет нашу фэктори передаем вью и инициализуем текущий биндинг
            // созданный с помощью фэктори
        return bindingFactory.invoke(thisRef.requireView()).also { this@FragmentBindingDelegate.binding = it }
    }

}

// .viewBinding - экстеншен который создает нам делегат
// в параметры передаем фэктори
fun <T : ViewBinding> Fragment.viewBinding(viewBindingFactory: (View) -> T) =
    FragmentBindingDelegate(this, viewBindingFactory)