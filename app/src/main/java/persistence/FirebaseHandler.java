package persistence;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FirebaseHandler {

    private static final String DATABASE_ADDRESS = "https://mysports-b4b34-default-rtdb.europe-west1.firebasedatabase.app/";
    private static final String STORAGE_ADDRESS = "gs://mysports-b4b34.appspot.com";

    // TODO: security, token or user/password

    public static DatabaseReference getDatabase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance(DATABASE_ADDRESS);
        return database.getReference();
    }

    public static StorageReference getStorage() {
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance(STORAGE_ADDRESS);
        return firebaseStorage.getReference();
    }

}
