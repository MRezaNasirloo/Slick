package com.github.slick.sample.di;


import com.github.slick.sample.activity.dagger.DaggerModule;
import com.github.slick.sample.activity.dagger.ActivitySimpleDagger;
import com.github.slick.sample.conductor.dagger.ControllerDagger;
import com.github.slick.sample.cutstomview.dagger.DaggerCustomView;
import com.github.slick.sample.fragment.dagger.DaggerFragment;
import com.github.slick.sample.fragment.dagger.delegate.DelegateDaggerFragment;
import com.github.slick.sample.fragmentsupport.dagger.DaggerFragmentSupport;

import javax.inject.Singleton;

import dagger.Component;
import dagger.Subcomponent;

/**
 * @author : Pedramrn@gmail.com
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

        void inject(DaggerFragment fragment);

        void inject(DelegateDaggerFragment delegateDaggerSlickFragment);

        void inject(DaggerFragmentSupport daggerFragment);

        void inject(DaggerCustomView daggerCustomView);
    }

}
