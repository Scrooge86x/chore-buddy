import com.example.chore_buddy.users.User
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase

object UserRepository {
    private val firestore: FirebaseFirestore = Firebase.firestore
    private const val USERS_COLLECTION = "users"

    interface UserCallback {
        fun onSuccess(user: User?)
        fun onFailure(e: Exception)
    }

    interface UsersListCallback {
        fun onSuccess(users: List<User>)
        fun onFailure(e: Exception)
    }

    interface CreateUserCallback {
        fun onSuccess()
        fun onFailure(e: Exception)
    }

    fun createUser(user: User, callback: CreateUserCallback) {
        firestore.collection(USERS_COLLECTION)
            .document(user.id)
            .set(user)
            .addOnSuccessListener {
                callback.onSuccess()
            }
            .addOnFailureListener { e ->
                callback.onFailure(e)
            }
    }

    fun getUserByUid(uid: String, callback: UserCallback) {
        firestore.collection(USERS_COLLECTION)
            .document(uid)
            .get()
            .addOnSuccessListener { documentSnapshot ->
                val user = documentSnapshot.toObject<User>()
                callback.onSuccess(user)
            }
            .addOnFailureListener { e ->
                callback.onFailure(e)
            }
    }

    fun getUsersWithSameGroupId(currentUserGroupId: String, callback: UsersListCallback) {
        firestore.collection(USERS_COLLECTION)
            .whereEqualTo("groupId", currentUserGroupId)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val users = querySnapshot.documents.mapNotNull { it.toObject<User>() }
                callback.onSuccess(users)
            }
            .addOnFailureListener { e ->
                callback.onFailure(e)
            }
    }
}