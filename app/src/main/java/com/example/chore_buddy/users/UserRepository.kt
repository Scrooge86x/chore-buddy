import com.example.chore_buddy.users.User
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

object UserRepository {
    private val firestore: FirebaseFirestore by lazy { Firebase.firestore }
    private const val USERS_COLLECTION = "users"

    suspend fun createUser(user: User): Result<Unit> {
        return try {
            firestore.collection(USERS_COLLECTION)
                .document(user.id)
                .set(user)
                .await()
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    suspend fun getUserByUid(uid: String): Result<User?> {
        return try {
            val document = firestore.collection(USERS_COLLECTION)
                .document(uid)
                .get()
                .await()
            Result.Success(document.toObject<User>())
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    suspend fun getUsersWithSameGroupId(currentUserGroupId: String): Result<List<User>> {
        return try {
            val querySnapshot = firestore.collection(USERS_COLLECTION)
                .whereEqualTo("groupId", currentUserGroupId)
                .get()
                .await()
            val users = querySnapshot.documents.mapNotNull { it.toObject<User>() }
            Result.Success(users)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    sealed class Result<out T> {
        data class Success<out T>(val data: T) : Result<T>()
        data class Error(val exception: Exception) : Result<Nothing>()
    }
}