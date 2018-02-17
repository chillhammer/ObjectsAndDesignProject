package edu.gatech.a2340.shelterme.Model;

public enum UserType {
    ANONYMOUS,
    USER,
    SHELTER_OWNER,
    ADMIN;

    /**
     * Formats the enum's default name, making it properly capitalized and
     * replacing underscores with spaces
     * @return formatted string
     */
    @Override
    public String toString() {
        String name = this.name().substring(0,1);
        if (this.name().length() > 1) {
            for (int i = 1; i < this.name().length(); i++) {
                String charAt = this.name().substring(i, i + 1);
                if (charAt.equals("_"))
                    charAt = " ";
                name += charAt.toLowerCase();
            }
        }
        return name;
    }


    /**
     * Removes the anonymous user type from values()
     * @return values() minus the anonymous user type
     */
    public static UserType[] getValues() {
        UserType[] values = new UserType[values().length - 1];
        for (int i = 0; i < values.length; i++) {
            values[i] = values()[i + 1];
        }
        return values;
    }
}
