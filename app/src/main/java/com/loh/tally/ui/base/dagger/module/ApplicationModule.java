package com.loh.tally.ui.base.dagger.module;

import android.content.Context;
import android.content.res.Resources;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.loh.tally.TallyApp;
import com.loh.tally.domain.authentication.AuthenticationManager;
import com.loh.tally.domain.authentication.AuthenticationManagerImpl;
import com.loh.tally.domain.database.chat.ChatService;
import com.loh.tally.domain.database.chat.ChatServiceImpl;
import com.loh.tally.domain.database.modules.ModuleService;
import com.loh.tally.domain.database.modules.ModuleServiceImpl;
import com.loh.tally.domain.database.presentation.PresentationService;
import com.loh.tally.domain.database.presentation.PresentationServiceImpl;
import com.loh.tally.domain.database.user.UserService;
import com.loh.tally.domain.database.user.UserServiceImpl;
import com.loh.tally.domain.profanity.ProfanityChecker;
import com.loh.tally.domain.profanity.ProfanityCheckerImpl;
import com.loh.tally.ui.base.dagger.scope.ApplicationScope;
import com.squareup.otto.Bus;

import dagger.Module;
import dagger.Provides;

/**
 * File: ApplicationModule.java
 * Date: 10/03/2017
 * Created By: Liam O'Hanlon
 * Description: Details how all components scoped by {@link ApplicationScope} are provided.
 */
@Module
public class ApplicationModule {

    private final TallyApp application;

    public ApplicationModule(TallyApp application) {
        this.application = application;
    }

    @Provides
    @ApplicationScope
    public Context provideApplicationContext() {
        return this.application;
    }

    @Provides
    @ApplicationScope
    public Resources provideResources(Context context) {
        return context.getResources();
    }

    @Provides
    @ApplicationScope
    public Bus provideEventBus() {
        return new Bus();
    }

    @Provides
    @ApplicationScope
    public FirebaseAuth provideFirebaseAuth() {
        return FirebaseAuth.getInstance();
    }

    @Provides
    @ApplicationScope
    public FirebaseDatabase provideFirebaseDatabase() {
        return FirebaseDatabase.getInstance();
    }

    @Provides
    @ApplicationScope
    public UserService provideUserService(FirebaseDatabase firebaseDatabase) {
        return new UserServiceImpl(firebaseDatabase);
    }

    @Provides
    @ApplicationScope
    public ModuleService provideModuleService(FirebaseDatabase firebaseDatabase) {
        return new ModuleServiceImpl(firebaseDatabase);
    }

    @Provides
    @ApplicationScope
    public ChatService provideChatService(FirebaseDatabase firebaseDatabase) {
        return new ChatServiceImpl(firebaseDatabase);
    }

    @Provides
    @ApplicationScope
    public PresentationService providePresentationService(FirebaseDatabase firebaseDatabase) {
        return new PresentationServiceImpl(firebaseDatabase);
    }

    @Provides
    @ApplicationScope
    public AuthenticationManager provideAuthenticationManager(FirebaseAuth firebaseAuth, Bus bus, UserService userService) {
        return new AuthenticationManagerImpl(firebaseAuth, bus, userService);
    }

    @Provides
    @ApplicationScope
    public ProfanityChecker provideProfanityChecker() {
        return new ProfanityCheckerImpl();
    }
}
