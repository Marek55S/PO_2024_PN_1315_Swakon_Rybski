package agh.ics.oop.model;

//Explanatory comment - to be removed after lab5 PR
//exact methods for this interface weren't mentioned. It would be great idea to add get position method to every world element, but
//as it's not mentioned in spec i won't implement it, because I don't want to implement features based on deducted spec and end up with broken program later on when my
//assumption turns out to be false
public interface WorldElement {
    public Vector2d getPosition();

    public String getResourceString();
}
