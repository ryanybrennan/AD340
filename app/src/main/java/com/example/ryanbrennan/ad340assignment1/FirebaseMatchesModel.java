package com.example.ryanbrennan.ad340assignment1;
import com.example.ryanbrennan.ad340assignment1.Match;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.function.Consumer;

public class FirebaseMatchesModel {
    private DatabaseReference mDatabase;
    private HashMap<DatabaseReference, ValueEventListener> listeners;

    public FirebaseMatchesModel() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        listeners = new HashMap<>();
    }

    public void addMatch(Match item) {
        DatabaseReference MatchesRef = mDatabase.child("matches");
        MatchesRef.push().setValue(item);
    }

    public void getMatches(Consumer<DataSnapshot> dataChangedCallback, Consumer<DatabaseError> dataErrorCallback) {
        DatabaseReference matchesRef = mDatabase.child("matches");
        ValueEventListener matchesListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataChangedCallback.accept(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                dataErrorCallback.accept(databaseError);
            }
        };
        matchesRef.addValueEventListener(matchesListener);
        listeners.put(matchesRef, matchesListener);
    }

    public void updateMatchById(Match item) {
        DatabaseReference matchesRef = mDatabase.child("matches");
        if(item != null){
            matchesRef.child(item.uid).setValue(item);
        }else{
            System.out.println("No object here!");
        }

    }

    public void clear() {
        // Clear all the listeners onPause
        listeners.forEach(Query::removeEventListener);
    }
}
