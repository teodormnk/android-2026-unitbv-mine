package cst.unitbvfmi2026.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class AuthState(
    val isLoading: Boolean = false,
    val errorMsg: String? = null
)
// data class = clasa specializata pe RETENTIA DATELOR
// la marcarea unei clase ca fiind de tip "data", compilatorul genereaza automat cod standard
// "boilerplate" care ar fi trebuit scris manual
// exemple:
// ".equals()", "/hashCode()" - pt compararea obiectelor in baza valorilor si nu a adreselor (de memorie)
// ".toString()" - returneaza un string citibil din datele retinute, pe caz concret (ex.): "AuthState(isLoading=true, errorMsg=null)"
// ".copy()" - permite creearea unei noi instante a unui obiect in timp ce schimbi doar anumite campuri (NECESAR PT UNIDIR DATA FLOW)
// functii tip ".componentN()" - permite "declararea destructurata" (ex.): val (loading, error) = state
// de ce trebuie aici?:
// pt a avea un indicator de stare al interfetei grafice la un moment dat
// de ce trebuie la Firebase?:
// 1. Autentificarea prin Firebase este ASINCRONA, astfel un data class permite UI-ului sa treaca prin 3 stari:
// - idle/initial: AuthState(isLoading=false, errorMsg = null)
// - loading: AuthState(isLoading=true) => UI-ul vede asta si afiseaza un CircularProgressIndicator
// - error: AuthState(isLoading = false, errorMsg = "Invalid credentials") - UI-ul vede asta si afiseaza un element Text cu mesajul de eroare / un Snackbar (= lightweight UI element that provides brief, actionable feedback about an operation by popping up a small message bar at the bottom of the screen, which automatically disappears after a short timeout or can be dismissed by the user.)
// 2. Folosind un data class ce are proprietati declarate de tip "val", care sunt IMUTABILE, asiguram faptul ca starea UI-ului NU POATE FI MODIFICATA "PE LA SPATE" intr-un mod ce o poate strica
// in loc sa schimbi o variabila din cadrul clasei, tu practic "emiti" o versiune noua a starii prin ".copy()"
// 3. poate fi utilizata si in scopul de a simplifica gestiunea rezultatelor, practic ca in Java, in ORM: trecerea de la obiecte simple Java la tupluri din SQL
class AuthViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val _authState = MutableStateFlow(AuthState())
    val authState: StateFlow<AuthState> = _authState

    fun login(email: String, password: String) {
        _authState.value = AuthState(true)
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener{//fara lambda
                Log.e("Login", "Success")
                _authState.value = AuthState()
            }
            .addOnFailureListener{ error -> //cu lambda
                Log.e("Login", "Failed")
                _authState.value = AuthState(errorMsg = error.message)
            }
    }

    fun register(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                Log.e("Register", "Success")
            }
            .addOnFailureListener {
                Log.e("Register", "Failed")
            }
    }
}