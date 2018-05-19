package com.example.ryanbrennan.ad340assignment1;

import com.example.ryanbrennan.ad340assignment1.Match;
import com.example.ryanbrennan.ad340assignment1.FirebaseMatchesModel;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.function.Consumer;

public class FirebaseMatchesViewModel {
    private FirebaseMatchesModel matchesModel;

    public FirebaseMatchesViewModel(){
        matchesModel = new FirebaseMatchesModel();
    }

    public void addMatch(Match item) {
        matchesModel.addMatch(item);
    }

    public void getMatches(Consumer<ArrayList<Match>> responseCallback) {
        matchesModel.getMatches(
                (DataSnapshot dataSnapshot) -> {
                    ArrayList<Match> matches = new ArrayList<>();
                    for (DataSnapshot matchSnapshot : dataSnapshot.getChildren()) {
                        Match item = matchSnapshot.getValue(Match.class);
                        assert item != null;
                        item.uid = matchSnapshot.getKey();
                        matches.add(item);
                    }
                    responseCallback.accept(matches);
                },
                (databaseError -> System.out.println("Error reading Match Items: " + databaseError))
        );
    }

    public void updateMatch(Match item) {
        matchesModel.updateMatchById(item);
    }

    public void clear() {
        matchesModel.clear();
    }
}
