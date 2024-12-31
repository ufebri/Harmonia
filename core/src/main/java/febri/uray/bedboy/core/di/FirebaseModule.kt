package febri.uray.bedboy.core.di

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import febri.uray.bedboy.core.BuildConfig
import febri.uray.bedboy.core.data.source.firebase.AuthRepositoryImpl
import febri.uray.bedboy.core.data.source.firebase.FirebaseDataSource
import febri.uray.bedboy.core.domain.repository.AuthRepository
import febri.uray.bedboy.core.domain.usecase.auth.CheckUserLoggedInUseCase
import febri.uray.bedboy.core.domain.usecase.auth.SignInWithGoogleUseCase
import febri.uray.bedboy.core.domain.usecase.user.UserUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    fun provideFirebaseAuthDataSource(
        firebaseAuth: FirebaseAuth
    ): FirebaseDataSource {
        return FirebaseDataSource(firebaseAuth)
    }

    @Provides
    fun provideAuthRepository(
        firebaseAuthDataSource: FirebaseDataSource
    ): AuthRepository {
        return AuthRepositoryImpl(firebaseAuthDataSource)
    }

    // GoogleSignInOptions
    @Provides
    fun provideGoogleSignInOptions(): GoogleSignInOptions {
        return GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(BuildConfig.WEB_CLIENT_ID)
            .requestEmail()
            .build()
    }

    @Provides
    fun provideSignInWithGoogleUseCase(authRepository: AuthRepository): SignInWithGoogleUseCase {
        return SignInWithGoogleUseCase(authRepository)
    }

    @Provides
    fun provideCheckUserLoggedInUseCase(authRepository: AuthRepository): CheckUserLoggedInUseCase {
        return CheckUserLoggedInUseCase(authRepository)
    }

    @Provides
    @Singleton
    fun provideUserUseCase(authRepository: AuthRepository): UserUseCase {
        return UserUseCase(authRepository)
    }

    @Provides
    fun provideGoogleSignInClient(
        @ApplicationContext context: Context,
        gso: GoogleSignInOptions
    ): GoogleSignInClient {
        return GoogleSignIn.getClient(context, gso)
    }
}
