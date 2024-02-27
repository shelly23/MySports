package persistence;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FirebaseHandler {

    private static final String DATABASE_ADDRESS = "https://mysports-b4b34-default-rtdb.europe-west1.firebasedatabase.app/";
    private static final String STORAGE_ADDRESS = "gs://mysports-b4b34.appspot.com";

    // TODO: security, token or user/password

    public static boolean first = true;

    public static DatabaseReference getDatabase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance(DATABASE_ADDRESS);
        if (first) {
            database.getReference().child("keepalive").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            first = false;
        }
        return database.getReference();
    }

    public static StorageReference getStorage() {
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance(STORAGE_ADDRESS);
        return firebaseStorage.getReference();
    }

}
