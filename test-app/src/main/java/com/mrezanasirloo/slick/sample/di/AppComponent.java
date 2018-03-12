package com.mrezanasirloo.slick.sample.di;


import com.mrezanasirloo.slick.sample.activity.dagger.ActivitySimpleDagger;
import com.mrezanasirloo.slick.sample.activity.dagger.DaggerModule;
import com.mrezanasirloo.slick.sample.conductor.dagger.ControllerDagger;
import com.mrezanasirloo.slick.sample.cutstomview.dagger.CustomViewDagger;
import com.mrezanasirloo.slick.sample.fragment.dagger.FragmentDagger;
import com.mrezanasirloo.slick.sample.fragment.dagger.delegate.FragmentDelegateDagger;
import com.mrezanasirloo.slick.sample.fragmentsupport.dagger.FragmentSupportDagger;

import javax.inject.Singleton;

import dagger.Component;
import dagger.Subcomponent;

/**
 * @author : M.Reza.Nasirloo@gmail.com
 *         Created on: 2016-11-01
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    DaggerComponent add(DaggerModule mainModule);

    @Subcomponent(modules = DaggerModule.class)
    interface DaggerComponent {
        void inject(ActivitySimpleDagger activity);

        void inject(ControllerDagger controller);

        void inject(FragmentDagger fragment);

        void inject(FragmentDelegateDagger delegateDaggerSlickFragment);

        void inject(FragmentSupportDagger daggerFragment);

        void inject(CustomViewDagger daggerCustomView);
    }

}
