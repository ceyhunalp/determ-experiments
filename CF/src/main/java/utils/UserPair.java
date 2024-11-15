package utils;

import java.util.Objects;

public class UserPair {

    public int firstID;
    public int secondID;

    public UserPair(int firstID, int secondID) {
        this.firstID = firstID;
        this.secondID = secondID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPair userPair = (UserPair) o;
        return firstID == userPair.firstID && secondID == userPair.secondID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstID, secondID);
    }
}
