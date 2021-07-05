package com.lichao666.design_pattern.iterator.example.social_networks;

import com.lichao666.design_pattern.iterator.example.iterators.ProfileIterator;

public interface SocialNetwork {
    public ProfileIterator createFriendsIterator(String profileEmail);

    public ProfileIterator createCoworkersIterator(String profileEmail);
}
