package design_pattern.iterator.example.iterators;

import design_pattern.iterator.example.profile.Profile;

public interface ProfileIterator {
    public boolean hasNext();

    public Profile getNext();

    public void reset();
}