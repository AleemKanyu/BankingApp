package com.example.beginnerbanking;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;



@Database(entities = {Customer.class}, version = 1)
public abstract class BankDatabase extends RoomDatabase {

    public abstract CustomerDao customerDao();

    private static BankDatabase INSTANCE;

    public static synchronized BankDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    BankDatabase.class, "bank_db").build();
        }
        return INSTANCE;
    }
}
