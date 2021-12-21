package com.mikeandliam.spaceexplorers;

import com.mikeandliam.spaceexplorers.inventory.CrewUsableInventoryItem;
import com.mikeandliam.spaceexplorers.inventory.FoodItem;
import com.mikeandliam.spaceexplorers.inventory.InventoryItem;
import com.mikeandliam.spaceexplorers.inventory.MedicalItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;


/**
 * Does all the prompting/input for running the game in console made.
 * No game logic should be in this class, only prompting and sending the data to the gameEnvironment
 * (which handles all the game logic)
 */
public class CommandLineManager {
    private Scanner inputScanner;
    private GameEnvironment environment;

    /**
     * This is just how the program reads the input from the command line.
      */
    public CommandLineManager() {
        inputScanner = new Scanner(System.in);
    }

    /**
     * Starts running the command line version of the game. Initiates game environment.
     * Creates the inital console menu for the game.
      */
    public void run() {

        System.out.println(GameEnvironment.startText);

        //use the following line if you want to manually input crew/ship names etc
        environment = createEnvironment();

        //create a dummy crew/ship for testing so you don't have to input it each time
//        environment = new GameEnvironment(
//                new ArrayList<>(Arrays.asList(
//                        new CrewMember(CrewMemberType.CONGOLESE_PYGMY_WARRIOR, "asd"),
//                        new CrewMember(CrewMemberType.ALIEN, "eee")
//                )),
//                new Ship("owo"),
//                33
//        );

        //whenever an environment happens, just print it to stdout
        environment.getEnvironmentEvent().addHandler(data ->
                System.out.println(data.getType().toString() + ": " + data.getText())
        );
        //also print crew events
        environment.getCrewEvent().addHandler(data ->
                System.out.println(String.format("(%s)%s:%s", data.getType(), data.getMember().getName(), data.getText()))
        );

        environment.getGameEndEvent().addHandler(e -> {
            System.out.println("Ship name: " + e.getShipName());
            System.out.println("Days to complete: " + e.getDaysToComplete());
            System.out.println("All parts found: " + (e.getAllPartsFound() ? "yes" : "no"));
            System.out.println("Score: " + e.getScore());
            System.exit(0);
        });

        //main loop, prompts for player action
        while (true) {
            System.out.println("Actions:\n" +
                    "0. view crew status\n" +
                    "1. view spaceship status\n" +
                    "2. visit space outpost\n" +
                    "3. perform crew actions\n" +
                    "4. next day");
            switch (intPrompt(">")) {
                case 0:
                    printCrewStatus();
                    break;
                case 1:
                    System.out.println(String.format("Spaceship %s, shield strength=%d", environment.getShip().getName(), environment.getShip().getShieldHealth()));
                    break;
                case 2:
                    visitOutpost();
                    break;
                case 3:
                    doCrewActions();
                    break;
                case 4:
                    environment.nextDay(true);
                    break;
            }
        }
    }

    /**
     * prompts the player to add crew members and create ship etc. to initialise the game environment
     *
     * @return the initialised game environment
     */
    private GameEnvironment createEnvironment() {
        ArrayList<CrewMember> crew = new ArrayList<>();

        while (true) {
            System.out.println("Current members:");
            crew.forEach((c) -> System.out.println(c.getType().getDisplayName()));

            System.out.println("\nSelect new crew member (f to finish):");
            for (int i = 0; i < CrewMemberType.values().length; i++) {
                System.out.println(i + ". " + CrewMemberType.values()[i].getDisplayName());
            }
            String p = prompt(">");
            if (p.equals("f")) {
                break;
            } else {
                crew.add(new CrewMember(CrewMemberType.values()[Integer.parseInt(p)], "placeholdername"));
            }
        }

        return new GameEnvironment(crew, new Ship(prompt("Ship name\n>")), intPrompt("num days:"));
    }


    private void visitOutpost() {
        //getItemsForSale().contains(item)
        //environment.getCurrentPlanet().attemptPurchase()
        //instructions on how to buy with use item..

        boolean tracker = true;

        while (tracker) {
            System.out.println("0. View your Inventory and Bank Account: \n" +
                    "1. View items for sale: \n" +
                    "2. Buy an Item: \n" +
                    "3. Enter the character 3 to exit to previous menu."
            );

            switch (intPrompt(">")) {
                case 0:
                    //show what objects the playe1er currently owns, their amounts and the amount of $ a player has
                    ArrayList<String> copyOfInv = new ArrayList<>();
                    for (int i = 0; i < environment.getInventory().size(); i++) {
                        copyOfInv.add(environment.getInventory().get(i).getDisplayName());
                    }
                    System.out.println("\nThis is your current inventory: " + copyOfInv + "\n" +
                            "This is the money you currently have: $" + environment.getMoney() + "\n");
                    break;
                case 1:
                    //View objects ie food and medical supplies for sale and their price + description.
                    System.out.println("The available food items for sale at this outpost: " + "\n");
                    printItemArrays(FoodItem.class);
                    System.out.println();
                    System.out.println("The available medical items for sale at this outpost: ");
                    printItemArrays(MedicalItem.class);
                    System.out.println();
                    break;
                case 2:
                    String a = prompt("Which item would you like to buy: ");
                    //Check input purchase
                    InventoryItem tester = checkPurchaseItem(a);
                    if (tester != null) {
                        environment.getCurrentPlanet().attemptPurchase(tester);
                    } else {
                        System.out.println("\nPlease enter a valid item to purchase. \n");
                    }

                    break;
                case 3:
                    //Menu exit option.
                    tracker = false;
                    break;
            }
        }

    }

    /**
     * loops through medical and food items in arrays, prevents writing code multiple times.
     */
    private void printItemArrays(Class<?> cls) {

        List<InventoryItem> Items = environment.getCurrentPlanet().getItemsForSale().stream()
                .filter(cls::isInstance).collect(Collectors.toList());

        for (int i = 0; i < Items.size(); i++) {
            System.out.println(Items.get(i).getDisplayName() + ": $" + Items.get(i)
                    .getPrice() + " > " + Items.get(i).getDescription());
        }
    }


    //Check the input for a purchase if it is a valid item and return the item to purchase.
    private InventoryItem checkPurchaseItem(String s) {
        ArrayList<InventoryItem> items = environment.getCurrentPlanet().getItemsForSale();
        for (int testint = 0; testint < items.size(); testint++) {
            if (s.equals(items.get(testint).getDisplayName())) {
                return items.get(testint);
            }
        }
        return null;
    }

    /**
     * allows you to choose a crew member and select crew actions for them to do
     */
    private void doCrewActions() {
        CrewMember selectedCrew = selectCrew();

        System.out.println("Choose crew action:\n" +
                "0. Use item\n" +
                "1. sleep\n" +
                "2. repair ship shields\n" +
                "3. search planet\n" +
                "4. pilot to a new planet");

        int actionNum = intPrompt(">");
        switch (actionNum) {
            case 0:
                //note unsafe cast here
                if (environment.getInventory().size() > 0) {
                    environment.crewUseItem(selectedCrew, (CrewUsableInventoryItem) selectItem());
                } else {
                    System.out.println("No items to use");
                }
                break;
            case 1:
                environment.crewSleep(selectedCrew);
                break;
            case 2:
                environment.crewRepairShields(selectedCrew);
                break;
            case 3:
                environment.crewSearchForParts(selectedCrew);
                break;
            case 4:
                //this prompts for a second crew member to pilot the ship by calling selectCrew() again
                environment.crewPilotToNewPlanet(selectedCrew, selectCrew());
                break;
        }

    }

    /**
     * prompts the player to select one of their crew members
     *
     * @return the selected crew member
     */
    private CrewMember selectCrew() {
        System.out.println("Choose crew member");
        for (int i = 0; i < environment.getCrew().size(); i++) {
            System.out.println(i + ". " + environment.getCrew().get(i).getType());
        }

        int crewIndex = intPrompt(">");
        return environment.getCrew().get(crewIndex);
    }

    /**
     * prompts the user to select an item from their inventory
     *
     * @return the selected InventoryItem
     */
    private InventoryItem selectItem() {
        for (int i = 0; i < environment.getInventory().size(); i++) {
            System.out.println(i + ". " + environment.getInventory().get(i).getDisplayName());
        }
        int itemIndex = intPrompt(">");
        return environment.getInventory().get(itemIndex);
    }

    /**
     * prints type, health, etc for each crew member
     */
    private void printCrewStatus() {
        System.out.println("Crew status:");
        for (CrewMember crewMember : environment.getCrew()) {
            System.out.println(String.format("%s, health=%d, hunger=%d, tiredness=%d",
                    crewMember.getType().getDisplayName(),
                    crewMember.getHealth(),
                    crewMember.getHunger(),
                    crewMember.getTiredness()
            ));
        }
    }

    /**
     * @param text the prompt text
     * @return the users reply to the prompt
     */
    private String prompt(String text) {
        System.out.print(text);
        return inputScanner.nextLine();
    }

    /**
     * same as prompt(text) but returns the inputted integer instead
     */
    private int intPrompt(String text) {
        return Integer.parseInt(prompt(text));
    }
}
