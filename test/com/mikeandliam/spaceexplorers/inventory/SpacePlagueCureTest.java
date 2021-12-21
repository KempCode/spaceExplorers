package com.mikeandliam.spaceexplorers.inventory;

import com.mikeandliam.spaceexplorers.CrewMember;
import com.mikeandliam.spaceexplorers.CrewMemberType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpacePlagueCureTest {

    @Test
    void useOn() {
        SpacePlagueCure cure = new SpacePlagueCure();
        CrewMember member = new CrewMember(CrewMemberType.MONKEY, "test");
        member.setHasSpacePlague(true);
        assertTrue(member.getHasSpacePlague());

        cure.useOn(member);
        assertFalse(member.getHasSpacePlague());
    }
}