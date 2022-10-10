package seedu.duke;

import seedu.duke.command.Command;
import seedu.duke.command.CommandAddClient;
import seedu.duke.command.CommandDeleteClient;
import seedu.duke.command.CommandUndefined;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Parser {
    public static final int ADD_CLIENT_FLAG_SIZE = 4;

    public Command parseCommand(String input, ClientList clientList) throws EmptyCommandAddDetailException,
            UndefinedSubCommandAddTypeException, EmptyClientDetailException, MissingClientFlagException,
            IncorrectAddClientFlagOrderException, MissingClientDetailException, InvalidContactNumberException,
            InvalidEmailException, InvalidBudgetFormatException, UndefinedSubCommandDeleteTypeException,
            EmptyCommandDeleteDetailException, InvalidClientIndexDeleteException {
        ArrayList<String> processedCommandDetails = partitionCommandTypeAndDetails(input);
        String commandType    = processedCommandDetails.get(0);
        String commandDetails = processedCommandDetails.get(1);
        switch (commandType) {
        case ("add"):
            checkForEmptyCommandAddDetails(commandDetails);
            ArrayList<String> processedAddCommandDetails = partitionCommandTypeAndDetails(commandDetails);
            String subCommandType = processedAddCommandDetails.get(0);
            String clientOrPropertyDescriptions = processedAddCommandDetails.get(1);

            if (subCommandType.equals("-client")) {
                return prepareForCommandAddClient(clientOrPropertyDescriptions);
            } else {
                throw new UndefinedSubCommandAddTypeException();
            }
        case ("delete"):
            checkForEmptyCommandDeleteDetails(commandDetails);
            ArrayList<String> processedDeleteCommandDetails = partitionCommandTypeAndDetails(commandDetails);
            String subDeleteCommandType = processedDeleteCommandDetails.get(0);
            int clientIndexToDelete = getClientIndex(processedDeleteCommandDetails.get(1)) - 1;

            if (subDeleteCommandType.equals("-client")) {
                return prepareForCommandDeleteClient(clientIndexToDelete, clientList);
            } else {
                throw new UndefinedSubCommandDeleteTypeException();
            }
        default:
            return new CommandUndefined();
        }
    }

    private ArrayList<String> partitionCommandTypeAndDetails(String fullCommandDetails) {
        String[] inputDetails = fullCommandDetails.trim().split(" ", 2);
        // This is the type of command/sub-command that will be executed
        String commandType    = inputDetails[0];
        String commandDetails = fullCommandDetails.replaceFirst(commandType, "").trim();
        ArrayList<String> processedCommandDetails = new ArrayList<>();
        processedCommandDetails.add(commandType);
        processedCommandDetails.add(commandDetails);
        return processedCommandDetails;
    }

    private void checkForEmptyCommandAddDetails(String commandAddDetails) throws EmptyCommandAddDetailException {
        boolean isEmptyCommandAddDetail = checkForEmptyDetail(commandAddDetails);
        if (isEmptyCommandAddDetail) {
            throw new EmptyCommandAddDetailException();
        }
    }

    private void checkForEmptyCommandDeleteDetails(String commandAddDetails) throws EmptyCommandDeleteDetailException {
        boolean isEmptyCommandAddDetail = checkForEmptyDetail(commandAddDetails);
        if (isEmptyCommandAddDetail) {
            throw new EmptyCommandDeleteDetailException();
        }
    }

    private Command prepareForCommandAddClient(String rawClientDescriptions) throws EmptyClientDetailException,
            MissingClientFlagException, IncorrectAddClientFlagOrderException, MissingClientDetailException,
            InvalidContactNumberException, InvalidEmailException, InvalidBudgetFormatException {
        checkForEmptyAddClientDetails(rawClientDescriptions);
        try {
            ArrayList<String> clientDetails = processClientDetails(rawClientDescriptions);
            validateClientDetails(clientDetails);
            return new CommandAddClient(clientDetails);
        } catch (MissingFlagException e) {
            throw new MissingClientFlagException();
        } catch (IncorrectFlagOrderException e) {
            throw new IncorrectAddClientFlagOrderException();
        }
    }

    private void validateClientDetails(ArrayList<String> clientDetails) throws MissingClientDetailException,
            InvalidContactNumberException, InvalidEmailException, InvalidBudgetFormatException {
        //Checks for Missing Client Name, Contact Number, Budget Per Month (SGD)
        checkForMissingClientDetails(clientDetails.get(0));
        checkForMissingClientDetails(clientDetails.get(1));
        checkForMissingClientDetails(clientDetails.get(3));

        //Checks for Contact Number, Email and Budget Format
        checkForValidSingaporeContactNumber(clientDetails.get(1));
        boolean hasEmail = !clientDetails.get(2).isEmpty();
        if (hasEmail) {
            checkForValidEmail(clientDetails.get(2));
        }
        checkForBudgetNumberFormat(clientDetails.get(3));
    }

    private void checkForValidSingaporeContactNumber(String clientContactNumber) throws InvalidContactNumberException {
        boolean hasValidContactNumber = checkForDetailFormat("^[689]\\d{7}$", clientContactNumber);
        if (!hasValidContactNumber) {
            throw new InvalidContactNumberException();
        }
    }

    private void checkForValidEmail(String clientEmail) throws InvalidEmailException {
        //General Email Regex (RFC 5322 Official Standard)
        String regex = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\"
                + "x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-"
                + "9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-"
                + "9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0"
                + "c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
        boolean hasValidContactNumber = checkForDetailFormat(regex, clientEmail);
        if (!hasValidContactNumber) {
            throw new InvalidEmailException();
        }
    }

    private void checkForBudgetNumberFormat(String budget) throws InvalidBudgetFormatException {
        //Accepts only positive whole number
        String regex = "^[1-9]\\d*$";
        boolean hasValidBudgetNumberFormat = checkForDetailFormat(regex, budget);
        if (!hasValidBudgetNumberFormat) {
            throw new InvalidBudgetFormatException();
        }
    }

    private boolean checkForDetailFormat(String regex, String detail) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(detail);
        return matcher.matches();
    }

    private void checkForMissingClientDetails(String clientDetail) throws MissingClientDetailException {
        boolean isEmptyDetail = checkForEmptyDetail(clientDetail);
        if (isEmptyDetail) {
            throw new MissingClientDetailException();
        }
    }

    private void checkForEmptyAddClientDetails(String commandAddDescriptions) throws EmptyClientDetailException {
        boolean isEmptyCommandAddDetail = checkForEmptyDetail(commandAddDescriptions);
        if (isEmptyCommandAddDetail) {
            throw new EmptyClientDetailException();
        }
    }

    private ArrayList<String> processClientDetails(String rawClientDetails) throws MissingFlagException,
            IncorrectFlagOrderException {
        String[] addClientFlags    = {"n/", "c/", "e/", "b/"};
        int[] addClientFlagIndexPositions = new int[ADD_CLIENT_FLAG_SIZE];

        for (int i = 0; i < addClientFlags.length; i++) {
            addClientFlagIndexPositions[i] = rawClientDetails.indexOf(addClientFlags[i]);
        }

        checkForMissingClientFlags(addClientFlagIndexPositions);
        checkForClientFlagsOrder(addClientFlagIndexPositions);
        return extractClientDetails(rawClientDetails, addClientFlagIndexPositions);
    }

    private void checkForMissingClientFlags(int[] addClientFlagIndexPositions) throws MissingFlagException {
        checkForEssentialAddFlag(addClientFlagIndexPositions[0]);
        checkForEssentialAddFlag(addClientFlagIndexPositions[1]);
        checkForEssentialAddFlag(addClientFlagIndexPositions[3]);
    }

    private void checkForClientFlagsOrder(int[] addClientFlagIndexPositions) throws IncorrectFlagOrderException {
        boolean hasEmail = (addClientFlagIndexPositions[2] != -1);
        checkForCorrectFlagOrder(addClientFlagIndexPositions[0], addClientFlagIndexPositions[1]);
        checkForCorrectFlagOrder(addClientFlagIndexPositions[1], addClientFlagIndexPositions[3]);
        if (hasEmail) {
            checkForCorrectFlagOrder(addClientFlagIndexPositions[1], addClientFlagIndexPositions[2]);
            checkForCorrectFlagOrder(addClientFlagIndexPositions[2], addClientFlagIndexPositions[3]);
        }
    }

    private ArrayList<String> extractClientDetails(String rawClientDetails, int[] addClientFlagIndexPositions) {
        boolean hasEmail = (addClientFlagIndexPositions[2] != -1);
        String clientContactNumber;
        String clientEmail = "";
        if (hasEmail) {
            clientContactNumber = extractDetail(rawClientDetails, addClientFlagIndexPositions[1] + 2,
                    addClientFlagIndexPositions[2]);
            clientEmail = extractDetail(rawClientDetails, addClientFlagIndexPositions[2] + 2,
                    addClientFlagIndexPositions[3]);
        } else {
            clientContactNumber = extractDetail(rawClientDetails, addClientFlagIndexPositions[1] + 2,
                    addClientFlagIndexPositions[3]);
        }
        String clientName = extractDetail(rawClientDetails, addClientFlagIndexPositions[0] + 2,
                addClientFlagIndexPositions[1]);
        String clientBudgetPerMonth = extractDetail(rawClientDetails, addClientFlagIndexPositions[3] + 2);

        ArrayList<String> processedClientDetails = new ArrayList<>();
        processedClientDetails.add(clientName.trim());
        processedClientDetails.add(clientContactNumber.trim());
        processedClientDetails.add(clientEmail.trim());
        processedClientDetails.add(clientBudgetPerMonth.trim());
        return processedClientDetails;
    }

    private void checkForEssentialAddFlag(int addClientFlagIndexes) throws MissingFlagException {
        boolean hasFlag = (addClientFlagIndexes != -1);
        if (!hasFlag) {
            throw new MissingFlagException();
        }
    }

    private void checkForCorrectFlagOrder(int flagPosition, int nextFlagPosition) throws IncorrectFlagOrderException {
        boolean hasCorrectOrder = (flagPosition < nextFlagPosition);
        if (!hasCorrectOrder) {
            throw new IncorrectFlagOrderException();
        }
    }

    private static String extractDetail(String rawClientDetails, int beginIndex) {
        return rawClientDetails.substring(beginIndex).trim();
    }

    private static String extractDetail(String rawClientDetails, int beginIndex, int endIndex) {
        return rawClientDetails.substring(beginIndex, endIndex).trim();
    }

    private boolean checkForEmptyDetail(String commandDetails) {
        return commandDetails.trim().isEmpty();
    }

    private int getClientIndex(String commandDetails) {
        return Integer.parseInt(commandDetails.trim());
    }

    private Command prepareForCommandDeleteClient(int clientIndex, ClientList clientList)
            throws InvalidClientIndexDeleteException {
        checkForInvalidClientIndexDelete(clientIndex, clientList);
        return new CommandDeleteClient(clientIndex);
    }

    private void checkForInvalidClientIndexDelete(int clientIndex, ClientList clientList)
            throws InvalidClientIndexDeleteException {
        int currentListSize = clientList.getCurrentListSize();
        if (clientIndex < 0 || clientIndex >= currentListSize) {
            throw new InvalidClientIndexDeleteException();
        }
    }
}