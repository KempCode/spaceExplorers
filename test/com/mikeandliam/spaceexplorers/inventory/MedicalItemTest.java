package com.mikeandliam.spaceexplorers.inventory;

import com.mikeandliam.spaceexplorers.CrewMember;
import com.mikeandliam.spaceexplorers.CrewMemberType;
import com.mikeandliam.spaceexplorers.Resources;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MedicalItemTest {

    @Test
    void useOn() {
        int healthRestore = 50;
        MedicalItem testItem = new MedicalItem(healthRestore, "test", "test", 0, Resources.INFO_ICON);
        CrewMember member = new CrewMember(CrewMemberType.MONKEY, "test");

        while (member.getHealth() > 0) {
            member.nextDay();
        }

        testItem.useOn(member);
        assertEquals(member.getHealth(), testItem.getHealthRestoration());
    }
}