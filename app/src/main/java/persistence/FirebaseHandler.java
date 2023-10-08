package persistence;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseHandler {

    private static final String DATABASE_ADDRESS = "https://mysports-b4b34-default-rtdb.europe-west1.firebasedatabase.app/";

    // TODO: security, token or user/password

    public static DatabaseReference getDatabase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance(DATABASE_ADDRESS);
        return database.getReference();
    }

}
